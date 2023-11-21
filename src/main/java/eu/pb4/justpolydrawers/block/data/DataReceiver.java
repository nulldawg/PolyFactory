package eu.pb4.justpolydrawers.block.data;

import eu.pb4.justpolydrawers.data.DataContainer;
import net.minecraft.block.BlockState;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

public interface DataReceiver {
    boolean receiveData(ServerWorld world, BlockPos selfPos, BlockState selfState, int channel, DataContainer data);
}
