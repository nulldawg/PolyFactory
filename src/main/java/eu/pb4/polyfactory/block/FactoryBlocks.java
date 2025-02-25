package eu.pb4.polyfactory.block;

import eu.pb4.polyfactory.ModInit;
import eu.pb4.factorytools.api.block.AttackableBlock;
import eu.pb4.polyfactory.block.creative.CreativeContainerBlock;
import eu.pb4.polyfactory.block.creative.CreativeMotorBlock;
import eu.pb4.polyfactory.block.data.CableBlock;
import eu.pb4.polyfactory.block.data.providers.TinyPotatoSpringBlock;
import eu.pb4.polyfactory.block.data.output.NixieTubeControllerBlock;
import eu.pb4.polyfactory.block.data.output.RedstoneOutputBlock;
import eu.pb4.polyfactory.block.data.providers.ItemReaderBlock;
import eu.pb4.polyfactory.block.data.providers.DataProviderBlock;
import eu.pb4.polyfactory.block.data.providers.BlockObserverBlock;
import eu.pb4.polyfactory.block.data.providers.RedstoneInputBlock;
import eu.pb4.polyfactory.block.electric.ElectricGeneratorBlock;
import eu.pb4.polyfactory.block.electric.ElectricMotorBlock;
import eu.pb4.polyfactory.block.electric.WitherSkullGeneratorBlock;
import eu.pb4.polyfactory.block.mechanical.machines.PlacerBlock;
import eu.pb4.polyfactory.block.mechanical.machines.PlanterBlock;
import eu.pb4.polyfactory.block.mechanical.machines.crafting.GrinderBlock;
import eu.pb4.polyfactory.block.mechanical.machines.MinerBlock;
import eu.pb4.polyfactory.block.mechanical.machines.crafting.MCrafterBlock;
import eu.pb4.polyfactory.block.mechanical.machines.crafting.MixerBlock;
import eu.pb4.polyfactory.block.mechanical.machines.crafting.PressBlock;
import eu.pb4.polyfactory.block.mechanical.*;
import eu.pb4.polyfactory.block.mechanical.conveyor.ConveyorBlock;
import eu.pb4.polyfactory.block.mechanical.conveyor.FunnelBlock;
import eu.pb4.polyfactory.block.mechanical.conveyor.SplitterBlock;
import eu.pb4.polyfactory.block.mechanical.source.HandCrankBlock;
import eu.pb4.polyfactory.block.mechanical.source.SteamEngineBlock;
import eu.pb4.polyfactory.block.mechanical.source.WindmillBlock;
import eu.pb4.polyfactory.block.other.*;
import eu.pb4.polyfactory.block.data.output.NixieTubeBlock;
import net.fabricmc.fabric.api.event.player.AttackBlockCallback;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.MapColor;
import net.minecraft.block.enums.Instrument;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;

public class FactoryBlocks {
    public static final ConveyorBlock CONVEYOR = register("conveyor", new ConveyorBlock(Block.Settings.create().hardness(3).nonOpaque()));
    public static final ConveyorBlock STICKY_CONVEYOR = register("sticky_conveyor", new ConveyorBlock(Block.Settings.create().hardness(3).nonOpaque()));
    public static final FunnelBlock FUNNEL = register("funnel", new FunnelBlock(Block.Settings.copy(Blocks.SPRUCE_TRAPDOOR).nonOpaque()));
    public static final SplitterBlock SPLITTER = register("splitter", new SplitterBlock(Block.Settings.create().mapColor(MapColor.STONE_GRAY).instrument(Instrument.BASEDRUM).requiresTool().strength(3.3F).nonOpaque()));
    public static final FanBlock FAN = register("fan", new FanBlock(Block.Settings.create().nonOpaque().hardness(1)));
    public static final SelectivePassthroughBlock METAL_GRID = register("metal_grid", new SelectivePassthroughBlock(Block.Settings.copy(Blocks.IRON_BLOCK).strength(4.0F, 3.0F).nonOpaque()));
    public static final HandCrankBlock HAND_CRANK = register("hand_crank", new HandCrankBlock(Block.Settings.create().hardness(1).nonOpaque()));
    public static final SteamEngineBlock STEAM_ENGINE = register("steam_engine", new SteamEngineBlock(Block.Settings.copy(SPLITTER).strength(4F).nonOpaque()));
    public static final GrinderBlock GRINDER = register("grinder", new GrinderBlock(Block.Settings.copy(SPLITTER)));
    public static final PressBlock PRESS = register("press", new PressBlock(Block.Settings.copy(SPLITTER)));
    public static final MixerBlock MIXER = register("mixer", new MixerBlock(Block.Settings.copy(SPLITTER)));
    public static final MCrafterBlock CRAFTER = register("crafter", new MCrafterBlock(Block.Settings.copy(SPLITTER)));
    public static final MinerBlock MINER = register("miner", new MinerBlock(Block.Settings.copy(SPLITTER)));
    public static final PlacerBlock PLACER = register("placer", new PlacerBlock(Block.Settings.copy(SPLITTER)));
    public static final PlanterBlock PLANTER = register("planter", new PlanterBlock(Block.Settings.copy(SPLITTER)));
    public static final AxleBlock AXLE = register("axle", new AxleBlock(Block.Settings.copy(Blocks.STRIPPED_OAK_WOOD).strength(2.5F).nonOpaque()));
    public static final AxleWithGearBlock AXLE_WITH_GEAR = register("axle_with_gear", new AxleWithGearBlock(Block.Settings.copy(Blocks.STRIPPED_OAK_WOOD).strength(2.5F).nonOpaque()));
    public static final AxleWithLargeGearBlock AXLE_WITH_LARGE_GEAR = register("axle_with_large_gear", new AxleWithLargeGearBlock(Block.Settings.copy(Blocks.STRIPPED_OAK_WOOD).strength(2.5F).nonOpaque()));
    public static final GearboxBlock GEARBOX = register("gearbox", new GearboxBlock(Block.Settings.copy(Blocks.STRIPPED_OAK_WOOD).strength(2.5F).nonOpaque()));
    public static final WindmillBlock WINDMILL = register("windmill", new WindmillBlock(Block.Settings.copy(Blocks.STRIPPED_OAK_WOOD).strength(2.5F).nonOpaque()));
    public static final ContainerBlock CONTAINER = register("wooden_container", new ContainerBlock(Block.Settings.copy(Blocks.CHEST).nonOpaque()));
    public static final CableBlock CABLE = register("cable", new CableBlock(Block.Settings.copy(Blocks.GLASS).breakInstantly().nonOpaque()));
    public static final DataProviderBlock ITEM_COUNTER = register("item_counter", new DataProviderBlock(AbstractBlock.Settings.copy(SPLITTER)));
    public static final RedstoneInputBlock REDSTONE_INPUT = register("redstone_input", new RedstoneInputBlock(AbstractBlock.Settings.copy(ITEM_COUNTER)));
    public static final RedstoneOutputBlock REDSTONE_OUTPUT = register("redstone_output", new RedstoneOutputBlock(AbstractBlock.Settings.copy(ITEM_COUNTER)));
    public static final ItemReaderBlock ITEM_READER = register("item_reader", new ItemReaderBlock(AbstractBlock.Settings.copy(ITEM_COUNTER)));
    public static final BlockObserverBlock BLOCK_OBSERVER = register("block_observer", new BlockObserverBlock(AbstractBlock.Settings.copy(ITEM_COUNTER)));

