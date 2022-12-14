package net.luis.utils.util;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Map;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Luis-st
 *
 */

class UtilsTest {
	
	@Test
	void make() {
		assertThrows(NullPointerException.class, () -> Utils.make(null, (list) -> {}));
		assertEquals(Utils.make(Lists.newArrayList(), (list) -> list.addAll(Arrays.asList(0, 1, 2, 3, 4))), Lists.newArrayList(0, 1, 2, 3, 4));
	}
	
	@Test
	void mapToList() {
		assertEquals(Utils.mapToList(Maps.newHashMap(Map.of("0", 0, "1", 1, "2", 2)), (key, value) -> value), Lists.newArrayList(0, 1, 2));
	}
	
	@Test
	void listToMap() {
		assertEquals(Utils.listToMap(Lists.newArrayList(0, 1, 2), (value) -> new SimpleEntry<>(String.valueOf(value), value)), Maps.newHashMap(Map.of("0", 0, "1", 1, "2", 2)));
	}
	
	@Test
	void mapList() {
		assertEquals(Utils.mapList(Lists.newArrayList(0, 1, 2), String::valueOf), Lists.newArrayList("0", "1", "2"));
		assertEquals(Utils.mapList(Lists.newArrayList("0", "1", "2"), Integer::parseInt, (value) -> value * 2), Lists.newArrayList(0, 2, 4));
	}
	
	@Test
	void mapKey() {
		assertEquals(Utils.mapKey(Map.of("0", 0, "1", 1, "2", 2), Integer::parseInt), Map.of(0, 0, 1, 1, 2, 2));
	}
	
	@Test
	void mapValue() {
		assertEquals(Utils.mapValue(Map.of(0, "0", 1, "1", 2, "2"), Integer::parseInt), Map.of(0, 0, 1, 1, 2, 2));
	}
	
	@Test
	void reverseList() {
		assertEquals(Utils.reverseList(Lists.newArrayList(0, 1, 2, 3, 4)), Lists.newArrayList(4, 3, 2, 1, 0));
	}
	
	@Test
	void runIfNotNull() {
		assertNull(Utils.runIfNotNull(null, String::trim));
		assertEquals(Utils.runIfNotNull(" runIfNotNull ", String::trim), "runIfNotNull");
	}
	
	@Test
	void warpNullTo() {
		assertThrows(NullPointerException.class, () -> Utils.warpNullTo(null, null));
		assertEquals(Utils.warpNullTo(null, "fallback"), "fallback");
		assertEquals(Utils.warpNullTo("value", "fallback"), "value");
	}
	
	@Test
	void concatLists() {
		assertTrue(Utils.concatLists().isEmpty());
		assertEquals(Utils.concatLists(Lists.newArrayList(0, 1, 2), Lists.newArrayList(3, 4)), Lists.newArrayList(0, 1, 2, 3, 4));
	}
	
	@Test
	void getRandomSafe() {
		assertTrue(Utils.getRandomSafe(new Integer[0], new Random()).isEmpty());
		assertTrue(Utils.getRandomSafe(new Integer[] {0, 1, 2, 3, 4}, new Random()).isPresent());
		
		assertTrue(Utils.getRandomSafe(Lists.newArrayList(), new Random()).isEmpty());
		assertTrue(Utils.getRandomSafe(Lists.newArrayList(0, 1, 2, 3, 4), new Random()).isPresent());
	}
}