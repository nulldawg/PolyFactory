package eu.pb4.justpolydrawers.item;

import eu.pb4.justpolydrawers.ModInit;
import eu.pb4.justpolydrawers.item.enchantment.IgnoreMovementEnchantment;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class FactoryEnchantments {
    public static final Enchantment IGNORE_MOVEMENT = register("ignore_movement", new IgnoreMovementEnchantment());
    public static void register() {

    }


    public static <T extends Enchantment> T register(String path, T item) {
        Registry.register(Registries.ENCHANTMENT, new Identifier(ModInit.ID, path), item);
        return item;
    }
}
