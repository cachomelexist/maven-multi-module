package com.company;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.Iterator;

import org.apache.commons.io.FileUtils;

public class CustomTableServiceImpl implements CustomTableService {
	// default table -> defaultTable.txt
	private	CustomTable dataTable;
	private String fileName = "defaultTable.txt";
	private Random rand = new Random();
	
	public CustomTableServiceImpl() throws IOException {
		createTable();
		saveTable();
	}
	
	public CustomTableServiceImpl(Integer rowCount, Integer colCount) throws IOException {
		validateDimension(rowCount);
		validateDimension(colCount);
		createTable(rowCount, colCount);
		saveTable();
	}
	
	public CustomTableServiceImpl(String fileName) throws IOException {
		createTable(fileName);
		saveTable();
	}
	
	public void validateDimension(Integer dim) throws IllegalArgumentException {
		if(dim <= 0) throw new IllegalArgumentException("Must not be Less than one (1)");
	}
	
	// set defaultTable.txt as a READ ONLY default table inside the jar file
	public void createTable() throws IOException {
		InputStream in = getClass().getResourceAsStream("/"+fileName); 
		Map<String, String> keyValueMap = new HashMap<String, String>();
		List<ArrayList<String>> coordList = new ArrayList<ArrayList<String>>();
		
		BufferedReader fileReader = new BufferedReader(new InputStreamReader(in));
		this.fileName = fileName;
		String row;
		int rowCount = 0;
		while((row = fileReader.readLine()) != null) { // row
			//System.out.println(row); 
			coordList.add(new ArrayList<String>());
			String cells[] = row.split(" "); // cell
			for(String pairs: cells) {
				String pair[] = pairs.split("!"); // key-value pair
				keyValueMap.put(pair[0], pair[1]);
				coordList.get(rowCount).add(pair[0]);
			}
			rowCount++;
		}
		dataTable = new CustomTable(keyValueMap, coordList);
		fileReader.close();
		//System.out.println(coordList);
	}
	
	public void createTable(Integer rows, Integer cols) throws IOException {
		Map<String, String> keyValueMap = new HashMap<String, String>();
		List<ArrayList<String>> coordList = new ArrayList<ArrayList<String>>();
		
		for (int i = 0; i < rows; i++){
			coordList.add(new ArrayList<String>());
			for (int j = 0; j < cols; j++){
				String key = "", value = "";
				int strLen = rand.nextInt(3,7);
				// generate randomized Key
				do{
					for (int k = 0; k < strLen; k++){
						char ch = (char)(rand.nextInt(92) + 34); // ASCII except [SPACE] and !
						key += ch;
					}
				}
				while (keyValueMap.containsKey(key));
				// generate randomized Value
				for (int k = 0; k < strLen; k++){
					char ch = (char)(rand.nextInt(92) + 34); // ASCII except [SPACE] and !
					value += ch;
				}
				
				keyValueMap.put(key, value);
				coordList.get(i).add(key);
			}
		}
		dataTable = new CustomTable(keyValueMap, coordList);
	}

	public void createTable(String fileName) throws IOException {
		Map<String, String> keyValueMap = new HashMap<String, String>();
		List<ArrayList<String>> coordList = new ArrayList<ArrayList<String>>();
		
		BufferedReader fileReader = new BufferedReader(new FileReader(fileName));
		this.fileName = fileName;
		String row;
		int rowCount = 0;
		while((row = fileReader.readLine()) != null) { // row
			//System.out.println(row); 
			coordList.add(new ArrayList<String>());
			String cells[] = row.split(" "); // cell
			for(String pairs: cells) {
				String pair[] = pairs.split("!"); // key-value pair
				keyValueMap.put(pair[0], pair[1]);
				coordList.get(rowCount).add(pair[0]);
			}
			rowCount++;
		}
		dataTable = new CustomTable(keyValueMap, coordList);
		fileReader.close();
		//System.out.println(coordList);
	}
	
	public void printTable() {
		int row = 0;
		for(ArrayList<String> rowArr: dataTable.getCoords()) {
			System.out.print(row+" ");
			for(String key: rowArr) {
				System.out.print("[ "+key+" , "+dataTable.getMap().get(key)+" ] ");
			}
			row++;
			System.out.println("");
		}
	}
	
	// searchKeyInstance(String key) - search for matching instances in each key
	// output: [[key], [instances]]
	public List<ArrayList<String>> searchKeyInstances(String key) {
		return searchInstances(key, true);
	}
	
	public List<ArrayList<String>> searchValueInstances(String value) {
		return searchInstances(value, false);
	}
	
