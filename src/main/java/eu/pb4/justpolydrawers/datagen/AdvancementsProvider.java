package eu.pb4.justpolydrawers.datagen;

import eu.pb4.justpolydrawers.advancement.FactoryTriggers;
import eu.pb4.justpolydrawers.advancement.TriggerCriterion;
import eu.pb4.justpolydrawers.item.FactoryItemTags;
import eu.pb4.justpolydrawers.item.FactoryItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricAdvancementProvider;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementEntry;
import net.minecraft.advancement.AdvancementFrame;
import net.minecraft.advancement.criterion.InventoryChangedCriterion;
import net.minecraft.item.Items;
import net.minecraft.predicate.item.ItemPredicate;
import net.minecraft.text.Text;

import java.util.function.Consumer;

import static eu.pb4.justpolydrawers.util.FactoryUtil.id;

class AdvancementsProvider extends FabricAdvancementProvider {

    protected AdvancementsProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateAdvancement(Consumer<AdvancementEntry> exporter) {
        var root = Advancement.Builder.create()
                .display(
                        FactoryItems.MOD_ICON,
                        Text.translatable("advancements.polyfactory.root.title"),
                        Text.translatable("advancements.polyfactory.root.description"),
                        id("textures/advancements/background.png"),
                        AdvancementFrame.TASK,
                        false,
                        false,
                        false
                )
                .criterion("any_item", InventoryChangedCriterion.Conditions.items(
                        ItemPredicate.Builder.create().tag(FactoryItemTags.ROOT_ADVANCEMENT)
                ))
                .build(exporter, "polyfactory:main/root");

        this.base(root, exporter);
        this.taters(root, exporter);
    }

    private void taters(AdvancementEntry root, Consumer<AdvancementEntry> exporter) {
        var tater16 = Advancement.Builder.create()
                .parent(root)
                .display(
                        Items.POTATO,
                        Text.translatable("advancements.polyfactory.tater_16.title"),
                        Text.translatable("advancements.polyfactory.tater_16.description"),
                        null,
                        AdvancementFrame.GOAL,
                        true,
                        true,
                        true
                )
                .criterion("use", TriggerCriterion.of(FactoryTriggers.TATER_16))
                .build(exporter, "polyfactory:main/taters/16");

        var tater128 = Advancement.Builder.create()
                .parent(tater16)
                .display(
                        Items.BAKED_POTATO,
                        Text.translatable("advancements.polyfactory.tater_128.title"),
                        Text.translatable("advancements.polyfactory.tater_128.description"),
                        null,
                        AdvancementFrame.CHALLENGE,
                        true,
                        true,
                        true
                )
                .criterion("use", TriggerCriterion.of(FactoryTriggers.TATER_128))
                .build(exporter, "polyfactory:main/taters/128");

    }

    private void base(AdvancementEntry root, Consumer<AdvancementEntry> exporter) {
        // Start

        // Grinder -> Gravel



        // Grinder -> Steel
    }
}
