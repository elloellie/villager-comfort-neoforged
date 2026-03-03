package net.elleoellie.villagercomfort.dataattachment;

import com.mojang.serialization.Codec;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

import static net.elleoellie.villagercomfort.VillagerComfort.MOD_ID;

public class ComfortData {

    private static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES =
            DeferredRegister.create(NeoForgeRegistries.Keys.ATTACHMENT_TYPES, MOD_ID);

    public static final Supplier<AttachmentType<Integer>> BEDROOM_SIZE = ATTACHMENT_TYPES.register(
            "bedroom_size", () -> AttachmentType.<Integer>builder(() -> 0).serialize(Codec.INT).build());
    public static final Supplier<AttachmentType<Integer>> BEDS_COUNT = ATTACHMENT_TYPES.register(
            "beds_count", () -> AttachmentType.<Integer>builder(() -> 0).serialize(Codec.INT).build());
    public static final Supplier<AttachmentType<Integer>> BEDROOM_LIGHT = ATTACHMENT_TYPES.register(
            "bedroom_light", () -> AttachmentType.<Integer>builder(() -> 0).serialize(Codec.INT).build());

    public static final Supplier<AttachmentType<Boolean>> IS_WORKSTATION_IN_BEDROOM = ATTACHMENT_TYPES.register(
            "is_workstation_in_bedroom", () -> AttachmentType.<Boolean>builder(() -> false).serialize(Codec.BOOL).build());
    public static final Supplier<AttachmentType<Boolean>> HAS_BED = ATTACHMENT_TYPES.register(
            "has_bed", () -> AttachmentType.<Boolean>builder(() -> false).serialize(Codec.BOOL).build());
    public static final Supplier<AttachmentType<Boolean>> HAS_WORKSTATION = ATTACHMENT_TYPES.register(
            "has_workstation", () -> AttachmentType.<Boolean>builder(() -> false).serialize(Codec.BOOL).build());

    public static final Supplier<AttachmentType<Integer>> WORKPLACE_SIZE = ATTACHMENT_TYPES.register(
            "workplace_size", () -> AttachmentType.<Integer>builder(() -> 0).serialize(Codec.INT).build());
    public static final Supplier<AttachmentType<Integer>> WORKSTATIONS_COUNT = ATTACHMENT_TYPES.register(
            "workstations_count", () -> AttachmentType.<Integer>builder(() -> 0).serialize(Codec.INT).build());
    public static final Supplier<AttachmentType<Integer>> WORKPLACE_LIGHT = ATTACHMENT_TYPES.register(
            "workplace_light", () -> AttachmentType.<Integer>builder(() -> 0).serialize(Codec.INT).build());
    public static final Supplier<AttachmentType<Integer>> BED_WORKSTATION_DISTANCE = ATTACHMENT_TYPES.register(
            "bed_workstation_distance", () -> AttachmentType.<Integer>builder(() -> 0).serialize(Codec.INT).build());


    public static final Supplier<AttachmentType<Integer>> TODAY_OUTSIDE_TICKS = ATTACHMENT_TYPES.register(
            "today_outside_ticks", () -> AttachmentType.<Integer>builder(() -> 0).serialize(Codec.INT).build());
    public static final Supplier<AttachmentType<Integer>> CURRENT_DAY = ATTACHMENT_TYPES.register(
            "current_day", () -> AttachmentType.<Integer>builder(() -> -2).serialize(Codec.INT).build());
    public static final Supplier<AttachmentType<Integer>> LAST_DAY_OUTSIDE = ATTACHMENT_TYPES.register(
            "last_day_outside", () -> AttachmentType.<Integer>builder(() -> -2).serialize(Codec.INT).build());
    public static final Supplier<AttachmentType<Long>> LAST_REMEMBERED_WORKSTATION_POS = ATTACHMENT_TYPES.register(
            "last_remembered_workstation_pos", () -> AttachmentType.<Long>builder(()-> 0L).serialize(Codec.LONG).build());
    public static final Supplier<AttachmentType<Integer>> LAST_SLEEP_DAY = ATTACHMENT_TYPES.register(
            "last_sleep_day", () -> AttachmentType.<Integer>builder(() -> -2).serialize(Codec.INT).build());
    public static final Supplier<AttachmentType<Integer>> LAST_ZOMBIE_DAY = ATTACHMENT_TYPES.register(
            "last_zombie_day", () -> AttachmentType.<Integer>builder(() -> -2).serialize(Codec.INT).build());

    //    public static final Supplier<AttachmentType<Integer>> OUTSIDE_TICKS = ATTACHMENT_TYPES.register(
//            "outside_ticks", () -> AttachmentType.<Integer>builder(() -> 0).serialize(Codec.INT).build());
    public static final Supplier<AttachmentType<Integer>> DAYS_WITHOUT_OUTSIDE = ATTACHMENT_TYPES.register(
            "days_without_outside", () -> AttachmentType.<Integer>builder(() -> 0).serialize(Codec.INT).build());


    public static final Supplier<AttachmentType<Integer>> COMFORT = ATTACHMENT_TYPES.register(
            "comfort", () -> AttachmentType.<Integer>builder(() -> 0).serialize(Codec.INT).build());

    public static void register(IEventBus eventBus) {
        ATTACHMENT_TYPES.register(eventBus);
    }
}
