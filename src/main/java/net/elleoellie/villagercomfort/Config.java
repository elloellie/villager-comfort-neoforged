package net.elleoellie.villagercomfort;

import java.util.List;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.ModConfigSpec;

// An example config class. This is not required, but it's a good idea to have one to keep your config organized.
// Demonstrates how to use Neo's config APIs
public class Config {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    // Price Values
    public static final ModConfigSpec.IntValue MAX_COMFORT_RANGE = BUILDER
            .comment("Set the comfort value boundaries to be -maxComfortRange to maxComfortRange. Allows for more precise customizability! Impact on price is linear between 0 and maxComfortRange")
            .defineInRange("maxComfortRange", 1000, 0, Integer.MAX_VALUE);
    public static final ModConfigSpec.IntValue MIN_COMFORT_PRICE_REDUCTION = BUILDER
            .comment("Min percentage of original price that an item can be reduced to. For example, a value of 70 means items can at most be reduced to 70% of their original price. A value of 0 means all items can reach 1 emerald in price. A value of 100 means items cannot go down in price. Only positive comfort values reduce item prices, and maxium comfort means lowest prices! :D")
            .defineInRange("minComfortPriceReduction", 10, 0, Integer.MAX_VALUE);
    public static final ModConfigSpec.IntValue MAX_COMFORT_PRICE_INCREASE = BUILDER
            .comment("Max percentage of original price that an item can go up in price. For example, a value of 0 means items cannot go up in price, and a value of 100 means items can at most double in price. 200 means tripling, etc.")
            .defineInRange("maxComfortPriceReduction", 300, 0, Integer.MAX_VALUE);

    // Max Volumes
    public static final ModConfigSpec.IntValue MAX_BEDROOM_VOLUME = BUILDER
            .comment("Max room volume to take into account when during bedroom calculations (includes air, objects in room and the walls themselves)")
            .defineInRange("maxBedroomVolume", 3000, 0, Integer.MAX_VALUE);
    public static final ModConfigSpec.IntValue MAX_WORKPLACE_VOLUME = BUILDER
            .comment("Max room volume to take into account when during workplace calculations (includes air, objects in room and the walls themselves)")
            .defineInRange("maxBedroomVolume", 3000, 0, Integer.MAX_VALUE);

    // Bedroom Values
    public static final ModConfigSpec.IntValue MAX_BEDROOM_SIZE = BUILDER
            .comment("Maximum bedroom size, at which comfort is the highest and can't increase further")
            .defineInRange("maxBedroomSize", 150, 0, Integer.MAX_VALUE);
    public static final ModConfigSpec.IntValue MAX_BEDROOM_SIZE_COMFORT = BUILDER
            .comment("Comfort value for when the bedroom is at maxBedroomSize")
            .defineInRange("maxBedroomSizeComfort", 400, 0, Integer.MAX_VALUE);
    public static final ModConfigSpec.IntValue MIN_BEDROOM_SIZE = BUILDER
            .comment("Minimum bedroom size, at which comfort is the worst and can't decrease further")
            .defineInRange("minBedroomSize", 1, 0, Integer.MAX_VALUE);
    public static final ModConfigSpec.IntValue MIN_BEDROOM_SIZE_COMFORT = BUILDER
            .comment("Comfort value for when the bedroom is at minBedroomSize")
            .defineInRange("minBedroomSizeComfort", -400, Integer.MIN_VALUE, 0);
    public static final ModConfigSpec.IntValue PREFERRED_BEDROOM_SIZE = BUILDER
            .comment("How much empty space (measured in air blocks) the villager would like to have in their bedroom. Building a bigger room will increase comfort, and a smaller room will decrease comfort.")
            .defineInRange("maxBedroomSize", 27, 0, Integer.MAX_VALUE);
    public static final ModConfigSpec.IntValue BEDS_THRESHOLD = BUILDER
            .comment("Max number of beds that the villager is okay with having in their bedroom. Going above this number affects comfort according to comfortPerExtraBed")
            .defineInRange("bedsThreshold", 2, 1, Integer.MAX_VALUE);
    public static final ModConfigSpec.IntValue COMFORT_PER_BED = BUILDER
            .comment("Comfort impact from each bed above bedsThreshold")
            .defineInRange("bedsThreshold", -20, -2000, Integer.MAX_VALUE);
    public static final ModConfigSpec.IntValue PREFERRED_BEDROOM_LIGHT = BUILDER
            .comment("The sky light level the villager would like in their room (measured at the brightest air block in the room)")
            .defineInRange("preferredBedroomLight", 12, 0, 15);
    public static final ModConfigSpec.IntValue COMFORT_PER_LESS_BEDROOM_LIGHT = BUILDER
            .comment("Comfort impact for each light level the brightest air block in the bedroom has below preferredBedroomLight")
            .defineInRange("comfortPerLessBedroomLight", -25, Integer.MIN_VALUE, 0);
    public static final ModConfigSpec.IntValue COMFORT_PER_MORE_BEDROOM_LIGHT = BUILDER
            .comment("Comfort impact for each light level the brightest air block in the bedroom has above preferredBedroomLight")
            .defineInRange("comfortPerMoreBedroomLight", 0, Integer.MIN_VALUE, Integer.MAX_VALUE);
    public static final ModConfigSpec.IntValue WORKSTATION_IN_BEDROOM_COMFORT = BUILDER
            .comment("Comfort impact if the villager's workstation is in their bedroom")
            .defineInRange("workstationInBedroomComfort", -20, Integer.MIN_VALUE, Integer.MAX_VALUE);

