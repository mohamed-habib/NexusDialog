
package com.github.dkharrat.nexusdialog.sample.model.property;


import java.text.SimpleDateFormat;

public class PropertiesDatePicker extends Property {
    public PropertiesDatePicker(String displayFormat) {
        super.displayFormat = displayFormat;
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

}
