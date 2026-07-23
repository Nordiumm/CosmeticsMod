package org.nordiumm.cosmetics.client.mixin;

import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.client.renderer.item.ItemModel;
import net.minecraft.resources.Identifier;
import org.nordiumm.cosmetics.data.Cosmetic;
import org.nordiumm.cosmetics.loader.CosmeticsLoader;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ModelManager.class)
public class ModelManagerMixin {


    @Inject(
            method = "getItemModel",
            at = @At("RETURN"),
            cancellable = true
    )
    private void cosmetics$replaceModel(
            Identifier id,
            CallbackInfoReturnable<ItemModel> cir
    ) {


        for (Cosmetic cosmetic : CosmeticsLoader.getAll()) {


            if (id.equals(Identifier.parse(cosmetic.getItem()))) {


                System.out.println(
                        "Found cosmetic model: "
                                + cosmetic.getName()
                );


                // TODO: return the cosmetic model here

                return;
            }
        }
    }
}