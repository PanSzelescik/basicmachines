package pl.panszelescik.basicmachines.api.common.type;

import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.resources.ResourceLocation;
import pl.panszelescik.basicmachines.BasicMachinesMod;
import pl.panszelescik.basicmachines.api.common.item.UpgradeItem;
import pl.panszelescik.basicmachines.init.BasicMachinesItems;

import java.util.Locale;

public enum UpgradeType {

    SPEED,
    ENERGY,
    ;

    private final ResourceLocation resourceLocation;
    private final RegistrySupplier<UpgradeItem> item;

    UpgradeType() {
        this.resourceLocation = BasicMachinesMod.id(this.name().toLowerCase(Locale.ROOT) + "_upgrade");
        this.item = this.registerItem();
    }

    public ResourceLocation getResourceLocation() {
        return this.resourceLocation;
    }

    public UpgradeItem getItem() {
        return this.getItemSupplier().get();
    }

    public RegistrySupplier<UpgradeItem> getItemSupplier() {
        return this.item;
    }

    private RegistrySupplier<UpgradeItem> registerItem() {
        return BasicMachinesItems.ITEMS.register(this.getResourceLocation(), () -> new UpgradeItem(this));
    }
}
