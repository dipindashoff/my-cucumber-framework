package reusables;

import com.codoid.products.exception.FilloException;
import com.codoid.products.fillo.Connection;
import com.codoid.products.fillo.Fillo;
import com.codoid.products.fillo.Recordset;

public class ExcelUtilities {

	/**
	 * Read from excel workbook
	 */
	public static Recordset readExcel(String workbookPath, String query) {
		Connection connection = null;
		Recordset recordset = null;
		Fillo fillo = null;

		try {
			fillo = new Fillo();
			connection = fillo.getConnection(workbookPath);
			recordset = connection.executeQuery(query);
//            recordset.moveFirst();

			return recordset;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

//        recordset.close();
//        connection.close();
	}

	/**
	 * @throws FilloException
	 * 
	 */
	public static void navigateEveryRecord() throws FilloException {

		String workbookPath = ".\\src\\test\\resources\\Test Data\\InputTestData.xlsx";
		String query = "Select * from TagNames";

		Recordset rs = readExcel(workbookPath, query);

		try {

			rs.moveFirst();

			for (int i = 1; i <= rs.getCount(); i++) {
				System.out.println(rs.getField("TagNames"));

				rs.moveNext();
			}

		} catch (FilloException e) {
//			e.printStackTrace();
		}
		
		// OR by using a while loop -- make sure you remove rs.moveFirst() everywhere
		while (rs.next()) {
			System.out.println(rs.getField("TagNames"));
		}
	}

	public static void usingWhileLoop() throws FilloException {

		String workbookPath = ".\\src\\test\\resources\\Test Data\\InputTestData.xlsx";
		String query = "Select * from TagNames";

		Fillo fillo = new Fillo();
		Connection connection = fillo.getConnection(workbookPath);
		String strQuery = query;
		Recordset recordset = connection.executeQuery(strQuery);

		while (recordset.next()) {
			System.out.println(recordset.getField("TagNames"));
		}

		recordset.close();
		connection.close();
	}

	public static void main(String... args) throws FilloException {
//		navigateEveryRecord();
//    	usingWhileLoop();
//		insertInto();
		tudy();
	}
	
	/**
	 * Insert into sheet
	 * @throws FilloException 
	 */
	public static void insertInto() throws FilloException {
		String workbookPath = ".\\src\\test\\resources\\Test Data\\InputTestData.xlsx";
		
		Fillo fillo=new Fillo();
		Connection connection=fillo.getConnection(workbookPath);
		String strQuery="INSERT INTO TagNames(TEST) VALUES('Peter')";
		 
		connection.executeUpdate(strQuery);
		 
		connection.close();
	}
	
	/**
	 * Insert into sheet
	 * @throws FilloException 
	 */
	public static void tudy() throws FilloException {
		String workbookPath = ".\\src\\test\\resources\\Test Data\\OutputData.xlsx";
		
		Fillo fillo=new Fillo();
		Connection connection=fillo.getConnection(workbookPath);
//		String strQuery="INSERT INTO Sheet1(Output) VALUES('Output1')"; // Output // throws error
//		String strQuery="INSERT INTO Sheet1(OUTPUT) VALUES('OUTPUT1')"; // OUTPUT // throws error
//		String strQuery="INSERT INTO Sheet1(TEST) VALUES('TEST1')"; // TEST // 1 columns(s) affected
		String strQuery="INSERT INTO Sheet1(Name,Country) VALUES('Peter','UK')"; //2 columns(s) affected
		 
		connection.executeUpdate(strQuery);
		 
		connection.close();
	}

}
