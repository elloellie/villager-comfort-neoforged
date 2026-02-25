package net.elleoellie.villagercomfort.util;

import net.minecraft.world.item.ItemStack;

public class TagChecker {
    public static boolean TagCheckerValidate(ItemStack item) {
        return item.is(ComfortTags.Items.COMFORT_DISPLAY_ITEMS);
    }
}
