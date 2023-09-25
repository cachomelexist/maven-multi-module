package com.company;

import java.util.List;
import java.util.ArrayList;
import java.io.IOException;

public interface CustomTableService {	
	public void createTable() throws IOException;
	public void createTable(Integer rows, Integer cols) throws IllegalArgumentException;
	public void createTable(String fileName) throws IOException;
	
	public void setDataTable(CustomTable dataTable);
	
	public void validateDimension(Integer dim) throws IllegalArgumentException;
	
	public void printTable();
	
	// searchKeyInstance(String key) - search for matching instances in each key
	// output: searchInstances()
	public List<ArrayList<String>> searchKeyInstances(String key);
	
	// searchKeyInstance(String key) - search for matching instances in each value
	// output: searchInstances()
	public List<ArrayList<String>> searchValueInstances(String value);
	
	// searchKeyInstance(String key) - search for matching instances in each key
	// output: [[key], [instances]]
	public List<ArrayList<String>> searchInstances(String searchData, Boolean keyMode);
	
	// getDataByKey(String key) - get data using the FULL SPECIFIC KEYs
	// output: [(row, col), key, value]
	public List<String> getDataByKey(String key);
	
	// getDataByKey(String key) - get data using the COORDINATE
	// output: [(row, col), key, value]
	public List<String> getDataByCoord(int row, int col);
	
	// getCoordByKey(String key) - get the (row, col) coordinates using FULL SPECIFIC KEYs
	// output: [row, col]
	public int[] getCoordByKey(String key);
	
	public void changeKey(String oldKey, String newKey) throws KeyAlreadyExistException;
	
	public void changeValueByKey(String key, String value);
	
	public void saveTable() throws IOException;
	
	public void sortRow(Integer row);
	
	public void insertRow(Integer row, Integer colCount);
	
	public Integer getRowCount();
}