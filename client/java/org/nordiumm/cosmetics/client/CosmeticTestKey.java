package org.nordiumm.cosmetics.client;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.Minecraft;
import org.nordiumm.cosmetics.data.Cosmetic;

public class CosmeticTestKey {

    public static void register() {

        ClientTickEvents.END_CLIENT_TICK.register(client -> {

            if (client.player == null) {
                return;
            }

            Cosmetic cosmetic =
                    ItemCosmeticMatcher.getCosmetic(
                            client.player.getMainHandItem()
                    );

            if (cosmetic != null) {
                System.out.println(
                        "Found cosmetic: " + cosmetic.getName()
                );

                System.out.println(
                        "Texture: " + CosmeticTextureManager.getTexturePath(cosmetic)
                );
            }

        });
    }
}