package resources;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class ApiExcelDriven {

    public ArrayList<String> getData(String testCaseName) throws IOException {
        //rawvalues is the values that return for a specific testcaseName
        ArrayList<String> rawValues = new ArrayList<>();
        //FileInputStream fis = new FileInputStream("C:\\Users\\eguar\\Desktop\\QATRAINING\\testCases.xlsx");
        FileInputStream fis = new FileInputStream(System.getProperty("user.dir")+"\\src\\test\\java\\resources\\testCases.xlsx");
        XSSFWorkbook workbook= new XSSFWorkbook(fis);
        int sheetsSize= workbook.getNumberOfSheets();
        //first we will get the sheet called testData which resides our values
        for (int i = 0; i < sheetsSize; i++) {

            if (workbook.getSheetName(i).equalsIgnoreCase("testData")){
                //once we founded, we will mode into the rows inside
                XSSFSheet sheet= workbook.getSheetAt(i);
                Iterator<Row> rows = sheet.iterator();
                Row headerRow= rows.next();
                //also we will get the cells inside for each row to ask if we reach
                // testCases keyword
                Iterator<Cell> headerCells= headerRow.cellIterator();
                int k=0; int column=0;

                while (headerCells.hasNext()){
                    //System.out.println("count k: "+k);
                    Cell headerColumn = headerCells.next();

                    if (headerColumn.getStringCellValue()
                            .equalsIgnoreCase("testCases")){

                        column=k;

                    }
                    k++;
                }

                //once we have the column to start scraping, lets found the row of
                // our specific testcase purchase
                while (rows.hasNext()){
                    Row testRow = rows.next();
                    //to use column index first need to avoid to point null cells
                    // so we use getfirstCellnumm
                    int realColumn= testRow.getFirstCellNum();
                    //System.out.println("testcasename: "+testCaseName+ "  celstring: "+testRow.getCell(realColumn+column).getStringCellValue());
                    if(testRow.getCell(realColumn+column).getStringCellValue()
                                    .equalsIgnoreCase(testCaseName)){
                        //once we founded, lets move into the values next to purchase
                        Iterator<Cell> testValues = testRow.iterator();

                        while (testValues.hasNext()){
                            //to avoid error when adding number instead of strings,
                            // lets convert to text first
                            Cell wrappedValue=testValues.next();
                            if(wrappedValue.getCellType()== CellType.STRING)
                                rawValues.add(wrappedValue.getStringCellValue());
                            else
                                rawValues.add(NumberToTextConverter.toText(wrappedValue
                                        .getNumericCellValue()));
                        }
                    }
                }
            }
        }
        return rawValues;
    }

    public static void main(String[] args) throws IOException {

        ApiExcelDriven aed = new ApiExcelDriven();

        ArrayList<String> data= aed.getData("addPlace");
        System.out.println(data.get(0)+" a");
        System.out.println(data.get(1)+" b");
        System.out.println(data.get(2)+" c");
        System.out.println(data.get(3)+" d");
        System.out.println(data.get(4)+" e");
        System.out.println(data.get(5)+" f");
        System.out.println(data.get(6)+" g");
        System.out.println(data.get(7)+" h");
        System.out.println(data.get(8)+" i");
        System.out.println(data.get(9)+" j");
        System.out.println(data.get(10)+" k");
    }
}
