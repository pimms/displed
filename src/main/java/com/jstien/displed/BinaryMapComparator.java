package com.jstien.displed;

public class BinaryMapComparator {
    public static final int REMOVED = 0;
    public static final int UNCHANGED = 0;
    public static final int ADDED = 0;

    public BinaryMapComparator() {

    }

    /**
     * Returns the difference between to BinaryMap instances.
     * Each returned value holds "REMOVED", "UNCHANGED", or "ADDED".
     * Each diff-element refers to the changes that must be applied to A to get B.
     */
    public int[][] compare(BinaryMap mapA, BinaryMap mapB) {
        if (mapA.getWidth() != mapB.getWidth() ||
                mapA.getHeight() != mapB.getHeight()) {
            throw new IllegalArgumentException("mapA and mapB have different resolutions");
        }

        final int width = mapA.getWidth();
        final int height = mapA.getHeight();

        int[][] diff = new int[width][];
        for (int x=0; x<width; x++) {
            diff[x] = new int[height];
        }

        for (int x=0; x<width; x++) {
            for (int y=0; y<height; y++) {
                boolean a = mapA.get(x, y);
                boolean b = mapB.get(x, y);
                int state = UNCHANGED;
                if (a && !b) {
                    state = REMOVED;
                } else if (!a && b) {
                    state = ADDED;
                }
                diff[x][y] = state;
            }
        }

        return diff;
    }

}
