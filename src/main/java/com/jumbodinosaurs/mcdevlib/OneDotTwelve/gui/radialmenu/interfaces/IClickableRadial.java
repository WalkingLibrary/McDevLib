package com.jumbodinosaurs.mcdevlib.OneDotTwelve.gui.radialmenu.interfaces;


import com.jumbodinosaurs.mcdevlib.OneDotTwelve.util.objects.Point;

public interface IClickableRadial extends IClickable
{
    void setCenterPoint(Point centerPoint);
    
    Point getCenterPoint();
    
}
