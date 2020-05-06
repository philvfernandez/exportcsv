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

    }
}