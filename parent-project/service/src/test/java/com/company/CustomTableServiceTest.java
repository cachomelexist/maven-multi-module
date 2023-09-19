package com.company;

import java.util.List;
import java.util.ArrayList;
import java.io.IOException;
import java.lang.IndexOutOfBoundsException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Assertions;

public class CustomTableServiceTest {
	private String keySearch, keyResult;
	private Integer order;
	private CustomTableServiceImpl tableService;
	
	@BeforeEach
	public void setup() {
		Assertions.assertDoesNotThrow(() -> {
			tableService = new CustomTableServiceImpl();
		});
	}
	
	/*******************************************************************************
	
			searchKeyInstances()
		
	********************************************************************************/	
	
    @Test
	@DisplayName("Should return 0 since search have no match")
    public void searchKeyInstancesShouldReturnZero() {
		List<ArrayList<String>> keyInstanceList = tableService.searchKeyInstances("");
		
		Assertions.assertEquals(0, keyInstanceList.size());
    }
	
    @Test
    public void searchKeyInstancesSingleCharStart() {
		keySearch = "@";
		keyResult = "@J0gK\"";
		order = 0;
		
		List<ArrayList<String>> keyInstanceList = tableService.searchKeyInstances(keySearch);
		Assertions.assertEquals(keyResult, keyInstanceList.get(order).get(0));
	}
	
    @Test
    public void searchKeyInstancesSingleCharMiddle() {
		keySearch = "7";
		keyResult = "B77RNX";
		order = 0;
		
		List<ArrayList<String>> keyInstanceList = tableService.searchKeyInstances(keySearch);
		Assertions.assertEquals(keyResult, keyInstanceList.get(order).get(0));
	}
	
    @Test
    public void searchKeyInstancesSingleCharEnd() {
		keySearch = "\\";
		keyResult = "\\G\\";
		order = 0;
		
		List<ArrayList<String>> keyInstanceList = tableService.searchKeyInstances(keySearch);
		Assertions.assertEquals(keyResult, keyInstanceList.get(order).get(0));
	}
	
    @Test
    public void searchKeyInstancesSingleCharMultiKeys() {
		List<ArrayList<String>> keyInstanceList = tableService.searchKeyInstances("F");
		
		Assertions.assertEquals(3, keyInstanceList.size());
	}
	
    @Test
    public void searchKeyInstancesTwoCharStart() {
		List<ArrayList<String>> keyInstanceList = tableService.searchKeyInstances("z5");
		
		Assertions.assertEquals("z5\\oI", keyInstanceList.get(0).get(0));
    }
	
    @Test
    public void searchKeyInstancesTwoCharMiddle() {
		List<ArrayList<String>> keyInstanceList = tableService.searchKeyInstances("rQ");
		
		Assertions.assertEquals("HrQ[", keyInstanceList.get(0).get(0));
    }
	
    @Test
    public void searchKeyInstancesTwoCharEnd() {
		List<ArrayList<String>> keyInstanceList = tableService.searchKeyInstances("/O");
		
		Assertions.assertEquals("&0h{/O", keyInstanceList.get(0).get(0));
    }
	
    @Test
    public void searchKeyInstancesMultiCharStart() {
		List<ArrayList<String>> keyInstanceList = tableService.searchKeyInstances("FyZu");
		
		Assertions.assertEquals("FyZu:$", keyInstanceList.get(0).get(0));
    }
	
    @Test
    public void searchKeyInstancesMultiCharMiddle() {
		List<ArrayList<String>> keyInstanceList = tableService.searchKeyInstances("0h{/");
		
		Assertions.assertEquals("&0h{/O", keyInstanceList.get(0).get(0));
    }
	
    @Test
    public void searchKeyInstancesMultiCharEnd() {
		List<ArrayList<String>> keyInstanceList = tableService.searchKeyInstances("rlF,D");
		
		Assertions.assertEquals("UrlF,D", keyInstanceList.get(0).get(0));
    }
	
	/*******************************************************************************
	
			searchValueInstances()
		
	********************************************************************************/
	
