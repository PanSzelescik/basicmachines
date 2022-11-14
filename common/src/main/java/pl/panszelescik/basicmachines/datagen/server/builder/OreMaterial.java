package pl.panszelescik.basicmachines.datagen.server.builder;

import net.minecraft.resources.ResourceLocation;
import pl.panszelescik.basicmachines.BasicMachinesPlatform;

import java.util.Arrays;
import java.util.List;

public record OreMaterial(String name) {

    public static final List<String> MATERIALS_NAMES = Arrays.asList("iron", "gold", "copper", "tin", "silver", "lead", "osmium", "nickel", "zinc");
    public static final List<OreMaterial> MATERIALS = MATERIALS_NAMES.stream().map(OreMaterial::new).toList();

    public ResourceLocation getOreTag() {
        return BasicMachinesPlatform.getOreTag(this.name);
    }

    public ResourceLocation getRawOreTag() {
        return BasicMachinesPlatform.getRawOreTag(this.name);
    }

    public ResourceLocation getDustTag() {
        return BasicMachinesPlatform.getDustTag(this.name);
    }

    public ResourceLocation getIngotTag() {
        return BasicMachinesPlatform.getIngotTag(this.name);
    }
}
