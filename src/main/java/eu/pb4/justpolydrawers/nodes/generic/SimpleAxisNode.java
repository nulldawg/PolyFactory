package eu.pb4.justpolydrawers.nodes.generic;

import com.kneelawk.graphlib.api.graph.NodeHolder;
import com.kneelawk.graphlib.api.graph.user.BlockNode;
import com.kneelawk.graphlib.api.graph.user.BlockNodeType;
import com.kneelawk.graphlib.api.util.HalfLink;
import eu.pb4.justpolydrawers.ModInit;
import eu.pb4.justpolydrawers.nodes.AxisNode;
import eu.pb4.justpolydrawers.nodes.FactoryNodes;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtString;
import net.minecraft.util.math.Direction;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;

public record SimpleAxisNode(Direction.Axis axis) implements AxisNode {
    public static BlockNodeType TYPE = BlockNodeType.of(ModInit.id("axis"),
            tag -> new SimpleAxisNode(tag instanceof NbtString string ? Direction.Axis.fromName(string.asString()) : Direction.Axis.Y));

    @Override
    public @NotNull BlockNodeType getType() {
        return TYPE;
    }

    @Override
    public @Nullable NbtElement toTag() {
        return NbtString.of(axis.asString());
    }

    @Override
    public @NotNull Collection<HalfLink> findConnections(@NotNull NodeHolder<BlockNode> self) {
        var list = new ArrayList<HalfLink>();
        FactoryNodes.findNodes(self, self.getBlockPos().offset(this.axis,1)).forEach(list::add);
        FactoryNodes.findNodes(self, self.getBlockPos().offset(this.axis,-1)).forEach(list::add);
        return list;
    }

    @Override
    public void onConnectionsChanged(@NotNull NodeHolder<BlockNode> self) {

    }

}