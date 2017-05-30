
package com.github.dkharrat.nexusdialog.sample.model.property;

import java.util.List;

public class PropertiesRadioGroup extends Property {
    public PropertiesRadioGroup(List<String> items) {
        super.items = items;
    }

    public List<String> getItems() {
        return items;
    }

    public void setItems(List<String> items) {
        this.items = items;
    }

}
