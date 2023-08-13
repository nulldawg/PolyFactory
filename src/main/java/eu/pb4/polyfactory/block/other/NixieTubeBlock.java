package eu.pb4.polyfactory.block.other;

import eu.pb4.polyfactory.block.FactoryBlocks;
import eu.pb4.polyfactory.item.FactoryItems;
import eu.pb4.polyfactory.models.BaseModel;
import eu.pb4.polyfactory.util.DyeColorExtra;
import eu.pb4.polyfactory.util.FactoryUtil;
import eu.pb4.polyfactory.util.VirtualDestroyStage;
import eu.pb4.polymer.core.api.block.PolymerBlock;
import eu.pb4.polymer.virtualentity.api.BlockWithElementHolder;
import eu.pb4.polymer.virtualentity.api.ElementHolder;
import eu.pb4.polymer.virtualentity.api.attachment.BlockBoundAttachment;
import eu.pb4.polymer.virtualentity.api.attachment.HolderAttachment;
import eu.pb4.polymer.virtualentity.api.elements.ItemDisplayElement;
import eu.pb4.polymer.virtualentity.api.elements.TextDisplayElement;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.enums.BlockHalf;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.entity.decoration.Brightness;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.DyeItem;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.*;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.*;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4fStack;

import java.util.ArrayList;
import java.util.List;

public class NixieTubeBlock extends Block implements PolymerBlock, BlockEntityProvider, BlockWithElementHolder, VirtualDestroyStage.Marker {
    public static Property<Direction.Axis> AXIS = Properties.AXIS;
    public static BooleanProperty POSITIVE_CONNECTED = BooleanProperty.of("positive_connected");
    public static BooleanProperty NEGATIVE_CONNECTED = BooleanProperty.of("negative_connected");
    public static EnumProperty<BlockHalf> HALF = Properties.BLOCK_HALF;


