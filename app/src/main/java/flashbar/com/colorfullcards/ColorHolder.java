package flashbar.com.colorfullcards;

import android.graphics.Color;

public class ColorHolder {
    int backgroundColor;
    int headingColor;
    int infoColor;

    public static ColorHolder getDefaultColorHolder(){
        ColorHolder colorHolder = new ColorHolder();
        colorHolder.backgroundColor = Color.WHITE;
        colorHolder.headingColor = Color.BLACK;
        colorHolder.infoColor = Color.GRAY;
        return colorHolder;
    }
}
