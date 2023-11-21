package eu.pb4.justpolydrawers;

import eu.pb4.factorytools.impl.DebugData;
import eu.pb4.justpolydrawers.advancement.FactoryAdvancementCriteria;
import eu.pb4.justpolydrawers.polydex.PolydexCompat;
import eu.pb4.justpolydrawers.ui.GuiTextures;
import eu.pb4.justpolydrawers.ui.UiResourceCreator;
import eu.pb4.justpolydrawers.util.*;
import eu.pb4.polymer.resourcepack.api.PolymerResourcePackUtils;
import eu.pb4.justpolydrawers.block.FactoryBlockEntities;
import eu.pb4.justpolydrawers.block.FactoryBlocks;
import eu.pb4.justpolydrawers.item.FactoryItems;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ModInit implements ModInitializer {
	public static final String ID = "justpolydrawers";
	public static final String MOD_NAME = "Just Poly Drawers";
	public static final String VERSION = FabricLoader.getInstance().getModContainer(ID).get().getMetadata().getVersion().getFriendlyString();
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);
    public static final boolean DEV_ENV = FabricLoader.getInstance().isDevelopmentEnvironment();
    public static final boolean DEV_MODE = VERSION.contains("-dev.") || DEV_ENV;
    @SuppressWarnings("PointlessBooleanExpression")
	public static final boolean DYNAMIC_ASSETS = true && DEV_ENV;

    public static Identifier id(String path) {
		return new Identifier(ID, path);
	}

	@Override
	public void onInitialize() {
		if (VERSION.contains("-dev.")) {
			LOGGER.warn("=====================================================");
			LOGGER.warn(String.format("You are using development version of {0}}!", MOD_NAME));
			LOGGER.warn("Support is limited, as features might be unfinished!");
			LOGGER.warn("You are on your own!");
			LOGGER.warn("=====================================================");
		}

		FactoryBlocks.register();
		FactoryBlockEntities.register();
//		FactoryEnchantments.register();
		FactoryItems.register();
//		FactoryEntities.register();
//		FactoryNodes.register();
//		FactoryRecipeTypes.register();
//		FactoryRecipeSerializers.register();
//		FactoryLootTables.register();
		FactoryCommands.register();
		FactoryUtil.register(); // need
//		FactoryItemPredicates.register();
		FactoryAdvancementCriteria.register(); // need
		DebugData.register();
//		PotatoWisdom.load();

//		ConveyorModel.registerAssetsEvents();
//		CableModel.registerAssetsEvents();
//		initModels();
		UiResourceCreator.setup();
		GuiTextures.register();
		PolydexCompat.register();
		PolymerResourcePackUtils.addModAssets(ID);
		PolymerResourcePackUtils.markAsRequired();

		// Todo: Move it out
		(new eu.pb4.factorytools.impl.ModInit()).onInitialize();

		ServerPlayConnectionEvents.JOIN.register(FactorySecrets::onJoin);
	}

	@SuppressWarnings("ResultOfMethodCallIgnored")
	private void initModels() {
//		AxleBlock.Model.ITEM_MODEL.getItem();
//		WindmillBlock.Model.MODEL.getItem();
//		AxleWithGearBlock.Model.ITEM_MODEL_1.getItem();
//		AxleWithLargeGearBlock.Model.GEAR_MODEL.getItem();
//		PlanterBlock.Model.OUTPUT_1.getItem();
//		TinyPotatoSpringBlock.Model.BASE_MODEL.getItem();
//		RedstoneOutputBlock.Model.OUTPUT_OVERLAY.item();
//		GenericParts.SMALL_GEAR.isEmpty();
	}
}
