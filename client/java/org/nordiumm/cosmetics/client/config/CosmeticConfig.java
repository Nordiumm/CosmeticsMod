package org.nordiumm.cosmetics.client.config;

import net.minecraft.client.Minecraft;

import java.io.File;
import java.io.FileReader;
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
            PROPERTIES.clear();


            if (!CONFIG_FILE.exists()) {

                createDefaultConfig();

                return;
            }



            try (FileReader reader =
                         new FileReader(CONFIG_FILE)) {

                PROPERTIES.load(reader);
            }



            for (String key :
                    PROPERTIES.stringPropertyNames()) {


                if (key.startsWith("always_use.")) {


                    String item =
                            key.substring(
                                    "always_use.".length()
                            );


                    String cosmetic =
                            PROPERTIES.getProperty(
                                    key
                            );


                    ALWAYS_USE.put(
                            item,
                            cosmetic
                    );
                }
            }



            System.out.println(
                    "Loaded cosmetic config!"
            );


            if (isDebug()) {

                System.out.println(
                        "Always use cosmetics:"
                );

                ALWAYS_USE.forEach(
                        (item, cosmetic) ->
                                System.out.println(
                                        item
                                                + " -> "
                                                + cosmetic
                                )
                );
            }



        } catch (Exception e) {

            e.printStackTrace();

        }
    }




    private static void createDefaultConfig()
            throws Exception {


        CONFIG_FOLDER.mkdirs();


        try (FileWriter writer =
                     new FileWriter(CONFIG_FILE)) {


            writer.write(
                    "# NordiummCosmetics configuration\n"
            );

            writer.write(
                    "# Config version - DO NOT CHANGE\n"
            );

            writer.write(
                    "config_version=1\n\n"
            );


            writer.write(
                    "# Enable the cosmetics system\n"
            );

            writer.write(
                    "enabled=true\n\n"
            );


            writer.write(
                    "# Show cosmetic update messages\n"
            );

            writer.write(
                    "notifications=true\n\n"
            );


            writer.write(
                    "# Enable debug logging\n"
            );

            writer.write(
                    "debug=false\n\n"
            );


            writer.write(
                    "# Always use cosmetics\n"
            );

            writer.write(
                    "# Format:\n"
            );

            writer.write(
                    "# always_use.minecraft:item=cosmetic_id\n"
            );

            writer.write(
                    "# Example:\n"
            );

            writer.write(
                    "# always_use.minecraft:totem_of_undying=blahaj\n\n"
            );


            writer.write(
                    "# Commands\n"
            );

            writer.write(
                    "commands.refresh=true\n"
            );

            writer.write(
                    "commands.list=true\n"
            );
        }


        System.out.println(
                "Created default cosmetics config."
        );
    }





    public static void reload() {

        ALWAYS_USE.clear();

        load();
    }





    public static String getAlwaysUse(
            String item
    ) {

        return ALWAYS_USE.get(item);

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

}