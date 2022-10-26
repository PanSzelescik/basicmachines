package pl.panszelescik.basicmachines.api.common.block.entity;

import java.util.function.IntUnaryOperator;

public interface IMachineEnergyStorage {

    void setCurrentEnergy(int energy);

    int getCurrentEnergy();

    int getMaxEnergy();

    default int inputEnergy(int maxAmount, boolean simulate) {
        var inserted = Math.max(0, Math.min(this.getMaxEnergy() - this.getCurrentEnergy(), Math.min(this.getInputMaxEnergy(), maxAmount)));
        if (!simulate) {
            this.incrementEnergy(inserted);
        }

        return inserted;
    }

    default int outputEnergy(int maxAmount, boolean simulate) {
        var extracted = Math.max(0, Math.min(this.getOutputMaxEnergy(), Math.min(this.getCurrentEnergy(), maxAmount)));
        if (!simulate) {
            this.decrementEnergy(extracted);
        }

        return extracted;
    }

    default boolean canInputEnergy() {
        return true;
    }

    default boolean canOutputEnergy() {
        return false;
    }

    default void incrementEnergy(int toIncrement) {
        this.setCurrentEnergy(energy -> energy + toIncrement);
    }

    default void decrementEnergy(int toDecrement) {
        this.setCurrentEnergy(energy -> energy - toDecrement);
    }

    default void setCurrentEnergy(IntUnaryOperator operator) {
        this.setCurrentEnergy(operator.applyAsInt(getCurrentEnergy()));
    }

    default int getInputMaxEnergy() {
        return this.canInputEnergy() ? this.getMaxEnergy() : 0;
    }

    default int getOutputMaxEnergy() {
        return this.canOutputEnergy() ? this.getMaxEnergy() : 0;
    }
}
