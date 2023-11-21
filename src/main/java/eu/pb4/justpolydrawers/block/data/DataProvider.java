package eu.pb4.justpolydrawers.block.data;

import eu.pb4.justpolydrawers.data.DataContainer;
import net.minecraft.block.BlockState;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

public interface DataProvider {
    @Nullable
    DataContainer provideData(ServerWorld world, BlockPos selfPos, BlockState selfState, int channel);
}
