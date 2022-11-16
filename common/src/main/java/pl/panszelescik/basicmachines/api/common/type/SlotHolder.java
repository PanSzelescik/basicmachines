package pl.panszelescik.basicmachines.api.common.type;

import com.google.common.base.Suppliers;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectList;
import it.unimi.dsi.fastutil.objects.ObjectLists;
import net.minecraft.resources.ResourceLocation;
import pl.panszelescik.basicmachines.BasicMachinesMod;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

public enum SlotHolder {

    ONE_INPUT_ONE_OUTPUT(builder -> builder
            .addSlot(slot -> slot
                    .setInput()
                    .setX(56)
                    .setY(35))
            .addSlot(slot -> slot
                    .setOutput()
                    .setX(x -> x + 60)
                    .setPreviousY())
            .addSlot(slot -> slot
                    .setEnergy()
                    .setX(8)
                    .setY(62))
            .addSlot(slot -> slot
                    .setUpgrade()
                    .setX(152)
                    .setY(8))
            .addSlot(slot -> slot
                    .setUpgrade()
                    .setPreviousX()
                    .setNextY())
            .addSlot(slot -> slot
                    .setUpgrade()
                    .setPreviousX()
                    .setNextY())
            .addSlot(slot -> slot
                    .setUpgrade()
                    .setPreviousX()
                    .setNextY()),
            BasicMachinesMod.id("textures/gui/container/one_input_one_output.png")),
    ;

    private static final Function<SlotType, ObjectList<MachineSlot>> EMPTY_LIST_FUNCTION = t -> ObjectLists.emptyList();

    private final ResourceLocation texture;
    private final Map<SlotType, ObjectList<MachineSlot>> slots;
    private final Supplier<ObjectArrayList<MachineSlot>> allSlots;
    private final Supplier<int[]> allSlotsIntArray;
    private final Supplier<int[]> allInputSlotsForShiftClick;
    private final int slotsCount;

    SlotHolder(UnaryOperator<Builder> builder, ResourceLocation texture) {
        this(builder.apply(new SlotHolder.Builder()), texture);
    }

    SlotHolder(SlotHolder.Builder builder, ResourceLocation texture) {
        this.texture = texture;
        this.slots = builder.build();
        this.allSlots = Suppliers.memoize(this::getAllSlotsFunction);
        this.allSlotsIntArray = Suppliers.memoize(this::getAllSlotsIntArrayFunction);
        this.allInputSlotsForShiftClick = Suppliers.memoize(this::getAllInputSlotsForShiftClickFunction);
        this.slotsCount = this.slots
                .values()
                .stream()
                .mapToInt(List::size)
                .sum();
    }

    public ResourceLocation getTexture() {
        return texture;
    }

    public int getCount() {
        return this.slotsCount;
    }

    public Stream<MachineSlot> getSlotsStream() {
        return this.slots.values()
                .stream()
                .flatMap(List::stream)
                .sorted(Comparator.comparingInt(MachineSlot::id));
    }

    public ObjectList<MachineSlot> getSlots(SlotType slotType) {
        return this.slots.computeIfAbsent(slotType, EMPTY_LIST_FUNCTION);
    }

    public MachineSlot getSlot(int id) {
        return this.getAllSlots().get(id);
    }

    public ObjectArrayList<MachineSlot> getAllSlots() {
        return this.allSlots.get();
    }

    public int[] getAllSlotsIntArray() {
        return this.allSlotsIntArray.get();
    }

    public int[] getAllInputSlotsForShiftClick() {
        return this.allInputSlotsForShiftClick.get();
    }

    // INTERNAL
    private ObjectArrayList<MachineSlot> getAllSlotsFunction() {
        return this.getSlotsStream()
                .collect(ObjectArrayList.toList());
    }

    private int[] getAllSlotsIntArrayFunction() {
        return this.getSlotsStream()
                .mapToInt(MachineSlot::id)
                .toArray();
    }

    private int[] getAllInputSlotsForShiftClickFunction() {
        return this.getSlotsStream()
                .filter(slot -> slot.slotType() != SlotType.OUTPUT)
                .sorted((slot1, slot2) -> SlotType.orderingForShiftClickInsert().compare(slot1.slotType(), slot2.slotType()))
                .mapToInt(MachineSlot::id)
                .toArray();
    }

    public static class Builder {

        private static final Function<SlotType, ObjectList<MachineSlot>> NEW_LIST_FUNCTION = (k) -> new ObjectArrayList<>();
        private final Map<SlotType, ObjectList<MachineSlot>> slots = new Object2ObjectOpenHashMap<>();

        public SlotHolder.Builder addSlot(UnaryOperator<MachineSlot.Builder> slot) {
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

        public Map<SlotType, ObjectList<MachineSlot>> build() {
            return this.slots;
        }
    }
}
