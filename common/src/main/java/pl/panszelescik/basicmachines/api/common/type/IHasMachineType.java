package pl.panszelescik.basicmachines.api.common.type;

import net.minecraft.world.Container;
import net.minecraft.world.item.crafting.Recipe;

public interface IHasMachineType<R extends Recipe<Container>> {

    MachineType<R> getMachineType();
}
