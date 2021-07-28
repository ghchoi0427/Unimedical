package com.sample.unimedical.util;

import android.util.Log;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;

public class FirebaseHandler {

    private static FirebaseStorage storage = FirebaseStorage.getInstance("gs://medi-5dbf9.appspot.com/");
    private static final String CUSTOMER_FILE_NAME = "customer.xls";

    public static void test() throws IOException {
        StorageReference storageReference = storage.getReference();
        StorageReference customerRef = storageReference.child(CUSTOMER_FILE_NAME);

        File localCustomerFile = File.createTempFile("customer", "xls");
        customerRef.getFile(localCustomerFile)
                .addOnSuccessListener(e -> Log.d("tester", "file load succeed"))
                .addOnFailureListener(e -> Log.d("tester", "fail"));


    }
}