    @Test
    public void searchValueInstancesNoMatch() {
		List<ArrayList<String>> keyInstanceList = tableService.searchValueInstances("");
		
		Assertions.assertEquals(0, keyInstanceList.size());
    }
	
    @Test
    public void searchValueInstancesSingleCharStart() {
		List<ArrayList<String>> keyInstanceList = tableService.searchValueInstances("R");
		
		Assertions.assertEquals("@iC3&4", keyInstanceList.get(2).get(0));
	}
	
    @Test
    public void searchValueInstancesSingleCharMiddle() {
		List<ArrayList<String>> keyInstanceList = tableService.searchValueInstances("F");
		
		Assertions.assertEquals("2]4hi{", keyInstanceList.get(0).get(0));
	}
	
    @Test
    public void searchValueInstancesSingleCharEnd() {
		List<ArrayList<String>> keyInstanceList = tableService.searchValueInstances("j");
		
		Assertions.assertEquals("HrQ[", keyInstanceList.get(0).get(0));
	}
	
    @Test
    public void searchValueInstancesSingleCharMultiKeys() {
		List<ArrayList<String>> keyInstanceList = tableService.searchValueInstances("F");
		
		Assertions.assertEquals(2, keyInstanceList.size());
	}
	
    @Test
    public void searchValueInstancesTwoCharStart() {
		List<ArrayList<String>> keyInstanceList = tableService.searchValueInstances("_{");
		
		Assertions.assertEquals("FyZu:$", keyInstanceList.get(0).get(0));
    }
	
    @Test
    public void searchValueInstancesTwoCharMiddle() {
		List<ArrayList<String>> keyInstanceList = tableService.searchValueInstances("k9");
		
		Assertions.assertEquals("L-b;3", keyInstanceList.get(0).get(0));
    }
	
    @Test
    public void searchValueInstancesTwoCharEnd() {
		List<ArrayList<String>> keyInstanceList = tableService.searchValueInstances("Gp");
		
		Assertions.assertEquals("nFB,/", keyInstanceList.get(0).get(0));
    }
	
    @Test
    public void searchValueInstancesMultiCharStart() {
		List<ArrayList<String>> keyInstanceList = tableService.searchValueInstances("FftBi");
		
		Assertions.assertEquals("&0h{/O", keyInstanceList.get(0).get(0));
    }
	
    @Test
    public void searchValueInstancesMultiCharMiddle() {
		List<ArrayList<String>> keyInstanceList = tableService.searchValueInstances("TWCR");
		
		Assertions.assertEquals("UrlF,D", keyInstanceList.get(0).get(0));
    }
	
    @Test
    public void searchValueInstancesMultiCharEnd() {
		List<ArrayList<String>> keyInstanceList = tableService.searchValueInstances("H7W}Q");
		
		Assertions.assertEquals("B77RNX", keyInstanceList.get(0).get(0));
    }
	
	/*******************************************************************************
	
			getDataByKey()
		
	********************************************************************************/
	
    @Test
    public void getDataByKeyFirst() {
		List<String> searchData = tableService.getDataByKey("d3?");
		
		Assertions.assertEquals("0,0", searchData.get(0));
		Assertions.assertEquals("d3?", searchData.get(1));
		Assertions.assertEquals("X&)", searchData.get(2));
    }
	
    @Test
    public void getDataByKeyMiddle() {
		List<String> searchData = tableService.getDataByKey("q,ZpI]");
		
		Assertions.assertEquals("1,2", searchData.get(0));
		Assertions.assertEquals("q,ZpI]", searchData.get(1));
		Assertions.assertEquals("uyESws", searchData.get(2));
    }
	
    @Test
    public void getDataByKeyLast() {
		List<String> searchData = tableService.getDataByKey("nFB,/");
		
		Assertions.assertEquals("4,4", searchData.get(0));
		Assertions.assertEquals("nFB,/", searchData.get(1));
		Assertions.assertEquals("U;0Gp", searchData.get(2));
    }
	
	/*******************************************************************************
	
			getDataByCoord()
		
	********************************************************************************/
	
