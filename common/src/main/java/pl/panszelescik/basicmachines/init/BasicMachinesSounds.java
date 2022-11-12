package pl.panszelescik.basicmachines.init;

import dev.architectury.registry.registries.DeferredRegister;
import net.minecraft.core.Registry;
import net.minecraft.sounds.SoundEvent;
import pl.panszelescik.basicmachines.BasicMachinesMod;

public class BasicMachinesSounds {

    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(BasicMachinesMod.MOD_ID, Registry.SOUND_EVENT_REGISTRY);

    public static final SoundEvent CRUSHER_WORK = registerSound("block.crusher.work");

    private BasicMachinesSounds() {
    }

    private static SoundEvent registerSound(String name) {
        var location = BasicMachinesMod.id(name);
        var soundEvent = new SoundEvent(location);
        SOUNDS.register(location, () -> soundEvent);
        return soundEvent;
    }

    public static void register() {
        SOUNDS.register();
    }
}
