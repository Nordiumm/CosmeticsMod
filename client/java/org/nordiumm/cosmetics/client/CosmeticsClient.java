package org.nordiumm.cosmetics.client;

import net.fabricmc.api.ClientModInitializer;
import org.nordiumm.cosmetics.loader.CosmeticsJsonLoader;
import org.nordiumm.cosmetics.loader.GitHubCosmeticsLoader;
import org.nordiumm.cosmetics.client.resource.CosmeticDownloader;
import org.nordiumm.cosmetics.client.command.CosmeticsCommand;
import org.nordiumm.cosmetics.client.config.CosmeticConfig;

public class CosmeticsClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {

        System.out.println("Cosmetics client initialized!");
        CosmeticConfig.load();

        String json = GitHubCosmeticsLoader.download();

        CosmeticsJsonLoader.load(json);

        CosmeticDownloader.downloadAll();

        CosmeticsCommand.register();
    }
}