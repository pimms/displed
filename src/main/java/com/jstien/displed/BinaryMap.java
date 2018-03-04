package com.jstien.displed;

import java.util.LinkedList;
import java.util.List;

public class BinaryMap {
    private int width;
    private int height;
    private boolean[] map;

    public class Tuple {
        private int first;
        private int second;
        public Tuple(int a, int b) {
            first = a;
            second = b;
        }
        public int getFirst() {
            return first;

        }
        public int getSecond() {
            return second;
        }
    }

    public BinaryMap(int width, int height) {
        this.width = width;
        this.height = height;
        this.map  = new boolean[width * height];
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean get(int x, int y) {
        return map[getIndex(x, y)];
    }

    public void set(int x, int y, boolean flag) {
        map[getIndex(x, y)] = flag;
    }

    public List<Tuple> getFlagged() {
        LinkedList<Tuple> flagged = new LinkedList<>();
        int index = 0;
        for (int y=0; y<height; y++) {
            for (int x=0; x<width; x++, index++) {
                if (map[index]) {
                    Tuple pair = new Tuple(x, y);
                    flagged.add(pair);
                }
            }
        }

        return flagged;
    }

    private int getIndex(int x, int y) {
        if (x < 0 || x >= width || y < 0 || y>= height)
            throw new IllegalArgumentException("Index out of range");
        return x + (y * width);
    }

}
