package com.jumbodinosaurs.mcdevlib.OneDotTwelve.gui.radialmenu.buttons.radial;


import com.jumbodinosaurs.mcdevlib.OneDotTwelve.gui.radialmenu.buttons.Button;
import com.jumbodinosaurs.mcdevlib.OneDotTwelve.gui.radialmenu.interfaces.IInteractive;
import com.jumbodinosaurs.mcdevlib.OneDotTwelve.gui.radialmenu.util.LabelCon;
import com.jumbodinosaurs.mcdevlib.OneDotTwelve.util.objects.Point;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;

public abstract class IconButton extends Button implements IInteractive
{
    protected LabelCon labelCon;
    private int baseSize;
    private int displaySize;
    private boolean shouldGrow;
    private int maxSize;
    
    public IconButton(LabelCon labelCon, int baseSize)
    {
        this.labelCon = labelCon;
        this.baseSize = baseSize;
        this.maxSize = baseSize + 4;
    }
    
    
    
    @Override
    public void draw(Point screenCenter)
    {
        if(shouldGrow() && getDisplaySize() < getMaxSize())
        {
            setDisplaySize(getDisplaySize() + 1);
        }
        else if(!shouldGrow())
        {
            setDisplaySize(getBaseSize());
    
        }
        setShouldGrow(false);
    }
    
    public void drawIcon(double x, double y, float u, float v, double width, double height, float textureWidth,
                         float textureHeight)
    {
        float f = 1.0F / textureWidth;
        float f1 = 1.0F / textureHeight;
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
        bufferbuilder.pos(x, (y + height), 0.0D).tex((double)(u * f), (double)((v + (float)height) * f1)).endVertex();
        bufferbuilder.pos((x + width), (y + height), 0.0D).tex((double)((u + (float)width) * f), (double)((v + (float)height) * f1)).endVertex();
        bufferbuilder.pos((x + width), y, 0.0D).tex((double)((u + (float)width) * f), (double)(v * f1)).endVertex();
        bufferbuilder.pos(x, y, 0.0D).tex((double)(u * f), (double)(v * f1)).endVertex();
        tessellator.draw();
    }
    
    @Override
    public int getBaseSize()
    {
        return this.baseSize;
    }
    
    @Override
    public void setBaseSize(int baseSize)
    {
        this.baseSize = baseSize;
    }
    
    @Override
    public int getDisplaySize()
    {
        return this.displaySize;
    }
    
    @Override
    public void setDisplaySize(int displaySize)
    {
        this.displaySize = displaySize;
    }
    
    @Override
    public boolean shouldGrow()
    {
        return this.shouldGrow;
    }
    
    @Override
    public void setShouldGrow(boolean shouldGrow)
    {
        this.shouldGrow = shouldGrow;
    }
    
    @Override
    public int getMaxSize()
    {
        return this.maxSize;
    }
    
    @Override
    public void setMaxSize(int maxSize)
    {
        this.maxSize = maxSize;
    }
    
    @Override
    public void onClick(int btn)
    {
        this.labelCon.getAction().onClick();
    }
    
    public LabelCon getLabelCon()
    {
        return labelCon;
    }
    
    public void setLabelCon(LabelCon labelCon)
    {
        this.labelCon = labelCon;
    }
}
