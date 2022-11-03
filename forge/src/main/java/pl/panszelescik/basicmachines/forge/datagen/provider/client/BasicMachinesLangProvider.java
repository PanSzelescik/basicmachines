package pl.panszelescik.basicmachines.forge.datagen.provider.client;

import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.LanguageProvider;
import pl.panszelescik.basicmachines.BasicMachinesMod;
import pl.panszelescik.basicmachines.api.common.type.UpgradeType;
import pl.panszelescik.basicmachines.init.BasicMachinesTypes;

import java.util.Arrays;
import java.util.stream.Collectors;

public class BasicMachinesLangProvider extends LanguageProvider {

    public BasicMachinesLangProvider(DataGenerator gen) {
        super(gen, BasicMachinesMod.MOD_ID, "en_us");
    }

    private void addItem(RegistrySupplier<? extends Item> item) {
        addItem(item, this.generateName(item.getId()));
    }

    private void addBlock(RegistrySupplier<? extends Block> block) {
        addBlock(block, this.generateName(block.getId()));
    }

    private String generateName(ResourceLocation location) {
        return Arrays.stream(location.getPath().split("_"))
                .map(s -> Character.toUpperCase(s.charAt(0)) + s.substring(1))
                .collect(Collectors.joining(" "));
    }

    @Override
    protected void addTranslations() {
        add("itemGroup." + BasicMachinesMod.MOD_ID + ".creative_tab", "Basic Machines");

        for (var upgradeItem : UpgradeType.values()) {
            addItem(upgradeItem.getItemSupplier());
        }

        for (var machineType : BasicMachinesTypes.MACHINE_TYPES) {
            addBlock(machineType.getBlockSupplier());
        }

        add("tooltip.basicmachines.energy", "%s/%s %s");
        add("tooltip.basicmachines.energy_usage", "%s %s/t");
        add("tooltip.basicmachines.progress", "%s/%s ticks");
    }
}
