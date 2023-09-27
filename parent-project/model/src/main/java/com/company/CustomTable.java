package com.company;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

public class CustomTable {
	private Map<String, String> keyValue = new HashMap<String, String>();
	private List<ArrayList<String>> tableCoords = new ArrayList<ArrayList<String>>();
	private String fileName;
	
	public CustomTable() {}
	
	public CustomTable(Map<String, String> keyValue, List<ArrayList<String>> tableCoords) {
		createTable(keyValue, tableCoords);
	}
	
	public void createTable(Map<String, String> keyValue, List<ArrayList<String>> tableCoords) {
		this.keyValue = keyValue;
		this.tableCoords = tableCoords;
	}
	
	public List<ArrayList<String>> getCoords() {
		return tableCoords;
	}
	
	public Map<String, String> getMap() {
		return keyValue;
	}
	
	public void updateKey(String oldKey, String newKey) {
		keyValue.put(newKey, keyValue.remove(oldKey));
	}
	
	public void updateCoord(Integer row, Integer col, String key) {
		tableCoords.get(row).set(col, key);
	}
}