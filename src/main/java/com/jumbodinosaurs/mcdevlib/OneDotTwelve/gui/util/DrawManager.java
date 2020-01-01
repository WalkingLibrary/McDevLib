package com.jumbodinosaurs.mcdevlib.OneDotTwelve.gui.util;


import com.jumbodinosaurs.mcdevlib.OneDotTwelve.bot.pathfinding.util.WayPoint;
import com.jumbodinosaurs.mcdevlib.OneDotTwelve.gui.objects.Line;
import com.jumbodinosaurs.mcdevlib.OneDotTwelve.util.minecraft.GameHelper;
import com.jumbodinosaurs.mcdevlib.OneDotTwelve.util.minecraft.PlayerHelper;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.ArrayList;

public class DrawManager
{
    private static final DrawManager drawManager = new DrawManager();
    //TODO CopyOnWriteArrayList ???
    private ArrayList<Line> linesToDraw = new ArrayList<Line>();
    private int threshold = 25000;
    private boolean messaged;
    
    public static DrawManager getDrawManager()
    {
        return drawManager;
    }
    
    public void addLine(Line line)
    {
        if(linesToDraw.size() < threshold)
        {
            this.linesToDraw.add(line);
            messaged = false;
        }
        else
        {
            if(!messaged)
            {
                GameHelper.sendLocalMessage("Line Draw Limit Reached");
                messaged = true;
            }
        }
    }
    
    public void removeLine(Line line)
    {
        this.linesToDraw.remove(line);
    }
    
    
    public void onDraw(RenderWorldLastEvent event)
    {
        
        float partialTickTime = event.getPartialTicks();
        WayPoint relativePlayerPosition = PlayerHelper.getPlayerPositionForRenderEvent(partialTickTime);
        for(int i = 0; i < this.linesToDraw.size(); i++)
        {
            
            GL11.glPushMatrix();
            Line currentLine = this.linesToDraw.get(i);
           
            GL11.glLineWidth(currentLine.getWidth());
            GL11.glDisable(GL11.GL_TEXTURE_2D);
            if(!currentLine.needsDepth())
            {
                GL11.glDisable(GL11.GL_DEPTH_TEST);
                GL11.glDepthMask(false);
            }
            else
            {
                GL11.glEnable(GL11.GL_DEPTH_TEST);
                GL11.glDepthMask(true);
    
            }
            GL11.glEnable(GL11.GL_BLEND);
            Color currentColor = currentLine.getColor();
            GL11.glColor3d(currentColor.getRed(), currentColor.getGreen(), currentColor.getBlue());
            GL11.glTranslated(-relativePlayerPosition.getX(),
                              -relativePlayerPosition.getY(),
                              -relativePlayerPosition.getZ());
            GL11.glBegin(GL11.GL_LINES);
            
            
            WayPoint pointOne = currentLine.getPointOne();
            WayPoint pointTwo = currentLine.getPointTwo();
            GL11.glVertex3d(pointOne.getX(), pointOne.getY(), pointOne.getZ());
            GL11.glVertex3d(pointTwo.getX(), pointTwo.getY(), pointTwo.getZ());
            GL11.glEnd();
            GL11.glDisable(GL11.GL_BLEND);
            GL11.glEnable(GL11.GL_TEXTURE_2D);
            GL11.glEnable(GL11.GL_DEPTH_TEST);
            GL11.glDepthMask(true);
            GL11.glPopMatrix();
        }
        
    }
}
