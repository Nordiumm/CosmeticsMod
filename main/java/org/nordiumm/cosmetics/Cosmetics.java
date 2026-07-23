package org.nordiumm.cosmetics;

import net.fabricmc.api.ModInitializer;
import org.nordiumm.cosmetics.loader.CosmeticsJsonLoader;
import org.nordiumm.cosmetics.loader.GitHubCosmeticsLoader;

public class Cosmetics implements ModInitializer {

    public static final String MOD_ID = "cosmetics";

    @Override
    public void onInitialize() {

        System.out.println("Cosmetics mod initialized!");

        String json = GitHubCosmeticsLoader.download();

        CosmeticsJsonLoader.load(json);
    }
}