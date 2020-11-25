package com.jumbodinosaurs.mcdevlib.OneDotTwelve.gui.radialmenu;




import com.jumbodinosaurs.devlib.util.objects.Point2D;
import com.jumbodinosaurs.mcdevlib.OneDotTwelve.gui.radialmenu.buttons.radial.InformativeRadialButton;
import com.jumbodinosaurs.mcdevlib.OneDotTwelve.gui.radialmenu.buttons.radial.RadialButton;
import com.jumbodinosaurs.mcdevlib.OneDotTwelve.gui.radialmenu.util.Circle2D;
import com.jumbodinosaurs.mcdevlib.OneDotTwelve.gui.radialmenu.util.RadialButtonList;
import com.jumbodinosaurs.mcdevlib.OneDotTwelve.util.minecraft.GameHelper;
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
        this.subCircle = new Circle2D(RadialMenuUtil.getSubRadius());
        this.mainCircle = new Circle2D(RadialMenuUtil.getMainRadius());
    }
    
    
    @Override
    public void drawScreen(int mouseX, int mouseZ, float partialTicks)
    {
        super.drawScreen(mouseX, mouseZ, partialTicks);
        drawDefaultBackground();
        ScaledResolution resolution = new ScaledResolution(GameHelper.getInstance());
        double centerX = (resolution.getScaledWidth() / 2.0);
        double centerZ = (resolution.getScaledHeight() / 2.0);
        Point2D centerPoint = new Point2D(centerX, centerZ);
        Point2D mousePoint = new Point2D(mouseX, mouseZ);
        subCircle.draw(centerPoint);
        mainCircle.draw(centerPoint);
        
        for(int i = 0; i < buttons.getButtons().size(); i++)
        {
            RadialButton current = buttons.getButtons().get(i);
            current.draw(centerPoint);
        }
        for(int i = 0; i < buttons.getButtons().size(); i++)
        {
            RadialButton current = buttons.getButtons().get(i);
            current.updateCenterPoint(centerPoint);
            if(current.isInClickableSpace(mousePoint))
            {
                
                current.onHover();
                if(current instanceof InformativeRadialButton)
                {
                    this.drawHoveringText(((InformativeRadialButton) current).getToolTip(), mouseX, mouseZ);
                }
            }
            
        }
    
        
    }
    
    @Override
    protected void mouseClicked(int mouseX, int mouseZ, int mouseButton)
            throws IOException
    {
        super.mouseClicked(mouseX, mouseZ, mouseButton);
        
        ScaledResolution resolution = new ScaledResolution(GameHelper.getInstance());
        double centerX = (resolution.getScaledWidth() / 2.0);
        double centerZ = (resolution.getScaledHeight() / 2.0);
        Point2D centerPoint = new Point2D(centerX, centerZ);
        Point2D mousePoint = new Point2D(mouseX, mouseZ);
        for(int i = 0; i < buttons.getButtons().size(); i++)
        {
            RadialButton current = buttons.getButtons().get(i);
            current.onScreenClick(mousePoint, mouseButton);
        }
        
    }
    
    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException
    {
        super.keyTyped(typedChar, keyCode);
    }
    
    @Override
    protected void handleComponentHover(ITextComponent component, int x, int z)
    {
        super.handleComponentHover(component, x, z);
        
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
