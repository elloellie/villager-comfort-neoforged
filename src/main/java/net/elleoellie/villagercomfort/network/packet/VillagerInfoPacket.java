package net.elleoellie.villagercomfort.network.packet;

import net.elleoellie.villagercomfort.Config;
import net.elleoellie.villagercomfort.VillagerComfort;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.npc.Villager;

import static net.elleoellie.villagercomfort.dataattachment.ComfortData.*;

/* Essentially, the constructor is called here when the player right clicks on a villager
with an item in the Comfort Viewer tag. Then, this packet is sent as registered in ModEvents. It takes all this data
and sends it to the client, saving it to ComfortInfoRecord, and then opens ComfortScreen, passing along the record.
 */

public class VillagerInfoPacket implements CustomPacketPayload {
    public final int bedroomSize;
    public final int bedsCount;
    public final int bedroomLight;

    public final boolean isWorkstationInBedroom;
    public final boolean hasBed;
    public final boolean hasWorkstation;

    public final int workplaceSize;
    public final int workstationsCount;
    public final int workplaceLight;
    public final int daysWithoutOutside;
    public final int bedWorkstationDistance;

    public final int todayOutsideTicks;
    public final int currentDay;
    public final int lastDayOutside;
    public final long lastRememberedWorkstationPos;
    public final int lastSleepDay;
    public final int lastZombieDay;

    public final int comfort;

    //Config Values
    public final int configMaxBedroom;
    public final int configBedroomLight;
    public final int configMaxWorkplace;
    public final int configWorkplaceLight;
    public final int configBedWorkplaceDistance;
    public final int configBedsThreshold;
    public final int configWorkstationsThreshold;


    public VillagerInfoPacket(Villager villager) {
        this.bedroomSize = villager.getData(BEDROOM_SIZE);
        this.bedsCount = villager.getData(BEDS_COUNT);
        this.bedroomLight = villager.getData(BEDROOM_LIGHT);

        this.isWorkstationInBedroom = villager.getData(IS_WORKSTATION_IN_BEDROOM);
        this.hasBed = villager.getData(HAS_BED);
        this.hasWorkstation = villager.getData(HAS_WORKSTATION);

        this.workplaceSize = villager.getData(WORKPLACE_SIZE);
        this.workstationsCount = villager.getData(WORKSTATIONS_COUNT);
        this.workplaceLight = villager.getData(WORKPLACE_LIGHT);
        this.daysWithoutOutside = villager.getData(DAYS_WITHOUT_OUTSIDE);
        this.bedWorkstationDistance = villager.getData(BED_WORKSTATION_DISTANCE);

        this.todayOutsideTicks = villager.getData(TODAY_OUTSIDE_TICKS);
        this.currentDay = villager.getData(CURRENT_DAY);
        this.lastDayOutside = villager.getData(LAST_DAY_OUTSIDE);
        this.lastRememberedWorkstationPos = villager.getData(LAST_REMEMBERED_WORKSTATION_POS);
        this.lastSleepDay = villager.getData(LAST_SLEEP_DAY);
        this.lastZombieDay = villager.getData(LAST_ZOMBIE_DAY);

        this.comfort = villager.getData(COMFORT);

        this.configMaxBedroom = Config.MAX_BEDROOM_SIZE.get();
        this.configBedroomLight = Config.PREFERRED_BEDROOM_LIGHT.get();
        this.configMaxWorkplace = Config.MAX_WORKPLACE_SIZE.get();
        this.configWorkplaceLight = Config.PREFERRED_WORKPLACE_LIGHT.get();
        this.configBedWorkplaceDistance = Config.MAX_BED_WORKPLACE_DISTANCE.get();
        this.configBedsThreshold = Config.BEDS_THRESHOLD.get();
        this.configWorkstationsThreshold = Config.WORKSTATIONS_THRESHOLD.get();
    }

    public VillagerInfoPacket(FriendlyByteBuf buffer) {
        this.bedroomSize = buffer.readInt();
        this.bedsCount = buffer.readInt();
        this.bedroomLight = buffer.readInt();

        this.isWorkstationInBedroom = buffer.readBoolean();
        this.hasBed = buffer.readBoolean();
        this.hasWorkstation = buffer.readBoolean();

        this.workplaceSize = buffer.readInt();
        this.workstationsCount = buffer.readInt();
        this.workplaceLight = buffer.readInt();
        this.daysWithoutOutside = buffer.readInt();
        this.bedWorkstationDistance = buffer.readInt();

        this.todayOutsideTicks = buffer.readInt();
        this.currentDay = buffer.readInt();
        this.lastDayOutside = buffer.readInt();
        this.lastRememberedWorkstationPos = buffer.readLong();
        this.lastSleepDay = buffer.readInt();
        this.lastZombieDay = buffer.readInt();

        this.comfort = buffer.readInt();

        this.configMaxBedroom = buffer.readInt();
        this.configBedroomLight = buffer.readInt();
        this.configMaxWorkplace = buffer.readInt();
        this.configWorkplaceLight = buffer.readInt();
        this.configBedWorkplaceDistance = buffer.readInt();
        this.configBedsThreshold = buffer.readInt();
        this.configWorkstationsThreshold = buffer.readInt();
    }

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeInt(this.bedroomSize);
        buffer.writeInt(this.bedsCount);
        buffer.writeInt(this.bedroomLight);

        buffer.writeBoolean(this.isWorkstationInBedroom);
        buffer.writeBoolean(this.hasBed);
        buffer.writeBoolean(this.hasWorkstation);

        buffer.writeInt(this.workplaceSize);
        buffer.writeInt(this.workstationsCount);
        buffer.writeInt(this.workplaceLight);
        buffer.writeInt(this.daysWithoutOutside);
        buffer.writeInt(this.bedWorkstationDistance);

        buffer.writeInt(this.todayOutsideTicks);
        buffer.writeInt(this.currentDay);
        buffer.writeInt(this.lastDayOutside);
        buffer.writeLong(this.lastRememberedWorkstationPos);
        buffer.writeInt(this.lastSleepDay);
        buffer.writeInt(this.lastZombieDay);

        buffer.writeInt(this.comfort);
        buffer.writeInt(this.configMaxBedroom);
        buffer.writeInt(this.configBedroomLight);
        buffer.writeInt(this.configMaxWorkplace);
        buffer.writeInt(this.configWorkplaceLight);
        buffer.writeInt(this.configBedWorkplaceDistance);
        buffer.writeInt(this.configBedsThreshold);
        buffer.writeInt(this.configWorkstationsThreshold);
    }

    public static final CustomPacketPayload.Type<VillagerInfoPacket> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(VillagerComfort.MOD_ID, "villager_info"));

    public static StreamCodec<FriendlyByteBuf, VillagerInfoPacket> STREAM_CODEC =
            StreamCodec.ofMember(VillagerInfoPacket::encode, VillagerInfoPacket::new);

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
