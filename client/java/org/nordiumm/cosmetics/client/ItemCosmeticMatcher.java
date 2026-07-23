package org.nordiumm.cosmetics.client;

import net.minecraft.world.item.ItemStack;
import org.nordiumm.cosmetics.data.Cosmetic;
import org.nordiumm.cosmetics.loader.CosmeticsLoader;

public class ItemCosmeticMatcher {

    public static Cosmetic getCosmetic(ItemStack stack) {

        String itemName = stack.getHoverName().getString();

        for (Cosmetic cosmetic : CosmeticsLoader.getAll()) {

            if (itemName.equals(cosmetic.getName())) {
                return cosmetic;
            }

        }

        return null;
    }
}