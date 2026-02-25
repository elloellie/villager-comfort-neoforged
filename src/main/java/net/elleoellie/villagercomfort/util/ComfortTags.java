package net.elleoellie.villagercomfort.util;

import net.elleoellie.villagercomfort.VillagerComfort;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class ComfortTags {
    public static class Items {
        public static final TagKey<Item> COMFORT_DISPLAY_ITEMS = createTag("comfort_display_items");

        private static TagKey<Item> createTag(String name) {
            return ItemTags.create(ResourceLocation.fromNamespaceAndPath(VillagerComfort.MOD_ID, name));
        }
    }
}
