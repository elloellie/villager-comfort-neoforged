package net.elleoellie.villagercomfort.event;

import net.elleoellie.villagercomfort.VillagerComfort;
import net.elleoellie.villagercomfort.network.ServerPayloadHandler;
import net.elleoellie.villagercomfort.network.packet.*;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.HandlerThread;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

@EventBusSubscriber (bus = EventBusSubscriber.Bus.MOD, modid= VillagerComfort.MOD_ID)
public class ModEvents {
    @SubscribeEvent
    public static void registerPayloads(RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar("1")
                .executesOn(HandlerThread.MAIN);

        registrar.playToClient(VillagerInfoPacket.TYPE, VillagerInfoPacket.STREAM_CODEC, ServerPayloadHandler::openMenuOnClient);
    }
}