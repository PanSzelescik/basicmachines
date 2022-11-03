package pl.panszelescik.basicmachines.forge.datagen.provider.client;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.common.data.ExistingFileHelper;
import pl.panszelescik.basicmachines.BasicMachinesMod;
import pl.panszelescik.basicmachines.api.common.block.MachineBlock;
import pl.panszelescik.basicmachines.init.BasicMachinesTypes;

public class BasicMachinesBlockstateProvider extends BlockStateProvider {

    public BasicMachinesBlockstateProvider(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, BasicMachinesMod.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        for (var machineType : BasicMachinesTypes.MACHINE_TYPES) {
            getVariantBuilder(machineType.getBlockSupplier().get()).forAllStatesExcept(state ->
                    ConfiguredModel.builder()
                            .modelFile(models().getExistingFile(modLoc("block/" + machineType.getResourceLocation().getPath() + (state.getValue(MachineBlock.LIT) ? "_on" : ""))))
                            .rotationY(((state.getValue(MachineBlock.FACING).get2DDataValue() & 3) * 90 + 180) % 360)
                            .build());
        }
    }
}
