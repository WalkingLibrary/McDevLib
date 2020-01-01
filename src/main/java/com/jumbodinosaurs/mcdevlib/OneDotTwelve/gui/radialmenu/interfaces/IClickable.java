package com.jumbodinosaurs.mcdevlib.OneDotTwelve.gui.radialmenu.interfaces;


import com.jumbodinosaurs.mcdevlib.OneDotTwelve.util.objects.Point;

public interface IClickable
{
    void onClick(int btn);
    
    boolean isInClickableSpace(Point mouseLocation);
    
    default void onScreenClick(Point mouseLocation, int btn)
    {
        if(isInClickableSpace(mouseLocation))
        {
            onClick(btn);
        }
    }
}
