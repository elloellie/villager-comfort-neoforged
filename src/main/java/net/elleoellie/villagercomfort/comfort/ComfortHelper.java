package net.elleoellie.villagercomfort.comfort;

import net.elleoellie.villagercomfort.VillagerComfort;
import net.elleoellie.villagercomfort.Config;
import net.elleoellie.villagercomfort.core.math.MathHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.npc.Villager;
import org.slf4j.Logger;
import com.mojang.logging.LogUtils;
import net.elleoellie.villagercomfort.dataattachment.ComfortData;

import static net.elleoellie.villagercomfort.dataattachment.ComfortData.*;

public class ComfortHelper
{
    private static Logger LOGGER = LogUtils.getLogger();

    public static int calculatePriceModifier(Villager villager, int basePrice)
    {
        int comfort = getVillagerComfort(villager);
        int new_price = basePrice;

        if (comfort >= 0) {
            int price_reduction = MathHelper.interpolate(
                    comfort,
                    0,
                    Config.MAX_COMFORT_RANGE.get().intValue(),
                    100,
                    Config.MIN_COMFORT_PRICE_REDUCTION.get().intValue()
            );

            LOGGER.info("price reduction: " + price_reduction);
            new_price = (int) Math.round((float) (basePrice * price_reduction) / 100);
        } else {
            int price_increase = MathHelper.interpolate(
                    comfort,
                    0,
                    - Config.MAX_COMFORT_RANGE.get().intValue(),
                    0,
                    Config.MAX_COMFORT_PRICE_INCREASE.get().intValue()
            );

            LOGGER.info("price increase: " + price_increase);
            new_price = basePrice + (int) Math.round((float) (basePrice * price_increase) / 100);
        }

        LOGGER.info("base price: " + basePrice + ", new price: " + new_price);
        return new_price - basePrice;
    }

