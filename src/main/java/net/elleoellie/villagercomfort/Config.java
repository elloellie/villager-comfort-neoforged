package net.elleoellie.villagercomfort;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;

// An example config class. This is not required, but it's a good idea to have one to keep your config organized.
// Demonstrates how to use Neo's config APIs
public class Config {

    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

//    private static final ModConfigSpec.ConfigValue<List<? extends String>> ITEM_STRINGS = BUILDER
//            .comment("When right clicking any villager with this item in your hand, it will reveal their comfort values.")
//            .defineList("comfortValueDisplayItem", List.of("minecraft:spyglass"), Config::validateItemName);

    // Price Values
    public static final ModConfigSpec.IntValue MAX_COMFORT_RANGE = BUILDER
            .comment("Set the comfort value boundaries to be -maxComfortRange to maxComfortRange. Allows for more precise customizability! Impact on price is linear between 0 and maxComfortRange")
            .translation("config.villagercomfort.max_comfort_range")
            .defineInRange("maxComfortRange", 1000, 0, Integer.MAX_VALUE);
    public static final ModConfigSpec.IntValue MIN_COMFORT_PRICE_REDUCTION = BUILDER
            .comment("Min percentage of original price that an item can be reduced to. For example, a value of 70 means items can at most be reduced to 70% of their original price. A value of 0 means all items can reach 1 emerald in price. A value of 100 means items cannot go down in price. Only positive comfort values reduce item prices, and maximum comfort means lowest prices! :D")
            .translation("config.villagercomfort.min_comfort_price_reduction")
            .defineInRange("minComfortPriceReduction", 10, 0, 100);
    public static final ModConfigSpec.IntValue MAX_COMFORT_PRICE_INCREASE = BUILDER
            .comment("Max percentage of original price that an item can go up in price. For example, a value of 0 means items cannot go up in price, and a value of 100 means items can at most double in price. 200 means tripling, etc.")
            .translation("config.villagercomfort.max_comfort_range_price_increase")
            .defineInRange("maxComfortPriceIncrease", 300, 0, Integer.MAX_VALUE);

    // Max Volumes
    public static final ModConfigSpec.IntValue MAX_BEDROOM_VOLUME = BUILDER
            .comment("Max room volume to take into account when during bedroom calculations (includes air, objects in room and the walls themselves)")
            .translation("config.villagercomfort.max_bedroom_volume")
            .defineInRange("maxBedroomVolume", 3000, 0, Integer.MAX_VALUE);
    public static final ModConfigSpec.IntValue MAX_WORKPLACE_VOLUME = BUILDER
            .comment("Max room volume to take into account when during workplace calculations (includes air, objects in room and the walls themselves)")
            .translation("config.villagercomfort.max_workplace_volume")
            .defineInRange("maxWorkplaceVolume", 3000, 0, Integer.MAX_VALUE);

