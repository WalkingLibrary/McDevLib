package com.jumbodinosaurs.mcdevlib.OneDotTwelve.gui.radialmenu.objects;


import com.jumbodinosaurs.mcdevlib.OneDotTwelve.gui.radialmenu.RadialMenuHelper;
import com.jumbodinosaurs.mcdevlib.OneDotTwelve.gui.radialmenu.buttons.RadialButton;
import com.jumbodinosaurs.mcdevlib.OneDotTwelve.util.minecraft.GameHelper;
import com.jumbodinosaurs.mcdevlib.OneDotTwelve.util.objects.Point;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.text.ITextComponent;

import java.io.IOException;

public class RadialMenu extends GuiScreen
{
    private RadialButtonList buttons;
    private Circle2D subCircle;
    private Circle2D mainCircle;
    
    public RadialMenu(RadialButtonList buttons)
    {
        this.buttons = buttons;
        this.subCircle = new Circle2D(RadialMenuHelper.getSubRadius());
        this.mainCircle = new Circle2D(RadialMenuHelper.getMainRadius());
    }
    
    
    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        super.drawScreen(mouseX, mouseY, partialTicks);
        drawDefaultBackground();
        ScaledResolution resolution = new ScaledResolution(GameHelper.getInstance());
        int centerX = (resolution.getScaledWidth() / 2);
        int centerY = (resolution.getScaledHeight() / 2);
        Point centerPoint = new Point(centerX, centerY);
        Point mousePoint = new Point(mouseX, mouseY);
        
        
        for(int i = 0; i < buttons.getButtons().size(); i++)
        {
            RadialButton current = buttons.getButtons().get(i);
            current.draw(centerPoint);
            current.setCenterPoint(centerPoint);
            if(current.isInClickableSpace(mousePoint))
            {
                buttons.getButtons().get(i).onHover();
            }
            
        }
        
        subCircle.draw(centerPoint);
        mainCircle.draw(centerPoint);
    }
    
    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException
    {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        
        ScaledResolution resolution = new ScaledResolution(GameHelper.getInstance());
        int centerX = (resolution.getScaledWidth() / 2);
        int centerY = (resolution.getScaledHeight() / 2);
        Point centerPoint = new Point(centerX, centerY);
        Point mousePoint = new Point(mouseX, mouseY);
        for(int i = 0; i < buttons.getButtons().size(); i++)
        {
            RadialButton current = buttons.getButtons().get(i);
            current.setCenterPoint(centerPoint);
            current.onScreenClick(centerPoint, mouseButton);
        }
        
    }
    
    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException
    {
        super.keyTyped(typedChar, keyCode);
    }
    
    @Override
    protected void handleComponentHover(ITextComponent component, int x, int y)
    {
        super.handleComponentHover(component, x, y);
        
    }
    
    @Override
    protected void actionPerformed(GuiButton button) throws IOException
    {
        super.actionPerformed(button);
    }
    
    @Override
    public void onGuiClosed()
    {
        super.onGuiClosed();
    }
    
    @Override
    public boolean doesGuiPauseGame()
    {
        return false;
    }
    
    public RadialButtonList getButtons()
    {
        return buttons;
    }
}
