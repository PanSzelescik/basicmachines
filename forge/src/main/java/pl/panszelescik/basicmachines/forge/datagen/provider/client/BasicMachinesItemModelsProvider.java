package pl.panszelescik.basicmachines.forge.datagen.provider.client;

import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import pl.panszelescik.basicmachines.BasicMachinesMod;
import pl.panszelescik.basicmachines.api.common.type.UpgradeType;
import pl.panszelescik.basicmachines.init.BasicMachinesBlocks;
import pl.panszelescik.basicmachines.init.BasicMachinesItems;
import pl.panszelescik.basicmachines.init.BasicMachinesTypes;

public class BasicMachinesItemModelsProvider extends ItemModelProvider {

    public BasicMachinesItemModelsProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, BasicMachinesMod.MOD_ID, existingFileHelper);
    }

    private void basicItem(RegistrySupplier<? extends Item> item) {
        basicItem(item.get());
    }

    private void basicBlockItem(RegistrySupplier<? extends Block> block) {
        String id = block.getId().getPath();
        withExistingParent(id, modLoc("block/" + id));
    }

    @Override
    protected void registerModels() {
        basicBlockItem(BasicMachinesBlocks.IRON_CASING);

        basicItem(BasicMachinesItems.BLANK_UPGRADE);
        for (var upgradeItem : UpgradeType.values()) {
            basicItem(upgradeItem.getItemSupplier());
        }

        for (var machineType : BasicMachinesTypes.MACHINE_TYPES) {
            basicBlockItem(machineType.getBlockSupplier());
        }
    }
}
