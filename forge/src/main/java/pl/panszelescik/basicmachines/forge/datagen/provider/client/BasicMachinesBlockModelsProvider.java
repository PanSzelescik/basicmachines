package pl.panszelescik.basicmachines.forge.datagen.provider.client;

import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import pl.panszelescik.basicmachines.BasicMachinesMod;
import pl.panszelescik.basicmachines.init.BasicMachinesBlocks;
import pl.panszelescik.basicmachines.init.BasicMachinesTypes;

public class BasicMachinesBlockModelsProvider extends BlockModelProvider {

    public BasicMachinesBlockModelsProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, BasicMachinesMod.MOD_ID, existingFileHelper);
    }

    private void block(RegistrySupplier<Block> block) {
        var name = block.getId().getPath();
        var blockName = "block/" + name;
        cubeAll(name, modLoc(blockName));
    }

    @Override
    protected void registerModels() {
        block(BasicMachinesBlocks.IRON_CASING);

        var ironBlock = mcLoc("block/iron_block");
        for (var machineType : BasicMachinesTypes.MACHINE_TYPES) {
            orientable(machineType.getName(), ironBlock, modLoc("block/" + machineType.getResourceLocation().getPath()), ironBlock);
            orientable(machineType.getName() + "_on", ironBlock, modLoc("block/" + machineType.getResourceLocationOn().getPath()), ironBlock);
        }
    }
}
