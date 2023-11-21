package eu.pb4.justpolydrawers.nodes;

import net.minecraft.util.math.Direction;

public interface AxisNode extends DirectionCheckingNode {
    Direction.Axis axis();

    @Override
    default boolean canConnectDir(Direction direction) {
        return this.axis() == direction.getAxis();
    }
}
