package com.example.bioregproject.entities;

public class ExportFiles {

    private String tableName;
    private long id;
    private String text;

    public ExportFiles(String tableName, String text) {
        this.tableName = tableName;
        this.text = text;
    }

    public ExportFiles() {
        super();
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
