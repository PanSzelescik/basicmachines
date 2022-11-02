package pl.panszelescik.basicmachines.api.common.type;

import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import pl.panszelescik.basicmachines.api.common.block.inventory.menu.slot.MachineMenuSlot;

import java.util.Optional;
import java.util.function.IntUnaryOperator;

public record MachineSlot(SlotType slotType, int id, int x, int y) {

    public static final int SIZE = 18;

    public boolean canInsert(ItemStack itemStack) {
        return this.slotType.canInsert(itemStack);
    }

    public boolean canExtract() {
        return this.slotType.canExtract;
    }

    public boolean canAutoInsert(ItemStack itemStack) {
        return this.slotType.canAutoInsert(itemStack);
    }

    public boolean canAutoExtract() {
        return this.slotType.canAutoExtract;
    }

    public MachineMenuSlot toMenuSlot(Container container) {
        return new MachineMenuSlot(container, this);
    }

    public static class Builder {

        private SlotType slotType;
        private IntUnaryOperator x;
        private IntUnaryOperator y;

        public Builder setSlotType(SlotType slotType) {
            this.slotType = slotType;
            return this;
        }

        public Builder setInput() {
            return this.setSlotType(SlotType.INPUT);
        }

        public Builder setOutput() {
            return this.setSlotType(SlotType.OUTPUT);
        }

        public Builder setUpgrade() {
            return this.setSlotType(SlotType.UPGRADE);
        }

        public Builder setEnergy() {
            return this.setSlotType(SlotType.ENERGY);
        }

        public Builder setX(IntUnaryOperator x) {
            this.x = x;
            return this;
        }

        public Builder setY(IntUnaryOperator y) {
            this.y = y;
            return this;
        }

        public Builder setX(int x) {
            return this.setX(a -> x);
        }

        public Builder setY(int y) {
            return this.setY(a -> y);
        }

        public Builder setPreviousX() {
            return this.setX(IntUnaryOperator.identity());
        }

        public Builder setPreviousY() {
            return this.setY(IntUnaryOperator.identity());
        }

        public MachineSlot build(Optional<MachineSlot> optionalPreviousSlot) {
            var previousId = optionalPreviousSlot.map(MachineSlot::id).orElse(-1);
            var previousX = optionalPreviousSlot.map(MachineSlot::x).orElse(0);
            var previousY = optionalPreviousSlot.map(MachineSlot::y).orElse(0);

            var id = previousId + 1;
            var x = this.x.applyAsInt(previousX);
            var y = this.y.applyAsInt(previousY);

            return new MachineSlot(this.slotType, id, x, y);
        }
    }
}