    // Workplace Values
    public static final ModConfigSpec.IntValue MAX_WORKPLACE_SIZE = BUILDER
            .comment("Maximum workplace size, at which comfort is the highest and can't increase further")
            .defineInRange("maxWorkplaceSize", 150, 0, Integer.MAX_VALUE);
    public static final ModConfigSpec.IntValue MAX_WORKPLACE_SIZE_COMFORT = BUILDER
            .comment("Comfort value for when the workplace is at maxWorkplaceSize")
            .defineInRange("maxWorkplaceSizeComfort", 350, 0, Integer.MAX_VALUE);
    public static final ModConfigSpec.IntValue MIN_WORKPLACE_SIZE = BUILDER
            .comment("Minimum workplace size, at which comfort is the worst and can't decrease further")
            .defineInRange("minWorkplaceSize", 1, 0, Integer.MAX_VALUE);
    public static final ModConfigSpec.IntValue MIN_WORKPLACE_SIZE_COMFORT = BUILDER
            .comment("Comfort value for when the workplace is at minWorkplaceSize")
            .defineInRange("minWorkplaceSizeComfort", -300, Integer.MIN_VALUE, 0);
    public static final ModConfigSpec.IntValue PREFERRED_WORKPLACE_SIZE = BUILDER
            .comment("How much empty space (measured in air blocks) the villager would like to have at their workplace. Building a bigger room will increase comfort, and a smaller room will decrease comfort.")
            .defineInRange("averageWorkplaceSize", 18, 0, Integer.MAX_VALUE);
    public static final ModConfigSpec.IntValue WORKSTATIONS_THRESHOLD = BUILDER
            .comment("Max number of workstations that the villager is okay with having present at their workplace (of the same type). Going above this number affects comfort according to comfortPerExtraWorkstation")
            .defineInRange("workstationsThreshold", 6, 1, Integer.MAX_VALUE);
    public static final ModConfigSpec.IntValue COMFORT_PER_WORKSTATION = BUILDER
            .comment("Comfort impact from each workstation above workstationsThreshold")
            .defineInRange("comfortPerExtraWorkstation", -20, Integer.MIN_VALUE, Integer.MAX_VALUE);
    public static final ModConfigSpec.IntValue PREFERRED_WORKPLACE_LIGHT = BUILDER
            .comment("The sky light level the villager would like at their workplace (measured at the brightest air block in the room)")
            .defineInRange("preferredWorkplaceLight", 12, 0, 15);
    public static final ModConfigSpec.IntValue COMFORT_PER_LESS_WORKPLACE_LIGHT = BUILDER
            .comment("Comfort impact for each light level the brightest air block in the workplace has below preferredWorkplaceLight")
            .defineInRange("comfortPerLessWorkplaceLight", -50, Integer.MIN_VALUE, 0);
    public static final ModConfigSpec.IntValue COMFORT_PER_MORE_WORKPLACE_LIGHT = BUILDER
            .comment("Comfort impact for each light level the brightest air block in the workplace has above preferredWorkplaceLight")
            .defineInRange("comfortPerMoreWorkplaceLight", 0, Integer.MIN_VALUE, Integer.MAX_VALUE);

