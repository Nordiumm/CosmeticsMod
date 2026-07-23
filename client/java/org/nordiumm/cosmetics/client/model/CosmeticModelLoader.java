package org.nordiumm.cosmetics.client.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.client.renderer.item.ItemModel;
import net.minecraft.resources.Identifier;

public class CosmeticModelLoader {

    public static ItemModel load(String modelPath) {

        ModelManager manager =
                Minecraft.getInstance()
                        .getModelManager();

        Identifier id = Identifier.parse(
                "cosmetics:" + modelPath.replace("models/", "")
                        .replace(".json", "")
        );

        return manager.getItemModel(id);
    }
}