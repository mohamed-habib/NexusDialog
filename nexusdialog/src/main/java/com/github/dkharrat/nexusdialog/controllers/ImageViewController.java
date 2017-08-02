package com.github.dkharrat.nexusdialog.controllers;

import android.content.Context;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.ImageView;

import com.github.dkharrat.nexusdialog.FormController;
import com.github.dkharrat.nexusdialog.R;
import com.github.dkharrat.nexusdialog.validations.InputValidator;
import com.squareup.picasso.Picasso;

import java.util.Set;

/**
 * Represents a field that shows image.
 */
public class ImageViewController extends LabeledFieldController {
    private final int imageViewId = FormController.generateViewId();

    private final String imageResource;
    private final ImageResourceType imageResourceType;

    /**
     * Constructs a new instance of an image view field.
     *
     * @param ctx               the Android context
     * @param name              the name of the field
     * @param labelText         the label to display beside the field. Set to {@code null} to not show a label.
     * @param imageResource     the image to view
     * @param imageResourceType the type of the @param imageResource
     * @param validators        contains the validations to process on the field
     */
    public ImageViewController(Context ctx, String name, String labelText, Set<InputValidator> validators, String imageResource, ImageResourceType imageResourceType) {
        super(ctx, name, labelText, validators);
        this.imageResource = imageResource;
        this.imageResourceType = imageResourceType;
    }

    /**
     * Constructs a new instance of an edit text field.
     *
     * @param ctx               the Android context
     * @param name              the name of the field
     * @param labelText         the label to display beside the field. Set to {@code null} to not show a label.
     * @param imageResource     the image to view
     * @param imageResourceType the type of the @param imageResource
     * @param isRequired        indicates if the field is required or not
     */
    public ImageViewController(Context ctx, String name, String labelText, boolean isRequired, String imageResource, ImageResourceType imageResourceType) {
        super(ctx, name, labelText, isRequired);
        this.imageResource = imageResource;
        this.imageResourceType = imageResourceType;
    }

    /**
     * Constructs a new instance of an edit text field.
     *
     * @param ctx               the Android context
     * @param name              the name of the field
     * @param labelText         the label to display beside the field
     * @param imageResource     the image to view
     * @param imageResourceType the type of the @param imageResource
     */
    public ImageViewController(Context ctx, String name, String labelText, String imageResource, ImageResourceType imageResourceType) {
        this(ctx, name, labelText, false, imageResource, imageResourceType);

    }

    /**
     * Returns the ImageView associated with this element.
     *
     * @return the ImageView view associated with this element
     */
    public ImageView getImageView() {
        return (ImageView) getView().findViewById(imageViewId);
    }

    @Override
    protected View createFieldView() {
        final ImageView imageView = new ImageView(getContext());
        imageView.setId(imageViewId);

        setImageResource(imageView);

        refresh(imageView);

        return imageView;
    }

    private void setImageResource(ImageView imageView) {
        switch (imageResourceType) {
            case DRAWABLE:
                setImageDrawable(imageView);
                break;
            case URL:
                setImageUrl(imageView);
                break;
        }
        //setting image resource
    }

    private void setImageUrl(ImageView imageView) {
        Picasso.with(getContext()).load(imageResource).into(imageView);
        imageView.setTag(R.id.imageResource, imageResource);
    }

    private void setImageDrawable(ImageView imageView) {
        int drawableId = getDrawableId(getContext(), imageResource);
        imageView.setImageResource(drawableId);
        imageView.setTag(R.id.imageResource, imageResource);
    }

    public static int getDrawableId(Context context, String imageResource) {
        return context.getResources().getIdentifier(imageResource, "drawable", context.getPackageName());
    }

    /**
     * should be called before adding ImageViewController element to the form
     * TODO: 31/05/2017: find a way to force this to be called before calling the constructor
     *
     * @return
     */
    public static boolean isImageResourceTypeValid(Context context, String imageResource, ImageResourceType imageResourceType) {
        switch (imageResourceType) {
            case DRAWABLE:
                try {
                    return getDrawableId(context, imageResource) != 0;
                } catch (Exception e) {
                    return false;
                }
            case URL:
                return URLUtil.isValidUrl(imageResource);
        }
        return false;
    }

    private void refresh(ImageView imageView) {
        Object value = getModel().getValue(getName());
        String valueStr = value != null ? value.toString() : "";

        if (imageView.getTag(R.id.imageResource) != null) {
            if (!valueStr.equals(imageView.getTag(R.id.imageResource).toString()))
                setImageResource(imageView);
        } else
            setImageResource(imageView);
    }

    @Override
    public void refresh() {
        refresh(getImageView());
    }

    public enum ImageResourceType {
        DRAWABLE,
        URL
    }

}
