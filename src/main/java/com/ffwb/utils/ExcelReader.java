package com.ffwb.utils;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.IOException;

/**
 * Created by jinchuyang on 2017/6/22.
 */
public class ExcelReader {
    private XSSFWorkbook wb;

    public ExcelReader(File file){
        try {
            wb = new XSSFWorkbook(OPCPackage.openOrCreate(file));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        }
    }

    public XSSFWorkbook getWb() {
        return wb;
    }
}
