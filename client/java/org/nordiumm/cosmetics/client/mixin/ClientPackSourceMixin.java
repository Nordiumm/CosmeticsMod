package org.nordiumm.cosmetics.client.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.server.packs.repository.BuiltInPackSource;
import net.minecraft.server.packs.repository.Pack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.BiConsumer;
import java.util.function.Function;


@Mixin(BuiltInPackSource.class)
public abstract class ClientPackSourceMixin {


    @Shadow
    protected abstract void discoverPacksInPath(
            Path targetDir,
            BiConsumer<String, Function<String, Pack>> discoveredPacks
    );


    @Inject(
            method = "populatePackList",
            at = @At("TAIL")
    )
    private void cosmetics$addCosmeticsPack(
            BiConsumer<String, Function<String, Pack>> discoveredPacks,
            CallbackInfo ci
    ) {


        Path cosmeticsPack =
                Minecraft.getInstance()
                        .gameDirectory
                        .toPath()
                        .resolve("cosmetics_cache");


        if (Files.exists(cosmeticsPack)) {


            System.out.println(
                    "Found cosmetics_cache, adding pack..."
            );


            discoverPacksInPath(
                    cosmeticsPack,
                    discoveredPacks
            );


            System.out.println(
                    "Nordiumm Cosmetics pack added!"
            );
        }
    }
}