package org.nordiumm.cosmetics.client;

import net.fabricmc.api.ClientModInitializer;
import org.nordiumm.cosmetics.client.resource.CosmeticDownloader;
import org.nordiumm.cosmetics.loader.CosmeticsJsonLoader;
import org.nordiumm.cosmetics.loader.GitHubCosmeticsLoader;

public class CosmeticsClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {

        String json = GitHubCosmeticsLoader.download();

        CosmeticsJsonLoader.load(json);

        // Download cosmetic textures/models from GitHub
        CosmeticDownloader.downloadAll();

        CosmeticTestKey.register();

        System.out.println(
                "Cosmetics loaded: "
                        + org.nordiumm.cosmetics.loader.CosmeticsLoader.getAll().size()
        );
    }
}