package org.nordiumm.cosmetics.client.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import net.minecraft.client.Minecraft;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;

public class CosmeticConfig {

    private static final Map<String, String> ALWAYS_USE = new HashMap<>();

    private static final File CONFIG_FILE =
            new File(
                    Minecraft.getInstance()
                            .gameDirectory,
                    "config/cosmetics.json"
            );


    public static void load() {

        try {

            if (!CONFIG_FILE.exists()) {

                CONFIG_FILE.getParentFile().mkdirs();

                JsonObject defaultConfig =
                        new JsonObject();

                defaultConfig.add(
                        "alwaysUse",
                        new JsonObject()
                );


                try (FileWriter writer =
                             new FileWriter(CONFIG_FILE)) {

                    new GsonBuilder()
                            .setPrettyPrinting()
                            .create()
                            .toJson(
                                    defaultConfig,
                                    writer
                            );
                }

                return;
            }


            JsonObject root =
                    new Gson()
                            .fromJson(
                                    new FileReader(CONFIG_FILE),
                                    JsonObject.class
                            );


            if (root.has("alwaysUse")) {

                JsonObject always =
                        root.getAsJsonObject(
                                "alwaysUse"
                        );


                for (String item :
                        always.keySet()) {

                    ALWAYS_USE.put(
                            item,
                            always.get(item)
                                    .getAsString()
                    );
                }
            }


            System.out.println(
                    "Loaded cosmetic config!"
            );


        } catch (Exception e) {

            e.printStackTrace();

        }
    }


    public static String getAlwaysUse(String item) {

        return ALWAYS_USE.get(item);

    }
}