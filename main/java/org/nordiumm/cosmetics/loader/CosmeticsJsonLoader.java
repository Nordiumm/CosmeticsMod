package org.nordiumm.cosmetics.loader;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.nordiumm.cosmetics.data.Cosmetic;

public class CosmeticsJsonLoader {


    public static void load(String jsonText) {


        if (jsonText == null) {

            System.out.println(
                    "No cosmetics JSON received!"
            );

            return;
        }



        CosmeticsLoader.clear();



        try {


            JsonObject json =
                    JsonParser
                            .parseString(jsonText)
                            .getAsJsonObject();



            JsonArray cosmetics =
                    json.getAsJsonArray(
                            "cosmetics"
                    );



            for (var element :
                    cosmetics) {


                JsonObject cosmetic =
                        element.getAsJsonObject();



                String id =
                        cosmetic.get(
                                "id"
                        ).getAsString();



                String name =
                        cosmetic.get(
                                "name"
                        ).getAsString();



                String item =
                        normalizeItem(
                                cosmetic.get(
                                        "item"
                                ).getAsString()
                        );



                String texture =
                        cosmetic.has("texture")
                                ? cosmetic.get("texture")
                                  .getAsString()
                                : null;



                String model =
                        cosmetic.has("model")
                                ? cosmetic.get("model")
                                  .getAsString()
                                : null;



                String override =
                        cosmetic.has("override")
                                ? cosmetic.get("override")
                                  .getAsString()
                                : null;



                CosmeticsLoader.add(
                        new Cosmetic(
                                id,
                                name,
                                item,
                                texture,
                                model,
                                override
                        )
                );



                System.out.println(
                        "Loaded cosmetic: "
                                + id
                                + " | item="
                                + item
                );

            }



            System.out.println(
                    "Loaded "
                            + CosmeticsLoader.getAll().size()
                            + " cosmetics from GitHub!"
            );



        } catch (Exception e) {


            System.out.println(
                    "Failed loading cosmetics JSON!"
            );


            e.printStackTrace();

        }

    }





    private static String normalizeItem(
            String item
    ) {


        if (item == null) {

            return null;

        }



        if (!item.contains(":")) {

            return "minecraft:" + item;

        }



        return item;

    }

}