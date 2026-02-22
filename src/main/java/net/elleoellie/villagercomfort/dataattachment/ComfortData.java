package net.elleoellie.villagercomfort.dataattachment;

import com.mojang.serialization.Codec;
import net.minecraft.nbt.CompoundTag;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

import static net.elleoellie.villagercomfort.VillagerComfort.MOD_ID;

public class ComfortData {

    private static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES =
            DeferredRegister.create(NeoForgeRegistries.Keys.ATTACHMENT_TYPES, MOD_ID);

    public static final Supplier<AttachmentType<Integer>> BEDROOMSIZE = ATTACHMENT_TYPES.register(
            "bedroomsize", () -> AttachmentType.<Integer>builder(() -> 0).serialize(Codec.INT).build());
    public static final Supplier<AttachmentType<Integer>> BEDSCOUNT = ATTACHMENT_TYPES.register(
            "bedscount", () -> AttachmentType.<Integer>builder(() -> 0).serialize(Codec.INT).build());
    public static final Supplier<AttachmentType<Integer>> BEDROOMLIGHT = ATTACHMENT_TYPES.register(
            "bedroomlight", () -> AttachmentType.<Integer>builder(() -> 0).serialize(Codec.INT).build());
    public static final Supplier<AttachmentType<Boolean>> ISWORKSTATIONINBEDROOM = ATTACHMENT_TYPES.register(
            "isworkstationinbedroom", () -> AttachmentType.<Boolean>builder(() -> false).serialize(Codec.BOOL).build());
    public static final Supplier<AttachmentType<Integer>> WORKPLACESIZE = ATTACHMENT_TYPES.register(
            "workplacesize", () -> AttachmentType.<Integer>builder(() -> 0).serialize(Codec.INT).build());
    public static final Supplier<AttachmentType<Integer>> WORKSTATIONSCOUNT = ATTACHMENT_TYPES.register(
            "workstationscount", () -> AttachmentType.<Integer>builder(() -> 0).serialize(Codec.INT).build());
    public static final Supplier<AttachmentType<Integer>> WORKPLACELIGHT = ATTACHMENT_TYPES.register(
            "workplacelight", () -> AttachmentType.<Integer>builder(() -> 0).serialize(Codec.INT).build());
    public static final Supplier<AttachmentType<Integer>> OUTSIDETICKS = ATTACHMENT_TYPES.register(
            "outsideticks", () -> AttachmentType.<Integer>builder(() -> 0).serialize(Codec.INT).build());
    public static final Supplier<AttachmentType<Integer>> DAYSWITHOUTOUTSIDE = ATTACHMENT_TYPES.register(
            "dayswithoutoutside", () -> AttachmentType.<Integer>builder(() -> 0).serialize(Codec.INT).build());
    public static final Supplier<AttachmentType<Integer>> BEDWORKSTATIONDISTANCE = ATTACHMENT_TYPES.register(
            "bedworkstationdistance", () -> AttachmentType.<Integer>builder(() -> 0).serialize(Codec.INT).build());
    public static final Supplier<AttachmentType<Integer>> TODAY_OUTSIDE_TICKS = ATTACHMENT_TYPES.register(
            "today_outside_ticks", () -> AttachmentType.<Integer>builder(() -> 0).serialize(Codec.INT).build());
    public static final Supplier<AttachmentType<Integer>> CURRENT_DAY_FOR_MEASURING_OUTSIDE_TICKS = ATTACHMENT_TYPES.register(
            "current_day_for_measuring_outside_ticks", () -> AttachmentType.<Integer>builder(() -> -2).serialize(Codec.INT).build());
    public static final Supplier<AttachmentType<Integer>> LAST_DAY_OUTSIDE = ATTACHMENT_TYPES.register(
            "last_day_outside", () -> AttachmentType.<Integer>builder(() -> -2).serialize(Codec.INT).build());
    public static final Supplier<AttachmentType<Long>> LAST_REMEMBERED_WORKSTATION_POS = ATTACHMENT_TYPES.register(
            "last_remembered_workstation_pos", () -> AttachmentType.<Long>builder(()-> 0L).serialize(Codec.LONG).build());
    public static final Supplier<AttachmentType<Integer>> LAST_SLEEP_DAY = ATTACHMENT_TYPES.register(
            "last_sleep_day", () -> AttachmentType.<Integer>builder(() -> -2).serialize(Codec.INT).build());
    public static final Supplier<AttachmentType<Integer>> LAST_ZOMBIE_DAY = ATTACHMENT_TYPES.register(
            "last_zombie_day", () -> AttachmentType.<Integer>builder(() -> -2).serialize(Codec.INT).build());
    public static final Supplier<AttachmentType<Integer>> COMFORT = ATTACHMENT_TYPES.register(
            "comfort", () -> AttachmentType.<Integer>builder(() -> 0).serialize(Codec.INT).build());

    public static void register(IEventBus eventBus) {
        ATTACHMENT_TYPES.register(eventBus);
    }
}
