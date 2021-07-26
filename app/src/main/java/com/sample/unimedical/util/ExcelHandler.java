package com.sample.unimedical.util;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;

import com.sample.unimedical.domain.hospital.Hospital;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class ExcelHandler extends AppCompatActivity {

    private final String EXCEL_CONTRACT_FILENAME = "account0726.xls";
    private final int rowIndexStart = 2;
    private final int colHospitalName = 4;

    public List<Hospital> searchContracts(Context context, String keyword) {
        List<Hospital> searchList = new ArrayList<>();

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
                        if (hospitalName.equals(keyword)) {
                            searchList.add(getHospitalInfo(sheet, row));
                        }
                    }
                }
            }

        } catch (IOException | BiffException e) {
            e.printStackTrace();
        }
        return searchList;
    }

    private boolean hasContract(Sheet sheet, String hospitalName) {
        for (int row = rowIndexStart; row < sheet.getColumn(sheet.getColumns() - 1).length; row++) {
            if (sheet.getCell(colHospitalName, row).equals(hospitalName)) {
                return true;
            }
        }
        return false;
    }

    private boolean validateHospital(String hospitalCell) {
        if (hospitalCell.contains("폐업") || hospitalCell.contains("거래중지") || hospitalCell.contains("(주)") || hospitalCell.contains("주식회사") || hospitalCell.equals("")) {
            return false;
        }
        return true;
    }

    private Hospital getHospitalInfo(Sheet sheet, int row) {
        Hospital hospital = new Hospital();
        hospital.setYadmNm(sheet.getCell(colHospitalName, row).getContents());
        hospital.setManager(sheet.getCell(1, row).getContents());
        hospital.setDevice(sheet.getCell(9, row).getContents());

        return hospital;
    }
}
