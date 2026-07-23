package org.nordiumm.cosmetics.client.resource;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraft.client.Minecraft;
import org.nordiumm.cosmetics.data.Cosmetic;
import org.nordiumm.cosmetics.loader.CosmeticsLoader;

import java.io.InputStream;
import java.net.URL;
import java.nio.file.*;
import java.util.Comparator;

public class CosmeticDownloader {

    private static final String GITHUB_ROOT =
            "https://raw.githubusercontent.com/Nordiumm/Cosmetics/main/";

    private static final Path CACHE_FOLDER =
            Path.of("cosmetics_cache");

    private static final Path PACK_FOLDER =
            CACHE_FOLDER.resolve("NordiummCosmetics");


    public static void downloadAll() {

        try {

            System.out.println("Updating Nordiumm Cosmetics pack...");


            deleteFolder(CACHE_FOLDER);


            Files.createDirectories(PACK_FOLDER);


            // Download pack metadata
            download(
                    GITHUB_ROOT + "pack.mcmeta",
                    PACK_FOLDER.resolve("pack.mcmeta")
            );


            // Download atlases from atlases.json
            downloadAtlases();


            // Download cosmetics
            for (Cosmetic cosmetic : CosmeticsLoader.getAll()) {


                // Texture
                if (cosmetic.getTexture() != null) {

                    download(
                            GITHUB_ROOT +
                                    "assets/minecraft/" +
                                    cosmetic.getTexture(),

                            PACK_FOLDER.resolve(
                                    "assets/minecraft/" +
                                            cosmetic.getTexture()
                            )
                    );
                }


                // Model
                if (cosmetic.getModel() != null) {

                    download(
                            GITHUB_ROOT +
                                    "assets/minecraft/" +
                                    cosmetic.getModel(),

                            PACK_FOLDER.resolve(
                                    "assets/minecraft/" +
                                            cosmetic.getModel()
                            )
                    );
                }


                System.out.println(
                        "Downloaded cosmetic: "
                                + cosmetic.getName()
                );
            }


            // Generate diamond.json
            CosmeticPackGenerator.generate();


            System.out.println(
                    "NordiummCosmetics resource pack updated!"
            );


            Minecraft.getInstance()
                    .reloadResourcePacks();


        } catch (Exception e) {

            e.printStackTrace();

        }
    }



    private static void downloadAtlases() throws Exception {


        String url =
                GITHUB_ROOT + "atlases.json";


        String jsonText;


        try (InputStream input =
                     new URL(url).openStream()) {

            jsonText =
                    new String(
                            input.readAllBytes()
                    );
        }


        JsonObject json =
                JsonParser
                        .parseString(jsonText)
                        .getAsJsonObject();


        JsonArray atlases =
                json.getAsJsonArray("atlases");


        for (var element : atlases) {


            String file =
                    element.getAsString();


            String path =
                    "assets/minecraft/atlases/"
                            + file;


            download(
                    GITHUB_ROOT + path,
                    PACK_FOLDER.resolve(path)
            );


            System.out.println(
                    "Downloaded atlas: "
                            + file
            );
        }
    }



    private static void download(
            String url,
            Path output
    ) throws Exception {


        Files.createDirectories(
                output.getParent()
        );


        try (InputStream input =
                     new URL(url).openStream()) {


            Files.copy(
                    input,
                    output,
                    StandardCopyOption.REPLACE_EXISTING
            );
        }


        System.out.println(
                "Downloaded: " + output
        );
    }



    private static void deleteFolder(
            Path path
    ) throws Exception {


        if (!Files.exists(path)) {
            return;
        }


        Files.walk(path)
                .sorted(
                        Comparator.reverseOrder()
                )
                .forEach(file -> {

                    try {

                        Files.deleteIfExists(file);

                    } catch (Exception e) {

                        e.printStackTrace();

                    }

                });
    }
}