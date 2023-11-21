package eu.pb4.justpolydrawers.block.data.util;

import eu.pb4.justpolydrawers.block.network.NetworkBlock;
import eu.pb4.justpolydrawers.block.network.NetworkComponent;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldAccess;

public abstract class DataNetworkBlock extends NetworkBlock implements NetworkComponent.Data {
    protected DataNetworkBlock(Settings settings) {
        super(settings);
    }


    @Override
    protected void updateNetworkAt(WorldAccess world, BlockPos pos) {
        NetworkComponent.Data.updateDataAt(world, pos);
    }

    @Override
    protected boolean isSameNetworkType(Block block) {
        return block instanceof NetworkComponent.Data;
    }
}
