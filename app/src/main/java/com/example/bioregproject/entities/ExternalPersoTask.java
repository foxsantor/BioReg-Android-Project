package com.example.bioregproject.entities;

import java.util.Date;
import java.util.List;

public class ExternalPersoTask {
    private Date creation;
    private List<PersoTask> list;

    public ExternalPersoTask(Date creation, List<PersoTask> list) {
        this.creation = creation;
        this.list = list;
    }

    public ExternalPersoTask() {
    }

    public Date getCreation() {
        return creation;
    }

    public void setCreation(Date creation) {
        this.creation = creation;
    }

    public List<PersoTask> getList() {
        return list;
    }

    public void setList(List<PersoTask> list) {
        this.list = list;
    }
}

