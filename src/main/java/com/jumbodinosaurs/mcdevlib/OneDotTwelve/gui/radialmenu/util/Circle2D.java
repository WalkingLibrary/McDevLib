package com.jumbodinosaurs.mcdevlib.OneDotTwelve.gui.radialmenu.util;


import com.jumbodinosaurs.mcdevlib.OneDotTwelve.gui.radialmenu.interfaces.IDrawable;
import com.jumbodinosaurs.mcdevlib.OneDotTwelve.util.objects.Point;
import org.lwjgl.opengl.GL11;


public class Circle2D implements IDrawable
{
    private double radius;
    
    public Circle2D(double radius)
    {
        this.radius = radius;
    }
    
    @Override
    public void draw(Point screenCenter)
    {
        int x = (int) screenCenter.getX();
        int y = (int) screenCenter.getY();
        
        for(int i = 1; i <= 360; i++)
        {
            double radium = i * (Math.PI / 180);
            double nextRadium = (i + 1) * (Math.PI / 180);
            GL11.glPushMatrix();
            GL11.glDisable(GL11.GL_TEXTURE_2D);
            GL11.glEnable(GL11.GL_DEPTH_TEST);
            GL11.glDepthMask(true);
       
            GL11.glLineWidth(100);
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glBegin(GL11.GL_LINES);
            GL11.glColor3d(0,0,0);
            double lastX, latsY;
            lastX = (radius * Math.cos(radium)) + x;
            latsY = (radius * Math.sin(radium)) + y;
            GL11.glVertex2d(lastX, latsY);
            double tempX, tempY;
            tempX = (radius * Math.cos(nextRadium)) + x;
            tempY = (radius * Math.sin(nextRadium)) + y;
            GL11.glVertex2d(tempX, tempY);
            GL11.glEnd();
            GL11.glDisable(GL11.GL_BLEND);
            GL11.glEnable(GL11.GL_TEXTURE_2D);
            GL11.glEnable(GL11.GL_DEPTH_TEST);
            GL11.glDepthMask(true);
            GL11.glPopMatrix();
            
        }
    }
    
    public double getRadius()
    {
        return radius;
    }
    
    public void setRadius(double radius)
    {
        this.radius = radius;
    }
}
