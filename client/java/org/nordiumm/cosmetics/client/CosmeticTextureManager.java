package org.nordiumm.cosmetics.client;

import org.nordiumm.cosmetics.data.Cosmetic;

public class CosmeticTextureManager {

    public static String getTexturePath(Cosmetic cosmetic) {

        if (cosmetic == null) {
            return null;
        }

        return cosmetic.getTexture();
    }
}