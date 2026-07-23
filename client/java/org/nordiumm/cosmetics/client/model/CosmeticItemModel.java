package org.nordiumm.cosmetics.client.model;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.ItemModel;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.world.entity.ItemOwner;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.nordiumm.cosmetics.data.Cosmetic;

public class CosmeticItemModel implements ItemModel {

    private final ItemModel baseModel;
    private final Cosmetic cosmetic;


    public CosmeticItemModel(
            ItemModel baseModel,
            Cosmetic cosmetic
    ) {
        this.baseModel = baseModel;
        this.cosmetic = cosmetic;
    }


    @Override
    public void update(
            ItemStackRenderState output,
            ItemStack stack,
            ItemModelResolver resolver,
            ItemDisplayContext context,
            ClientLevel level,
            ItemOwner owner,
            int seed
    ) {

        baseModel.update(
                output,
                stack,
                resolver,
                context,
                level,
                owner,
                seed
        );

        System.out.println(
                "Rendering cosmetic: " + cosmetic.getName()
        );
    }

    public Cosmetic getCosmetic() {
        return cosmetic;
    }
}