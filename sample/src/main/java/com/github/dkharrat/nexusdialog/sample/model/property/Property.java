package com.github.dkharrat.nexusdialog.sample.model.property;

import com.squareup.moshi.Json;

import java.util.List;

/**
 * Created by Mohamed Habib on 29/05/2017.
 */

public class Property {

    @Json(name = "display_format")
    protected String displayFormat;
    @Json(name = "is_24HourView")
    protected boolean is24HourView;

    @Json(name = "items")
    protected List<String> items = null;
    @Json(name = "prompt")
    protected String prompt;

    @Json(name = "input_value")
    protected String inputValue;
    @Json(name = "place_holder")
    protected String placeHolder;
    //type are from here, https://developer.android.com/reference/android/text/InputType.html
    @Json(name = "input_type")
    protected int inputType;

    public PropertiesEditText getPropertiesEditText() {
        return new PropertiesEditText(inputValue, placeHolder, inputType);
    }

    public PropertiesCheckBox getPropertiesCheckBox() {
        return new PropertiesCheckBox(items);
    }

    public PropertiesDatePicker getPropertiesDatePicker() {
        return new PropertiesDatePicker(displayFormat);
    }

    public PropertiesTimePicker getPropertiesTimePicker() {
        return new PropertiesTimePicker(displayFormat, is24HourView);
    }

    public PropertiesSpinner getPropertiesSpinner() {
        return new PropertiesSpinner(items, prompt);
    }
}
