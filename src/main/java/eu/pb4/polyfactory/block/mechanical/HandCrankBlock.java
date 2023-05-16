package eu.pb4.polyfactory.block.mechanical;

import com.kneelawk.graphlib.graph.BlockNode;
import eu.pb4.polyfactory.block.network.NetworkBlock;
import eu.pb4.polyfactory.display.LodElementHolder;
import eu.pb4.polyfactory.display.LodItemDisplayElement;
import eu.pb4.polyfactory.item.FactoryItems;
import eu.pb4.polyfactory.item.util.SimpleModeledPolymerItem;
import eu.pb4.polyfactory.nodes.mechanical.RotationalSourceNode;
import eu.pb4.polyfactory.util.FactoryUtil;
import eu.pb4.polymer.core.api.block.PolymerBlock;
import eu.pb4.polymer.resourcepack.api.PolymerResourcePackUtils;
import eu.pb4.polymer.virtualentity.api.BlockWithElementHolder;
import eu.pb4.polymer.virtualentity.api.ElementHolder;
import eu.pb4.polymer.virtualentity.api.attachment.BlockBoundAttachment;
import eu.pb4.polymer.virtualentity.api.elements.ItemDisplayElement;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.enums.WallMountLocation;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;

import java.util.Collection;
import java.util.List;

public class HandCrankBlock extends NetworkBlock implements PolymerBlock, RotationalSource, BlockEntityProvider, BlockWithElementHolder {
    public static final DirectionProperty FACING = Properties.FACING;

    public HandCrankBlock(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (hand == Hand.MAIN_HAND && world.getBlockEntity(pos) instanceof HandCrankBlockEntity be && be.lastTick != world.getServer().getTicks()) {
            be.lastTick = world.getServer().getTicks();
            return ActionResult.SUCCESS;
        }

        return super.onUse(state, world, pos, player, hand, hit);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(FACING, ctx.getSide().getOpposite());
    }

    @Override
    public double getSpeed(BlockState state, ServerWorld world, BlockPos pos) {
        if (world.getBlockEntity(pos) instanceof HandCrankBlockEntity be) {
            return MathHelper.lerp(MathHelper.clamp((world.getServer().getTicks() - be.lastTick - 4) / 2d, 0, 1), 0.1, 0);
        }

        return 0;
    }

    @Override
    public Collection<BlockNode> createNodes(BlockState state, ServerWorld world, BlockPos pos) {
        return List.of(new RotationalSourceNode(state.get(FACING)));
    }

    @Override
    public Block getPolymerBlock(BlockState state) {
        return Blocks.BARRIER;
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new HandCrankBlockEntity(pos, state);
    }

    @Override
    public boolean tickElementHolder(ServerWorld world, BlockPos pos, BlockState initialBlockState) {
        return true;
    }

    @Override
    public ElementHolder createElementHolder(ServerWorld world, BlockPos pos, BlockState initialBlockState) {
        return new Model(world, initialBlockState);
    }

    public final class Model extends LodElementHolder {
        private final Matrix4f mat = new Matrix4f();
        private final ItemDisplayElement mainElement;

        private Model(ServerWorld world, BlockState state) {
            this.mainElement = new LodItemDisplayElement(FactoryItems.HAND_CRANK_BLOCK.getDefaultStack());
            this.mainElement.setDisplaySize(1, 1);
            this.mainElement.setModelTransformation(ModelTransformationMode.FIXED);
            this.mainElement.setInterpolationDuration(5);
            this.updateAnimation(0, 0, state.get(FACING));
            this.addElement(this.mainElement);
        }

        private void updateAnimation(double speed, long worldTick, Direction facing) {
            mat.identity();
            mat.rotate(facing.getOpposite().getRotationQuaternion());
            mat.rotateY(((float) ((facing.getDirection() == Direction.AxisDirection.POSITIVE) == (facing.getAxis() == Direction.Axis.Z) ? speed : -speed) * worldTick));

            mat.scale(2f);
            this.mainElement.setTransformation(mat);
        }

        @Override
        protected void onTick() {
            var tick = this.getAttachment().getWorld().getTime();

            if (tick % 4 == 0) {
                this.updateAnimation(RotationalSource.getNetworkSpeed(this.getAttachment().getWorld(),
                                ((BlockBoundAttachment) this.getAttachment()).getBlockPos()), tick,
                        ((BlockBoundAttachment) this.getAttachment()).getBlockState().get(FACING));
                if (this.mainElement.isDirty()) {
                    this.mainElement.startInterpolation();
                }
            }
        }
    }
}
