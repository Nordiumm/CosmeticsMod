package org.nordiumm.cosmetics.client.config;

import net.minecraft.client.Minecraft;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class CosmeticConfig {


    private static final Map<String, String> ALWAYS_USE =
            new HashMap<>();


    private static final Properties PROPERTIES =
            new Properties();



    private static final File CONFIG_FOLDER =
            new File(
                    Minecraft.getInstance()
                            .gameDirectory,
                    "config/NordiummCosmetics"
            );


    private static final File CONFIG_FILE =
            new File(
                    CONFIG_FOLDER,
                    "cosmetics.properties"
            );





    public static void load() {


        try {


            ALWAYS_USE.clear();



            if (!CONFIG_FILE.exists()) {


                CONFIG_FOLDER.mkdirs();


                try (FileWriter writer =
                             new FileWriter(CONFIG_FILE)) {


                    writer.write(
                            "# NordiummCosmetics configuration\n" +
                                    "# Config version - DO NOT CHANGE\n" +
                                    "config_version=1\n\n" +

                                    "# Enable cosmetics system\n" +
                                    "enabled=true\n\n" +

                                    "# Show cosmetic update messages\n" +
                                    "notifications=true\n\n" +

                                    "# Enable debug logging\n" +
                                    "debug=false\n\n" +

                                    "# Always use cosmetics\n" +
                                    "# Format:\n" +
                                    "# always_use.namespace.item=cosmetic_id\n" +
                                    "# Example:\n" +
                                    "always_use.minecraft.totem_of_undying=blahaj\n\n" +

                                    "# Commands\n" +
                                    "commands.refresh=true\n" +
                                    "commands.list=true\n"
                    );

                }


                return;

            }





            try (FileInputStream input =
                         new FileInputStream(CONFIG_FILE)) {


                PROPERTIES.clear();

                PROPERTIES.load(input);

            }






            for (String key :
                    PROPERTIES.stringPropertyNames()) {



                if (key.startsWith(
                        "always_use."
                )) {



                    String item =
                            key.substring(
                                    "always_use.".length()
                            );



                    // Convert minecraft.totem_of_undying
                    // into minecraft:totem_of_undying
                    item =
                            item.replaceFirst(
                                    "\\.",
                                    ":"
                            );



                    String cosmetic =
                            PROPERTIES.getProperty(
                                    key
                            );



                    ALWAYS_USE.put(
                            normalizeItem(item),
                            cosmetic
                    );

                }

            }





            System.out.println(
                    "Loaded cosmetic config!"
            );



            ALWAYS_USE.forEach(
                    (item, cosmetic) ->
                            System.out.println(
                                    "Always use: "
                                            + item
                                            + " -> "
                                            + cosmetic
                            )
            );



        } catch (Exception e) {


            e.printStackTrace();

        }

    }







    public static void reload() {

        load();

    }







    public static String getAlwaysUse(
            String item
    ) {


        return ALWAYS_USE.get(
                normalizeItem(item)
        );

    }







    public static Map<String, String> getAlwaysUseEntries() {

        return ALWAYS_USE;

    }







    public static boolean isEnabled() {


        return Boolean.parseBoolean(
                PROPERTIES.getProperty(
                        "enabled",
                        "true"
                )
        );

    }







    public static boolean notifications() {


        return Boolean.parseBoolean(
                PROPERTIES.getProperty(
                        "notifications",
                        "true"
                )
        );

    }







    public static boolean isDebug() {


        return Boolean.parseBoolean(
                PROPERTIES.getProperty(
                        "debug",
                        "false"
                )
        );

    }







    public static boolean refreshCommandEnabled() {


        return Boolean.parseBoolean(
                PROPERTIES.getProperty(
                        "commands.refresh",
                        "true"
                )
        );

    }







    public static boolean listCommandEnabled() {


        return Boolean.parseBoolean(
                PROPERTIES.getProperty(
                        "commands.list",
                        "true"
                )
        );

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