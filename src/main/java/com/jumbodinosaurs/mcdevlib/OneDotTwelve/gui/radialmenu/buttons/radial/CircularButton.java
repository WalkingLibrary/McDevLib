package com.jumbodinosaurs.mcdevlib.OneDotTwelve.gui.radialmenu.buttons.radial;


import com.jumbodinosaurs.mcdevlib.OneDotTwelve.gui.radialmenu.util.LabelCon;
import com.jumbodinosaurs.mcdevlib.OneDotTwelve.util.objects.Point;

public abstract class CircularButton extends IconButton
{
    private int radius;
    private Point centerPoint;
    
    public CircularButton(LabelCon labelCon, int radius)
    {
        super(labelCon, radius - 1);
        this.radius = radius;
    }
    
    public abstract void updateCenterPoint(Point screenCenter);
    
    @Override
    public boolean isInClickableSpace(Point mouseLocation)
    {
        double pointDifference = centerPoint.getEuclideanDistance(mouseLocation);
        if(pointDifference < (double) this.radius)
        {
            return true;
        }
        return false;
    }
    
    @Override
    public void draw(Point screenCenter)
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
    
    public Point getCenterPoint()
    {
        return centerPoint;
    }
    
    public void setCenterPoint(Point centerPoint)
    {
        this.centerPoint = centerPoint;
    }
}
