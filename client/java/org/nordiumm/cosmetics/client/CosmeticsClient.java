package org.nordiumm.cosmetics.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import org.nordiumm.cosmetics.loader.CosmeticsJsonLoader;
import org.nordiumm.cosmetics.loader.GitHubCosmeticsLoader;
import org.nordiumm.cosmetics.client.resource.CosmeticDownloader;
import org.nordiumm.cosmetics.client.command.CosmeticsCommand;
import org.nordiumm.cosmetics.client.config.CosmeticConfig;
import org.nordiumm.cosmetics.client.update.UpdateChecker;

public class CosmeticsClient implements ClientModInitializer {


    private static boolean checkedUpdate = false;



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




        ClientTickEvents.END_CLIENT_TICK.register(
                client -> {


                    if (!checkedUpdate
                            &&
                            client.player != null) {


                        checkedUpdate = true;


                        UpdateChecker.check();


                    }


                }
        );


    }

}