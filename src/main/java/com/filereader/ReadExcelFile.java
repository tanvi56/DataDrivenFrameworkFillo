package com.filereader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


import com.codoid.products.exception.FilloException;
import com.codoid.products.fillo.Connection;
import com.codoid.products.fillo.Fillo;
import com.codoid.products.fillo.Recordset;

public class ReadExcelFile {

	public static HashMap<String, String> readData(String SheetName, String TestcaseName) throws FilloException{
		Fillo fillo = new Fillo();
		Connection connection = fillo.getConnection(System.getProperty("user.dir") + "\\DataFile\\TestData.xlsx");
		String Query = "select * from " + SheetName +" where testcasename="+"'"+TestcaseName+"'";
		System.out.println(Query);
		Recordset rs = connection.executeQuery(Query);
		
		List<String> columns = new ArrayList<String>(rs.getCount());
		
		while (rs.next()) {
			rs.getFieldNames();
			columns = rs.getFieldNames();
		}
		
		HashMap<String, String> map = new HashMap<String, String>();
		
		for(int i =0;i<columns.size();i++) {
			String fieldName = columns.get(i);
			map.put(columns.get(i), rs.getField(fieldName));
		}
		
		
		rs.close();
		connection.close();
		
		return map;
		
	}
}
