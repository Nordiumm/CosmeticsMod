package org.nordiumm.cosmetics.client.resource;

import com.google.gson.*;
import org.nordiumm.cosmetics.data.Cosmetic;
import org.nordiumm.cosmetics.loader.CosmeticsLoader;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

public class CosmeticPackGenerator {

    private static final Path PACK_FOLDER =
            Path.of("cosmetics_cache/NordiummCosmetics");


    public static void generate() {

        try {

            System.out.println("=== GENERATING COSMETIC PACK ===");


            // Group cosmetics by item
            Set<String> generatedItems = new HashSet<>();


            for (Cosmetic cosmetic : CosmeticsLoader.getAll()) {


                String item = cosmetic.getItem();


                if (item == null) {
                    System.out.println(
                            "Skipping cosmetic without item: "
                                    + cosmetic.getId()
                    );
                    continue;
                }


                if (!generatedItems.add(item)) {
                    continue;
                }


                generateItemFile(item);

            }


            System.out.println(
                    "Generated cosmetic item overrides!"
            );


            System.out.println("==============================");


        } catch (Exception e) {

            e.printStackTrace();

        }
    }



    private static void generateItemFile(String item)
            throws Exception {


        JsonObject root = new JsonObject();


        JsonObject model = new JsonObject();

        model.addProperty(
                "type",
                "minecraft:select"
        );

        model.addProperty(
                "property",
                "minecraft:component"
        );

        model.addProperty(
                "component",
                "minecraft:custom_name"
        );


        JsonArray cases = new JsonArray();


        Set<String> addedNames = new HashSet<>();


        for (Cosmetic cosmetic : CosmeticsLoader.getAll()) {


            if (!item.equals(cosmetic.getItem())) {
                continue;
            }


            if (cosmetic.getName() == null ||
                    cosmetic.getModel() == null) {
                continue;
            }


            System.out.println(
                    cosmetic.getId()
                            + " | "
                            + cosmetic.getName()
                            + " | "
                            + cosmetic.getModel()
            );


            if (!addedNames.add(cosmetic.getName())) {

                System.out.println(
                        "Duplicate name skipped: "
                                + cosmetic.getName()
                );

                continue;
            }



            JsonObject entry =
                    new JsonObject();


            entry.addProperty(
                    "when",
                    cosmetic.getName()
            );



            JsonObject cosmeticModel =
                    new JsonObject();


            cosmeticModel.addProperty(
                    "type",
                    "minecraft:model"
            );


            cosmeticModel.addProperty(
                    "model",
                    "minecraft:item/"
                            + cosmetic.getId()
            );


            entry.add(
                    "model",
                    cosmeticModel
            );


            cases.add(entry);
        }



        model.add(
                "cases",
                cases
        );



        JsonObject fallback =
                new JsonObject();


        fallback.addProperty(
                "type",
                "minecraft:model"
        );


        fallback.addProperty(
                "model",
                "minecraft:item/"
                        + item.replace(
                        "minecraft:",
                        ""
                )
        );


        model.add(
                "fallback",
                fallback
        );



        root.add(
                "model",
                model
        );



        Path output =
                PACK_FOLDER.resolve(
                        "assets/minecraft/items/"
                                + item.replace(
                                "minecraft:",
                                ""
                        )
                                + ".json"
                );


        Files.createDirectories(
                output.getParent()
        );


        Files.writeString(
                output,
                new GsonBuilder()
                        .setPrettyPrinting()
                        .create()
                        .toJson(root)
        );


        System.out.println(
                "Generated: "
                        + output
        );
    }
}