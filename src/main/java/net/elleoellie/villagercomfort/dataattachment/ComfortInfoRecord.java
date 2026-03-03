package net.elleoellie.villagercomfort.dataattachment;

public record ComfortInfoRecord(
        int bedroomSize,
        int bedsCount,
        int bedroomLight,

         boolean isWorkstationInBedroom,
         boolean hasBed,
         boolean hasWorkstation,

         int workplaceSize,
         int workstationsCount,
         int workplaceLight,
         int daysWithoutOutside,
         int bedWorkstationDistance,

         int todayOutsideTicks,
         int currentDay,
         int lastDayOutside,
         long lastRememberedWorkstationPos,
         int lastSleepDay,
         int lastZombieDay,

         int comfort,

        int configMaxBedroom,
        int configBedroomLight,
        int configMaxWorkplace,
        int configWorkplaceLight,
        int configBedWorkplaceDistance,
        int configBedsThreshold,
        int configWorkstationsThreshold
) {
}
