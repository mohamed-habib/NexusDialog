
package com.github.dkharrat.nexusdialog.sample.model;

import com.squareup.moshi.Json;

import java.util.List;

public class Section {

    @Json(name = "section_id")
    private String sectionId;
    @Json(name = "section_title")
    private String sectionTitle;
    @Json(name = "components")
    private List<Component> components = null;

    public String getSectionId() {
        return sectionId;
    }

    public void setSectionId(String sectionId) {
        this.sectionId = sectionId;
    }

    public String getSectionTitle() {
        return sectionTitle;
    }

    public void setSectionTitle(String sectionTitle) {
        this.sectionTitle = sectionTitle;
    }

    public List<Component> getComponents() {
        return components;
    }

    public void setComponents(List<Component> components) {
        this.components = components;
    }

}
