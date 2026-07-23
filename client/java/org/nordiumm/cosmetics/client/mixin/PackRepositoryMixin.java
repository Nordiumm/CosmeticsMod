package org.nordiumm.cosmetics.client.mixin;

import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackRepository;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Mixin(PackRepository.class)
public class PackRepositoryMixin {


    @Inject(
            method = "rebuildSelected",
            at = @At("RETURN"),
            cancellable = true
    )
    private void cosmetics$forceEnable(
            Collection<String> selectedNames,
            CallbackInfoReturnable<List<Pack>> cir
    ) {


        PackRepository repository =
                (PackRepository)(Object)this;


        if (!repository.isAvailable("NordiummCosmetics")) {
            return;
        }


        List<Pack> packs =
                new ArrayList<>(
                        cir.getReturnValue()
                );


        Pack cosmeticPack =
                repository.getPack("NordiummCosmetics");


        if (cosmeticPack != null &&
                !packs.contains(cosmeticPack)) {


            packs.add(cosmeticPack);


            System.out.println(
                    "Forced Nordiumm Cosmetics enabled!"
            );
        }


        cir.setReturnValue(packs);
    }
}