    // Bedroom Values
    public static final ModConfigSpec.IntValue MAX_BEDROOM_SIZE = BUILDER
            .comment("Maximum bedroom size, at which comfort is the highest and can't increase further")
            .translation("config.villagercomfort.max_bedroom_size")
            .defineInRange("maxBedroomSize", 150, 0, Integer.MAX_VALUE);
    public static final ModConfigSpec.IntValue MAX_BEDROOM_SIZE_COMFORT = BUILDER
            .comment("Comfort value for when the bedroom is at maxBedroomSize")
            .translation("config.villagercomfort.max_bedroom_size_comfort")
            .defineInRange("maxBedroomSizeComfort", 400, 0, Integer.MAX_VALUE);
    public static final ModConfigSpec.IntValue MIN_BEDROOM_SIZE = BUILDER
            .comment("Minimum bedroom size, at which comfort is the worst and can't decrease further")
            .translation("config.villagercomfort.min_bedroom_size")
            .defineInRange("minBedroomSize", 1, 0, Integer.MAX_VALUE);
    public static final ModConfigSpec.IntValue MIN_BEDROOM_SIZE_COMFORT = BUILDER
            .comment("Comfort value for when the bedroom is at minBedroomSize")
            .translation("config.villagercomfort.min_bedroom_size_comfort")
            .defineInRange("minBedroomSizeComfort", -400, Integer.MIN_VALUE, 0);
    public static final ModConfigSpec.IntValue PREFERRED_BEDROOM_SIZE = BUILDER
            .comment("How much empty space (measured in air blocks) the villager would like to have in their bedroom. Building a bigger room will increase comfort, and a smaller room will decrease comfort.")
            .translation("config.villagercomfort.preferred_bedroom_size")
            .defineInRange("preferredBedroomSize", 27, 0, Integer.MAX_VALUE);
    public static final ModConfigSpec.IntValue BEDS_THRESHOLD = BUILDER
            .comment("Max number of beds that the villager is okay with having in their bedroom. Going above this number affects comfort according to comfortPerExtraBed")
            .translation("config.villagercomfort.beds_threshold")
            .defineInRange("bedsThreshold", 2, 1, Integer.MAX_VALUE);
    public static final ModConfigSpec.IntValue COMFORT_PER_BED = BUILDER
            .comment("Comfort impact from each bed above bedsThreshold")
            .translation("config.villagercomfort.comfort_per_bed")
            .defineInRange("comfortPerBed", -20, -2000, Integer.MAX_VALUE);
    public static final ModConfigSpec.IntValue PREFERRED_BEDROOM_LIGHT = BUILDER
            .comment("The sky light level the villager would like in their room (measured at the brightest air block in the room)")
            .translation("config.villagercomfort.preferred_bedroom_light")
            .defineInRange("preferredBedroomLight", 12, 0, 15);
    public static final ModConfigSpec.IntValue COMFORT_PER_LESS_BEDROOM_LIGHT = BUILDER
            .comment("Comfort impact for each light level the brightest air block in the bedroom has below preferredBedroomLight")
            .translation("config.villagercomfort.comfort_per_less_bedroom_light")
            .defineInRange("comfortPerLessBedroomLight", -25, Integer.MIN_VALUE, 0);
    public static final ModConfigSpec.IntValue COMFORT_PER_MORE_BEDROOM_LIGHT = BUILDER
            .comment("Comfort impact for each light level the brightest air block in the bedroom has above preferredBedroomLight")
            .translation("config.villagercomfort.comfort_per_more_bedroom_light")
            .defineInRange("comfortPerMoreBedroomLight", 0, Integer.MIN_VALUE, Integer.MAX_VALUE);
    public static final ModConfigSpec.IntValue WORKSTATION_IN_BEDROOM_COMFORT = BUILDER
            .comment("Comfort impact if the villager's workstation is in their bedroom")
            .translation("config.villagercomfort.workstation_in_bedroom_comfort")
            .defineInRange("workstationInBedroomComfort", -20, Integer.MIN_VALUE, Integer.MAX_VALUE);

