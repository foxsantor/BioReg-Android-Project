package com.example.bioregproject.entities;

public class MenuItems {
    private String name;
    private int image;
    private int destination;

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public MenuItems() {
    }

    public MenuItems(String name, int image , int destination) {
        this.name = name;
        this.image = image;
        this.destination = destination;
    }

    public String getName() {
        return name;
    }

    public int getDestination() {
        return destination;
    }

    public void setDestination(int destination) {
        this.destination = destination;
    }

    public void setName(String name) {
        this.name = name;
    }
}
