package com.dev.kratos.exportcsv;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.monitorjbl.xlsx.StreamingReader;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import sun.rmi.runtime.Log;

import java.io.*;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

@SpringBootApplication
@Slf4j
public class ExportcsvApplication {

    public static void main(String[] args) {

        SpringApplication.run(ExportcsvApplication.class, args);

        File file = new File("map_ALL_export_20200430110001_file.xlsx");
        File csvFile = new File("C:/Edit/Dev/map_export.csv");
        BufferedWriter bw = null;

        //InputStream inputStream = null;
        try {
            /*
            Makes sure that the file gets created if it is not present at the specified location
             */
            if(!csvFile.exists()) {
                csvFile.createNewFile();
            }

            FileWriter fw = new FileWriter(csvFile);
            bw = new BufferedWriter(fw);

            Workbook workbook = StreamingReader.builder()
                    .rowCacheSize(100)
                    .bufferSize(4096)
                    .open(file);

            String cellValue = "";
            Sheet sheet = workbook.getSheetAt(0);

            Iterator<Row> rowIterator = sheet.rowIterator();
            //Create a DataFormatter to format get each cell's value as String
            DataFormatter dataFormatter = new DataFormatter();

            while(rowIterator.hasNext()) {
                Row row = rowIterator.next();
                int rVal = row.getRowNum();

                if(rVal > 3) {
                    //Now let's interate over the columns of the current row
                    Iterator<Cell> cellIterator = row.cellIterator();
                    //Logger.getLogger(ReadExcel.class.getName()).log(Level.INFO,"Current Row Number: "+row.getRowNum());

                    while (cellIterator.hasNext()) {
                        Cell cell = cellIterator.next();
                        cellValue += dataFormatter.formatCellValue(cell);
                        cellValue += ",";
                        //System.out.print(cellValue + ",");
                    }
                }

                //System.out.println();
                bw.write(cellValue);
                bw.newLine();
                bw.flush();
                cellValue = "";

                /* if(rVal == 20) {
                    bw.close();
                    System.exit(1);
                } */
            }



        } catch (Exception ex) {
            log.debug("General Exception thrown.  See stack trace for more additional information");
            ex.printStackTrace();
        } finally {
            try {
                if (bw != null) {
                    bw.close();
                }
            } catch (Exception ex) {
                log.debug("General Exception thrown.  See stack trace for more additional information");
                ex.printStackTrace();
            }
        }
    }
}