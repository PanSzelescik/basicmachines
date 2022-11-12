package pl.panszelescik.basicmachines.fabric.datagen.provider.client;

import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.ItemModelGenerators;
import net.minecraft.data.models.model.ModelTemplates;
import net.minecraft.data.models.model.TexturedModel;
import pl.panszelescik.basicmachines.api.common.type.UpgradeType;
import pl.panszelescik.basicmachines.init.BasicMachinesBlocks;
import pl.panszelescik.basicmachines.init.BasicMachinesItems;
import pl.panszelescik.basicmachines.init.BasicMachinesTypes;

public class BasicMachinesModelsProvider extends FabricModelProvider {

    public BasicMachinesModelsProvider(FabricDataGenerator dataGenerator) {
        super(dataGenerator);
    }

    @Override
    public void generateBlockStateModels(BlockModelGenerators blockStateModelGenerator) {
        blockStateModelGenerator.createTrivialCube(BasicMachinesBlocks.IRON_CASING.get());

        for (var machineType : BasicMachinesTypes.MACHINE_TYPES) {
            blockStateModelGenerator.createFurnace(machineType.getBlock(), TexturedModel.ORIENTABLE_ONLY_TOP);
        }
    }

    @Override
    public void generateItemModels(ItemModelGenerators itemModelGenerator) {
        itemModelGenerator.generateFlatItem(BasicMachinesItems.BLANK_UPGRADE.get(), ModelTemplates.FLAT_ITEM);

        for (var upgradeItem : UpgradeType.values()) {
            itemModelGenerator.generateFlatItem(upgradeItem.getItem(), ModelTemplates.FLAT_ITEM);
        }
    }
}
