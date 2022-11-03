package pl.panszelescik.basicmachines.fabric.client;

import net.fabricmc.api.ClientModInitializer;
import pl.panszelescik.basicmachines.init.BasicMachinesTypes;

public class BasicMachinesFabricClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        BasicMachinesTypes.registerClient();
    }
}
