package com.jumbodinosaurs.mcdevlib.OneDotTwelve.gui.radialmenu.util;


import com.jumbodinosaurs.devlib.util.objects.Point2D;
import com.jumbodinosaurs.mcdevlib.OneDotTwelve.gui.radialmenu.interfaces.IDrawable;
import org.lwjgl.opengl.GL11;


public class Circle2D implements IDrawable
{
    private double radius;
    
    public Circle2D(double radius)
    {
        this.radius = radius;
    }
    
    @Override
    public void draw(Point2D screenCenter)
    {
        int x = (int) screenCenter.getX();
        int z = (int) screenCenter.getZ();
        
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
            GL11.glColor3d(0, 0, 0);
            double lastX, latsZ;
            lastX = (radius * Math.cos(radium)) + x;
            latsZ = (radius * Math.sin(radium)) + z;
            GL11.glVertex2d(lastX, latsZ);
            double tempX, tempZ;
            tempX = (radius * Math.cos(nextRadium)) + x;
            tempZ = (radius * Math.sin(nextRadium)) + z;
            GL11.glVertex2d(tempX, tempZ);
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
