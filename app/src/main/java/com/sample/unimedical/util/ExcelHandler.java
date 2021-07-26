package com.sample.unimedical.util;

import android.content.Context;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.io.InputStream;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class ExcelHandler extends AppCompatActivity {

    public void readExcel(Context context) {
        try {
            InputStream is = context.getResources().getAssets().open("account0726.xls");
            Workbook wb = Workbook.getWorkbook(is);

            if (wb != null) {
                Sheet sheet = wb.getSheet(0);

                if (sheet != null) {
                    int colTotal = sheet.getColumns();
                    int rowIndexStart = 1;
                    int rowTotal = sheet.getColumn(colTotal - 1).length;

                    for (int row = rowIndexStart; row < rowTotal; row++) {

                        String content = sheet.getCell(4, row).getContents();
                        Log.d("tester", content);

                    }
                }
            }

        } catch (IOException | BiffException e) {
            e.printStackTrace();
        }
    }

    public boolean validateHospital(String hospitalCell) {
        if (hospitalCell.contains("폐업") || hospitalCell.contains("거래중지") || hospitalCell.contains("(주)") || hospitalCell.contains("주식회사")) {
            return false;
        }
        return true;
    }
}
