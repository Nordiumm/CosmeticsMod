package org.nordiumm.cosmetics.data;

public class Cosmetic {

    private final String id;
    private final String name;
    private final String item;
    private final String texture;
    private final String model;

    public Cosmetic(String id, String name, String item, String texture, String model) {
        this.id = id;
        this.name = name;
        this.item = item;
        this.texture = texture;
        this.model = model;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getItem() {
        return item;
    }

    public String getTexture() {
        return texture;
    }

    public String getModel() {
        return model;
    }
}