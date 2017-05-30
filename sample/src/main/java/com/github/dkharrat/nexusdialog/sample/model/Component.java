
package com.github.dkharrat.nexusdialog.sample.model;

import com.github.dkharrat.nexusdialog.sample.model.property.Property;
import com.squareup.moshi.Json;

import java.util.List;

public class Component {

    @Json(name = "id")
    private String id;
    @Json(name = "type")
    private ComponentType type;
    @Json(name = "label")
    private String label;
    @Json(name = "validators")
    private List<Validators> validators = null;
    @Json(name = "properties")
    private Property properties = null;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ComponentType getType() {
        return type;
    }

    public void setType(ComponentType type) {
        this.type = type;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public List<Validators> getValidators() {
        return validators;
    }

    public void setValidators(List<Validators> validators) {
        this.validators = validators;
    }

    public Property getProperties() {
        return properties;
    }

    public void setProperties(Property properties) {
        this.properties = properties;
    }

    public enum ComponentType {
        EDIT_TEXT,
        CHECK_BOX,
        DATE_PICKER,
        TIME_PICKER,
        SPINNER,
        RADIO_GROUP,
        SIGNATURE
    }

    public enum Validators {
        IS_REQUIRED
    }
}
