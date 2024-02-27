package com.iche.sco.utils;

import com.iche.sco.model.Drugs;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

public class ExcelUtils {
    public static String HEADER[] = {"id", "drugName", "label", "price", "packs","createDate", "updatedDate"};
    public static String SHEET_NAME = "sheetForProductData";

    public static ByteArrayInputStream dataToExcel(List<Drugs> drugsList){

        try (Workbook workbook = new SXSSFWorkbook();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()){

        Sheet sheet = workbook.createSheet(SHEET_NAME);
        Row row = sheet.createRow(0);



        for(int i =0; i< HEADER.length; i++){
            Cell cell = row.createCell(i);
            cell.setCellValue(HEADER[i]);
        }
        int rowIndex = 1;
        for(Drugs drugs: drugsList){
            Row dataRow = sheet.createRow(rowIndex++);
            dataRow.createCell(0).setCellValue(drugs.getId());
            dataRow.createCell(1).setCellValue(drugs.getDrugName());
            dataRow.createCell(2).setCellValue(drugs.getLabel());
            dataRow.createCell(3).setCellValue(drugs.getPrice().toString());
            dataRow.createCell(4).setCellValue(drugs.getDrugCategory().toString());
            dataRow.createCell(5).setCellValue(drugs.getPacks());
            dataRow.createCell(6).setCellValue(drugs.getDrugStatus().toString());
            dataRow.createCell(7).setCellValue(drugs.getCreateDate().toString());
            dataRow.createCell(8).setCellValue(drugs.getUpdateAt().toString());
        }
            workbook.write(byteArrayOutputStream);
            return new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
        } catch (IOException e) {
           throw  new RuntimeException("Error creating Excel file: e");
        }
    }
}
