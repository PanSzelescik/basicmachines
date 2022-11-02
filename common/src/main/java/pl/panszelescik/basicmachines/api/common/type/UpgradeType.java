package pl.panszelescik.basicmachines.api.common.type;

import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.resources.ResourceLocation;
import pl.panszelescik.basicmachines.BasicMachinesMod;
import pl.panszelescik.basicmachines.api.common.item.UpgradeItem;

import java.util.Locale;

public enum UpgradeType {

    SPEED,
    ENERGY,
    ;

    private final ResourceLocation resourceLocation;
    private RegistrySupplier<UpgradeItem> item;

    UpgradeType() {
        this.resourceLocation = BasicMachinesMod.id(this.name().toLowerCase(Locale.ROOT) + "_upgrade");

        this.registerItem();
    }

    public ResourceLocation getResourceLocation() {
        return this.resourceLocation;
    }

    public UpgradeItem getItem() {
        return this.item.get();
    }

    private void registerItem() {
        this.item = BasicMachinesMod.ITEMS.register(this.getResourceLocation(), () -> new UpgradeItem(this));
    }
}
