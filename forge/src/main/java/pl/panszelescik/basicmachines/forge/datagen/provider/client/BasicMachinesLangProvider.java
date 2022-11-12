package pl.panszelescik.basicmachines.forge.datagen.provider.client;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.LanguageProvider;
import pl.panszelescik.basicmachines.BasicMachinesMod;
import pl.panszelescik.basicmachines.datagen.client.BasicMachinesCommonLangProvider;

public class BasicMachinesLangProvider extends LanguageProvider {

    private final BasicMachinesCommonLangProvider provider;

    public BasicMachinesLangProvider(DataGenerator gen) {
        super(gen, BasicMachinesMod.MOD_ID, "en_us");
        this.provider = new BasicMachinesCommonLangProvider();
    }

    @Override
    protected void addTranslations() {
        for (var entry : this.provider.getCreativeModeTabTranslations().entrySet()) {
            add("itemGroup." + BasicMachinesMod.MOD_ID + ".items", entry.getValue());
        }

        for (var entry : this.provider.getBlockTranslations().entrySet()) {
            add(entry.getKey(), entry.getValue());
        }

        for (var entry : this.provider.getItemTranslations().entrySet()) {
            add(entry.getKey(), entry.getValue());
        }

        for (var entry : this.provider.getStringTranslations().entrySet()) {
            add(entry.getKey(), entry.getValue());
        }
    }
}
