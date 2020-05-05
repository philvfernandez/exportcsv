package com.dev.kratos.exportcsv;

import com.monitorjbl.xlsx.StreamingReader;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.ss.usermodel.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.*;
import java.util.*;

@SpringBootApplication
@Slf4j
public class ExportcsvApplication {

    public static void main(String[] args) {

        SpringApplication.run(ExportcsvApplication.class, args);

        FilenameFilter map_export_filter = new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                if(name.contains("map_ALL_export_")) {
                    //filter all map_export files
                    return true;
                } else {
                    return false;
                }
            }
        };

        String dirPath = "C:/Edit/Dev";
        File dir = new File(dirPath);
        File[] files = dir.listFiles(map_export_filter);

        //Returns a list of file names in sorted order by last modified date
        if(files != null) {
            Arrays.sort(files, Comparator.comparing(File::lastModified));
        }

        if(files.length == 0) {
            log.info("There is no map export files");
        }

        //File file = new File("map_ALL_export_20200430110001_file.xlsx");
        File file = new File(files[files.length-1].toString());
        //Get only the base file name w/o extension
        String theFile = FilenameUtils.getBaseName(file.toString());

        //Output CSV file name
        File csvFile = new File("C:/Edit/Dev/"+theFile+".csv");
        BufferedWriter bw = null;

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
                int i = 1;

                //write out header

                if(rVal >= 3) {
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
                if(rVal % 1000 == 0) {
                    log.info("Writing Out Record: " + rVal);
                }

                bw.write(cellValue);
                if(rVal > 4) {
                    bw.newLine();
                }
                bw.flush();
                cellValue = "";
            }
            bw.close();
            System.exit(1);
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