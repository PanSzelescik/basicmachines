package pl.panszelescik.basicmachines.fabric.api.block.entity;

import net.fabricmc.fabric.api.transfer.v1.storage.StoragePreconditions;
import net.fabricmc.fabric.api.transfer.v1.transaction.TransactionContext;
import net.fabricmc.fabric.api.transfer.v1.transaction.base.SnapshotParticipant;
import net.minecraft.world.Container;
import net.minecraft.world.item.crafting.Recipe;
import pl.panszelescik.basicmachines.api.common.util.Numbers;
import team.reborn.energy.api.EnergyStorage;

public class MachineEnergyStorageFabric<R extends Recipe<Container>> extends SnapshotParticipant<Long> implements EnergyStorage {

    private final MachineBlockEntityFabric<R> machineBlockEntity;

    public MachineEnergyStorageFabric(MachineBlockEntityFabric<R> machineBlockEntity) {
        this.machineBlockEntity = machineBlockEntity;
    }

    @Override
    protected Long createSnapshot() {
        return this.getAmount();
    }

    @Override
    protected void readSnapshot(Long snapshot) {
        this.machineBlockEntity.setCurrentEnergy(Math.toIntExact(snapshot));
    }

    @Override
    public boolean supportsInsertion() {
        return this.machineBlockEntity.canInputEnergy();
    }

    @Override
    public long insert(long maxAmount, TransactionContext transaction) {
        StoragePreconditions.notNegative(maxAmount);

        if (!this.supportsInsertion()) {
            return 0;
        }

        var inserted = (int) Math.max(0, Math.min(this.getCapacity() - this.getAmount(), Math.min(this.machineBlockEntity.getInputMaxEnergy(), Numbers.safeInt(maxAmount))));
        if (inserted > 0) {
            updateSnapshots(transaction);
            this.machineBlockEntity.incrementEnergy(inserted);
        }

        return inserted;
    }

    @Override
    public boolean supportsExtraction() {
        return this.machineBlockEntity.canOutputEnergy();
    }

    @Override
    public long extract(long maxAmount, TransactionContext transaction) {
        StoragePreconditions.notNegative(maxAmount);

        if (!this.supportsExtraction()) {
            return 0;
        }

        var extracted = (int) Math.max(0, Math.min(this.machineBlockEntity.getOutputMaxEnergy(), Math.min(this.getAmount(), Numbers.safeInt(maxAmount))));
        if (extracted > 0) {
            updateSnapshots(transaction);
            this.machineBlockEntity.decrementEnergy(extracted);
        }

        return extracted;
    }

    @Override
    public long getAmount() {
        return this.machineBlockEntity.getCurrentEnergy();
    }

    @Override
    public long getCapacity() {
        return this.machineBlockEntity.getMaxEnergy();
    }

    @Override
    protected void onFinalCommit() {
        this.machineBlockEntity.setChanged();
    }
}
