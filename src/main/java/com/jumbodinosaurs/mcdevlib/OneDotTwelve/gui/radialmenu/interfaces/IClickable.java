package com.jumbodinosaurs.mcdevlib.OneDotTwelve.gui.radialmenu.interfaces;


import com.jumbodinosaurs.devlib.util.objects.Point2D;

public interface IClickable
{
    void onClick(int btn);
    
    boolean isInClickableSpace(Point2D mouseLocation);
    
    default void onScreenClick(Point2D mouseLocation, int btn)
    {
        if(isInClickableSpace(mouseLocation))
        {
            onClick(btn);
        }
    }
}
