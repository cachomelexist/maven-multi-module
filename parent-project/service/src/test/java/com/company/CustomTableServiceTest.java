package com.company;


import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.io.IOException;
import java.lang.IndexOutOfBoundsException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Assertions;

import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

public class CustomTableServiceTest {
	private String search, result;
	private CustomTableService tableService;
	private Map<String, String> keyValue = new HashMap<String, String>() {{
				put("asd123", "321dsa");
				put("d3?", "X&)");
				put("lq2=", ":{R\"");
				put("UrlF,D", "+TWCRe");
				put("g\"3I@", "h>Vxv");
				put("qwe456", "654ewq");
			}};
	private List<ArrayList<String>> tableCoords;
	
	@BeforeEach
	public void customTableMocking() {
		/*CustomTable sampleTable = mock(CustomTable.class);
		when(sampleTable.getMap()).thenReturn(keyValue);
		when(sampleTable.getCoords()).thenReturn(tableCoords);*/
		
		tableCoords = new ArrayList<ArrayList<String>>();
		tableService = spy(new CustomTableServiceImpl());
	}
	
	/*******************************************************************************
	
			searchKeyInstances()
		
	********************************************************************************/	
	
    @Test
	@DisplayName("Should return 0 since search have no match")
    public void searchKeyInstancesShouldReturnZero() {
		tableCoords.add(new ArrayList<String>(Arrays.asList("UrlF,D","g\"3I@","qwe456")));
		CustomTable sampleTable = new CustomTable(keyValue, tableCoords);
		tableService.setDataTable(sampleTable);
		
		List<ArrayList<String>> keyInstanceList = tableService.searchKeyInstances("lq2=");
		Assertions.assertEquals(0, keyInstanceList.size());
    }
	
    @Test
    public void searchKeyInstancesSingleCharStart() {
		tableCoords.add(new ArrayList<String>(Arrays.asList("UrlF,D","g\"3I@","qwe456")));
		CustomTable sampleTable = new CustomTable(keyValue, tableCoords);
		tableService.setDataTable(sampleTable);
		
		search = "U";
		result = "UrlF,D";
		
		List<ArrayList<String>> keyInstanceList = tableService.searchKeyInstances(search);
		Assertions.assertEquals(result, keyInstanceList.get(0).get(0));
	}
	
    @Test
    public void searchKeyInstancesSingleCharMiddle() {
		tableCoords.add(new ArrayList<String>(Arrays.asList("asd123","d3?","lq2=")));
		CustomTable sampleTable = new CustomTable(keyValue, tableCoords);
		tableService.setDataTable(sampleTable);
		
		search = "1";
		result = "asd123";
		
		List<ArrayList<String>> keyInstanceList = tableService.searchKeyInstances(search);
		Assertions.assertEquals(result, keyInstanceList.get(0).get(0));
	}
	
    @Test
    public void searchKeyInstancesSingleCharEnd() {
		tableCoords.add(new ArrayList<String>(Arrays.asList("UrlF,D","g\"3I@","qwe456")));
		CustomTable sampleTable = new CustomTable(keyValue, tableCoords);
		tableService.setDataTable(sampleTable);
		
		search = "D";
		result = "UrlF,D";
		
		List<ArrayList<String>> keyInstanceList = tableService.searchKeyInstances(search);
		Assertions.assertEquals(result, keyInstanceList.get(0).get(0));
	}
	
    @Test
    public void searchKeyInstancesSingleCharMultiKeys() {
		tableCoords = new ArrayList<ArrayList<String>>();
		tableCoords.add(new ArrayList<String>(Arrays.asList("asd123","d3?","lq2=")));
		CustomTable sampleTable = new CustomTable(keyValue, tableCoords);
		tableService.setDataTable(sampleTable);
		
		List<ArrayList<String>> keyInstanceList = tableService.searchKeyInstances("3");
		Assertions.assertEquals(2, keyInstanceList.size());
	}
	
    @Test
    public void searchKeyInstancesTwoCharStart() {
		tableCoords.add(new ArrayList<String>(Arrays.asList("UrlF,D","g\"3I@","qwe456")));
		CustomTable sampleTable = new CustomTable(keyValue, tableCoords);
		tableService.setDataTable(sampleTable);
		
		search = "Ur";
		result = "UrlF,D";
		
		List<ArrayList<String>> keyInstanceList = tableService.searchKeyInstances(search);
		Assertions.assertEquals(result, keyInstanceList.get(0).get(0));
    }
	
