package com.sample.unimedical.util;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;

import junit.framework.TestCase;

public class XMLHandlerTest extends TestCase {
    Context context;

    public void testLoadXml() {
        context = ApplicationProvider.getApplicationContext();
        try {
            XMLHandler.readHospitalList(context);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}