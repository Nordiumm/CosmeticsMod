package org.nordiumm.cosmetics.client.resource;

import org.nordiumm.cosmetics.data.Cosmetic;
import org.nordiumm.cosmetics.loader.CosmeticsLoader;

import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class CosmeticDownloader {

    private static final String GITHUB_ROOT =
            "https://raw.githubusercontent.com/Nordiumm/Cosmetics/main/";

    private static final Path PACK_FOLDER =
            Path.of("resourcepacks/NordiummCosmetics");


    public static void downloadAll() {

        try {

            Files.createDirectories(PACK_FOLDER);


            download(
                    GITHUB_ROOT + "pack.mcmeta",
                    PACK_FOLDER.resolve("pack.mcmeta")
            );


            // Download diamond override
            download(
                    GITHUB_ROOT + "assets/minecraft/items/diamond.json",
                    PACK_FOLDER.resolve(
                            "assets/minecraft/items/diamond.json"
                    )
            );


            for (Cosmetic cosmetic : CosmeticsLoader.getAll()) {


                download(
                        GITHUB_ROOT + "assets/minecraft/" + cosmetic.getTexture(),
                        PACK_FOLDER.resolve(
                                "assets/minecraft/" + cosmetic.getTexture()
                        )
                );


                download(
                        GITHUB_ROOT + "assets/minecraft/" + cosmetic.getModel(),
                        PACK_FOLDER.resolve(
                                "assets/minecraft/" + cosmetic.getModel()
                        )
                );


                System.out.println(
                        "Downloaded cosmetic: "
                                + cosmetic.getName()
                );
            }


            System.out.println(
                    "Cosmetic resource pack ready!"
            );


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static void download(
            String url,
            Path output
    ) throws Exception {

        Files.createDirectories(output.getParent());

        try (InputStream input = new URL(url).openStream()) {

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
}