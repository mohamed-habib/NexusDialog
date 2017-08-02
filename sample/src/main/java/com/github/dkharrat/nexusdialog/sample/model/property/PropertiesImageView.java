
package com.github.dkharrat.nexusdialog.sample.model.property;


import com.github.dkharrat.nexusdialog.controllers.ImageViewController;

public class PropertiesImageView extends Property {

    public PropertiesImageView(String imageResource, ImageViewController.ImageResourceType imageResourceType) {
        super.imageResource = imageResource;
        super.imageResourceType = imageResourceType;
    }

    public ImageViewController.ImageResourceType getImageResourceType() {
        return imageResourceType;
    }

    public String getImageResource() {
        return imageResource;
    }


}
