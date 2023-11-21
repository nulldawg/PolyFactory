package eu.pb4.justpolydrawers.nodes.generic;

import com.kneelawk.graphlib.api.graph.NodeHolder;
import com.kneelawk.graphlib.api.graph.user.BlockNode;
import com.kneelawk.graphlib.api.graph.user.BlockNodeType;
import com.kneelawk.graphlib.api.util.HalfLink;
import eu.pb4.justpolydrawers.ModInit;
import eu.pb4.justpolydrawers.nodes.DirectionNode;
import eu.pb4.justpolydrawers.nodes.FactoryNodes;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtString;
import net.minecraft.util.math.Direction;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

public record SimpleDirectionNode(Direction direction) implements DirectionNode {
    public static BlockNodeType TYPE = BlockNodeType.of(ModInit.id("direction"),
            tag -> new SimpleDirectionNode(tag instanceof NbtString string ? Direction.byName(string.asString()) : Direction.NORTH));

    @Override
    public @NotNull BlockNodeType getType() {
        return TYPE;
    }

    @Override
    public @Nullable NbtElement toTag() {
        return NbtString.of(direction.asString());
    }

    @Override
    public @NotNull Collection<HalfLink> findConnections(@NotNull NodeHolder<BlockNode> self) {
        return FactoryNodes.findNodes(self, self.getBlockPos().offset(this.direction)).toList();
    }

    @Override
    public void onConnectionsChanged(@NotNull NodeHolder<BlockNode> self) {

    }

}
