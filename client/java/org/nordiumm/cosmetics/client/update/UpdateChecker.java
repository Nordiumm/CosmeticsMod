package org.nordiumm.cosmetics.client.update;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.Style;
import org.nordiumm.cosmetics.client.config.CosmeticConfig;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
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



            String releaseUrl =
                    release.get("html_url")
                            .getAsString();




            String lastNotified =
                    UpdateCache.getLastNotifiedVersion();




            if (!currentVersion.equals(latestVersion)
                    &&
                    !latestVersion.equals(lastNotified)) {



                sendMessage(
                        Component.literal(
                                "§b[NordiummCosmetics] §fNew update available!"
                        )
                );


                sendMessage(
                        Component.literal(
                                "§7Current: §f"
                                        + currentVersion
                                        + " §7→ Latest: §a"
                                        + latestVersion
                        )
                );



                sendClickableDownload(
                        releaseUrl
                );



                UpdateCache.setLastNotifiedVersion(
                        latestVersion
                );


            }



            if (CosmeticConfig.isDebug()) {


                System.out.println(
                        "[NordiummCosmetics] Update check:"
                );


                System.out.println(
                        "Current: "
                                + currentVersion
                );


                System.out.println(
                        "Latest: "
                                + latestVersion
                );


            }



        } catch (Exception e) {


            if (CosmeticConfig.isDebug()) {

                e.printStackTrace();

            }


        }


    }






    private static void sendClickableDownload(
            String url
    ) {


        Minecraft minecraft =
                Minecraft.getInstance();



        if (minecraft.player == null) {
            return;
        }



        Component download =
                Component.literal(
                                "§e[Click here to download]"
                        )
                        .setStyle(
                                Style.EMPTY
                                        .withClickEvent(
                                                new ClickEvent.OpenUrl(
                                                        URI.create(url)
                                                )
                                        )
                                        .withHoverEvent(
                                                new HoverEvent.ShowText(
                                                        Component.literal(
                                                                "§7Open GitHub release page"
                                                        )
                                                )
                                        )
                        );



        minecraft.player.sendSystemMessage(
                download
        );

    }






    private static void sendMessage(
            Component message
    ) {


        Minecraft minecraft =
                Minecraft.getInstance();



        if (minecraft.player != null) {


            minecraft.player.sendSystemMessage(
                    message
            );


        }


    }


}