package TestCases;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

 public class ExcelUtils {

			private static XSSFSheet Sheet;

			private static XSSFWorkbook wb;

			private static XSSFCell Cell;
			private static DataFormatter df = new DataFormatter();

			public static Object[][] getTableArray(String FilePath, String SheetName) throws Exception {   

		   String[][] tabArray = null;

		   try {

			   FileInputStream fis = new FileInputStream(FilePath);

			   // Access the required test data sheet

			   wb = new XSSFWorkbook(fis);

			   Sheet = wb.getSheet(SheetName);

			   int startRow = 1;

			   int startCol = 0;

			   int ci,cj;

			   int totalRows = Sheet.getLastRowNum();
			   //System.out.println(totalRows);

			   // you can write a function as well to get Column count

			   int totalCols = 5;

			   tabArray=new String[totalRows][totalCols];

			   ci=0;

			   for (int i=startRow;i<=totalRows;i++, ci++) {           	   

				  cj=0;

				   for (int j=startCol;j<totalCols;j++, cj++){

					   System.out.print(ci);
					   System.out.print(cj);
					   tabArray[ci][cj]=getCellData(i,j);
					   
					   

					   System.out.println(tabArray[ci][cj]);  

						}

					}

				}

			catch (FileNotFoundException e){

				System.out.println("Could not read the Excel sheet");

				e.printStackTrace();

				}

			catch (IOException e){

				System.out.println("Could not read the Excel sheet");

				e.printStackTrace();

				}

			return(tabArray);

			}

		public static String getCellData(int RowNum, int ColNum) throws Exception {

			try{

				Cell = Sheet.getRow(RowNum).getCell(ColNum);

				int dataType = Cell.getCellType();
				if  (dataType == 3) {

					return "";

				}else{

					String CellData = df.formatCellValue(Cell);

					return CellData;

				}
			}
			catch (Exception e){

				System.out.println(e.getMessage());

				throw (e);

				}

			}

	}