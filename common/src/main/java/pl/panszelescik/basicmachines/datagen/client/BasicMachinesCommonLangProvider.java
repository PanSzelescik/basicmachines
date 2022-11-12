package pl.panszelescik.basicmachines.datagen.client;

import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import pl.panszelescik.basicmachines.api.common.type.UpgradeType;
import pl.panszelescik.basicmachines.init.BasicMachinesBlocks;
import pl.panszelescik.basicmachines.init.BasicMachinesItems;
import pl.panszelescik.basicmachines.init.BasicMachinesTypes;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class BasicMachinesCommonLangProvider {

    public Map<CreativeModeTab, String> getCreativeModeTabTranslations() {
        return Map.of(
                BasicMachinesItems.CREATIVE_TAB, "Basic Machines"
        );
    }

    public Map<Block, String> getBlockTranslations() {
        var map = new HashMap<Block, String>();

        addBlock(map, BasicMachinesBlocks.IRON_CASING);

        for (var machineType : BasicMachinesTypes.MACHINE_TYPES) {
            addBlock(map, machineType.getBlockSupplier());
        }

        return map;
    }

    public Map<Item, String> getItemTranslations() {
        var map = new HashMap<Item, String>();

        addItem(map, BasicMachinesItems.BLANK_UPGRADE);

        for (var upgradeItem : UpgradeType.values()) {
            addItem(map, upgradeItem.getItemSupplier());
        }

        return map;
    }

    public Map<String, String> getStringTranslations() {
        return Map.of(
                "tooltip.basicmachines.energy", "%s/%s %s",
                "tooltip.basicmachines.energy_usage", "%s %s/t",
                "tooltip.basicmachines.progress", "%s/%s ticks"
        );
    }

    private void addBlock(Map<Block, String> map, RegistrySupplier<? extends Block> block) {
        map.put(block.get(), this.generateName(block.getId()));
    }

    private void addItem(Map<Item, String> map, RegistrySupplier<? extends Item> item) {
        map.put(item.get(), this.generateName(item.getId()));
    }

    private String generateName(ResourceLocation location) {
        return Arrays.stream(location.getPath().split("_"))
                .map(s -> Character.toUpperCase(s.charAt(0)) + s.substring(1))
                .collect(Collectors.joining(" "));
    }
}
