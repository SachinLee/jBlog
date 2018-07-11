package cn.sachin.jaBlog.poi;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PoiTest {

    @Test
    public void createExcel() {
        Workbook workbook = new XSSFWorkbook(); //xlxs格式
        try {
            FileOutputStream out = new FileOutputStream(new File("createworkbook.xlsx"));

            Sheet sheet = workbook.createSheet("测试页");
            Row rules = sheet.createRow(0);
            Row headers = sheet.createRow(1);
            Cell cell = rules.createCell(0);
            cell.setCellValue("1");

            workbook.write(out);
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        System.out.println("createworkbook.xlsx written successfully");

    }

    @Test
    public void readExcel() {

    }

}
