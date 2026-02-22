package net.elleoellie.villagercomfort.core.mixin;

import net.elleoellie.villagercomfort.comfort.BedroomComfortValues;
import net.elleoellie.villagercomfort.comfort.ComfortHelper;
import net.elleoellie.villagercomfort.comfort.WorkplaceComfortValues;
import net.elleoellie.villagercomfort.Config;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.level.LightLayer;
import org.slf4j.Logger;
import java.util.Optional;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.elleoellie.villagercomfort.dataattachment.ComfortData.*;

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
}
