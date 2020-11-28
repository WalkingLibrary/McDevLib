package com.jumbodinosaurs.mcdevlib.OneDotTwelve.retentions.subclasses;


import com.jumbodinosaurs.mcdevlib.OneDotTwelve.bot.pathfinding.util.WayPoint;
import com.jumbodinosaurs.mcdevlib.OneDotTwelve.gui.objects.Line;
import com.jumbodinosaurs.mcdevlib.OneDotTwelve.gui.objects.TranslatedLine;
import com.jumbodinosaurs.mcdevlib.OneDotTwelve.retentions.Retention;
import com.jumbodinosaurs.mcdevlib.OneDotTwelve.util.minecraft.GameHelper;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class DrawManager extends Retention
{
    private transient static final DrawManager drawManager = new DrawManager();
    private transient CopyOnWriteArrayList<Line> linesToDraw = new CopyOnWriteArrayList<Line>();
    private transient int threshold = 25000;
    private transient boolean messaged;
    
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
    
    @SubscribeEvent
    public void onDrawEvent(RenderWorldLastEvent event)
    {
        DrawManager.getDrawManager().onDraw(event);
    }
    
    public void onDraw(RenderWorldLastEvent event)
    {
        for(Line currentLine : linesToDraw)
        {
            
            drawLine(currentLine, event.getPartialTicks());
        }
        
    }
    
    public static void drawLine(Line line, float partialTicks)
    {
        Line lineToDraw = line;
        if(line instanceof TranslatedLine)
        {
            lineToDraw = ((TranslatedLine) line).getTranslateLine(partialTicks);
            
        }
        GL11.glPushMatrix();
        
        
        GL11.glLineWidth(line.getWidth());
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        if(!line.needsDepth())
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
        Color currentColor = line.getColor();
        GL11.glColor3d(currentColor.getRed(), currentColor.getGreen(), currentColor.getBlue());
        
        double xTranslation = GameHelper.getInstance().getRenderManager().renderPosX;
        double yTranslation = GameHelper.getInstance().getRenderManager().renderPosY;
        double zTranslation = GameHelper.getInstance().getRenderManager().renderPosZ;
        GL11.glBegin(GL11.GL_LINES);
        
        
        WayPoint pointOne = lineToDraw.getPointOne();
        WayPoint pointTwo = lineToDraw.getPointTwo();
        GL11.glVertex3d(pointOne.getX() - xTranslation, pointOne.getY() - yTranslation, pointOne.getZ() - zTranslation);
        GL11.glVertex3d(pointTwo.getX() - xTranslation, pointTwo.getY() - yTranslation, pointTwo.getZ() - zTranslation);
        GL11.glEnd();
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDepthMask(true);
        GL11.glPopMatrix();
    }
    
    @Override
    public String getInfoMessage()
    {
        return "Draws Line Based Objects";
    }
}
