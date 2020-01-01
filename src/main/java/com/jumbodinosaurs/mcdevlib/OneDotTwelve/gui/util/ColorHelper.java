package com.jumbodinosaurs.mcdevlib.OneDotTwelve.gui.util;

import java.awt.*;

public class ColorHelper
{
    public static Color getRandomColor()
    {
        int randomNumber = (int) (Math.random() * 8);
        switch(randomNumber)
        {
            case 0:
                return Color.BLACK;
            case 1:
                return Color.GRAY;
            
            case 2:
                return Color.orange;
            
            case 3:
                return Color.cyan;
            
            case 4:
                return Color.PINK;
            
            case 5:
                return Color.YELLOW;
            
            case 6:
                return Color.GREEN;
            
            case 7:
                return Color.RED;
            
            case 8:
                return Color.BLUE;
            
            default:
                return Color.WHITE;
            
        }
    }
}
