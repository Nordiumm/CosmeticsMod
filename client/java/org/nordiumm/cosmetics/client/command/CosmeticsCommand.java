package org.nordiumm.cosmetics.client.command;

import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.command.v2.ClientCommands;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import org.nordiumm.cosmetics.client.resource.CosmeticDownloader;
import org.nordiumm.cosmetics.data.Cosmetic;
import org.nordiumm.cosmetics.loader.CosmeticsJsonLoader;
import org.nordiumm.cosmetics.loader.CosmeticsLoader;
import org.nordiumm.cosmetics.loader.GitHubCosmeticsLoader;

public class CosmeticsCommand {


    public static void register() {

        ClientCommandRegistrationCallback.EVENT.register(
                (dispatcher, registryAccess) -> {

                    dispatcher.register(
                            ClientCommands.literal("cosmetics")

                                    .then(
                                            ClientCommands.literal("refresh")
                                                    .executes(context -> {

                                                        refresh();

                                                        return 1;
                                                    })
                                    )

                                    .then(
                                            ClientCommands.literal("list")
                                                    .executes(context -> {

                                                        listCosmetics();

                                                        return 1;
                                                    })
                                    )
                    );

                }
        );
    }



    private static void refresh() {

        sendMessage(
                "§7Refreshing cosmetics..."
        );


        CosmeticsLoader.clear();


        String json =
                GitHubCosmeticsLoader.download();


        CosmeticsJsonLoader.load(json);


        CosmeticDownloader.downloadAll();


        sendMessage(
                "§7Cosmetics refreshed!"
        );
    }



    private static void listCosmetics() {


        if (CosmeticsLoader.getAll().isEmpty()) {

            sendMessage(
                    "§cNo cosmetics loaded!"
            );

            return;
        }



        sendMessage(
                "§7=== Loaded Cosmetics ==="
        );


        for (Cosmetic cosmetic :
                CosmeticsLoader.getAll()) {


            sendMessage(
                    "§f"
                            + cosmetic.getId()
                            + " §7| §f"
                            + cosmetic.getName()
                            + " §7| §f"
                            + cosmetic.getItem()
            );

        }


        sendMessage(
                "§7Total cosmetics: "
                        + CosmeticsLoader.getAll().size()
        );
    }



    private static void sendMessage(String message) {

        Minecraft minecraft =
                Minecraft.getInstance();


        if (minecraft.player != null) {

            minecraft.player.sendSystemMessage(
                    Component.literal(message)
            );
        }
    }
}