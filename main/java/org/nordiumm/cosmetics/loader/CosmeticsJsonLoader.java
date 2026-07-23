package org.nordiumm.cosmetics.loader;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.nordiumm.cosmetics.data.Cosmetic;

public class CosmeticsJsonLoader {

    public static void load(String jsonText) {

        if (jsonText == null) {
            System.out.println("No cosmetics JSON received!");
            return;
        }

        CosmeticsLoader.clear();

        JsonObject json = JsonParser
                .parseString(jsonText)
                .getAsJsonObject();

        JsonArray cosmetics = json.getAsJsonArray("cosmetics");

        for (var element : cosmetics) {

            JsonObject cosmetic = element.getAsJsonObject();

            CosmeticsLoader.add(
                    new Cosmetic(
                            cosmetic.get("id").getAsString(),
                            cosmetic.get("name").getAsString(),
                            cosmetic.get("item").getAsString(),
                            cosmetic.get("texture").getAsString(),
                            cosmetic.get("model").getAsString(),
                            cosmetic.has("override")
                                    ? cosmetic.get("override").getAsString()
                                    : null
                    )
            );
        }

        System.out.println(
                "Loaded " +
                        CosmeticsLoader.getAll().size() +
                        " cosmetics from GitHub!"
        );
    }
}