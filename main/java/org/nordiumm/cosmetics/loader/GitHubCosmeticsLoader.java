package org.nordiumm.cosmetics.loader;

import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class GitHubCosmeticsLoader {

    private static final String COSMETICS_URL =
            "https://raw.githubusercontent.com/Nordiumm/Cosmetics/main/cosmetics.json";

    public static String download() {

        try {
            URL url = new URL(COSMETICS_URL);

            try (InputStream input = url.openStream()) {

                return new String(
                        input.readAllBytes(),
                        StandardCharsets.UTF_8
                );

            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}