    @Test
    public void searchKeyInstancesTwoCharMiddle() {
		tableCoords.add(new ArrayList<String>(Arrays.asList("asd123","d3?","lq2=")));
		CustomTable sampleTable = new CustomTable(keyValue, tableCoords);
		tableService.setDataTable(sampleTable);
		
		search = "q2";
		result = "lq2=";
		
		List<ArrayList<String>> keyInstanceList = tableService.searchKeyInstances(search);
		Assertions.assertEquals(result, keyInstanceList.get(0).get(0));
    }
	
    @Test
    public void searchKeyInstancesTwoCharEnd() {
		tableCoords.add(new ArrayList<String>(Arrays.asList("UrlF,D","g\"3I@","qwe456")));
		CustomTable sampleTable = new CustomTable(keyValue, tableCoords);
		tableService.setDataTable(sampleTable);
		
		search = "I@";
		result = "g\"3I@";
		
		List<ArrayList<String>> keyInstanceList = tableService.searchKeyInstances(search);
		Assertions.assertEquals(result, keyInstanceList.get(0).get(0));
    }
	
    @Test
    public void searchKeyInstancesMultiCharStart() {
		tableCoords.add(new ArrayList<String>(Arrays.asList("asd123","d3?","lq2=")));
		CustomTable sampleTable = new CustomTable(keyValue, tableCoords);
		tableService.setDataTable(sampleTable);
		
		search = "asd12";
		result = "asd123";
		
		List<ArrayList<String>> keyInstanceList = tableService.searchKeyInstances(search);
		Assertions.assertEquals(result, keyInstanceList.get(0).get(0));
    }
	
    @Test
    public void searchKeyInstancesMultiCharMiddle() {
		tableCoords.add(new ArrayList<String>(Arrays.asList("UrlF,D","g\"3I@","qwe456")));
		CustomTable sampleTable = new CustomTable(keyValue, tableCoords);
		tableService.setDataTable(sampleTable);
		
		search = "we45";
		result = "qwe456";
		
		List<ArrayList<String>> keyInstanceList = tableService.searchKeyInstances(search);
		Assertions.assertEquals(result, keyInstanceList.get(0).get(0));
    }
	
    @Test
    public void searchKeyInstancesMultiCharEnd() {
		tableCoords.add(new ArrayList<String>(Arrays.asList("asd123","d3?","lq2=")));
		CustomTable sampleTable = new CustomTable(keyValue, tableCoords);
		tableService.setDataTable(sampleTable);
		
		search = "q2=";
		result = "lq2=";
		
		List<ArrayList<String>> keyInstanceList = tableService.searchKeyInstances(search);
		Assertions.assertEquals(result, keyInstanceList.get(0).get(0));
    }
	
	/*******************************************************************************
	
			searchValueInstances()
		
	********************************************************************************/
	
    @Test
    public void searchValueInstancesNoMatch() {
		tableCoords.add(new ArrayList<String>(Arrays.asList("UrlF,D","g\"3I@","qwe456")));
		CustomTable sampleTable = new CustomTable(keyValue, tableCoords);
		tableService.setDataTable(sampleTable);
		
		List<ArrayList<String>> keyInstanceList = tableService.searchValueInstances("qwe456");
		Assertions.assertEquals(0, keyInstanceList.size());
    }
	
    @Test
    public void searchValueInstancesSingleCharStart() {
		tableCoords.add(new ArrayList<String>(Arrays.asList("asd123","d3?","lq2=")));
		CustomTable sampleTable = new CustomTable(keyValue, tableCoords);
		tableService.setDataTable(sampleTable);
		
		search = ":";
		result = "lq2=";
		
		List<ArrayList<String>> keyInstanceList = tableService.searchValueInstances(search);		
		Assertions.assertEquals(result, keyInstanceList.get(0).get(0));
	}
	
    @Test
    public void searchValueInstancesSingleCharMiddle() {
		tableCoords.add(new ArrayList<String>(Arrays.asList("UrlF,D","g\"3I@","qwe456")));
		CustomTable sampleTable = new CustomTable(keyValue, tableCoords);
		tableService.setDataTable(sampleTable);
		
		search = "V";
		result = "g\"3I@";
		
		List<ArrayList<String>> keyInstanceList = tableService.searchValueInstances(search);
		Assertions.assertEquals(result, keyInstanceList.get(0).get(0));
	}
	
    @Test
    public void searchValueInstancesSingleCharEnd() {
		tableCoords.add(new ArrayList<String>(Arrays.asList("asd123","d3?","lq2=")));
		CustomTable sampleTable = new CustomTable(keyValue, tableCoords);
		tableService.setDataTable(sampleTable);
		
		search = "a";
		result = "asd123";
		
		List<ArrayList<String>> keyInstanceList = tableService.searchValueInstances(search);
		Assertions.assertEquals(result, keyInstanceList.get(0).get(0));
	}
	
