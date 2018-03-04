package com.jstien.displed.font;

import com.jstien.displed.BinaryMap;
import com.sun.media.sound.InvalidFormatException;
import sun.plugin.dom.exception.InvalidStateException;

import java.util.Scanner;

public class Glyph {
    private final static int INVALID_INT_VALUE = Integer.MIN_VALUE;

    private String name;
    private int codePoint = INVALID_INT_VALUE;

    private int deviceWidth = INVALID_INT_VALUE;
    private int deviceHeight = INVALID_INT_VALUE;

    private int width = INVALID_INT_VALUE;
    private int height = INVALID_INT_VALUE;
    private int xOffset = INVALID_INT_VALUE;
    private int yOffset = INVALID_INT_VALUE;

    private BinaryMap binaryMap;

    public Glyph(Scanner scanner) throws InvalidFormatException {
        if (!scanner.hasNext("STARTCHAR")) {
            throw new InvalidFormatException("Expected next line to be 'STARTCHAR'");
        }

        scanner.next();
        name = scanner.next();

        while (scanner.hasNextLine()) {
            if (scanner.hasNext("ENDCHAR")) {
                break;
            } else if (scanner.hasNext("BBX")) {
                scanner.next();
                width = scanner.nextInt();
                height = scanner.nextInt();
                xOffset = scanner.nextInt();
                yOffset = scanner.nextInt();
            } else if (scanner.hasNext("ENCODING")) {
                scanner.next();
                codePoint = scanner.nextInt();
            } else if (scanner.hasNext("DWIDTH")) {
                scanner.next();
                deviceWidth = scanner.nextInt();
                deviceHeight = scanner.nextInt();
            } else if (scanner.hasNext("BITMAP")) {
                scanner.next();
                parseBitmap(scanner);
            } else {
                scanner.nextLine();
            }
        }

        ensureProperParsing();
    }

    public String getName() {
        return name;
    }

    public int getCodePoint() {
        return codePoint;
    }

    public int getDeviceWidth() {
        return deviceWidth;
    }

    public int getDeviceHeight() {
        return deviceHeight;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getxOffset() {
        return xOffset;
    }

    public int getyOffset() {
        return yOffset;
    }

    public BinaryMap getBinaryMap() {
        return binaryMap;
    }

    private void parseBitmap(Scanner scanner) {
        if (height == 0 || width == 0) {
            throw new InvalidStateException("Bitmap declared, but no dimensions defined");
        }

        binaryMap = new BinaryMap(width, height);

        for (int i=0; i<height; i++) {
            String hexRep = scanner.next();
            byte[] bytes = hexStringToByteArray(hexRep);

            for (int j=0; j<width; j++) {
                int byteIdx = j/8;
                int bitIdx = 7 - (j%8);
                int mask = (1 << bitIdx);
                int result = (int)bytes[byteIdx] & mask;
                boolean flag = result != 0;
                binaryMap.set(j, i, flag);
            }
        }
    }

    private static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                                 + Character.digit(s.charAt(i+1), 16));
        }
        return data;
    }

    private void ensureProperParsing() throws InvalidFormatException {
        if (name == null)
            throw new InvalidFormatException("No name provided");
        if (codePoint == INVALID_INT_VALUE)
            throw new InvalidFormatException("No codePoint defined");
        if (deviceWidth == INVALID_INT_VALUE || deviceHeight == INVALID_INT_VALUE)
            throw new InvalidFormatException("No device width specified");
        if (width == INVALID_INT_VALUE || height == INVALID_INT_VALUE
                || xOffset == INVALID_INT_VALUE || yOffset == INVALID_INT_VALUE)
            throw new InvalidFormatException("No bounding box defined");
        if (binaryMap == null || binaryMap.getWidth() != width || binaryMap.getHeight() != height)
            throw new InvalidFormatException("Invalid or NULL binaryMap");
    }

}
