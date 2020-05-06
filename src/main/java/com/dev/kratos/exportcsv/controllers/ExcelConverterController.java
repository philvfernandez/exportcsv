package com.dev.kratos.exportcsv.controllers;

import com.dev.kratos.exportcsv.services.ConvertExcelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class ExcelConverterController {

    private final ConvertExcelService convertExcelService;


    public ExcelConverterController(ConvertExcelService convertExcelService) {
        this.convertExcelService = convertExcelService;
    }

    @GetMapping("excel/csv/convert")
    public void convertExcel() {
        convertExcelService.convertExcelToCsv();
    }
}