    @Test
    public void searchValueInstancesSingleCharMultiKeys() {
		tableCoords.add(new ArrayList<String>(Arrays.asList("UrlF,D","g\"3I@","qwe456")));
		CustomTable sampleTable = new CustomTable(keyValue, tableCoords);
		tableService.setDataTable(sampleTable);
		
		search = "e";
		
		List<ArrayList<String>> keyInstanceList = tableService.searchValueInstances(search);		
		Assertions.assertEquals(2, keyInstanceList.size());
	}
	
    @Test
    public void searchValueInstancesTwoCharStart() {
		tableCoords.add(new ArrayList<String>(Arrays.asList("asd123","d3?","lq2=")));
		CustomTable sampleTable = new CustomTable(keyValue, tableCoords);
		tableService.setDataTable(sampleTable);
		
		search = "X&";
		result = "d3?";
		
		List<ArrayList<String>> keyInstanceList = tableService.searchValueInstances(search);		
		Assertions.assertEquals(result, keyInstanceList.get(0).get(0));
    }
	
    @Test
    public void searchValueInstancesTwoCharMiddle() {
		tableCoords.add(new ArrayList<String>(Arrays.asList("UrlF,D","g\"3I@","qwe456")));
		CustomTable sampleTable = new CustomTable(keyValue, tableCoords);
		tableService.setDataTable(sampleTable);
		
		search = "WC";
		result = "UrlF,D";
		
		List<ArrayList<String>> keyInstanceList = tableService.searchValueInstances(search);		
		Assertions.assertEquals(result, keyInstanceList.get(0).get(0));
    }
	
    @Test
    public void searchValueInstancesTwoCharEnd() {
		tableCoords.add(new ArrayList<String>(Arrays.asList("asd123","d3?","lq2=")));
		CustomTable sampleTable = new CustomTable(keyValue, tableCoords);
		tableService.setDataTable(sampleTable);
		
		search = "R\"";
		result = "lq2=";
		
		List<ArrayList<String>> keyInstanceList = tableService.searchValueInstances(search);		
		Assertions.assertEquals(result, keyInstanceList.get(0).get(0));
    }
	
    @Test
    public void searchValueInstancesMultiCharStart() {
		tableCoords.add(new ArrayList<String>(Arrays.asList("UrlF,D","g\"3I@","qwe456")));
		CustomTable sampleTable = new CustomTable(keyValue, tableCoords);
		tableService.setDataTable(sampleTable);
		
		search = "+TWCR";
		result = "UrlF,D";
		
		List<ArrayList<String>> keyInstanceList = tableService.searchValueInstances(search);		
		Assertions.assertEquals(result, keyInstanceList.get(0).get(0));
    }
	
    @Test
    public void searchValueInstancesMultiCharMiddle() {
		tableCoords.add(new ArrayList<String>(Arrays.asList("asd123","d3?","lq2=")));
		CustomTable sampleTable = new CustomTable(keyValue, tableCoords);
		tableService.setDataTable(sampleTable);
		
		search = "21ds";
		result = "asd123";
		
		List<ArrayList<String>> keyInstanceList = tableService.searchValueInstances(search);		
		Assertions.assertEquals(result, keyInstanceList.get(0).get(0));
    }
	
    @Test
    public void searchValueInstancesMultiCharEnd() {
		tableCoords.add(new ArrayList<String>(Arrays.asList("UrlF,D","g\"3I@","qwe456")));
		CustomTable sampleTable = new CustomTable(keyValue, tableCoords);
		tableService.setDataTable(sampleTable);
		
		search = ">Vxv";
		result = "g\"3I@";
		
		List<ArrayList<String>> keyInstanceList = tableService.searchValueInstances(search);		
		Assertions.assertEquals(result, keyInstanceList.get(0).get(0));
    }
	
	/*******************************************************************************
	
			getDataByKey()
		
	********************************************************************************/
	
    @Test
    public void getDataByKeyFirst() {
		tableCoords.add(new ArrayList<String>(Arrays.asList("asd123","d3?","lq2=")));
		CustomTable sampleTable = new CustomTable(keyValue, tableCoords);
		tableService.setDataTable(sampleTable);
		
		List<String> searchData = tableService.getDataByKey("asd123");
		Assertions.assertEquals("0,0", searchData.get(0));
		Assertions.assertEquals("asd123", searchData.get(1));
		Assertions.assertEquals("321dsa", searchData.get(2));
    }
	
