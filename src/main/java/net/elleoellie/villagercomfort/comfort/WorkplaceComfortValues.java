package net.elleoellie.villagercomfort.comfort;


import net.elleoellie.villagercomfort.Config;
import net.elleoellie.villagercomfort.room.RoomHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import com.mojang.logging.LogUtils;
import static net.elleoellie.villagercomfort.dataattachment.ComfortData.*;

public class WorkplaceComfortValues
{
    private static Logger LOGGER = LogUtils.getLogger();

    public static void setValuesToCap(Villager villager)
    {
        villager.getBrain().getMemory(MemoryModuleType.JOB_SITE).ifPresent(workstation ->
        {
            ServerLevel level = (ServerLevel) villager.level();
            BlockPos workstationPos = workstation.pos();
            BlockState workstationBlock = level.getBlockState(workstationPos);

            AtomicInteger workstationsCount = new AtomicInteger(0);
            AtomicInteger roomSize = new AtomicInteger(0);
            AtomicInteger maxLightValue = new AtomicInteger(0);

            // LOGGER.info("Doing workplace runforeveryblock!");
            RoomHelper.runForEveryBlock(villager.level(), workstationPos, Config.MAX_WORKPLACE_VOLUME.get().intValue(), (pos) ->
            {
                if((! workstationBlock.is(Blocks.AIR)) && level.getBlockState(pos).is(workstationBlock.getBlock()))
                {
                    //counts number of workstations
                    workstationsCount.getAndIncrement();
                    //level.getPoiManager().getType(pos).ifPresent(poi -> workstationsCount.getAndIncrement());
                }
                else if(level.getBlockState(pos).is(Blocks.AIR))
                {
                    //checks for max. light value
                    if(level.getBrightness(LightLayer.SKY, pos) > maxLightValue.get())
                        maxLightValue.set(level.getBrightness(LightLayer.SKY, pos));

                    //keeps track of room size
                    roomSize.getAndIncrement();
                }
            });

            villager.setData(WORKPLACESIZE, roomSize.get());
            villager.setData(WORKSTATIONSCOUNT, workstationsCount.get());
            villager.setData(WORKPLACELIGHT, maxLightValue.get());
        });
    }
}
