package com.example.bioregproject.entities;

import java.util.Date;
import java.util.List;

public class ExternalHistory {

    private Date creation;
    private List<History> list;

    public ExternalHistory(Date creation, List<History> list) {
        this.creation = creation;
        this.list = list;
    }

    public ExternalHistory() {
    }

    public Date getCreation() {
        return creation;
    }

    public void setCreation(Date creation) {
        this.creation = creation;
    }

    public List<History> getList() {
        return list;
    }

    public void setList(List<History> list) {
        this.list = list;
    }
}
