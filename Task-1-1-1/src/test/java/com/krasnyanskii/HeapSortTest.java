package com.krasnyanskii;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.Test;

public class HeapSortTest {

    @Test
    public void simpleTest() {
        int[] actual = {5, 4, 3, 2, 1};
        int[] expected = {1, 2, 3, 4, 5};

        HeapSort.sort(actual);

        assertArrayEquals(expected, actual);
    }

    @Test
    public void singleElement() {
        int[] actual = {1};
        int[] expected = {1};

        HeapSort.sort(actual);

        assertArrayEquals(expected, actual);
    }

    @Test
    public void empty() {
        int[] actual = {};
        int[] expected = {};

        HeapSort.sort(actual);

        assertArrayEquals(expected, actual);
    }

}
