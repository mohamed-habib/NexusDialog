
package com.github.dkharrat.nexusdialog.sample.model.property;

import java.util.List;

public class PropertiesSpinner extends Property {
    public PropertiesSpinner(List<String> items, String prompt) {
        super.items = items;
        super.prompt = prompt;
    }

    public List<String> getItems() {
        return items;
    }

    public void setItems(List<String> items) {
        this.items = items;
    }

    public String getPrompt() {
        return prompt;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }

}
