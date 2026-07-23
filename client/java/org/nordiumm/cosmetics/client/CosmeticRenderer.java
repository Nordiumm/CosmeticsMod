package org.nordiumm.cosmetics.client;

import org.nordiumm.cosmetics.data.Cosmetic;

public class CosmeticRenderer {

    private static Cosmetic currentCosmetic;

    public static void setCurrentCosmetic(Cosmetic cosmetic) {
        currentCosmetic = cosmetic;
    }

    public static Cosmetic getCurrentCosmetic() {
        return currentCosmetic;
    }
}