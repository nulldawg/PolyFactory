package eu.pb4.justpolydrawers.loottable;

import eu.pb4.justpolydrawers.ModInit;
import net.minecraft.loot.function.LootFunctionType;
import net.minecraft.loot.provider.number.LootNumberProviderType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class FactoryLootTables {
    public static void register() { // TODO-nulldawg
//        register("copy_color", CopyColorLootFunction.TYPE);
    }


    public static <T extends LootNumberProviderType> T register(String path, T item) {
        return Registry.register(Registries.LOOT_NUMBER_PROVIDER_TYPE, new Identifier(ModInit.ID, path), item);
    }

    public static <T extends LootFunctionType> T register(String path, T item) {
        return Registry.register(Registries.LOOT_FUNCTION_TYPE, new Identifier(ModInit.ID, path), item);
    }
}
