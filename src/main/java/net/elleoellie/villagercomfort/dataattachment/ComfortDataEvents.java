package net.elleoellie.villagercomfort.dataattachment;

import net.elleoellie.villagercomfort.VillagerComfort;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.npc.Villager;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityEvent;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.living.MobSpawnEvent;

import static net.elleoellie.villagercomfort.dataattachment.ComfortData.*;

@EventBusSubscriber(modid = VillagerComfort.MOD_ID, bus = EventBusSubscriber.Bus.GAME)
public class ComfortDataEvents {
    @SubscribeEvent
    public static void initializeVillagerValues(EntityEvent.EntityConstructing event)
    {
        if (event.getEntity() instanceof Villager villager){
            villager.getData(BEDROOMSIZE);
            villager.getData(BEDSCOUNT);
            villager.getData(BEDROOMLIGHT);
            villager.getData(ISWORKSTATIONINBEDROOM);
            villager.getData(WORKPLACESIZE);
            villager.getData(WORKSTATIONSCOUNT);
            villager.getData(WORKPLACELIGHT);
            villager.getData(OUTSIDETICKS);
            villager.getData(DAYSWITHOUTOUTSIDE);
            villager.getData(BEDWORKSTATIONDISTANCE);
            villager.getData(TODAY_OUTSIDE_TICKS);
            villager.getData(CURRENT_DAY_FOR_MEASURING_OUTSIDE_TICKS);
            villager.getData(LAST_DAY_OUTSIDE);
            villager.getData(LAST_REMEMBERED_WORKSTATION_POS);
            villager.getData(LAST_SLEEP_DAY);
            villager.getData(LAST_ZOMBIE_DAY);
            villager.getData(COMFORT);
//            System.out.println("SUCCESFULLY ATTACHED COMFORT VALUES!");
//            System.out.println("Comfort at Attachment: " + villager.getData(COMFORT));
        }
    }

}

