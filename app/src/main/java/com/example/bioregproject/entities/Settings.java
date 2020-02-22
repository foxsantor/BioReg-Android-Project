package com.example.bioregproject.entities;

public class Settings {

    private int Icon;
    private String text;
    private long id;
    private boolean isSelected;
    private int destination;
    private int iconSelected;



    public Settings(int icon, String text, boolean isSelected, int destination,int iconSelected) {
        Icon = icon;
        this.text = text;
        this.isSelected = isSelected;
        this.destination = destination;
        this.iconSelected = iconSelected;

    }

    public Settings() {
        super();
    }

    public int getIcon() {
        return Icon;
    }

    public void setIcon(int icon) {
        Icon = icon;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public int getDestination() {
        return destination;
    }

    public void setDestination(int destination) {
        this.destination = destination;
    }

    public int getIconSelected() {
        return iconSelected;
    }

    public void setIconSelected(int iconSelected) {
        this.iconSelected = iconSelected;
    }


}
