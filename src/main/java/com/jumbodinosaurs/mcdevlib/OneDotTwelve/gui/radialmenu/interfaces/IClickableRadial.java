package com.jumbodinosaurs.mcdevlib.OneDotTwelve.gui.radialmenu.interfaces;


import com.jumbodinosaurs.devlib.util.objects.Point2D;

public interface IClickableRadial extends IClickable
{
    void setCenterPoint(Point2D centerPoint);
    
    Point2D getCenterPoint();
    
}
