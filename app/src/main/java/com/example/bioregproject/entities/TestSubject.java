package com.example.bioregproject.entities;

public class TestSubject {


    private String name;
    private String lastName;
    private int numberOfTasks;
    private  String mostCategory;
    private String moduleNAme;
    private int numberOfTasksPerModule;
    private long idSubject;


    public long getIdSubject() {
        return idSubject;
    }

    public void setIdSubject(long idSubject) {
        this.idSubject = idSubject;
    }

    public TestSubject() {
    }

    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getNumberOfTasks() {
        return numberOfTasks;
    }

    public void setNumberOfTasks(int numberofTasks) {
        this.numberOfTasks = numberofTasks;
    }

    public String getMostCategory() {
        return mostCategory;
    }

    public void setMostCategory(String mostCategory) {
        this.mostCategory = mostCategory;
    }

    public String getModuleNAme() {
        return moduleNAme;
    }

    public void setModuleNAme(String moduleNAme) {
        this.moduleNAme = moduleNAme;
    }

    public int getNumberOfTasksPerModule() {
        return numberOfTasksPerModule;
    }

    public void setNumberOfTasksPerModule(int numberOfTasksPerModule) {
        this.numberOfTasksPerModule = numberOfTasksPerModule;
    }
}
