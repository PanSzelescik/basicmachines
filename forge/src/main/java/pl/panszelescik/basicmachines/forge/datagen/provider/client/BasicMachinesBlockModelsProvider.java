package pl.panszelescik.basicmachines.forge.datagen.provider.client;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.BlockModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import pl.panszelescik.basicmachines.BasicMachinesMod;
import pl.panszelescik.basicmachines.init.BasicMachinesTypes;

public class BasicMachinesBlockModelsProvider extends BlockModelProvider {

    public BasicMachinesBlockModelsProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, BasicMachinesMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        for (var machineType : BasicMachinesTypes.MACHINE_TYPES) {
            orientable(machineType.getName(), machineType.getResourceLocation(), mcLoc("iron_block"), mcLoc("iron_block"));
            orientable(machineType.getName() + "_on", machineType.getResourceLocationOn(), mcLoc("iron_block"), mcLoc("iron_block"));
        }
    }
}