	// searchKeyInstance(String key) - search for matching instances in each key
	// output: [[key], [instances]]
	//keymode - true:search in keys, false:search in values
	public List<ArrayList<String>> searchInstances(String searchData, Boolean keyMode) {
		List<ArrayList<String>> matchingContents = new ArrayList<ArrayList<String>>();
		
		int instCount;
		for(ArrayList<String> keyList: dataTable.getCoords()){
			for(String key: keyList) {
				String data = (keyMode) ? key : dataTable.getMap().get(key);
				int dataLen = data.length();
				int searchLen = searchData.length();
				
				instCount = 0;
				if(searchLen < dataLen && searchLen > 0) {
					for (int l = 0; l < (dataLen-(searchLen-1)); l++) {
						if(data.substring(l, l+searchLen).equals(searchData)) {
							instCount++;
						}
					}
				} else if(data.equals(searchData)) {
					instCount++;
				}
				
				if(instCount > 0) {
					matchingContents.add(new ArrayList<String>());
					int index = matchingContents.size()-1;
					matchingContents.get(index).add(key);
					matchingContents.get(index).add(Integer.toString(instCount));
				}
			}
		}
		
		//System.out.println(keyList);
		return matchingContents;
	}
	
	// getDataByKey(String key) - get data using FULL SPECIFIC KEYs
	// output: [(row, col), key, value]
	public List<String> getDataByKey(String key) {
		List<String> data = new ArrayList<>();
		
		int[] coord = getCoordByKey(key);
		int row = coord[0];
		int col = coord[1];
		if(col == -1) return null;
		data.add(row+","+col);
		data.add(key);
		data.add(dataTable.getMap().get(key));
		
		return data;
	}
	
	public List<String> getDataByCoord(int row, int col) {
		List<String> data = new ArrayList<>();
		try {
			String key = dataTable.getCoords().get(row).get(col);
			data.add(row+","+col);
			data.add(key);
			data.add(dataTable.getMap().get(key));
		
			return data;
		} catch (Exception e) {return null;}
	}
	
	// getCoordByKey(String key) - get the (row, col) coordinates using the FULL SPECIFIC KEY
	// output: [row, col]
	public int[] getCoordByKey(String key) {
		int length = dataTable.getCoords().size();
		int col = 0, row;
		for(row = 0; row < length; row++) {
			if((col = dataTable.getCoords().get(row).indexOf(key)) != -1) break;
		}
		
		return new int[]{row, col};
	}
	
	public void changeKey(String oldKey, String newKey) throws KeyAlreadyExistException {
		// check no duplicate key
		if(dataTable.getMap().containsKey(newKey)) {
			throw new KeyAlreadyExistException("Key already exists.");
		}
		int[] coord = getCoordByKey(oldKey);
		dataTable.updateKey(oldKey, newKey);
		dataTable.updateCoord(coord[0], coord[1], newKey);
	}
	
	public void changeValueByKey(String key, String value){
		dataTable.getMap().replace(key, value);
	}
	
	public void saveTable() throws IOException {
		List<String> data = new ArrayList<>();
		String line;
		for(ArrayList<String> rowArr: dataTable.getCoords()) {
			line = "";
			for(String key: rowArr) {
				line += key+"!"+dataTable.getMap().get(key)+" ";
			}
			data.add(line);
		}
		FileUtils.writeLines(new File(fileName), data);
	}
	
	public Integer getRowCount() {
		return dataTable.getRowCount();
	}
	
	public void sortRow(Integer row) {
		List<Integer> keyLen = new ArrayList<>();
		List<String> unsorted = new ArrayList<>();
		SortedSet<String> sorted = new TreeSet<String>();
		
		System.out.println("\nKeys: "+dataTable.getCoords().get(row));
        for(String key: dataTable.getCoords().get(row)) {
			keyLen.add(key.length());
			unsorted.add(key + dataTable.getMap().get(key));
			sorted.add(key + dataTable.getMap().get(key));
		}
		
		System.out.println("Unsorted Key+Value: "+unsorted);
		System.out.println("Sorted Key+Value: "+sorted);
		
		Integer col = 0;
		Iterator<String> i = sorted.iterator();
        while (i.hasNext()) {
			String merged = i.next();
			dataTable.getCoords().get(row).set(col++, merged.substring(0,keyLen.get(unsorted.indexOf(merged))));
		}
		
		System.out.println("Sorted Keys: "+dataTable.getCoords().get(row));
	}
	
	public void insertRow(Integer row, Integer colCount) {
		Random rand = new Random();
		dataTable.getCoords().add(row, new ArrayList<String>());
		for(int i = 0; i < colCount; i++){
			String key = "", value = "";
			int strLen = rand.nextInt(3,7);
			// generate randomized Key
			do{
				for (int k = 0; k < strLen; k++) {
					char ch = (char)(rand.nextInt(92) + 34); // ASCII except [SPACE] and !
					key += ch;
				}
			}
			while(dataTable.getMap().containsKey(key));
			// generate randomized Value
			for (int k = 0; k < strLen; k++) {
				char ch = (char)(rand.nextInt(92) + 34); // ASCII except [SPACE] and !
				value += ch;
			}
			dataTable.getMap().put(key, value);
			dataTable.getCoords().get(row).add(key);
		}
	}
}