    public NixieTubeBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(AXIS, HALF, POSITIVE_CONNECTED, NEGATIVE_CONNECTED);
    }

    @Override
    public Block getPolymerBlock(BlockState state) {
        return Blocks.BARRIER;
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!player.canModifyBlocks()) {
            return ActionResult.PASS;
        }
        var stack = player.getStackInHand(hand);

        if (stack.isOf(Items.NAME_TAG)) {
            var name = stack.hasCustomName() ? stack.getName().getString() : "";

            if (world.getBlockEntity(pos) instanceof NixieTubeBlockEntity be) {
                be.pushText(name);
                return ActionResult.SUCCESS;
            }
        } else if (stack.getItem() instanceof DyeItem dye) {
            var color = DyeColorExtra.getColor(dye.getColor());
            if (world.getBlockEntity(pos) instanceof NixieTubeBlockEntity be) {
                if (be.setColor(color)) {
                    be.updateText();
                    if (!player.isCreative()) {
                        stack.decrement(1);
                    }
                    return ActionResult.SUCCESS;
                }
            }
        }

        return super.onUse(state, world, pos, player, hand, hit);
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(POSITIVE_CONNECTED, false).with(NEGATIVE_CONNECTED, false).with(AXIS, ctx.getHorizontalPlayerFacing().rotateYClockwise().getAxis()).with(HALF,
                ((ctx.getSide().getAxis() == Direction.Axis.Y && ctx.getSide() == Direction.DOWN) || (ctx.getSide().getAxis() != Direction.Axis.Y && ctx.getHitPos().y - ctx.getBlockPos().getY() > 0.5)) ? BlockHalf.TOP : BlockHalf.BOTTOM);
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (state.get(AXIS) != direction.getAxis()) {
            return state;
        }

        return state.with(direction.getDirection() == Direction.AxisDirection.POSITIVE ? POSITIVE_CONNECTED : NEGATIVE_CONNECTED, neighborState.isOf(FactoryBlocks.NIXIE_TUBE) && neighborState.get(AXIS) == state.get(AXIS) && neighborState.get(HALF) == state.get(HALF));
    }

    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (newState.isOf(this) && world.getBlockEntity(pos) instanceof NixieTubeBlockEntity be) {
            be.updatePositions(world, pos, newState);
        }

        super.onStateReplaced(state, world, pos, newState, moved);
    }

    @Override
    public BlockState rotate(BlockState state, BlockRotation rotation) {
        return state.with(AXIS, rotation.rotate(Direction.get(Direction.AxisDirection.POSITIVE, state.get(AXIS))).getAxis());
    }

    @Override
    public @Nullable ElementHolder createElementHolder(ServerWorld world, BlockPos pos, BlockState initialBlockState) {
        return new Model(world, pos, initialBlockState);
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new NixieTubeBlockEntity(pos, state);
    }

    public final class Model extends BaseModel {
        private static final Brightness MAX_BRIGHTNESS = new Brightness(15, 15);
        private final Matrix4fStack mat = new Matrix4fStack(2);
        private final ItemDisplayElement mainElement;
        private final TextDisplayElement[] display = new TextDisplayElement[4];
        private char positiveFirst = ' ';
        private char positiveSecond = ' ';
        private char negativeFirst = ' ';
        private char negativeSecond = ' ';
        private int color;

        private Model(ServerWorld world, BlockPos pos, BlockState state) {
            //this.world = world;
            //this.pos = pos;
            this.mainElement = new ItemDisplayElement(FactoryItems.NIXIE_TUBE);
            this.mainElement.setDisplaySize(1, 1);
            this.mainElement.setModelTransformation(ModelTransformationMode.FIXED);
            this.mainElement.setInvisible(true);
            this.mainElement.setViewRange(0.8f);

            for (int i = 0; i < 4; i++) {
                var e = new TextDisplayElement();
                e.setBackground(0);
                e.setShadow(true);
                e.setDisplaySize(1, 1);
                e.setInvisible(true);
                e.setViewRange(0.4f);
                e.setBrightness(MAX_BRIGHTNESS);

                display[i] = e;
            }

            this.updateFacing(state);
            this.addElement(this.mainElement);
            for (int i = 0; i < 4; i++) {
                this.addElement(this.display[i]);
            }
        }

        public void setColor(int color) {
            if (this.color == color) {
                return;
            }
            this.positiveFirst = 0;
            this.positiveSecond = 0;
            this.negativeFirst = 0;
            this.negativeSecond = 0;
            this.color = color;
        }

        public void setText(char positiveFirst, char positiveSecond, char negativeFirst, char negativeSecond) {
            boolean dirty = false;
            if (this.positiveFirst != positiveFirst) {
                this.display[2].setText(asText(Character.toString(positiveFirst)));
                this.positiveFirst = positiveFirst;
                dirty = true;
            }

            if (this.positiveSecond != positiveSecond) {
                this.display[0].setText(asText(Character.toString(positiveSecond)));
                this.positiveSecond = positiveSecond;
                dirty = true;
            }

            if (this.negativeFirst != negativeFirst) {
                this.display[3].setText(asText(Character.toString(negativeFirst)));
                this.negativeFirst = negativeFirst;
                dirty = true;
            }

            if (this.negativeSecond != negativeSecond) {
                this.display[1].setText(asText(Character.toString(negativeSecond)));
                this.negativeSecond = negativeSecond;
                dirty = true;
            }

            if (dirty) {
                this.tick();
            }
        }

        private Text asText(String text) {
            return Text.literal(text).setStyle(Style.EMPTY.withColor(this.color));
        }



        /*private boolean updateValues() {
            var x = Math.min(getDisplayValue(world, pos), 99);

            if (x != this.value) {
                this.value = x;
for (int i = 0; i < 4; i += 2) {
                    var t = Text.literal("" + (x % 10)).formatted(Formatting.GOLD);
                    this.display[i].setText(t);
                    this.display[i + 1].setText(t);
                    x /= 10;
                }

                return true;
            }
            return false;
        }*/

        @Override
        public boolean startWatching(ServerPlayNetworkHandler player) {
            return super.startWatching(player);
        }

        public void update() {
           // if (updateValues()) {
                this.updateFacing(BlockBoundAttachment.get(this).getBlockState());
                this.tick();
            //}
        }

        private void updateFacing(BlockState facing) {
            var rot = Direction.get(Direction.AxisDirection.POSITIVE, facing.get(AXIS)).rotateYClockwise().getRotationQuaternion().mul(Direction.NORTH.getRotationQuaternion());
            var up = facing.get(HALF) == BlockHalf.TOP;
            mat.clear();
            mat.rotate(rot);
            mat.pushMatrix();
            mat.rotateY(MathHelper.PI);
            if (up) {
                mat.rotateZ(MathHelper.PI);
            }
            mat.scale(2f);
            this.mainElement.setTransformation(mat);
            mat.popMatrix();

            var yPos = up ? -0.1f : -0.4f;

            mat.pushMatrix();
            mat.translate(-0.25f, yPos, 0);
            mat.scale(1.5f);
            mat.rotateY(MathHelper.PI);
            this.display[0].setTransformation(mat);
            mat.rotateY(MathHelper.PI);
            this.display[3].setTransformation(mat);
            mat.popMatrix();

            mat.pushMatrix();
            mat.translate(0.25f, yPos, 0);
            mat.scale(1.5f);

            mat.rotateY(MathHelper.PI);
            this.display[2].setTransformation(mat);
            mat.rotateY(MathHelper.PI);
            this.display[1].setTransformation(mat);
            mat.popMatrix();


            this.tick();
        }

        @Override
        public void notifyUpdate(HolderAttachment.UpdateType updateType) {
            if (updateType == BlockBoundAttachment.BLOCK_STATE_UPDATE) {
                this.updateFacing(BlockBoundAttachment.get(this).getBlockState());
            }
        }
    }
}
