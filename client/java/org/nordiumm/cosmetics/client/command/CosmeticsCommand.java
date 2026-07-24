package org.nordiumm.cosmetics.client.command;

import com.mojang.brigadier.arguments.StringArgumentType;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.command.v2.ClientCommands;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import org.nordiumm.cosmetics.client.config.CosmeticConfig;
import org.nordiumm.cosmetics.client.resource.CosmeticDownloader;
import org.nordiumm.cosmetics.data.Cosmetic;
import org.nordiumm.cosmetics.loader.CosmeticsJsonLoader;
import org.nordiumm.cosmetics.loader.CosmeticsLoader;
import org.nordiumm.cosmetics.loader.GitHubCosmeticsLoader;


public class CosmeticsCommand {


    private static final String OWNER_UUID =
            "827437eb-ee7e-4a3a-9ebe-8398a0ba520d";



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


                                    .then(
                                            ClientCommands.literal("toast")
                                                    .then(
                                                            ClientCommands.argument(
                                                                            "text",
                                                                            StringArgumentType.greedyString()
                                                                    )
                                                                    .executes(context -> {


                                                                        if (!isOwner()) {

                                                                            sendMessage(
                                                                                    "§cNo permission."
                                                                            );

                                                                            return 0;
                                                                        }



                                                                        String text =
                                                                                StringArgumentType.getString(
                                                                                        context,
                                                                                        "text"
                                                                                );



                                                                        showToast(
                                                                                "Cosmetics",
                                                                                text
                                                                        );



                                                                        return 1;

                                                                    })
                                                    )
                                    )

                    );

                }
        );

    }






    private static void refresh() {


        CosmeticConfig.reload();



        if (!CosmeticConfig.refreshCommandEnabled()) {


            sendMessage(
                    "§cCosmetics refresh command disabled in config."
            );


            return;

        }





        if (CosmeticConfig.notifications()) {


            sendMessage(
                    "§7Refreshing cosmetics..."
            );

        }





        try {


            CosmeticsLoader.clear();



            String json =
                    GitHubCosmeticsLoader.download();



            CosmeticsJsonLoader.load(json);



            CosmeticConfig.reload();



            CosmeticDownloader.downloadAll();





            if (CosmeticConfig.isDebug()) {


                System.out.println(
                        "Cosmetics refresh completed."
                );

            }





        } catch (Exception e) {


            e.printStackTrace();



            sendMessage(
                    "§cFailed to refresh cosmetics!"
            );

        }

    }








    private static void listCosmetics() {


        CosmeticConfig.reload();




        if (!CosmeticConfig.listCommandEnabled()) {


            sendMessage(
                    "§cCosmetics list command disabled in config."
            );


            return;

        }






        if (CosmeticsLoader.getAll().isEmpty()) {


            sendMessage(
                    "§cNo cosmetics loaded!"
            );


            return;

        }







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






        if (CosmeticConfig.notifications()) {


            sendMessage(
                    "§7Total cosmetics: "
                            + CosmeticsLoader.getAll().size()
            );

        }






        if (CosmeticConfig.isDebug()) {


            System.out.println(
                    "Listed "
                            + CosmeticsLoader.getAll().size()
                            + " cosmetics."
            );

        }

    }









    private static boolean isOwner() {


        Minecraft minecraft =
                Minecraft.getInstance();



        if (minecraft.player == null) {

            return false;

        }



        return minecraft.player
                .getUUID()
                .toString()
                .equals(
                        OWNER_UUID
                );

    }









    private static void showToast(
            String title,
            String message
    ) {


        /*
         * Temporary testing output.
         *
         * Replace this later with:
         * CosmeticToast.show(title,message);
         */

        sendMessage(
                "§6[Toast] §f"
                        + title
                        + ": "
                        + message
        );


    }








    private static void sendMessage(
            String message
    ) {


        Minecraft minecraft =
                Minecraft.getInstance();



        if (minecraft.player != null) {


            minecraft.player.sendSystemMessage(
                    Component.literal(message)
            );


        }


    }


}