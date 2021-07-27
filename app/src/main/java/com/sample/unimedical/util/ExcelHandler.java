package com.sample.unimedical.util;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;

import com.sample.unimedical.domain.hospital.Hospital;

import net.daum.mf.map.api.MapPOIItem;

import java.io.IOException;
import java.io.InputStream;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class ExcelHandler extends AppCompatActivity {

    private static final String EXCEL_CONTRACT_FILENAME = "account0726.xls";
    private static final int rowIndexStart = 2;
    private static final int colHospitalName = 4;

    public static MapPOIItem setContract(Hospital hospital, MapPOIItem mapPOIItem, Context context) {
        try {
            InputStream is = context.getResources().getAssets().open(EXCEL_CONTRACT_FILENAME);
            Workbook wb = Workbook.getWorkbook(is);

            Sheet sheet = wb.getSheet(0);

            if (sheet != null) {
                int colTotal = sheet.getColumns();
                int rowTotal = sheet.getColumn(colTotal - 1).length;

                for (int row = rowIndexStart; row < rowTotal; row++) {

                    String hospitalName = sheet.getCell(colHospitalName, row).getContents();
                    if (validateHospital(hospitalName)) {
                        if (hospitalName.equals(hospital.getYadmNm())) {
                            hospital.setManager(sheet.getCell(1, row).getContents());
                            hospital.setDevice(sheet.getCell(9, row).getContents());

                            mapPOIItem.setItemName(setHospitalInfo(hospital));
                            mapPOIItem.setMarkerType(MapPOIItem.MarkerType.RedPin);
                            mapPOIItem.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin);
                        }
                    }
                }
            }

        } catch (IOException | BiffException e) {
            e.printStackTrace();
        }

        return mapPOIItem;
    }

    private static String setHospitalInfo(Hospital hospital) {
        return hospital.getYadmNm() + "/" + hospital.getManager() + "/" + hospital.getTelno() + "/" + hospital.getDevice();
    }


    private static boolean validateHospital(String hospitalCell) {
        if (hospitalCell.contains("폐업") || hospitalCell.contains("거래중지") || hospitalCell.contains("(주)") || hospitalCell.contains("주식회사") || hospitalCell.equals("")) {
            return false;
        }
        return true;
    }

}
