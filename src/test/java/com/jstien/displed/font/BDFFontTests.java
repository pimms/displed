package com.jstien.displed.font;

import com.jstien.displed.BinaryMap;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class BDFFontTests {
    @Test
    public void openingFileWorks() throws Exception {
        BDFFont font = new BDFFont("4x6.bdf");
    }

    @Test
    public void fontHeihgtReadCorrectly() throws Exception {
        BDFFont font = new BDFFont("4x6.bdf");
        Assert.assertEquals(6, font.getFontHeight());

        font = new BDFFont("5x7.bdf");
        Assert.assertEquals(7, font.getFontHeight());
    }

    @Test
    public void alphabetIsParsed() throws Exception {
        BDFFont font = new BDFFont("4x6.bdf");
        for (int i='a'; i<='z'; i++) {
            Assert.assertNotNull(font.getGlyph(i));
        }
    }

    @Test
    public void exclamationCharacterParsedCorrectly() throws Exception {
        BDFFont font = new BDFFont("4x6.bdf");
        Glyph glyph = font.getGlyph('!');
        BinaryMap map = glyph.getBinaryMap();

        List<BinaryMap.Tuple> flagged = map.getFlagged();
        Assert.assertEquals(4, flagged.size());

        Assert.assertEquals(1, flagged.get(0).getFirst());
        Assert.assertEquals(0, flagged.get(0).getSecond());

        Assert.assertEquals(1, flagged.get(1).getFirst());
        Assert.assertEquals(1, flagged.get(1).getSecond());

        Assert.assertEquals(1, flagged.get(2).getFirst());
        Assert.assertEquals(2, flagged.get(2).getSecond());

        Assert.assertEquals(1, flagged.get(3).getFirst());
        Assert.assertEquals(4, flagged.get(3).getSecond());
    }

    @Test
    public void letterABoundariesReadCorrectly() throws Exception {
        BDFFont font = new BDFFont("4x6.bdf");
        Glyph glyph = font.getGlyph('a');
        Assert.assertEquals("a", glyph.getName());
        Assert.assertEquals("a".codePointAt(0), glyph.getCodePoint());

        Assert.assertEquals(4, glyph.getWidth());
        Assert.assertEquals(6, glyph.getHeight());

        Assert.assertEquals(4, glyph.getDeviceWidth());
        Assert.assertEquals(0, glyph.getDeviceHeight());

        Assert.assertEquals(0, glyph.getxOffset());
        Assert.assertEquals(-1, glyph.getyOffset());
    }
}
