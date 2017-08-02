package com.github.dkharrat.nexusdialog.controllers;

import android.test.mock.MockContext;

import junit.framework.TestCase;

/**
 * Created by Mohamed Habib on 31/05/2017.
 */
public class ImageViewControllerTest extends TestCase {
    public void testIsImageResourceTypeValid() throws Exception {
        MockContext mockContext = new MockContext();
        String imageResource = "R.drawable.photo";
        ImageViewController.ImageResourceType imageResourceType = ImageViewController.ImageResourceType.DRAWABLE;

        ImageViewController.isImageResourceTypeValid(mockContext, imageResource, imageResourceType);

    }

}