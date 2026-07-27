package org.nordiumm.cosmetics.client.update;

import net.minecraft.client.Minecraft;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

public class UpdateCache {


    private static final File CACHE_FILE =
            new File(
                    Minecraft.getInstance()
                            .gameDirectory,
                    "config/NordiummCosmetics/update.properties"
            );


    private static final Properties PROPERTIES =
            new Properties();



    public static String getLastNotifiedVersion() {

        load();

        return PROPERTIES.getProperty(
                "last_notified_version",
                ""
        );

    }





    public static void setLastNotifiedVersion(
            String version
    ) {

        load();


        PROPERTIES.setProperty(
                "last_notified_version",
                version
        );


        save();

    }





    private static void load() {

        try {

            if (!CACHE_FILE.exists()) {

                CACHE_FILE.getParentFile()
                        .mkdirs();

                return;

            }


            try (
                    FileInputStream input =
                            new FileInputStream(CACHE_FILE)
            ) {

                PROPERTIES.load(input);

            }


        } catch (Exception e) {

            e.printStackTrace();

        }

    }





    private static void save() {

        try {


            CACHE_FILE.getParentFile()
                    .mkdirs();


            try (
                    FileOutputStream output =
                            new FileOutputStream(CACHE_FILE)
            ) {


                PROPERTIES.store(
                        output,
                        "NordiummCosmetics update cache"
                );


            }


        } catch (Exception e) {

            e.printStackTrace();

        }

    }

}