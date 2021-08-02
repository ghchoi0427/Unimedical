package com.sample.unimedical.util;

import android.content.Context;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class FirebaseHandler {

    private static FirebaseStorage storage = FirebaseStorage.getInstance("gs://medi-5dbf9.appspot.com/");
    private static final String CUSTOMER_FILE_NAME = "customer.xls";

    public static void downloadFile(Context context) throws FileNotFoundException {
        StorageReference storageReference = storage.getReference();
        StorageReference customerRef = storageReference.child(CUSTOMER_FILE_NAME);

        FileOutputStream fos = context.openFileOutput("test_file.xls", Context.MODE_PRIVATE);

        final long ONE_MEGABYTE = 1024 * 1024;

        customerRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(bytes -> {
            try {
                fos.write(bytes);
                fos.flush();
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).addOnCompleteListener(task -> {
            try {
                readFile(context);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public static void readFile(Context context) throws IOException {
        FileInputStream fis = context.openFileInput("test_file.xls");
        BufferedReader br = new BufferedReader(new InputStreamReader(fis, StandardCharsets.UTF_8));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
    }
}
