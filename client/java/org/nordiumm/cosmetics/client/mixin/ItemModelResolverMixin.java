package org.nordiumm.cosmetics.client.mixin;

import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.ItemOwner;
import net.minecraft.world.level.Level;
import org.nordiumm.cosmetics.client.ItemCosmeticMatcher;
import org.nordiumm.cosmetics.data.Cosmetic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.jspecify.annotations.Nullable;


@Mixin(ItemModelResolver.class)
public class ItemModelResolverMixin {


    @Inject(
            method = "appendItemLayers",
            at = @At("HEAD")
    )
    private void cosmetics$changeModel(
            ItemStackRenderState output,
            ItemStack item,
            ItemDisplayContext displayContext,
            @Nullable Level level,
            @Nullable ItemOwner owner,
            int seed,
            CallbackInfo ci
    ) {


        Cosmetic cosmetic = ItemCosmeticMatcher.getCosmetic(item);


        if (cosmetic != null) {


            Identifier cosmeticModel =
                    Identifier.parse(
                            "cosmetics:item/" + cosmetic.getId()
                    );


            item.set(
                    DataComponents.ITEM_MODEL,
                    cosmeticModel
            );


            System.out.println(
                    "Changed model to: " + cosmeticModel
            );
        }
    }
}