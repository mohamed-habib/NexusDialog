
package com.github.dkharrat.nexusdialog.sample.model;

import com.squareup.moshi.Json;

import java.util.List;

public class DynamicForm {


    @Json(name = "form_id")
    private String formId;
    @Json(name = "form_title")
    private String formTitle;
    @Json(name = "sections")
    private List<Section> sections = null;

    public String getFormId() {
        return formId;
    }

    public void setFormId(String formId) {
        this.formId = formId;
    }

    public String getFormTitle() {
        return formTitle;
    }

    public void setFormTitle(String formTitle) {
        this.formTitle = formTitle;
    }

    public List<Section> getSections() {
        return sections;
    }

    public void setSections(List<Section> sections) {
        this.sections = sections;
    }

}
