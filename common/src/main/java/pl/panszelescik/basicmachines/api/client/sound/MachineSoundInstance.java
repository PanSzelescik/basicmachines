package pl.panszelescik.basicmachines.api.client.sound;

import it.unimi.dsi.fastutil.objects.Object2ObjectMaps;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Container;
import net.minecraft.world.item.crafting.Recipe;
import pl.panszelescik.basicmachines.api.common.block.entity.MachineBlockEntity;

import java.util.Map;

public class MachineSoundInstance<R extends Recipe<Container>> extends AbstractTickableSoundInstance {

    private static final Map<BlockPos, MachineSoundInstance<?>> PLAYING_FOR = Object2ObjectMaps.synchronize(new Object2ObjectOpenHashMap<>());
    private final MachineBlockEntity<R> machineBlockEntity;

    public MachineSoundInstance(MachineBlockEntity<R> machineBlockEntity) {
        super(machineBlockEntity.machineType.getSoundEvent(), SoundSource.BLOCKS, machineBlockEntity.getLevel() == null ? RandomSource.create() : machineBlockEntity.getLevel().random);
        this.machineBlockEntity = machineBlockEntity;
        this.looping = true;

        var pos = machineBlockEntity.getBlockPos();
        this.x = pos.getX() + 0.5f;
        this.y = pos.getY() + 0.5f;
        this.z = pos.getZ() + 0.5f;

        PLAYING_FOR.put(pos, this);
    }

    @Override
    public void tick() {
        if (this.machineBlockEntity.isRemoved() || !this.machineBlockEntity.isProcessing()) {
            PLAYING_FOR.remove(this.machineBlockEntity.getBlockPos());
            this.stop();
        }
    }

    public static <R extends Recipe<Container>> void playSound(MachineBlockEntity<R> machineBlockEntity) {
        if (!PLAYING_FOR.containsKey(machineBlockEntity.getBlockPos())) {
            Minecraft.getInstance().getSoundManager().queueTickingSound(new MachineSoundInstance<>(machineBlockEntity));
        }
    }
}
