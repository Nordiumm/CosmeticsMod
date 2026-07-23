package org.nordiumm.cosmetics.loader;

import org.nordiumm.cosmetics.data.Cosmetic;

import java.util.ArrayList;
import java.util.List;

public class CosmeticsLoader {

    private static final List<Cosmetic> cosmetics = new ArrayList<>();


    public static void add(Cosmetic cosmetic) {

        cosmetics.removeIf(
                existing -> existing.getId().equals(cosmetic.getId())
        );

        cosmetics.add(cosmetic);
    }


    public static List<Cosmetic> getAll() {
        return cosmetics;
    }


    public static void clear() {
        cosmetics.clear();
    }
}