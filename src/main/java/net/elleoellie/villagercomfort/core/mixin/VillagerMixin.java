package net.elleoellie.villagercomfort.core.mixin;

import net.elleoellie.villagercomfort.comfort.BedroomComfortValues;
import net.elleoellie.villagercomfort.comfort.ComfortHelper;
import net.elleoellie.villagercomfort.comfort.WorkplaceComfortValues;
import net.elleoellie.villagercomfort.Config;
import net.elleoellie.villagercomfort.dataattachment.ComfortData;
import net.elleoellie.villagercomfort.util.ComfortTags;
import net.elleoellie.villagercomfort.util.TagChecker;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SpyglassItem;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import org.slf4j.Logger;
import java.util.Optional;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static net.elleoellie.villagercomfort.dataattachment.ComfortData.*;
import static net.elleoellie.villagercomfort.util.TagChecker.TagCheckerValidate;

@Mixin(Villager.class)
public class VillagerMixin
{
    @Shadow @Final private static Logger LOGGER;

    /**
     * Hook to calculate comfort values relative to a villager's bedroom
     * */
    @Inject(method = "startSleeping", at = @At("HEAD"))
    private void scanBedroomComfortValues(BlockPos pos, CallbackInfo ci)
    {
        Villager villager = ((Villager)(Object)this);

        if (villager.hasData(COMFORT)){
            int current_day = (int) (villager.level().getDayTime() / 24000L);

            // LOGGER.info("sleep: " + villager.level().getDayTime() + " " + last_sleep_day);

            BedroomComfortValues.setValuesToCap(villager, pos);
            villager.setData(LAST_SLEEP_DAY, current_day);
        };
    }

    /**
     * Hook to calculate comfort values relative to a villager's workplace
     * */
    @Inject(method = "playWorkSound", at = @At("HEAD"))
    private void scanWorkstationComfortValues(CallbackInfo ci)
    {
        Villager villager = ((Villager)(Object)this);

        // LOGGER.info("play work sound played");

        if (villager.hasData(COMFORT)){
                WorkplaceComfortValues.setValuesToCap(villager);
        }
    }

    @Inject(method = "tick", at = @At("HEAD"))
    private void checkSunlightTick(CallbackInfo ci)
    {
        Villager villager = ((Villager)(Object)this);

        if(!villager.level().isClientSide() && (villager.tickCount % 100 == 0))
        {
            if (villager.hasData(COMFORT)){
                int current_day = (int) (villager.level().getDayTime() / 24000L);

                if (villager.getData(CURRENT_DAY_FOR_MEASURING_OUTSIDE_TICKS) != current_day) {
                    villager.setData(TODAY_OUTSIDE_TICKS, 0) ;
                    villager.setData(CURRENT_DAY_FOR_MEASURING_OUTSIDE_TICKS, current_day);
                }

                if (villager.level().getBrightness(LightLayer.SKY, villager.blockPosition()) > 12) {
                    villager.setData(TODAY_OUTSIDE_TICKS, villager.getData(TODAY_OUTSIDE_TICKS) + 100);
                    // LOGGER.info("counting " + cap.today_outside_ticks + " outside ticks for day: " + cap.current_day_for_measuring_outside_ticks);

                    if (villager.getData(TODAY_OUTSIDE_TICKS) >= Config.MINIMUM_TICKS_OUTSIDE.get().intValue()) {
                        villager.setData(LAST_DAY_OUTSIDE, current_day);
                        // LOGGER.info("quota reached :D " + cap.today_outside_ticks);
                    }
                }

                if (villager.getBrain().isActive(Activity.PANIC)) {
                    villager.setData(LAST_ZOMBIE_DAY, current_day);
                }
            };
        }
    }


    /**
     * Applies the comfort modifier to the villager's trades
     * */
    @Inject(method = "updateSpecialPrices", at = @At("TAIL"))
    private void setComfortModifiers(Player player, CallbackInfo ci)
    {
        Villager villager = ((Villager)(Object)this);

        if (villager.hasData(COMFORT)) {

            for (MerchantOffer merchantOffer : villager.getOffers())
                merchantOffer.addToSpecialPriceDiff(ComfortHelper.calculatePriceModifier(villager, merchantOffer.getBaseCostA().getCount()));
        }
    }

    @Inject(method = "mobInteract", at = @At("RETURN"), cancellable = true)
    private void giveComfortDataOnInteract(Player player, InteractionHand hand, CallbackInfoReturnable<InteractionResult> callback){
        Villager villager = ((Villager)(Object)this);
       ItemStack itemstack = player.getItemInHand(hand);
        Item item = itemstack.getItem();

//        TagCheckerValidate(player.getItemInHand(hand));
//
//
//
//        Item itemForComfortData = Items.SPYGLASS;

        if (itemstack.is(ComfortTags.Items.COMFORT_DISPLAY_ITEMS)) {
            if (!villager.level().isClientSide) {
                player.sendSystemMessage(Component.literal("COMFORT: " + villager.getData(ComfortData.COMFORT)));
                player.sendSystemMessage(Component.literal("BEDROOM SIZE: " + villager.getData(BEDROOMSIZE)));
                player.sendSystemMessage(Component.literal("NUMBER OF BEDS IN BEDROOM: " + villager.getData(BEDSCOUNT)));
                player.sendSystemMessage(Component.literal("LIGHT LEVEL IN BEDROOM: " + villager.getData(BEDROOMLIGHT)));
                player.sendSystemMessage(Component.literal("IS WORKSTATION IN BEDROOM? " + villager.getData(ISWORKSTATIONINBEDROOM)));
                player.sendSystemMessage(Component.literal("WORKPLACE SIZE: " + villager.getData(WORKPLACESIZE)));
                player.sendSystemMessage(Component.literal("NUMBER OF WORKSTATIONS IN WORKPLACE: " + villager.getData(WORKSTATIONSCOUNT)));
                player.sendSystemMessage(Component.literal("LIGHT LEVEL IN WORKPLACE: " + villager.getData(WORKPLACELIGHT)));
               // player.sendSystemMessage(Component.literal("TICKS SPENT OUTSIDE: " + villager.getData(OUTSIDETICKS)));
                // player.sendSystemMessage(Component.literal("DAYS SINCE LAST OUTSIDE: " + villager.getData(DAYSWITHOUTOUTSIDE)));
                player.sendSystemMessage(Component.literal("DISTANCE BETWEEN BED AND WORKSTATION: " + villager.getData(BEDWORKSTATIONDISTANCE)));
               // player.sendSystemMessage(Component.literal("TICKS SPENT OUTSIDE TODAY: " + villager.getData(TODAY_OUTSIDE_TICKS)));
                // player.sendSystemMessage(Component.literal("WORKSTATION POSITION: " + villager.getData(LAST_REMEMBERED_WORKSTATION_POS)));
            } else {
                callback.setReturnValue(InteractionResult.FAIL);
            }
        }
    }

    @Inject(method = "startTrading", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/npc/Villager;setTradingPlayer(Lnet/minecraft/world/entity/player/Player;)V"), cancellable = true)
    private void preventTradingWhenHoldingItem (Player player, CallbackInfo callbackInfo) {
    ItemStack itemstack = player.getItemInHand(InteractionHand.MAIN_HAND);
//        Item item = itemstack.getItem();
//        Item itemForComfortData = Items.SPYGLASS;
        // TagCheckerValidate(player.getItemInHand(InteractionHand.MAIN_HAND));

        if (itemstack.is(ComfortTags.Items.COMFORT_DISPLAY_ITEMS)){
            callbackInfo.cancel();
        }
    }
}
