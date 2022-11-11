package pl.panszelescik.basicmachines.fabric.datagen.provider.client;

import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import pl.panszelescik.basicmachines.BasicMachinesMod;
import pl.panszelescik.basicmachines.datagen.client.BasicMachinesCommonLangProvider;

public class BasicMachinesLangProvider extends FabricLanguageProvider {

    private final BasicMachinesCommonLangProvider provider;

    public BasicMachinesLangProvider(FabricDataGenerator dataGenerator) {
        super(dataGenerator, "en_us");
        this.provider = new BasicMachinesCommonLangProvider();
    }

    @Override
    public void generateTranslations(TranslationBuilder translationBuilder) {
        for (var entry : this.provider.getCreativeModeTabTranslations().entrySet()) {
            translationBuilder.add("itemGroup." + BasicMachinesMod.MOD_ID + ".items", entry.getValue());
        }

        for (var entry : this.provider.getBlockTranslations().entrySet()) {
            translationBuilder.add(entry.getKey(), entry.getValue());
        }

        for (var entry : this.provider.getItemTranslations().entrySet()) {
            translationBuilder.add(entry.getKey(), entry.getValue());
        }

        for (var entry : this.provider.getStringTranslations().entrySet()) {
            translationBuilder.add(entry.getKey(), entry.getValue());
        }
    }
}
