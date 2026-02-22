package net.elleoellie.villagercomfort.core.world.entity.ai.memory;

import com.mojang.serialization.Codec;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.Optional;
import java.util.function.Supplier;

public class ModMemoryModuleType
{
    public static final DeferredRegister<MemoryModuleType<?>> MEMORIES = DeferredRegister.create(BuiltInRegistries.MEMORY_MODULE_TYPE, "thirst");

    public static final Supplier<MemoryModuleType<Integer>> BEDROOM_WIDTH = MEMORIES.register("bedroom_width", () -> new MemoryModuleType<>(Optional.of(Codec.INT)));
}
