package pl.panszelescik.basicmachines.fabric.client;

import net.fabricmc.api.ClientModInitializer;
import pl.panszelescik.basicmachines.api.common.type.MachineType;

public class BasicMachinesFabricClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        MachineType.registerAllClient();
    }
}
