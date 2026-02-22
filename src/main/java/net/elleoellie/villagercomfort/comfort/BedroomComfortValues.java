package net.elleoellie.villagercomfort.comfort;

import com.mojang.logging.LogUtils;
import net.elleoellie.villagercomfort.Config;
import net.elleoellie.villagercomfort.room.RoomHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.Blocks;
import org.slf4j.Logger;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import static net.elleoellie.villagercomfort.dataattachment.ComfortData.*;

public class BedroomComfortValues
{
    private static Logger LOGGER = LogUtils.getLogger();

    public static void setValuesToCap(Villager villager, BlockPos bedPos)
    {
        Level level = villager.level();
        GlobalPos workstation = villager.getBrain().getMemory(MemoryModuleType.JOB_SITE).orElse(null);

        AtomicInteger bedsCount = new AtomicInteger(0);
        AtomicInteger roomSize = new AtomicInteger(0);
        AtomicInteger maxLightValue = new AtomicInteger(0);
        AtomicBoolean isWorkstationInBedroom = new AtomicBoolean(false);

        // LOGGER.info("Doing bedroom runforeveryblock!");
        RoomHelper.runForEveryBlock(villager.level(), bedPos, Config.MAX_BEDROOM_VOLUME.get().intValue(), (pos) ->
        {
            if(workstation != null && pos.equals(workstation.pos()))
            {
                //checks for villager workstation
                isWorkstationInBedroom.set(true);
            }
            else if(level.getBlockState(pos).is(BlockTags.BEDS))
            {
                // LOGGER.info("bed at " + pos);
                //counts number of beds
                bedsCount.getAndIncrement();
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

        if(workstation != null)
        {
            int blockDistance = (int) Math.sqrt(bedPos.distSqr(workstation.pos()));
            villager.setData(BEDWORKSTATIONDISTANCE, blockDistance);
        }

        villager.setData(BEDROOMSIZE, roomSize.get());
        villager.setData(BEDSCOUNT, bedsCount.get() / 2);
        villager.setData(BEDROOMLIGHT, maxLightValue.get());
        villager.setData(ISWORKSTATIONINBEDROOM, isWorkstationInBedroom.get());
    }
}