    // Workplace Values
    public static final ModConfigSpec.IntValue MAX_WORKPLACE_SIZE = BUILDER
            .comment("Maximum workplace size, at which comfort is the highest and can't increase further")
            .translation("config.villagercomfort.max_workplace_size")
            .defineInRange("maxWorkplaceSize", 150, 0, Integer.MAX_VALUE);
    public static final ModConfigSpec.IntValue MAX_WORKPLACE_SIZE_COMFORT = BUILDER
            .comment("Comfort value for when the workplace is at maxWorkplaceSize")
            .translation("config.villagercomfort.max_workplace_size_comfort")
            .defineInRange("maxWorkplaceSizeComfort", 350, 0, Integer.MAX_VALUE);
    public static final ModConfigSpec.IntValue MIN_WORKPLACE_SIZE = BUILDER
            .comment("Minimum workplace size, at which comfort is the worst and can't decrease further")
            .translation("config.villagercomfort.min_workplace_size")
            .defineInRange("minWorkplaceSize", 1, 0, Integer.MAX_VALUE);
    public static final ModConfigSpec.IntValue MIN_WORKPLACE_SIZE_COMFORT = BUILDER
            .comment("Comfort value for when the workplace is at minWorkplaceSize")
            .translation("config.villagercomfort.min_workplace_size_comfort")
            .defineInRange("minWorkplaceSizeComfort", -300, Integer.MIN_VALUE, 0);
    public static final ModConfigSpec.IntValue PREFERRED_WORKPLACE_SIZE = BUILDER
            .comment("How much empty space (measured in air blocks) the villager would like to have at their workplace. Building a bigger room will increase comfort, and a smaller room will decrease comfort.")
            .translation("config.villagercomfort.preferred_workplace_size")
            .defineInRange("averageWorkplaceSize", 18, 0, Integer.MAX_VALUE);
    public static final ModConfigSpec.IntValue WORKSTATIONS_THRESHOLD = BUILDER
            .comment("Max number of workstations that the villager is okay with having present at their workplace (of the same type). Going above this number affects comfort according to comfortPerExtraWorkstation")
            .translation("config.villagercomfort.workstations_threshold")
            .defineInRange("workstationsThreshold", 6, 1, Integer.MAX_VALUE);
    public static final ModConfigSpec.IntValue COMFORT_PER_WORKSTATION = BUILDER
            .comment("Comfort impact from each workstation above workstationsThreshold")
            .translation("config.villagercomfort.comfort_per_workstation")
            .defineInRange("comfortPerExtraWorkstation", -20, Integer.MIN_VALUE, Integer.MAX_VALUE);
    public static final ModConfigSpec.IntValue PREFERRED_WORKPLACE_LIGHT = BUILDER
            .comment("The sky light level the villager would like at their workplace (measured at the brightest air block in the room)")
            .translation("config.villagercomfort.preferred_workplace_light")
            .defineInRange("preferredWorkplaceLight", 12, 0, 15);
    public static final ModConfigSpec.IntValue COMFORT_PER_LESS_WORKPLACE_LIGHT = BUILDER
            .comment("Comfort impact for each light level the brightest air block in the workplace has below preferredWorkplaceLight")
            .translation("config.villagercomfort.comfort_per_less_workplace_light")
            .defineInRange("comfortPerLessWorkplaceLight", -50, Integer.MIN_VALUE, 0);
    public static final ModConfigSpec.IntValue COMFORT_PER_MORE_WORKPLACE_LIGHT = BUILDER
            .comment("Comfort impact for each light level the brightest air block in the workplace has above preferredWorkplaceLight")
            .translation("config.villagercomfort.comfort_per_more_workplace_light")
            .defineInRange("comfortPerMoreWorkplaceLight", 0, Integer.MIN_VALUE, Integer.MAX_VALUE);

    // Bed and Workplace Distance Values
    public static final ModConfigSpec.IntValue MAX_BED_WORKPLACE_DISTANCE = BUILDER
            .comment("Maximum distance between bed and workplace, at which comfort is the highest and can't increase further")
            .translation("config.villagercomfort.max_bed_workplace_distance")
            .defineInRange("maxBedWorkplaceDistance", 20, 0, Integer.MAX_VALUE);
    public static final ModConfigSpec.IntValue MAX_BED_WORKPLACE_DISTANCE_COMFORT = BUILDER
            .comment("Comfort value for when the bed-workplace distance is at maxBedWorkplaceDistance")
            .translation("config.villagercomfort.max_bed_workplace_distance_comfort")
            .defineInRange("maxBedWorkplaceDistanceComfort", 0, Integer.MIN_VALUE, Integer.MAX_VALUE);
    public static final ModConfigSpec.IntValue MIN_BED_WORKPLACE_DISTANCE = BUILDER
            .comment("Comfort value for when the bed-workplace distance is at minBedWorkplaceDistance")
            .translation("config.villagercomfort.min_bed_workplace_distance_comfort")
            .defineInRange("minBedWorkplaceDistance", 1, 0, Integer.MAX_VALUE);
    public static final ModConfigSpec.IntValue MIN_BED_WORKPLACE_DISTANCE_COMFORT = BUILDER
            .comment("Minimum distance between bed and workplace, at which comfort is the lowest and can't decrease further")
            .translation("config.villagercomfort.min_bed_workplace_distance_comfort")
            .defineInRange("minBedWorkplaceDistanceComfort", -50, Integer.MIN_VALUE, Integer.MAX_VALUE);
    public static final ModConfigSpec.IntValue PREFERRED_BED_WORKPLACE_DISTANCE = BUILDER
            .comment("The distance the villager prefers between their bed and workplace (distance is measured in blocks, and is a straight lines that ignores walls). A longer distance will increase comfort, and a shorter distance will decrease comfort.")
            .translation("config.villagercomfort.preferred_bed_workplace_distance")
            .defineInRange("preferredDistanceBetweenBedAndWorkplace", 20, 1, Integer.MAX_VALUE);