    public static final NixieTubeBlock NIXIE_TUBE = register("nixie_tube", new NixieTubeBlock(Block.Settings.copy(Blocks.GLASS).nonOpaque()));

    public static final NixieTubeControllerBlock NIXIE_TUBE_CONTROLLER = register("nixie_tube_controller", new NixieTubeControllerBlock(AbstractBlock.Settings.copy(ITEM_COUNTER)));

    public static final ElectricMotorBlock ELECTRIC_MOTOR = register("electric_motor", new ElectricMotorBlock(Block.Settings.create().hardness(2).nonOpaque()));
    public static final ElectricGeneratorBlock ELECTRIC_GENERATOR = register("electric_generator", new ElectricGeneratorBlock(Block.Settings.create().hardness(2).nonOpaque()));
    public static final WitherSkullGeneratorBlock WITHER_SKULL_GENERATOR = register("wither_skull_generator", new WitherSkullGeneratorBlock(Block.Settings.create().hardness(2).nonOpaque()));

    public static final CreativeMotorBlock CREATIVE_MOTOR = register("creative_motor", new CreativeMotorBlock(AbstractBlock.Settings.create().strength(-1, -1).nonOpaque()));
    public static final CreativeContainerBlock CREATIVE_CONTAINER = register("creative_container", new CreativeContainerBlock(AbstractBlock.Settings.create().strength(-1, -1).nonOpaque()));
    public static final InvertedRedstoneLampBlock INVERTED_REDSTONE_LAMP = register("inverted_redstone_lamp",
            new InvertedRedstoneLampBlock(AbstractBlock.Settings.copy(Blocks.REDSTONE_LAMP).luminance((state) -> {
                return (Boolean)state.get(Properties.LIT) ? 0 : 15;
            })));
    public static final LampBlock LAMP = register("colored_lamp", new LampBlock(Block.Settings.copy(Blocks.REDSTONE_LAMP).nonOpaque(), false));
    public static final LampBlock INVERTED_LAMP = register("inverted_colored_lamp", new LampBlock(Block.Settings.copy(INVERTED_REDSTONE_LAMP).nonOpaque(), true));

    public static final SmallLampBlock CAGED_LAMP = register("caged_lamp", new SmallLampBlock(Block.Settings.copy(Blocks.REDSTONE_LAMP).nonOpaque(), false));
    public static final SmallLampBlock INVERTED_CAGED_LAMP = register("inverted_caged_lamp", new SmallLampBlock(Block.Settings.copy(INVERTED_REDSTONE_LAMP).nonOpaque(), true));

    public static final TinyPotatoSpringBlock TINY_POTATO_SPRING = register("tiny_potato_spring", new TinyPotatoSpringBlock(AbstractBlock.Settings.create().strength(1).nonOpaque()));


    public static final RotationalDebugBlock ROTATION_DEBUG = register("rot_debug", new RotationalDebugBlock(AbstractBlock.Settings.create().strength(-1, -1)));
    public static final GreenScreenBlock GREEN_SCREEN = register("green_screen", new GreenScreenBlock(AbstractBlock.Settings.copy(Blocks.GREEN_WOOL)));


    public static void register() {

    }


    public static <T extends Block> T register(String path, T item) {
        return Registry.register(Registries.BLOCK, new Identifier(ModInit.ID, path), item);
    }
}
