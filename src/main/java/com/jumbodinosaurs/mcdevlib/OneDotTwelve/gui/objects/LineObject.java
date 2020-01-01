package com.jumbodinosaurs.mcdevlib.OneDotTwelve.gui.objects;



import com.jumbodinosaurs.mcdevlib.OneDotTwelve.gui.util.DrawManager;

import java.util.ArrayList;

public abstract class LineObject
{
    protected ArrayList<Line> lines = new ArrayList<Line>();
    
    public void draw()
    {
        for(Line line : lines)
        {
            DrawManager.getDrawManager().addLine(line);
        }
        
    }
    
    public void erase()
    {
        for(Line line : lines)
        {
            DrawManager.getDrawManager().removeLine(line);
        }
        
    }
    
    public abstract void generate();
    
}