    // Misc Comfort Values
    public static final ModConfigSpec.IntValue MINIMUM_TICKS_OUTSIDE = BUILDER
            .comment("Minimum time (measured in ticks) a villager would like to spend outside each day (in multiples of 100)")
            .translation("config.villagercomfort.minimum_ticks_outside")
            .defineInRange("minimumTicksOutside", 1200, 0, Integer.MAX_VALUE);
    public static final ModConfigSpec.IntValue COMFORT_PER_DAY_WITHOUT_OUTSIDE = BUILDER
            .comment("Comfort impact per each consecutive day the villager spends less than minimumTicksOutside time outside")
            .translation("config.villagercomfort.comfort_per_day_without_outside")
            .defineInRange("comfortPerDayWithoutOutside", -50, Integer.MIN_VALUE, Integer.MAX_VALUE);
    public static final ModConfigSpec.IntValue MAX_DAYS_WITHOUT_OUTSIDE = BUILDER
            .comment("Max number of consecutive days for which comfortPerDayWithoutOutside can apply")
            .translation("config.villagercomfort.max_days_without_outside")
            .defineInRange("maxDaysWithoutOutside", 6, 0, Integer.MAX_VALUE);
    public static final ModConfigSpec.IntValue COMFORT_PER_DAY_WITHOUT_PANIC = BUILDER
            .comment("Comfort impact per each consecutive day the villager isn't panicking (usually from zombies, but other hostile mobs can cause panic too! Including being hit by players)")
            .translation("config.villagercomfort.comfort_per_day_without_panic")
            .defineInRange("comfortPerDayWithoutPanic", 10, Integer.MIN_VALUE, Integer.MAX_VALUE);
    public static final ModConfigSpec.IntValue MAX_DAYS_WITHOUT_PANIC = BUILDER
            .comment("Max number of consecutive days for which comfortPerDayWithoutPanic can apply")
            .translation("config.villagercomfort.max_days_without_panic")
            .defineInRange("maxDaysWithoutPanic", 25, 0, Integer.MAX_VALUE);
    public static final ModConfigSpec.IntValue COMFORT_PER_DAY_WITHOUT_SLEEP = BUILDER
            .comment("Comfort impact per each consecutive day a villager spends without sleeping")
            .translation("config.villagercomfort.comfort_impact_per_day_without_sleep")
            .defineInRange("comfortPerDayWithoutSleep", -100, Integer.MIN_VALUE, Integer.MAX_VALUE);

    static final ModConfigSpec SPEC = BUILDER.build();

//    public static Set<Item> comfortValueDisplayItem;
//
//    private static boolean validateItemName(final Object obj) {
//        return obj instanceof String itemName && BuiltInRegistries.ITEM.containsKey(ResourceLocation.parse(itemName));
//    }
//
//    @SubscribeEvent
//    static void onLoad(final ModConfigEvent event)
//    {
//        comfortValueDisplayItem = ITEM_STRINGS.get().stream()
//                .map(itemName -> BuiltInRegistries.ITEM.get(ResourceLocation.parse(itemName)))
//                .collect(Collectors.toSet());
//    }
}
