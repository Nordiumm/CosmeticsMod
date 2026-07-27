package org.nordiumm.cosmetics.client.update;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class UpdateChecker {


    private static final String MOD_ID =
            "cosmetics";


    private static final String GITHUB_API =
            "https://api.github.com/repos/Nordiumm/CosmeticsMod/releases/latest";



    private static boolean checked = false;




    public static void check() {


        if (checked) {
            return;
        }


        checked = true;



        try {


            String currentVersion =
                    FabricLoader.getInstance()
                            .getModContainer(MOD_ID)
                            .get()
                            .getMetadata()
                            .getVersion()
                            .getFriendlyString();




            HttpURLConnection connection =
                    (HttpURLConnection)
                            new URL(GITHUB_API)
                                    .openConnection();



            connection.setRequestProperty(
                    "User-Agent",
                    "NordiummCosmetics"
            );



            JsonObject release =
                    JsonParser.parseReader(
                                    new InputStreamReader(
                                            connection.getInputStream()
                                    )
                            )
                            .getAsJsonObject();




            String latestVersion =
                    release.get("tag_name")
                            .getAsString()
                            .replace(
                                    "v",
                                    ""
                            );



            String download =
                    release.get("html_url")
                            .getAsString();





            String last =
                    UpdateCache.getLastNotifiedVersion();




            if (!currentVersion.equals(latestVersion)
                    &&
                    !latestVersion.equals(last)) {



                sendMessage(
                        "§b[NordiummCosmetics] §fNew update available!"
                );


                sendMessage(
                        "§7Current: §f"
                                + currentVersion
                                + " §7→ Latest: §a"
                                + latestVersion
                );


                sendMessage(
                        "§eDownload: §9"
                                + download
                );



                UpdateCache.setLastNotifiedVersion(
                        latestVersion
                );

            }




        } catch (Exception e) {


            System.out.println(
                    "[NordiummCosmetics] Update check failed."
            );


            e.printStackTrace();

        }


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