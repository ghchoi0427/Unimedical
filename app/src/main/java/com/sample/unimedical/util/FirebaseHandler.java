package com.sample.unimedical.util;

import android.content.Context;
import android.widget.Toast;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class FirebaseHandler {

    private static FirebaseStorage storage = FirebaseStorage.getInstance("gs://medi-5dbf9.appspot.com/");
    private static final String CUSTOMER_FILE_NAME = "customer.xls";

    public static void downloadFile(Context context, String filename) throws FileNotFoundException {
        StorageReference storageReference = storage.getReference();
        StorageReference customerRef = storageReference.child(CUSTOMER_FILE_NAME);

        FileOutputStream fos = context.openFileOutput(filename, Context.MODE_PRIVATE);

        final long ONE_MEGABYTE = 1024 * 1024;

        customerRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(bytes -> {
            try {
                fos.write(bytes);
                fos.flush();
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).addOnCompleteListener(v -> Toast.makeText(context, "거래처 정보 업데이트가 완료되었습니다.", Toast.LENGTH_SHORT).show());
    }


}
