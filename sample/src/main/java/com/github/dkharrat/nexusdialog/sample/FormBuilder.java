package com.github.dkharrat.nexusdialog.sample;

import android.content.Context;
import android.support.annotation.NonNull;

import com.github.dkharrat.nexusdialog.FormController;
import com.github.dkharrat.nexusdialog.FormElementController;
import com.github.dkharrat.nexusdialog.FormWithAppCompatActivity;
import com.github.dkharrat.nexusdialog.controllers.CheckBoxController;
import com.github.dkharrat.nexusdialog.controllers.DatePickerController;
import com.github.dkharrat.nexusdialog.controllers.EditTextController;
import com.github.dkharrat.nexusdialog.controllers.FormSectionController;
import com.github.dkharrat.nexusdialog.controllers.RadioGroupController;
import com.github.dkharrat.nexusdialog.controllers.SelectionController;
import com.github.dkharrat.nexusdialog.controllers.TimePickerController;
import com.github.dkharrat.nexusdialog.sample.model.Component;
import com.github.dkharrat.nexusdialog.sample.model.DynamicForm;
import com.github.dkharrat.nexusdialog.sample.model.Section;
import com.github.dkharrat.nexusdialog.sample.model.property.PropertiesCheckBox;
import com.github.dkharrat.nexusdialog.sample.model.property.PropertiesDatePicker;
import com.github.dkharrat.nexusdialog.sample.model.property.PropertiesEditText;
import com.github.dkharrat.nexusdialog.sample.model.property.PropertiesRadioGroup;
import com.github.dkharrat.nexusdialog.sample.model.property.PropertiesSignature;
import com.github.dkharrat.nexusdialog.sample.model.property.PropertiesSpinner;
import com.github.dkharrat.nexusdialog.sample.model.property.PropertiesTimePicker;
import com.github.dkharrat.nexusdialog.validations.InputValidator;
import com.github.dkharrat.nexusdialog.validations.RequiredFieldValidator;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Mohamed Habib on 29/05/2017.
 */

public class FormBuilder extends FormWithAppCompatActivity {

    @Override
    public void initForm(FormController formController) {

        DynamicForm dynamicForm = readDynamicForm();
        if (dynamicForm != null) {
            setTitle(dynamicForm.getFormTitle());

            for (Section section : dynamicForm.getSections()) {
                FormSectionController formSectionController = new FormSectionController(this, section.getSectionId(), section.getSectionTitle());
                for (Component component : section.getComponents()) {
                    FormElementController formElementController = convertElement(this, component);
                    if (formElementController != null)
                        formSectionController.addElement(formElementController);
                }
                formController.addSection(formSectionController);
            }

//            FormSectionController section = new FormSectionController(this, "Personal Info");
//            section.addElement(new EditTextController(this, "firstName", "First name"));
//            section.addElement(new EditTextController(this, "lastName", "Last name"));
//            section.addElement(new SelectionController(this, "gender", "Gender", true, "Select", Arrays.asList("Male", "Female"), true));
//            section.addElement(new CheckBoxController(this, "hobbies", "You like", true, Arrays.asList("sport", "gaming", "relaxation", "development"), true));
//            section.addElement(new DatePickerController(this, "date", "Choose Date"));
//            section.addElement(new TimePickerController(this, "time", "Choose Time"));
//            formController.addSection(section);
        }
    }

    FormElementController convertElement(Context context, Component component) {
        switch (component.getType()) {
            case EDIT_TEXT:
                return convertEditText(context, component);
            case CHECK_BOX:
                return convertCheckBox(context, component);
            case DATE_PICKER:
                return convertDatePicker(context, component);
            case TIME_PICKER:
                return convertTimePicker(context, component);
            case SPINNER:
                return convertSpinner(context, component);
            case RADIO_GROUP:
                return convertRadioGroup(context, component);
            case SIGNATURE:
                return convertSignature(context, component);
            default:
                return null;
        }
    }

    private FormElementController convertSpinner(Context context, Component component) {
        Set<InputValidator> inputValidators = getValidators(component.getValidators());
        PropertiesSpinner properties = component.getProperties().getPropertiesSpinner();

        if (properties == null || properties.getItems() == null)
            return null;
        else
            //properties and validators are not null
            return new SelectionController(context, component.getId(), component.getLabel(), inputValidators, properties.getPrompt(), properties.getItems(), false);

    }

