package net.elleoellie.villagercomfort.room;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;

import java.util.ArrayList;
import java.util.List;
import java.util.ArrayDeque;
import java.util.Deque;

import org.slf4j.Logger;
import com.mojang.logging.LogUtils;

public class RoomHelper
{
    private static Logger LOGGER = LogUtils.getLogger();

    public static void runForEveryBlock(Level level, BlockPos roomStartPos, int maxVolume, IRoomBlockAction action)
    {
        List<Long> room = new ArrayList<>();
        Deque<BlockPos> room_run_queue = new ArrayDeque<>();

        checkAddLast(room, room_run_queue, maxVolume, roomStartPos);
        boolean initial_add = true;

        while (!room_run_queue.isEmpty()) {
            BlockPos block = room_run_queue.pollFirst();

            action.run(block);

            // LOGGER.info("HELLO!!!!!!!! " + roomStartPos + " | " + block + ", added myself. Room: " + room.size() + "|" + maxVolume);

            // for (Long l : room) {
            //     LOGGER.info(maxVolume + " " + BlockPos.of(l).toString());
            // }

            if (initial_add || (level.getBlockState(block).is(Blocks.AIR) && level.getBlockState(block.above()).is(Blocks.AIR))) {
                initial_add = false;
                checkAddLast(room, room_run_queue, maxVolume, block.north());
                checkAddLast(room, room_run_queue, maxVolume, block.south());
                checkAddLast(room, room_run_queue, maxVolume, block.west());
                checkAddLast(room, room_run_queue, maxVolume, block.east());
                checkAddLast(room, room_run_queue, maxVolume, block.above());
                checkAddLast(room, room_run_queue, maxVolume, block.below());
            }
        }
    }

    private static void checkAddLast(List<Long> room, Deque<BlockPos> room_run_queue, int maxVolume, BlockPos pos) {
        if (room.size() >= maxVolume) {
            return;
        }

        if (! room.contains(pos.asLong())) {
            room.add(pos.asLong());
            room_run_queue.addLast(pos);
        }
    }
}
