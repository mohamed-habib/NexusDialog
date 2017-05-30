package com.github.dkharrat.nexusdialog.sample;

import android.content.Context;
import android.view.View;
import android.widget.Button;

import com.github.dkharrat.nexusdialog.controllers.LabeledFieldController;
import com.github.dkharrat.nexusdialog.validations.InputValidator;

import java.util.Set;

public class SignatureLayoutElement extends LabeledFieldController {
    SignatureMainLayout signatureMainLayout;

    /**
     * Constructs a new instance of an edit text field.
     *
     * @param ctx   the Android context
     * @param name  the name of the field
     * @param label the label to display beside the field
     */
    public SignatureLayoutElement(Context ctx, String name, String label) {
        super(ctx, name, label, false);
    }


    /**
     * Constructs a new instance of an edit text field.
     *
     * @param ctx        the Android context
     * @param name       the name of the field
     * @param labelText  the label to display beside the field
     * @param validators contains the validations to process on the field
     */
    public SignatureLayoutElement(Context ctx, String name, String labelText, Set<InputValidator> validators) {
        super(ctx, name, labelText, validators);
    }


    @Override
    protected View createFieldView() {
        signatureMainLayout = new SignatureMainLayout(getContext());
        return signatureMainLayout;
    }

    public void refresh() {
        // nothing to refresh
    }
}