    private FormElementController convertDatePicker(Context context, Component component) {
        Set<InputValidator> inputValidators = getValidators(component.getValidators());
        PropertiesDatePicker properties = component.getProperties().getPropertiesDatePicker();

        if (properties == null || properties.getDisplayFormat() == null)
            return new DatePickerController(context, component.getId(), component.getLabel(), inputValidators, new SimpleDateFormat());
        else
            //properties and validators are not null
            return new DatePickerController(context, component.getId(), component.getLabel(), inputValidators, properties.getDisplayFormat());

    }

    private FormElementController convertTimePicker(Context context, Component component) {
        Set<InputValidator> inputValidators = getValidators(component.getValidators());
        PropertiesTimePicker properties = component.getProperties().getPropertiesTimePicker();

        if (properties == null || properties.getDisplayFormat() == null)
            return new TimePickerController(context, component.getId(), component.getLabel(), inputValidators, new SimpleDateFormat(), false);
        else
            //properties and validators are not null
            return new TimePickerController(context, component.getId(), component.getLabel(), inputValidators, properties.getDisplayFormat(), properties.getIs24HourView());
    }

    private FormElementController convertCheckBox(Context context, Component component) {
        Set<InputValidator> inputValidators = getValidators(component.getValidators());
        PropertiesCheckBox properties = component.getProperties().getPropertiesCheckBox();

        if (properties == null)
            //properties is null, then the check box is useless, it must have items
            return null;
        else
            //properties and validators are not null
            return new CheckBoxController(context, component.getId(), component.getLabel(), inputValidators, properties.getItems(), false);
    }

    private FormElementController convertRadioGroup(Context context, Component component) {
        Set<InputValidator> inputValidators = getValidators(component.getValidators());
        PropertiesRadioGroup properties = component.getProperties().getPropertiesRadioGroup();

        if (properties == null)
            //properties is null, then the check box is useless, it must have items
            return null;
        else
            //properties and validators are not null
            return new RadioGroupController(context, component.getId(), component.getLabel(), inputValidators, properties.getItems(), false);
    }

    @NonNull
    private FormElementController convertEditText(Context context, Component component) {
        Set<InputValidator> inputValidators = getValidators(component.getValidators());
        PropertiesEditText properties = component.getProperties().getPropertiesEditText();

        if (properties == null)
            //validators is null
            return new EditTextController(context, component.getId(), component.getLabel(), inputValidators);
        else
            //properties and validators are not null
            return new EditTextController(context, component.getId(), component.getLabel(), properties.getPlaceHolder(), inputValidators, properties.getInputType());
    }

    @NonNull
    private FormElementController convertSignature(Context context, Component component) {
        Set<InputValidator> inputValidators = getValidators(component.getValidators());
        PropertiesSignature properties = component.getProperties().getPropertiesSignature();
        return new SignatureLayoutElement(context, component.getId(), component.getLabel(), inputValidators);

    }

    @NonNull
    private Set<InputValidator> getValidators(List<Component.Validators> validators) {
        HashSet<InputValidator> inputValidators = new HashSet<InputValidator>();
        for (Component.Validators validator : validators)
            if (validator == Component.Validators.IS_REQUIRED)
                inputValidators.add(new RequiredFieldValidator());

        return inputValidators;
    }

    private DynamicForm readDynamicForm() {
        String json = readFile("sample.json", this);

        Moshi moshi = new Moshi.Builder().build();
        JsonAdapter<DynamicForm> jsonAdapter = moshi.adapter(DynamicForm.class);

        DynamicForm dynamicForm = null;
        try {
            dynamicForm = jsonAdapter.fromJson(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dynamicForm;
    }

    /**
     * Helper function to load file from assets
     */
    private String readFile(String fileName, Context context) {
        StringBuilder returnString = new StringBuilder();
        InputStream fIn = null;
        InputStreamReader isr = null;
        BufferedReader input = null;
        try {
            fIn = context.getResources().getAssets().open(fileName);
            isr = new InputStreamReader(fIn);
            input = new BufferedReader(isr);
            String line;
            while ((line = input.readLine()) != null) {
                returnString.append(line);
            }
        } catch (Exception e) {
            e.getMessage();
        } finally {
            try {
                if (isr != null) isr.close();
                if (fIn != null) fIn.close();
                if (input != null) input.close();
            } catch (Exception e2) {
                e2.getMessage();
            }
        }
        return returnString.toString();
    }

}
