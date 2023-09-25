package com.company;

import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.io.IOException;

public class App {
	private Scanner scan = new Scanner(System.in);
	private	List<String> searchData = new ArrayList<>();
	private	List<ArrayList<String>> keyInstanceList = new ArrayList<ArrayList<String>>();
	private InputUtil inputUtil = new InputUtil();
	private CustomTableService tableService;

	public void start(String[] sourceFile) {
		tableService = new CustomTableServiceImpl();
		if(sourceFile.length == 0) {
			try {
				tableService.createTable();
			}
			catch(IOException e) {
				e.printStackTrace();
			}
		}
		else {
			String fileName = sourceFile[0];
			while(tableService.getRowCount() == 0) {
				try {
					tableService.createTable(fileName);
				}
				catch(IOException e) {
					System.out.println("Table Empty. Try Again.");
					System.out.print("Filename: ");
					fileName = scan.nextLine();
				}
			}
		}
		try {
			tableService.saveTable();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		tableService.printTable();
		
		Integer funcID;
		Boolean done = false;		
		while(!done) {
			System.out.println("\nMenu:");
			System.out.println("[1] Search");
			System.out.println("[2] Edit");
			System.out.println("[3] Print");
			System.out.println("[4] Sort");
			System.out.println("[5] Add Row");
			System.out.println("[6] Reset");
			System.out.println("[7] Exit");
			System.out.print("Input the NUMBER to execute the corresponding function: ");
			funcID = inputUtil.inputPosInt(0);
			
			System.out.println("");
			switch(funcID) {
				case 1:
					search();
					break;
				case 2:
					edit();
					break;
				case 3:
					tableService.printTable();
					break;
				case 4:
					sortRow();
					break;
				case 5:
					insert();
					break;
				case 6:
					createTable();
					break;
				case 7:
					done = true;
					break;
				default:
					System.out.println("Function Not Found. Try Again.");
			}
		}
	}
	
	private void createTable() {
		System.out.println("\nInput table dimensions (Positive Integer) ");
		System.out.print("Height/Number of Rows: ");
		Integer inputRows = inputUtil.inputPosInt(1);
		System.out.print("Width/Number ofColumns: ");
		Integer inputCols = inputUtil.inputPosInt(1);
		System.out.println("Table Dimensions: (R:" + inputRows + ", C:" + inputCols + ")");
		
		try {
			tableService.createTable(inputRows, inputCols);
			tableService.saveTable();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		catch(IllegalArgumentException e) {
			e.printStackTrace();
		}
	}
	
	private void search() {
		Integer choice;
		String inputStr;
		do {
			System.out.print("Search in Key(1) or Value(2): ");
			choice = inputUtil.inputPosInt(0);
			if(choice != 1 && choice != 2) System.out.println("Function Not Found. Try Again.\n");
		} 
		while(choice != 1 && choice != 2);
		
		if(choice == 1) {
			System.out.print("Search Key: ");
			inputStr = scan.nextLine();
			keyInstanceList = tableService.searchKeyInstances(inputStr); // output: [[key], [instances]]
			if(keyInstanceList.isEmpty()) {
				System.out.println("No Match Results.");
			} 
			else {
				System.out.println("Results...");
				for(ArrayList<String> keyInst: keyInstanceList) {
					searchData = tableService.getDataByKey(keyInst.get(0)); // output: [(row, col), key, value]
					System.out.println("\nCoordinates: ("+searchData.get(0)+")");
					System.out.println("Key: "+searchData.get(1));
					System.out.println("Value: "+searchData.get(2));
					System.out.println("Instance/s: "+keyInst.get(1));
				}
			}
		} else {
			System.out.print("Search Value: ");
			inputStr = scan.nextLine();
			keyInstanceList = tableService.searchValueInstances(inputStr);
			if(keyInstanceList.isEmpty()) {
				System.out.println("No Match Results.");
			} else {
				System.out.println("Results...");
				for(ArrayList<String> keyInst: keyInstanceList) {
					searchData = tableService.getDataByKey(keyInst.get(0));
					System.out.println("\nCoordinates: ("+searchData.get(0)+")");
					System.out.println("Key: "+searchData.get(1));
					System.out.println("Value: "+searchData.get(2));
					System.out.println("Instance/s: "+keyInst.get(1));
				}
			}
		}
	}
	
	private void edit() {
		Integer choice = 0,inputRow, inputCol;
		String inputStr, oldKey = "";
		do {
			System.out.print("Search by Key(1) or by Coordinates(2): ");
			choice = inputUtil.inputPosInt(0);
			if(choice != 1 && choice != 2) System.out.println("Function Not Found. Try Again.\n");
		}
		while(choice != 1 && choice != 2);
		
		if(choice == 1) {
			System.out.print("Search by Key: ");
			inputStr = scan.nextLine();
			searchData = tableService.getDataByKey(inputStr); // output: [(row, col), key, value]
			if(searchData == null) {
				System.out.println("No Match Results.");
			} else {
				System.out.println("\nCoordinates: ("+searchData.get(0)+")");
				System.out.println("Key: "+(oldKey = searchData.get(1)));
				System.out.println("Value: "+searchData.get(2));
			}
		} else {
			System.out.print("Search Coordindates Row: ");
			inputRow = inputUtil.inputPosInt(0);
			System.out.print("Search Coordindates Column: ");
			inputCol = inputUtil.inputPosInt(0);
			searchData = tableService.getDataByCoord(inputRow, inputCol);
			if(searchData == null) {
				System.out.println("Coordinates not Found.");
			} else {
				System.out.println("\nCoordinates: ("+searchData.get(0)+")");
				System.out.println("Key: "+(oldKey = searchData.get(1)));
				System.out.println("Value: "+searchData.get(2));
			}
		}
		
		if(searchData != null) {
			choice = 0;
			do {
				System.out.print("Edit Key(1) or Value(2): ");
				choice = inputUtil.inputPosInt(0);
				if(choice != 1 && choice != 2) System.out.println("Function Not Found. Try Again.\n");
			}
			while(choice != 1 && choice != 2);
			
			if(choice == 1) {
				System.out.print("New Key: ");
				inputStr = scan.nextLine();
				try {
					tableService.changeKey(oldKey, inputStr);
					System.out.println("Updated.");
				} 
				catch (KeyAlreadyExistException kaee){
					System.out.println("Key Already Exist.");
				}
			} else {
				System.out.print("New Value: ");
				inputStr = scan.nextLine();
				tableService.changeValueByKey(oldKey, inputStr);
				System.out.println("Updated.");
			}
		}
		
		try {
			tableService.saveTable();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	private void sortRow() {
		Integer rowCount = tableService.getRowCount(), inputRow = 0;
		Boolean valid = false;
		while(!valid) {
			System.out.print("Select Row: (0-" + (rowCount - 1) + "): ");
			inputRow = inputUtil.inputPosInt(0);
			if(inputRow >= rowCount || rowCount < 0) {
				System.out.println("Invalid Coordinate.");
			} else {
				valid = true;
			}
		}
		tableService.sortRow(inputRow);
		
		try {
			tableService.saveTable();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	private void insert() {
		Integer rowCount = tableService.getRowCount(), 
			inputRow = 0, 
			choice;
		Boolean valid = false;
		while (!valid){
			// fix logic on where to put new row
			System.out.print("Insert before Row (R: 0-"+(rowCount)+", choose "+(rowCount)+" for new row): ");
			inputRow = inputUtil.inputPosInt(0);
			if (inputRow > rowCount || rowCount < 0){System.out.println("Invalid Row.");}
			else {valid = true;}
		}
		System.out.print("Number of Columns: ");
		choice = inputUtil.inputPosInt(1);
		tableService.insertRow(inputRow, choice);
		
		try {
			tableService.saveTable();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}	
}