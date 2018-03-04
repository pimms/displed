package com.jstien.displed.font;

import com.sun.media.sound.InvalidFormatException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class BDFFont {
    Logger LOG = LogManager.getLogger(BDFFont.class);

    private int baseLine;
    private int fontHeight;

    private Map<Integer,Glyph> glyphs = new HashMap<>();

    public BDFFont(String filePath) throws FileNotFoundException, InvalidFormatException {
        InputStream stream = this.getClass().getClassLoader().getResourceAsStream(filePath);
        Scanner scanner = new Scanner(stream);

        while (scanner.hasNextLine()) {
            if (scanner.hasNext("FONTBOUNDINGBOX")) {
                scanner.next();
                scanner.nextInt();
                fontHeight = scanner.nextInt();
                scanner.nextInt();
                baseLine = scanner.nextInt();
            } else if (scanner.hasNext("STARTCHAR")) {
                Glyph glyph = new Glyph(scanner);
                glyphs.put(glyph.getCodePoint(), glyph);
                LOG.debug("Parsed glyph '"+glyph.getName()+"'");
            } else {
                scanner.nextLine();
            }
        }
    }

    public Glyph getGlyph(int charCode) {
        return glyphs.get(charCode);
    }

    public Glyph getGlyph(char c) {
        return getGlyph((int)c);
    }

    public int getFontHeight() {
        return fontHeight;
    }
}
