package com.dev.kratos.exportcsv;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

public class ReadExcel {
    public static void convertToCsv(Sheet sheet) {
        Row row = null;
        for(int i = 0; i < sheet.getLastRowNum(); i++) {
            row = sheet.getRow(i);
            for(int j = 0; j < row.getLastCellNum(); j++) {
                System.out.println("\"" + row.getCell(j) + "\";");
            }
            System.out.println();
        }

    }

}
