package eu.pb4.justpolydrawers.util.movingitem;

import eu.pb4.justpolydrawers.util.CachedBlockPointer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

public interface MovingItemProvider {
    void getItemFrom(CachedBlockPointer self, Direction pushDirection, Direction relative, BlockPos conveyorPos, ContainerHolder conveyor);
}
