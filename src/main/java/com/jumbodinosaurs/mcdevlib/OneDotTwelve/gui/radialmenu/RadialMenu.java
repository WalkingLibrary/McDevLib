package com.jumbodinosaurs.mcdevlib.OneDotTwelve.gui.radialmenu;




import com.jumbodinosaurs.mcdevlib.OneDotTwelve.gui.radialmenu.buttons.radial.InformativeRadialButton;
import com.jumbodinosaurs.mcdevlib.OneDotTwelve.gui.radialmenu.buttons.radial.RadialButton;
import com.jumbodinosaurs.mcdevlib.OneDotTwelve.gui.radialmenu.util.Circle2D;
import com.jumbodinosaurs.mcdevlib.OneDotTwelve.gui.radialmenu.util.RadialButtonList;
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
        this.subCircle = new Circle2D(RadialMenuUtil.getSubRadius());
        this.mainCircle = new Circle2D(RadialMenuUtil.getMainRadius());
    }
    
 
    
    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        super.drawScreen(mouseX, mouseY, partialTicks);
        drawDefaultBackground();
        ScaledResolution resolution = new ScaledResolution(GameHelper.getInstance());
        double centerX = (resolution.getScaledWidth() / 2.0);
        double centerY = (resolution.getScaledHeight() / 2.0);
        Point centerPoint = new Point(centerX, centerY);
        Point mousePoint = new Point(mouseX, mouseY);
        subCircle.draw(centerPoint);
        mainCircle.draw(centerPoint);
    
        for(int i = 0 ; i < buttons.getButtons().size(); i++)
        {
            RadialButton current = (RadialButton) buttons.getButtons().get(i);
            current.draw(centerPoint);
        }
        for(int i = 0 ; i < buttons.getButtons().size(); i++)
        {
            RadialButton current = (RadialButton) buttons.getButtons().get(i);
            current.updateCenterPoint(centerPoint);
            if(current.isInClickableSpace(mousePoint))
            {
                
                current.onHover();
                if(current instanceof InformativeRadialButton)
                {
                    this.drawHoveringText(((InformativeRadialButton) current).getToolTip(), mouseX, mouseY);
                }
            }
            
        }
    
        
    }
    
    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException
    {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        
        ScaledResolution resolution = new ScaledResolution(GameHelper.getInstance());
        double centerX = (resolution.getScaledWidth() / 2.0);
        double centerY = (resolution.getScaledHeight() / 2.0);
        Point centerPoint = new Point(centerX, centerY);
        Point mousePoint = new Point(mouseX, mouseY);
        for(int i = 0 ; i < buttons.getButtons().size(); i++)
        {
            RadialButton current = (RadialButton) buttons.getButtons().get(i);
            current.onScreenClick(mousePoint, mouseButton);
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
