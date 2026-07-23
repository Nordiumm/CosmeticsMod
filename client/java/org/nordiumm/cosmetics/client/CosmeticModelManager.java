package org.nordiumm.cosmetics.client;

import net.minecraft.resources.Identifier;
import org.nordiumm.cosmetics.data.Cosmetic;

public class CosmeticModelManager {

    public static Identifier getModel(Cosmetic cosmetic) {

        if (cosmetic == null) {
            return null;
        }

        return Identifier.parse(
                "cosmetics:" + cosmetic.getId()
        );
    }
}