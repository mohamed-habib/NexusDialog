
package com.github.dkharrat.nexusdialog.sample.model.property;


import java.text.SimpleDateFormat;

public class PropertiesTimePicker extends Property {

    public PropertiesTimePicker(String displayFormat, boolean is24HourView) {
        super.displayFormat = displayFormat;
        super.is24HourView = is24HourView;
    }

    public SimpleDateFormat getDisplayFormat() {
        if (displayFormat == null)
            return new SimpleDateFormat();
        else
            return new SimpleDateFormat(displayFormat);
    }

    public void setDisplayFormat(String displayFormat) {
        this.displayFormat = displayFormat;
    }

    public boolean getIs24HourView() {
        return is24HourView;
    }

    public void setIs24HourView(boolean is24HourView) {
        this.is24HourView = is24HourView;
    }

}
