
package com.github.dkharrat.nexusdialog.sample.model.property;


public class PropertiesEditText extends Property {

    public PropertiesEditText(String inputValue, String placeHolder, int inputType) {
        super.inputValue = inputValue;
        super.placeHolder = placeHolder;
        super.inputType = inputType;
    }

    public String getInputValue() {
        return inputValue;
    }

    public void setInputValue(String inputValue) {
        this.inputValue = inputValue;
    }

    public String getPlaceHolder() {
        return placeHolder;
    }

    public void setPlaceHolder(String placeHolder) {
        this.placeHolder = placeHolder;
    }

    public int getInputType() {
        return inputType;
    }

    public void setInputType(int inputType) {
        this.inputType = inputType;
    }

}