    @Test
    public void getDataByCoordFirst() {
		List<String> searchData = tableService.getDataByCoord(0,0);
		
		Assertions.assertEquals("0,0", searchData.get(0));
		Assertions.assertEquals("d3?", searchData.get(1));
		Assertions.assertEquals("X&)", searchData.get(2));
    }
	
    @Test
    public void getDataByCoordMiddle() {
		List<String> searchData = tableService.getDataByCoord(1,2);
		
		Assertions.assertEquals("1,2", searchData.get(0));
		Assertions.assertEquals("q,ZpI]", searchData.get(1));
		Assertions.assertEquals("uyESws", searchData.get(2));
    }
	
    @Test
    public void getDataByCoordLast() {
		List<String> searchData = tableService.getDataByCoord(4,4);
		
		Assertions.assertEquals("4,4", searchData.get(0));
		Assertions.assertEquals("nFB,/", searchData.get(1));
		Assertions.assertEquals("U;0Gp", searchData.get(2));
    }
	
	/*******************************************************************************
	
			getCoordByKey()
		
	********************************************************************************/
	
    @Test
    public void getCoordByKeyFirst() {
		int[] searchData = tableService.getCoordByKey("d3?");
		
		Assertions.assertEquals(0, searchData[0]);
		Assertions.assertEquals(0, searchData[1]);
    }
	
    @Test
    public void getCoordByKeyMiddle() {
		int[] searchData = tableService.getCoordByKey("%ES");
		
		Assertions.assertEquals(2, searchData[0]);
		Assertions.assertEquals(3, searchData[1]);
    }
	
    @Test
    public void getCoordByKeyLast() {
		int[] searchData = tableService.getCoordByKey("nFB,/");
		
		Assertions.assertEquals(4, searchData[0]);
		Assertions.assertEquals(4, searchData[1]);
    }
	
	
	/*******************************************************************************
	
			saveTable()
		
	********************************************************************************/
	
    @Test
    public void saveTableTest() {
		Assertions.assertThrows(IOException.class, () -> {
			tableService = new CustomTableServiceImpl("nullTable.txt");
		});
    }
	
	
	/*******************************************************************************
	
			changeKey()
		
	********************************************************************************/
	
    @Test
    public void changeKeyShouldReturnKeyAlreadyExistException() {
		Assertions.assertThrows(KeyAlreadyExistException.class, () -> {
			tableService.changeKey("9Ps", "L-b;3");
		});
    }
	
    @Test
    public void changeKeyShouldReturnSameData() {
		String oldKey = "9Ps";
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
		Assertions.assertEquals(5, tableService.getRowCount());
	}
	
	
	/*******************************************************************************
	
			sortRow()
		
	********************************************************************************/
	
	@Test
	public void sortRowTest() {
		tableService.sortRow(4);
		Assertions.assertArrayEquals(new int[] {4,0}, tableService.getCoordByKey("&0h{/O"));
		Assertions.assertArrayEquals(new int[] {4,1}, tableService.getCoordByKey("@iC3&4"));
		Assertions.assertArrayEquals(new int[] {4,2}, tableService.getCoordByKey("V9|1E"));
		Assertions.assertArrayEquals(new int[] {4,3}, tableService.getCoordByKey("nFB,/"));
		Assertions.assertArrayEquals(new int[] {4,4}, tableService.getCoordByKey("z5\\oI"));
		
	}
	
	@Test
	public void sortRowTestIndexOutOfBounds() {
		Assertions.assertThrows(IndexOutOfBoundsException.class, () -> {
			tableService.sortRow(5);
		});
	}
	
	
	/*******************************************************************************
	
			insertRow()
		
	********************************************************************************/
	
	@Test
	public void insertRowTest() {
		tableService.insertRow(0,6);
		Assertions.assertEquals(6, tableService.getRowCount());
		Assertions.assertNotEquals(null, tableService.getDataByCoord(0,5));
		Assertions.assertEquals(null, tableService.getDataByCoord(0,6));
	}
	
}
