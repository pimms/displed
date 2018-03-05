package com.jstien.displed.display;

import com.jstien.displed.BinaryMap;
import com.jstien.displed.font.BDFFont;
import com.jstien.displed.font.Glyph;

import java.awt.*;

public class TextRenderer {
    private IDisplay display;
    private BDFFont font;
    private Color textColor;

    public TextRenderer(IDisplay display, BDFFont font) {
        this.display = display;
        this.font = font;
        this.textColor = Color.white;
    }

    public void setFont(BDFFont font) {
        this.font = font;
    }

    public void setTextColor(Color textColor) {
        this.textColor = textColor;
    }

    public void drawText(int startX, int startY, String text) {
        int xPos = startX;
        int yPos = startY;
        for (int i=0; i<text.length(); i++) {
            int codePoint = text.codePointAt(i);
            Glyph glyph = font.getGlyph(codePoint);
            for (BinaryMap.Tuple tuple: glyph.getBinaryMap().getFlagged()) {
                int deviceX = tuple.getFirst();
                int deviceY = tuple.getSecond();
                display.setPixel(xPos + deviceX, yPos + deviceY, textColor);
            }
            xPos += glyph.getDeviceWidth();
            yPos += glyph.getDeviceHeight();
        }
    }
}
