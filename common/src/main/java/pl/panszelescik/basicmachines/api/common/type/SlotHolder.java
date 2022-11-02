package pl.panszelescik.basicmachines.api.common.type;

import it.unimi.dsi.fastutil.objects.*;
import net.minecraft.resources.ResourceLocation;
import pl.panszelescik.basicmachines.BasicMachinesMod;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Stream;

public enum SlotHolder {

    ONE_INPUT_ONE_OUTPUT(holder -> holder
            .addSlot(slot -> slot
                    .setInput()
                    .setX(56)
                    .setY(35))
            .addSlot(slot -> slot
                    .setOutput()
                    .setX(x -> x + 60)
                    .setPreviousY()),
            BasicMachinesMod.id("textures/gui/container/one_input_one_output.png")),
    ;

    private final ResourceLocation texture;
    private final Map<SlotType, List<MachineSlot>> slots;
    private final int slotsCount;

    SlotHolder(Function<SlotHolder.Builder, SlotHolder.Builder> holder, ResourceLocation texture) {
        this(holder.apply(new SlotHolder.Builder()), texture);
    }

    SlotHolder(SlotHolder.Builder builder, ResourceLocation texture) {
        this.texture = texture;
        this.slots = builder.build();
        this.slotsCount = this.slots.values().stream().mapToInt(List::size).sum();
    }

    public ResourceLocation getTexture() {
        return texture;
    }

    public int getCount() {
        return this.slotsCount;
    }

    public List<MachineSlot> getSlotsList(SlotType type) {
        return this.slots.computeIfAbsent(type, t -> Collections.emptyList());
    }

    public List<MachineSlot> getSlotsList() {
        return this.getSlotsStream().toList();
    }

    public Stream<MachineSlot> getSlotsStream() {
        return this.slots.values()
                .stream()
                .flatMap(List::stream);
    }

    public int getCount(SlotType slotType) {
        return this.getSlotsList(slotType).size();
    }

    public int[] getSlots(SlotType slotType) {
        return this.getSlotsList(slotType)
                .stream()
                .mapToInt(MachineSlot::id)
                .toArray();
    }

    public int[] getSlots() {
        return this.getSlotsStream()
                .mapToInt(MachineSlot::id).
                toArray();
    }

    public MachineSlot getSlot(int id) {
        return this.getSlotsStream()
                .filter(slot -> slot.id() == id)
                .findFirst()
                .orElseThrow();
    }

    public static class Builder {

        private static final Function<SlotType, List<MachineSlot>> NEW_LIST_FUNCTION = (k) -> new ObjectArrayList<>();
        private final Map<SlotType, List<MachineSlot>> slots = new Object2ObjectOpenHashMap<>();

        public SlotHolder.Builder addSlot(Function<MachineSlot.Builder, MachineSlot.Builder> slot) {
            return this.addSlot(slot.apply(new MachineSlot.Builder()));
        }

        public SlotHolder.Builder addSlot(MachineSlot.Builder builder) {
            var slot = builder.build(this.getOptionalLastSlot());
            this.slots.computeIfAbsent(slot.slotType(), NEW_LIST_FUNCTION).add(slot);
            return this;
        }

        private Optional<MachineSlot> getOptionalLastSlot() {
            return this.slots.values()
                    .stream()
                    .flatMap(List::stream)
                    .max(Comparator.comparingInt(MachineSlot::id));
        }

        public Map<SlotType, List<MachineSlot>> build() {
            return this.slots;
        }
    }
}
