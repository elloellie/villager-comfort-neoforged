package net.elleoellie.villagercomfort.network;

import net.elleoellie.villagercomfort.dataattachment.ComfortInfoRecord;
import net.elleoellie.villagercomfort.network.packet.VillagerInfoPacket;
import net.elleoellie.villagercomfort.screens.ScreenHandler;
import net.neoforged.neoforge.network.handling.IPayloadContext;

// FROM SERVER, TO CLIENT
public class ServerPayloadHandler {
    // HERE WE ARE ON THE CLIENT
    public static void openMenuOnClient(VillagerInfoPacket villagerInfoPacket, IPayloadContext context){
        ComfortInfoRecord comfortInfoRecord = new ComfortInfoRecord(villagerInfoPacket.bedroomSize,
                villagerInfoPacket.bedsCount, villagerInfoPacket.bedroomLight,
                villagerInfoPacket.isWorkstationInBedroom, villagerInfoPacket.hasBed,
                villagerInfoPacket.hasWorkstation, villagerInfoPacket.workplaceSize,
                villagerInfoPacket.workstationsCount, villagerInfoPacket.workplaceLight,
                villagerInfoPacket.daysWithoutOutside,villagerInfoPacket.bedWorkstationDistance,
                villagerInfoPacket.todayOutsideTicks, villagerInfoPacket.currentDay,
                villagerInfoPacket.lastDayOutside, villagerInfoPacket.lastRememberedWorkstationPos,
                villagerInfoPacket.lastSleepDay, villagerInfoPacket.lastZombieDay, villagerInfoPacket.comfort,
                villagerInfoPacket.configMaxBedroom, villagerInfoPacket.configBedroomLight, villagerInfoPacket.configMaxWorkplace,
                villagerInfoPacket.configWorkplaceLight, villagerInfoPacket.configBedWorkplaceDistance, villagerInfoPacket.configBedsThreshold,
                villagerInfoPacket.configWorkstationsThreshold);
       // context.player().sendSystemMessage(Component.literal("COMFORT VALUE IS: " + comfortInfoRecord.comfort()));

        // ScreenHandler.openComfortScreen(comfortInfoRecord);

        ScreenHandler.openComfortScreen(comfortInfoRecord);

    }

}
