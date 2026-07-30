package org.nordiumm.cosmetics.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import org.nordiumm.cosmetics.loader.CosmeticsJsonLoader;
import org.nordiumm.cosmetics.loader.GitHubCosmeticsLoader;
import org.nordiumm.cosmetics.client.resource.CosmeticDownloader;
import org.nordiumm.cosmetics.client.command.CosmeticsCommand;
import org.nordiumm.cosmetics.client.config.CosmeticConfig;
import org.nordiumm.cosmetics.client.update.UpdateChecker;


public class CosmeticsClient implements ClientModInitializer {


    @Override
    public void onInitializeClient() {


        System.out.println(
                "Cosmetics client initialized!"
        );


        CosmeticConfig.load();



        String json =
                GitHubCosmeticsLoader.download();


        CosmeticsJsonLoader.load(json);



        if (CosmeticConfig.isEnabled()) {


            CosmeticDownloader.downloadAll();


        }



        CosmeticsCommand.register();



        /*
         * Check for mod updates when the player
         * actually joins a world/server.
         */
        ClientPlayConnectionEvents.JOIN.register(
                (handler, sender, client) -> {


                    UpdateChecker.check();


                }
        );


    }

}