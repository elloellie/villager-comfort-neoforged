package net.elleoellie.villagercomfort.event;


import net.elleoellie.villagercomfort.VillagerComfort;
import net.minecraft.world.entity.npc.Villager;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityEvent;

import static net.elleoellie.villagercomfort.dataattachment.ComfortData.*;

@EventBusSubscriber(modid = VillagerComfort.MOD_ID, bus = EventBusSubscriber.Bus.GAME)
public class AssignComfortValues {
    @SubscribeEvent
    public static void initializeVillagerValues(EntityEvent.EntityConstructing event)
    {
        if (event.getEntity() instanceof Villager villager){
            villager.getData(BEDROOM_SIZE);
            villager.getData(BEDS_COUNT);
            villager.getData(BEDROOM_LIGHT);
            villager.getData(IS_WORKSTATION_IN_BEDROOM);
            villager.getData(HAS_BED);
            villager.getData(HAS_WORKSTATION);
            villager.getData(WORKPLACE_SIZE);
            villager.getData(WORKSTATIONS_COUNT);
            villager.getData(WORKPLACE_LIGHT);
           // villager.getData(OUTSIDE_TICKS);
            villager.getData(DAYS_WITHOUT_OUTSIDE);
            villager.getData(BED_WORKSTATION_DISTANCE);
            villager.getData(TODAY_OUTSIDE_TICKS);
            villager.getData(CURRENT_DAY);
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

