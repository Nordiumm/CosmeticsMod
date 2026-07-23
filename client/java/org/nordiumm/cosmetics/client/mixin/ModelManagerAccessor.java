package org.nordiumm.cosmetics.client.mixin;

import net.minecraft.client.renderer.item.ItemModel;
import net.minecraft.resources.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(targets = "net.minecraft.client.resources.model.ModelManager")
public interface ModelManagerAccessor {

    @Accessor("bakedItemStackModels")
    Map<Identifier, ItemModel> cosmetics$getModels();
}