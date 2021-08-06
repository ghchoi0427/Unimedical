package com.sample.unimedical.util;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;

import com.sample.unimedical.R;
import com.sample.unimedical.domain.hospital.Hospital;

import net.daum.mf.map.api.MapPOIItem;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Objects;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class ExcelHandler extends AppCompatActivity {

    private static final int FirstSegment = 0;
    private static final int rowIndexStart = 2;
    private static final int colHospitalLocation = 3;
    private static final int colHospitalName = 4;

    public static MapPOIItem setContract(Hospital hospital, MapPOIItem mapPOIItem, Context context) {
        try {
            InputStream is;
            if (isFileExist(context, context.getString(R.string.local_customer_file_name))) {
                is = context.openFileInput(context.getString(R.string.local_customer_file_name));
            } else {
                is = context.getAssets().open(context.getString(R.string.local_customer_file_name));
            }
            Workbook wb = Workbook.getWorkbook(is);

            Sheet sheet = wb.getSheet(0);

            if (sheet != null) {
                int colTotal = sheet.getColumns();
                int rowTotal = sheet.getColumn(colTotal - 1).length;

                for (int row = rowIndexStart; row < rowTotal; row++) {

                    String hospitalName = sheet.getCell(colHospitalName, row).getContents();
                    String hospitalLocation = sheet.getCell(colHospitalLocation, row).getContents();
                    if (validateHospital(hospitalName) && validateLocation(hospitalLocation, hospital.getSidoCdNm())) {
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

    public static boolean isFileExist(Context context, String filename) throws IOException {
        return context.openFileInput(filename).available() > 0;
    }

    private static String setHospitalInfo(Hospital hospital) {
        return hospital.getYadmNm() + "/" + hospital.getManager() + "/" + hospital.getTelno() + "/" + hospital.getDevice();
    }


    private static boolean validateHospital(String hospitalCell) {
        return !hospitalCell.contains("폐업") && !hospitalCell.contains("거래중지") && !hospitalCell.contains("(주)") && !hospitalCell.contains("주식회사") && !hospitalCell.equals("");
    }

    private static boolean validateLocation(String hospitalCell, String sidoCodeName) {

        HashMap<String, String> sidoMap = new HashMap<>(DataMapper.getSidoMap());
        return (Objects.equals(sidoMap.get(hospitalCell.split(" ")[FirstSegment]), sidoCodeName));
    }


}
