package net.elleoellie.villagercomfort.screens;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;

import java.util.List;
import java.util.UUID;

public class ComfortMenu extends AbstractContainerMenu {
    private Container warturtleContainer;
    public Villager villager;

    // With Help from https://github.com/Mrbysco/ChocoCraft4/tree/arch/1.21
    // Under MIT LICENSE
    public static ComfortMenu create(int i, Inventory inventory, RegistryFriendlyByteBuf registryFriendlyByteBuf) {
        UUID uuid = registryFriendlyByteBuf.readUUID();
        List<Villager> villagers = inventory.player.level().getEntitiesOfClass(Villager.class,
                inventory.player.getBoundingBox().inflate(16), test -> test.getUUID().equals(uuid));
        Villager villager = villagers.isEmpty() ? null : villagers.getFirst();
        return new ComfortMenu(i, villager);
    }

    public ComfortMenu(int containerId, final Villager villager) {
        super(ModMenuTypes.COMFORT_MENU.get(), containerId);
        this.villager = villager;
    }

    @Override
    public ItemStack quickMoveStack(Player player, int i) {
        return null;
    }

    @Override
    public boolean stillValid(Player player) {
        return this.villager.isAlive()
        && player.canInteractWithEntity(this.villager, 4.0);
    }
}