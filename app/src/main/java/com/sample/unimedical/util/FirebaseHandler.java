package com.sample.unimedical.util;

import android.content.Context;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class FirebaseHandler {

    private static FirebaseStorage storage = FirebaseStorage.getInstance("gs://medi-5dbf9.appspot.com/");
    private static final String CUSTOMER_FILE_NAME = "customer.xls";
    private static final String LOCAL_CUSTOMER_FILE_NAME = "local_customer.xls";

    public static void downloadFile(Context context) throws FileNotFoundException {
        StorageReference storageReference = storage.getReference();
        StorageReference customerRef = storageReference.child(CUSTOMER_FILE_NAME);

        FileOutputStream fos = context.openFileOutput(LOCAL_CUSTOMER_FILE_NAME, Context.MODE_PRIVATE);

        final long ONE_MEGABYTE = 1024 * 1024;

        customerRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(bytes -> {
            try {
                fos.write(bytes);
                fos.flush();
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }


}
