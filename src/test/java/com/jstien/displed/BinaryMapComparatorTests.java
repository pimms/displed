package com.jstien.displed;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BinaryMapComparatorTests {
    private BinaryMapComparator comparator;

    public BinaryMapComparatorTests() {
        comparator = new BinaryMapComparator();
    }

    @Test
    public void returnValueCorrectDimensions() {
        BinaryMap a = new BinaryMap(20, 10);
        int[][] diff = comparator.compare(a, a);
        assertEquals(20, diff.length);

        for (int i=0; i<20; i++)
            assertEquals(10, diff[i].length);
    }

    @Test
    public void sameMapIsUnchanged() {
        BinaryMap a = new BinaryMap(10, 10);
        int[][] diff = comparator.compare(a, a);

        for (int i=0; i<10; i++) {
            for (int j=0; j<10; j++) {
                assertEquals(BinaryMapComparator.UNCHANGED, diff[i][j]);
            }
        }
    }

    @Test
    public void simpleDiffTest() {
        BinaryMap a = new BinaryMap(4, 4);
        BinaryMap b = new BinaryMap(4, 4);

        a.set(0, 0, true);
        b.set(0, 0, true);

        a.set(1, 0, false);
        b.set(1, 0, true);

        a.set(1, 1, true);
        b.set(1, 1, false);

        a.set(0, 1, false);
        b.set(0, 1, false);

        int[][] diff = comparator.compare(a, b);
        assertEquals(BinaryMapComparator.UNCHANGED, diff[0][0]);
        assertEquals(BinaryMapComparator.ADDED, diff[1][0]);
        assertEquals(BinaryMapComparator.REMOVED, diff[1][1]);
        assertEquals(BinaryMapComparator.UNCHANGED, diff[0][1]);

    }
}
