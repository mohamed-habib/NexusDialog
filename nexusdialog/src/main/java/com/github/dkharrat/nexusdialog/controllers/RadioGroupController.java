package com.github.dkharrat.nexusdialog.controllers;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.github.dkharrat.nexusdialog.FormController;
import com.github.dkharrat.nexusdialog.validations.InputValidator;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Represents a field that allows a user to select one item from radiobuttons.
 * <p/>
 * For the field value, the associated FormModel must return either a String or a 0-based index, representing the
 * currently selected items. Which representation to use is specified by the constructor. In either representation, no
 * selection can be represented by returning {@code null} for the value of the field.
 */
public class RadioGroupController extends LabeledFieldController {
    private final static int RADIOBUTTON_ID = 405060;
    private final List<String> items;
    private final List<?> values;
    private final int radioGroupId = FormController.generateViewId();


    /**
     * Constructs a new instance of a radio button field.
     *
     * @param ctx              the Android context
     * @param name             the name of the field
     * @param labelText        the label to display beside the field. Set to {@code null} to not show a label
     * @param validators       contains the validations to process on the field
     * @param items            a list of Strings defining the selection items to show
     * @param useItemsAsValues if true, {@code RadioGroupController} expects the associated form model to use
     *                         the same string of the selected item when getting or setting the field; otherwise,
     *                         {@code RadioGroupController} expects the form model to use index (as an Integer) to
     *                         represent the selected item
     */
    public RadioGroupController(Context ctx, String name, String labelText, Set<InputValidator> validators, List<String> items, boolean useItemsAsValues) {
        this(ctx, name, labelText, validators, items, useItemsAsValues ? items : null);
    }

    /**
     * Constructs a new instance of a radio button field.
     *
     * @param ctx        the Android context
     * @param name       the name of the field
     * @param labelText  the label to display beside the field
     * @param validators contains the validations to process on the field
     * @param items      a list of Strings defining the selection items to show
     * @param values     a list of Objects representing the values to set the form model on a selection (in
     *                   the same order as the {@code items}.
     */
    public RadioGroupController(Context ctx, String name, String labelText, Set<InputValidator> validators, List<String> items, List<?> values) {
        super(ctx, name, labelText, validators);
        this.items = items;
        this.values = values;
    }


    /**
     * Constructs a new instance of a radio button field.
     *
     * @param ctx              the Android context
     * @param name             the name of the field
     * @param labelText        the label to display beside the field. Set to {@code null} to not show a label
     * @param isRequired       indicates if the field is required or not
     * @param items            a list of Strings defining the selection items to show
     * @param useItemsAsValues if true, {@code RadioGroupController} expects the associated form model to use
     *                         the same string of the selected item when getting or setting the field; otherwise,
     *                         {@code RadioGroupController} expects the form model to use index (as an Integer) to
     *                         represent the selected item
     */
    public RadioGroupController(Context ctx, String name, String labelText, boolean isRequired, List<String> items, boolean useItemsAsValues) {
        this(ctx, name, labelText, isRequired, items, useItemsAsValues ? items : null);
    }

    /**
     * Constructs a new instance of a radio button field.
     *
     * @param ctx        the Android context
     * @param name       the name of the field
     * @param labelText  the label to display beside the field
     * @param isRequired indicates if the field is required or not
     * @param items      a list of Strings defining the selection items to show
     * @param values     a list of Objects representing the values to set the form model on a selection (in
     *                   the same order as the {@code items}.
     */
    public RadioGroupController(Context ctx, String name, String labelText, boolean isRequired, List<String> items, List<?> values) {
        super(ctx, name, labelText, isRequired);
        this.items = items;
        this.values = values;
    }

    @Override
    protected View createFieldView() {
//        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        ViewGroup radioGroupContainer = (ViewGroup) inflater.inflate(R.layout.form_radiogroup_container, null);
        RadioGroup radioGroupContainer = new RadioGroup(getContext());
        radioGroupContainer.setId(radioGroupId);

        RadioButton radioButton;
        int nbItem = items.size();
        for (int index = 0; index < nbItem; index++) {
            radioButton = new RadioButton(getContext());
            radioButton.setText(items.get(index));
            radioButton.setId(RADIOBUTTON_ID + index);
            radioButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CompoundButton buttonView = (CompoundButton) v;
                    int position = buttonView.getId() - RADIOBUTTON_ID;

                    Object value = areValuesDefined() ? values.get(position) : position;
                    Set<Object> modelValues = retrieveModelValues();
                    if (buttonView.isChecked()) {
                        modelValues.add(value);
                    } else {
                        modelValues.remove(value);
                    }
                    getModel().setValue(getName(), modelValues);
                }

            });

            radioGroupContainer.addView(radioButton);
            refresh(radioButton, index);
        }

        return radioGroupContainer;
    }


    /**
     * Returns the values hold in the model.
     *
     * @return The values from the model.
     */
    private Set<Object> retrieveModelValues() {
        Set<Object> modelValues = (Set<Object>) getModel().getValue(getName());
        if (modelValues == null) {
            modelValues = new HashSet<>();
        }
        return modelValues;
    }


    private void refresh(RadioButton radioButton, int index) {
        Set<Object> modelValues = retrieveModelValues();
        radioButton.setChecked(modelValues.contains(areValuesDefined() ? radioButton.getText() : index));
    }

//    public void refresh(CheckBox checkbox, int index) {
//        Set<Object> modelValues = retrieveModelValues();
//        checkbox.setChecked(
//                modelValues.contains(
//                        areValuesDefined() ? checkbox.getText() : index
//                )
//        );
//    }

    @Override
    public void refresh() {
        ViewGroup layout = getContainer();

        RadioButton radioButton;
        int nbItem = items.size();
        for (int index = 0; index < nbItem; index++) {
            radioButton = (RadioButton) layout.findViewById(RADIOBUTTON_ID + index);
            refresh(radioButton, index);
        }
    }

    /**
     * Returns the status of the values entry.
     *
     * @return true if values entry can be used. false otherwise.
     */
    private boolean areValuesDefined() {
        return values != null;
    }


    /**
     * Returns the View containing the checkboxes.
     *
     * @return The View containing the checkboxes.
     */
    private ViewGroup getContainer() {
        return (ViewGroup) getView().findViewById(radioGroupId);
    }
}