    @Test
    public void getDataByKeyMiddle() {
		tableCoords.add(new ArrayList<String>(Arrays.asList("UrlF,D","g\"3I@","qwe456")));
		CustomTable sampleTable = new CustomTable(keyValue, tableCoords);
		tableService.setDataTable(sampleTable);
		
		List<String> searchData = tableService.getDataByKey("g\"3I@");		
		Assertions.assertEquals("0,1", searchData.get(0));
		Assertions.assertEquals("g\"3I@", searchData.get(1));
		Assertions.assertEquals("h>Vxv", searchData.get(2));
    }
	
    @Test
    public void getDataByKeyLast() {
		tableCoords.add(new ArrayList<String>(Arrays.asList("asd123","d3?","lq2=")));
		CustomTable sampleTable = new CustomTable(keyValue, tableCoords);
		tableService.setDataTable(sampleTable);
		
		List<String> searchData = tableService.getDataByKey("lq2=");		
		Assertions.assertEquals("0,2", searchData.get(0));
		Assertions.assertEquals("lq2=", searchData.get(1));
		Assertions.assertEquals(":{R\"", searchData.get(2));
    }
	
	/*******************************************************************************
	
			getDataByCoord()
		
	********************************************************************************/
	
    @Test
    public void getDataByCoordFirst() {
		tableCoords.add(new ArrayList<String>(Arrays.asList("UrlF,D","g\"3I@","qwe456")));
		CustomTable sampleTable = new CustomTable(keyValue, tableCoords);
		tableService.setDataTable(sampleTable);
		
		List<String> searchData = tableService.getDataByCoord(0,0);		
		Assertions.assertEquals("0,0", searchData.get(0));
		Assertions.assertEquals("UrlF,D", searchData.get(1));
		Assertions.assertEquals("+TWCRe", searchData.get(2));
    }
	
    @Test
    public void getDataByCoordMiddle() {
		tableCoords.add(new ArrayList<String>(Arrays.asList("asd123","d3?","lq2=")));
		CustomTable sampleTable = new CustomTable(keyValue, tableCoords);
		tableService.setDataTable(sampleTable);
		
		List<String> searchData = tableService.getDataByCoord(0,1);		
		Assertions.assertEquals("0,1", searchData.get(0));
		Assertions.assertEquals("d3?", searchData.get(1));
		Assertions.assertEquals("X&)", searchData.get(2));
    }
	
    @Test
    public void getDataByCoordLast() {
		tableCoords.add(new ArrayList<String>(Arrays.asList("UrlF,D","g\"3I@","qwe456")));
		CustomTable sampleTable = new CustomTable(keyValue, tableCoords);
		tableService.setDataTable(sampleTable);
		
		List<String> searchData = tableService.getDataByCoord(0,2);		
		Assertions.assertEquals("0,2", searchData.get(0));
		Assertions.assertEquals("qwe456", searchData.get(1));
		Assertions.assertEquals("654ewq", searchData.get(2));
    }
	
	/*******************************************************************************
	
			getCoordByKey()
		
	********************************************************************************/
	
    @Test
    public void getCoordByKeyFirst() {
		tableCoords.add(new ArrayList<String>(Arrays.asList("asd123","d3?","lq2=")));
		CustomTable sampleTable = new CustomTable(keyValue, tableCoords);
		tableService.setDataTable(sampleTable);
		
		int[] searchData = tableService.getCoordByKey("asd123");		
		Assertions.assertEquals(0, searchData[0]);
		Assertions.assertEquals(0, searchData[1]);
    }
	
    @Test
    public void getCoordByKeyMiddle() {
		tableCoords.add(new ArrayList<String>(Arrays.asList("UrlF,D","g\"3I@","qwe456")));
		CustomTable sampleTable = new CustomTable(keyValue, tableCoords);
		tableService.setDataTable(sampleTable);
		
		int[] searchData = tableService.getCoordByKey("g\"3I@");		
		Assertions.assertEquals(0, searchData[0]);
		Assertions.assertEquals(1, searchData[1]);
    }
	
    @Test
    public void getCoordByKeyLast() {
		tableCoords.add(new ArrayList<String>(Arrays.asList("asd123","d3?","lq2=")));
		CustomTable sampleTable = new CustomTable(keyValue, tableCoords);
		tableService.setDataTable(sampleTable);
		
		int[] searchData = tableService.getCoordByKey("lq2=");		
		Assertions.assertEquals(0, searchData[0]);
		Assertions.assertEquals(2, searchData[1]);
    }
	
	
	/*******************************************************************************
	
			saveTable()
		
	********************************************************************************/
	
