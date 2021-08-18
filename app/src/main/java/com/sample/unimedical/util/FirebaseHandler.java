package com.sample.unimedical.util;

import android.content.Context;
import android.widget.Toast;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class FirebaseHandler {

    private static final FirebaseStorage storage = FirebaseStorage.getInstance("gs://medi-5dbf9.appspot.com/");

    public static void downloadFile(Context context, String localCustomerFileName, String customerFileName) throws FileNotFoundException {
        StorageReference storageReference = storage.getReference();
        StorageReference customerRef = storageReference.child(customerFileName);

        FileOutputStream fos = context.openFileOutput(localCustomerFileName, Context.MODE_PRIVATE);

        final long TEN_MEGABYTE = 1024 * 1024 * 10;

        customerRef.getBytes(TEN_MEGABYTE).addOnSuccessListener(bytes -> {
            try {
                fos.write(bytes);
                fos.flush();
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).addOnFailureListener(v -> Toast.makeText(context, "정보 업데이트 실패", Toast.LENGTH_SHORT).show())
                .addOnCompleteListener(v -> Toast.makeText(context, "정보 업데이트가 완료되었습니다.", Toast.LENGTH_SHORT).show());
    }

}