    public static int getVillagerComfort(Villager villager)
    {
        int comfort = 0;
        int current_day = (int) (villager.level().getDayTime() / 24000L);

        // process last sleep and has_bed

        // This would only happen if player did /time set, and the villager hasn't slept since
        if (villager.getData(LAST_SLEEP_DAY) > current_day) {
            villager.setData(LAST_SLEEP_DAY, -1);
        }

        if (villager.getData(LAST_SLEEP_DAY) == -2) {
            villager.setData(LAST_SLEEP_DAY, villager.getData(LAST_SLEEP_DAY) -1);
        }

        int last_sleep_day = villager.getData(LAST_SLEEP_DAY);
        int days_without_sleep = current_day - last_sleep_day - 1;
       //villager.setData(DAYS_WITHOUT_SLEEP, current_day - last_sleep_day - 1);

        if (villager.getData(BEDS_COUNT) > 0 && days_without_sleep <= 0) {
            villager.setData(HAS_BED, true);
        }

        // process last_zombie_day
        if (villager.getData(LAST_ZOMBIE_DAY) > current_day) {
            villager.setData(LAST_ZOMBIE_DAY, -1);
        }

        if (villager.getData(LAST_ZOMBIE_DAY) == -2) {
            villager.setData(LAST_ZOMBIE_DAY, current_day - 1);
        }

        // process last_day_outside
        if (villager.getData(LAST_DAY_OUTSIDE) > current_day) {
            villager.setData(LAST_DAY_OUTSIDE, -1);
        }

        if (villager.getData(LAST_DAY_OUTSIDE) == -2) {
            villager.setData(LAST_DAY_OUTSIDE, current_day -1);
        }

        if (villager.getBrain().hasMemoryValue(MemoryModuleType.JOB_SITE)) {
            if (villager.getData(WORKSTATIONS_COUNT) == 0) {
                villager.getBrain().getMemory(MemoryModuleType.JOB_SITE).ifPresent(workstation -> {
//                        villager.setData(LAST_REMEMBERED_WORKSTATION_POS, workstation.pos().asLong());
                });

                WorkplaceComfortValues.setValuesToCap(villager);
            } else {
                villager.getBrain().getMemory(MemoryModuleType.JOB_SITE).ifPresent(workstation -> {
                    Long workstation_pos = workstation.pos().asLong();

                    if (workstation_pos != villager.getData(LAST_REMEMBERED_WORKSTATION_POS)) {
                        villager.setData(LAST_REMEMBERED_WORKSTATION_POS, workstation.pos().asLong());
                        WorkplaceComfortValues.setValuesToCap(villager);
                    }
                });
            }
        }

        villager.setData(HAS_WORKSTATION, villager.getBrain().hasMemoryValue(MemoryModuleType.JOB_SITE) && villager.getData(WORKSTATIONS_COUNT) > 0);

        // bedroom related
        if(villager.getData(HAS_BED)) {
            if (villager.getData(BEDROOM_SIZE) >= Config.PREFERRED_BEDROOM_SIZE.get().intValue()) {
                comfort += MathHelper.interpolate(
                        villager.getData(BEDROOM_SIZE),
                        Config.PREFERRED_BEDROOM_SIZE.get().intValue(),
                        Config.MAX_BEDROOM_SIZE.get().intValue(),
                        0,
                        Config.MAX_BEDROOM_SIZE_COMFORT.get().intValue()
                );
            } else {
                comfort += MathHelper.interpolate(
                        villager.getData(BEDROOM_SIZE),
                        Config.PREFERRED_BEDROOM_SIZE.get().intValue(),
                        Config.MIN_BEDROOM_SIZE.get().intValue(),
                        0,
                        Config.MIN_BEDROOM_SIZE_COMFORT.get().intValue()
                );
            }

            //beds
            if (villager.getData(BEDS_COUNT) > Config.BEDS_THRESHOLD.get().intValue()) {
                comfort += (villager.getData(BEDS_COUNT) - Config.BEDS_THRESHOLD.get().intValue()) * Config.COMFORT_PER_BED.get().intValue();
            }

            //bedroom light
            if (villager.getData(BEDROOM_LIGHT) > Config.PREFERRED_BEDROOM_LIGHT.get().intValue()) {
                comfort += (villager.getData(BEDROOM_LIGHT) - Config.PREFERRED_BEDROOM_LIGHT.get().intValue()) * Config.COMFORT_PER_MORE_BEDROOM_LIGHT.get().intValue();
            } else if (villager.getData(BEDROOM_LIGHT) < Config.PREFERRED_BEDROOM_LIGHT.get().intValue()) {
                comfort += (Config.PREFERRED_BEDROOM_LIGHT.get().intValue() - villager.getData(BEDROOM_LIGHT)) * Config.COMFORT_PER_LESS_BEDROOM_LIGHT.get().intValue();
            }

            //workstation in bedroom
            if(villager.getData(IS_WORKSTATION_IN_BEDROOM)) {
                comfort += Config.WORKSTATION_IN_BEDROOM_COMFORT.get().intValue();
            }
        }

        // Update in case we lost our workplace!

        //workplace related
        if(villager.getData(HAS_WORKSTATION)) {
            if (villager.getData(WORKPLACE_SIZE) >= Config.PREFERRED_WORKPLACE_SIZE.get().intValue()) {
                comfort += MathHelper.interpolate(
                        villager.getData(WORKPLACE_SIZE),
                        Config.PREFERRED_WORKPLACE_SIZE.get().intValue(),
                        Config.MAX_WORKPLACE_SIZE.get().intValue(),
                        0,
                        Config.MAX_WORKPLACE_SIZE_COMFORT.get().intValue()
                );
            } else {
                comfort += MathHelper.interpolate(
                        villager.getData(WORKPLACE_SIZE),
                        Config.PREFERRED_WORKPLACE_SIZE.get().intValue(),
                        Config.MIN_WORKPLACE_SIZE.get().intValue(),
                        0,
                        Config.MIN_WORKPLACE_SIZE_COMFORT.get().intValue()
                );
            }

            //workstations
            if(villager.getData(WORKSTATIONS_COUNT) > Config.WORKSTATIONS_THRESHOLD.get().intValue()) {
                comfort += (villager.getData(WORKSTATIONS_COUNT) - Config.WORKSTATIONS_THRESHOLD.get().intValue()) * Config.COMFORT_PER_WORKSTATION.get().intValue();
            }

            //workplace light
            if(villager.getData(WORKPLACE_LIGHT) > Config.PREFERRED_WORKPLACE_LIGHT.get().intValue()) {
                comfort += (villager.getData(WORKPLACE_LIGHT) - Config.PREFERRED_WORKPLACE_LIGHT.get().intValue()) * Config.COMFORT_PER_MORE_WORKPLACE_LIGHT.get().intValue();
            } else if(villager.getData(WORKPLACE_LIGHT) < Config.PREFERRED_WORKPLACE_LIGHT.get().intValue()) {
                comfort += (Config.PREFERRED_WORKPLACE_LIGHT.get().intValue() - villager.getData(WORKPLACE_LIGHT)) * Config.COMFORT_PER_LESS_WORKPLACE_LIGHT.get().intValue();
            }
        }

        // distance between a villager's bed and their workplace
        if(villager.getData(HAS_BED) && villager.getData(HAS_WORKSTATION)) {
            if (villager.getData(BED_WORKSTATION_DISTANCE) >= Config.PREFERRED_BED_WORKPLACE_DISTANCE.get().intValue()) {
                comfort += MathHelper.interpolate(
                        villager.getData(BED_WORKSTATION_DISTANCE),
                        Config.PREFERRED_BED_WORKPLACE_DISTANCE.get().intValue(),
                        Config.MAX_BED_WORKPLACE_DISTANCE.get().intValue(),
                        0,
                        Config.MAX_BED_WORKPLACE_DISTANCE_COMFORT.get().intValue()
                );
            } else {
                comfort += MathHelper.interpolate(
                        villager.getData(BED_WORKSTATION_DISTANCE),
                        Config.PREFERRED_BED_WORKPLACE_DISTANCE.get().intValue(),
                        Config.MIN_BED_WORKPLACE_DISTANCE.get().intValue(),
                        0,
                        Config.MIN_BED_WORKPLACE_DISTANCE_COMFORT.get().intValue()
                );
            }
        }

//             LOGGER.info("distance bed workplace: " + villager.getData(BED_WORKSTATION_DISTANCE) + " workplace size: " + villager.getData(WORKPLACE_SIZE) + " workstation count: " + villager.getData(WORKSTATIONSCOUNT) + " work light: " + villager.getData(WORKPLACE_LIGHT));

        // days without outside
        int days_without_outside = current_day - villager.getData(LAST_DAY_OUTSIDE) - 1;
        //villager.setData(DAYS_WITHOUT_OUTSIDE, ) = current_day - villager.getData(LAST_DAY_OUTSIDE) - 1;

        if (days_without_outside >= Config.MAX_DAYS_WITHOUT_OUTSIDE.get().intValue()) {
            comfort += Config.MAX_DAYS_WITHOUT_OUTSIDE.get().intValue() * Config.COMFORT_PER_DAY_WITHOUT_OUTSIDE.get().intValue();
        } else if (days_without_outside > 0) {
            comfort += days_without_outside * Config.COMFORT_PER_DAY_WITHOUT_OUTSIDE.get().intValue();
        }

        // days without zombie attacks
        int days_without_zombie = current_day - villager.getData(LAST_ZOMBIE_DAY) - 1;

        if (days_without_zombie >= Config.MAX_DAYS_WITHOUT_PANIC.get().intValue()) {
            comfort += Config.MAX_DAYS_WITHOUT_PANIC.get().intValue() * Config.COMFORT_PER_DAY_WITHOUT_PANIC.get().intValue();
        } else if (days_without_zombie > 0) {
            comfort += days_without_zombie * Config.COMFORT_PER_DAY_WITHOUT_PANIC.get().intValue();
        }

        // days without sleeping
        last_sleep_day = villager.getData(LAST_SLEEP_DAY);
        days_without_sleep = current_day - last_sleep_day - 1;

        if (days_without_sleep > 0) {
            comfort += days_without_sleep * Config.COMFORT_PER_DAY_WITHOUT_SLEEP.get().intValue();
        }

        comfort = Mth.clamp(comfort, - Config.MAX_COMFORT_RANGE.get().intValue(), Config.MAX_COMFORT_RANGE.get().intValue());
        villager.setData(COMFORT, comfort);
        comfort = villager.getData(COMFORT);

           // LOGGER.info("days_without_sleep: " + days_without_sleep + " days_without_zombie: " + days_without_zombie + " days_without_outside: " + days_without_outside);
           // LOGGER.info("has_bed: " + villager.getData(HAS_BED) +  " has_workplace: " + villager.getData(HAS_WORKSTATION));
           // LOGGER.info(" comfort: " + comfort);
            // LOGGER.info("Bedroom size: " + villager.getData(BEDROOM_SIZE));

        return comfort;
    }
}
