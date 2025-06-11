package com.example.ProjectSearchApiHeallthHub.Model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "SearchCondition")
public class Search {

    @Id
    private String id;

    private String name;
    private String definition;
    private String overview;
    private List<String> prevention;
    private List<String> treatment;
    private List<String> management;
    private List<String> symptoms;

    public Search() {
    }

    public Search(String id, String name, String definition, String overview, List<String> prevention, List<String> treatment, List<String> management, List<String> symptoms) {
        this.id = id;
        this.name = name;
        this.definition = definition;
        this.overview = overview;
        this.prevention = prevention;
        this.treatment = treatment;
        this.management = management;
        this.symptoms = symptoms;
    }

    // Getters and setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public List<String> getPrevention() {
        return prevention;
    }

    public void setPrevention(List<String> prevention) {
        this.prevention = prevention;
    }

    public List<String> getTreatment() {
        return treatment;
    }

    public void setTreatment(List<String> treatment) {
        this.treatment = treatment;
    }

    public List<String> getManagement() {
        return management;
    }

    public void setManagement(List<String> management) {
        this.management = management;
    }

    public List<String> getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(List<String> symptoms) {
        this.symptoms = symptoms;
    }
}
