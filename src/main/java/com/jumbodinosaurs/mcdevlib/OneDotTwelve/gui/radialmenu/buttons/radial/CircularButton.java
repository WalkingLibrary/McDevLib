package com.jumbodinosaurs.mcdevlib.OneDotTwelve.gui.radialmenu.buttons.radial;


import com.jumbodinosaurs.devlib.util.objects.Point2D;
import com.jumbodinosaurs.mcdevlib.OneDotTwelve.gui.radialmenu.util.LabelCon;

public abstract class CircularButton extends IconButton
{
    private int radius;
    private Point2D centerPoint;
    
    public CircularButton(LabelCon labelCon, int radius)
    {
        super(labelCon, radius - 1);
        this.radius = radius;
    }
    
    public abstract void updateCenterPoint(Point2D screenCenter);
    
    @Override
    public boolean isInClickableSpace(Point2D mouseLocation)
    {
        double pointDifference = centerPoint.getEuclideanDistance(mouseLocation);
        return pointDifference < (double) this.radius;
    }
    
    @Override
    public void draw(Point2D screenCenter)
    {
        super.draw(screenCenter);
        updateCenterPoint(screenCenter);
    }
    
    
    public int getRadius()
    {
        return radius;
    }
    
    public void setRadius(int radius)
    {
        this.radius = radius;
    }
    
    public Point2D getCenterPoint()
    {
        return centerPoint;
    }
    
    public void setCenterPoint(Point2D centerPoint)
    {
        this.centerPoint = centerPoint;
    }
}
