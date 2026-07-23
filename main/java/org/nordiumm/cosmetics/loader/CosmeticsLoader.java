package org.nordiumm.cosmetics.loader;

import net.minecraft.resources.Identifier;
import org.nordiumm.cosmetics.data.Cosmetic;

import java.util.ArrayList;
import java.util.List;

public class CosmeticsLoader {

    private static final List<Cosmetic> COSMETICS = new ArrayList<>();

    public static void add(Cosmetic cosmetic) {
        COSMETICS.add(cosmetic);
    }

    public static List<Cosmetic> getAll() {
        return COSMETICS;
    }

    public static void clear() {
        COSMETICS.clear();
    }

    public static Cosmetic getCosmeticForItem(Identifier itemId) {

        for (Cosmetic cosmetic : COSMETICS) {

            if (cosmetic.getItem().equals(itemId.toString())) {
                return cosmetic;
            }
        }

        return null;
    }
}