    @Test
    public void saveTableTest() {
		tableCoords.add(new ArrayList<String>(Arrays.asList("UrlF,D","g\"3I@","qwe456")));
		CustomTable sampleTable = new CustomTable(keyValue, tableCoords);
		tableService.setDataTable(sampleTable);
		
		Assertions.assertDoesNotThrow(() -> {			
			tableService.saveTable();
			verify(tableService).saveTable();
		});
    }
	
	
	/*******************************************************************************
	
			changeKey()
		
	********************************************************************************/
	
    @Test
    public void changeKeyShouldReturnKeyAlreadyExistException() {
		tableCoords.add(new ArrayList<String>(Arrays.asList("asd123","d3?","lq2=")));
		CustomTable sampleTable = new CustomTable(keyValue, tableCoords);
		tableService.setDataTable(sampleTable);
		
		Assertions.assertThrows(KeyAlreadyExistException.class, () -> {
			tableService.changeKey("asd123", "d3?");
		});
    }
	
    @Test
    public void changeKeyShouldReturnSameData() {
		tableCoords.add(new ArrayList<String>(Arrays.asList("UrlF,D","g\"3I@","qwe456")));
		CustomTable sampleTable = new CustomTable(keyValue, tableCoords);
		tableService.setDataTable(sampleTable);
		
		String oldKey = "UrlF,D";
		String newKey = "ABC123";
		String coords, value;
		List<String> searchData = tableService.getDataByKey(oldKey);
		
		coords = searchData.get(0);
		value = searchData.get(2);
		
		Assertions.assertDoesNotThrow(() -> {			
			tableService.changeKey(oldKey, newKey);
		});
		searchData = tableService.getDataByKey(newKey);
		
		Assertions.assertEquals(coords, searchData.get(0));
		Assertions.assertEquals(value, searchData.get(2));
    }
	
	
	/*******************************************************************************
	
			getRowCount()
		
	********************************************************************************/
	
	@Test
	public void getRowCountTest() {
		tableCoords.add(new ArrayList<String>(Arrays.asList("asd123","d3?","lq2=")));
		tableCoords.add(new ArrayList<String>(Arrays.asList("UrlF,D","g\"3I@","qwe456")));
		CustomTable sampleTable = new CustomTable(keyValue, tableCoords);
		tableService.setDataTable(sampleTable);
		
		Assertions.assertEquals(2, tableService.getRowCount());
	}
	
	
	/*******************************************************************************
	
			sortRow()
		
	********************************************************************************/
	
	@Test
	public void sortRowTest() {
		tableCoords.add(new ArrayList<String>(Arrays.asList("asd123","d3?","lq2=")));
		tableCoords.add(new ArrayList<String>(Arrays.asList("UrlF,D","g\"3I@","qwe456")));
		CustomTable sampleTable = new CustomTable(keyValue, tableCoords);
		tableService.setDataTable(sampleTable);
		
		tableService.sortRow(1);
		Assertions.assertArrayEquals(new int[] {1,0}, tableService.getCoordByKey("UrlF,D"));
		Assertions.assertArrayEquals(new int[] {1,1}, tableService.getCoordByKey("g\"3I@"));
		Assertions.assertArrayEquals(new int[] {1,2}, tableService.getCoordByKey("qwe456"));
		
	}
	
	@Test
	public void sortRowTestIndexOutOfBounds() {
		tableCoords.add(new ArrayList<String>(Arrays.asList("asd123","d3?","lq2=")));
		tableCoords.add(new ArrayList<String>(Arrays.asList("UrlF,D","g\"3I@","qwe456")));
		CustomTable sampleTable = new CustomTable(keyValue, tableCoords);
		tableService.setDataTable(sampleTable);
		
		Assertions.assertThrows(IndexOutOfBoundsException.class, () -> {
			tableService.sortRow(3);
		});
	}
	
	
	/*******************************************************************************
	
			insertRow()
		
	********************************************************************************/
	
	@Test
	public void insertRowTest() {
		tableCoords.add(new ArrayList<String>(Arrays.asList("asd123","d3?","lq2=")));
		tableCoords.add(new ArrayList<String>(Arrays.asList("UrlF,D","g\"3I@","qwe456")));
		CustomTable sampleTable = new CustomTable(keyValue, tableCoords);
		tableService.setDataTable(sampleTable);
		
		tableService.insertRow(0,3);
		Assertions.assertEquals(3, tableService.getRowCount());
		Assertions.assertNotEquals(null, tableService.getDataByCoord(0,2));
		Assertions.assertEquals(null, tableService.getDataByCoord(0,4));
	}
}