    // Bed and Workplace Distance Values
    public static final ModConfigSpec.IntValue MAX_BED_WORKPLACE_DISTANCE = BUILDER
            .comment("Maximum distance between bed and workplace, at which comfort is the highest and can't increase further")
            .defineInRange("maxBedWorkplaceDistance", 6, 0, Integer.MAX_VALUE);
    public static final ModConfigSpec.IntValue MAX_BED_WORKPLACE_DISTANCE_COMFORT = BUILDER
            .comment("Comfort value for when the bed-workplace distance is at maxBedWorkplaceDistance")
            .defineInRange("maxBedWorkplaceDistanceComfort", 0, Integer.MIN_VALUE, Integer.MAX_VALUE);
    public static final ModConfigSpec.IntValue MIN_BED_WORKPLACE_DISTANCE = BUILDER
            .comment("Comfort value for when the bed-workplace distance is at minBedWorkplaceDistance")
            .defineInRange("minBedWorkplaceDistance", 1, 0, Integer.MAX_VALUE);
    public static final ModConfigSpec.IntValue MIN_BED_WORKPLACE_DISTANCE_COMFORT = BUILDER
            .comment("Minimum distance between bed and workplace, at which comfort is the lowest and can't decrease further")
            .defineInRange("minBedWorkplaceDistanceComfort", -50, Integer.MIN_VALUE, Integer.MAX_VALUE);
    public static final ModConfigSpec.IntValue PREFERRED_BED_WORKPLACE_DISTANCE = BUILDER
            .comment("The distance the villager prefers between their bed and workplace (distance is measured in blocks, and is a straight lines that ignores walls). A longer distance will increase comfort, and a shorter distance will decrease comfort.")
            .defineInRange("preferredDistanceBetweenBedAndWorkplace", 6, 1, Integer.MAX_VALUE);

    // Misc Comfort Values
    public static final ModConfigSpec.IntValue MINIMUM_TICKS_OUTSIDE = BUILDER
            .comment("Minimum time (measured in ticks) a villager would like to spend outside each day (in multiples of 100)")
            .defineInRange("minimumTicksOutside", 1200, 0, Integer.MAX_VALUE);
    public static final ModConfigSpec.IntValue COMFORT_PER_DAY_WITHOUT_OUTSIDE = BUILDER
            .comment("Comfort impact per each consecutive day the villager spends less than minimumTicksOutside time outside")
            .defineInRange("comfortPerDayWithoutOutside", -50, Integer.MIN_VALUE, Integer.MAX_VALUE);
    public static final ModConfigSpec.IntValue MAX_DAYS_WITHOUT_OUTSIDE = BUILDER
            .comment("Max number of consecutive days for which comfortPerDayWithoutOutside can apply")
            .defineInRange("maxDaysWithoutOutside", 6, 0, Integer.MAX_VALUE);
    public static final ModConfigSpec.IntValue COMFORT_PER_DAY_WITHOUT_PANIC = BUILDER
            .comment("Comfort impact per each consecutive day the villager isn't panicking (usually from zombies, but other hostile mobs can cause panic too! Including being hit by players)")
            .defineInRange("comfortPerDayWithoutPanic", 10, Integer.MIN_VALUE, Integer.MAX_VALUE);
    public static final ModConfigSpec.IntValue MAX_DAYS_WITHOUT_PANIC = BUILDER
            .comment("Max number of consecutive days for which comfortPerDayWithoutPanic can apply")
            .defineInRange("maxDaysWithoutPanic", 25, 0, Integer.MAX_VALUE);
    public static final ModConfigSpec.IntValue COMFORT_PER_DAY_WITHOUT_SLEEP = BUILDER
            .comment("Comfort impact per each consecutive day a villager spends without sleeping")
            .defineInRange("comfortPerDayWithoutSleep", -100, Integer.MIN_VALUE, Integer.MAX_VALUE);

    static final ModConfigSpec SPEC = BUILDER.build();

    private static boolean validateItemName(final Object obj) {
        return obj instanceof String itemName && BuiltInRegistries.ITEM.containsKey(ResourceLocation.parse(itemName));
    }
}
