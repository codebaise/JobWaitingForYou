package com.home.closematch;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Iterator;

public class IteratorTest {
    @Test
    public void iteratorTest() {
        ArrayList<Integer> objects = new ArrayList<>();
        objects.add(1);
        objects.add(2);
        objects.add(3);
        objects.add(4);
        Iterator<Integer> iterator = objects.iterator();

    }
}
