package com.jumbodinosaurs.mcdevlib.OneDotTwelve.gui.radialmenu;


import com.jumbodinosaurs.devlib.util.objects.Point2D;
import com.jumbodinosaurs.mcdevlib.OneDotTwelve.gui.radialmenu.buttons.Button;
import com.jumbodinosaurs.mcdevlib.OneDotTwelve.gui.radialmenu.buttons.radial.CenteredTextField;
import com.jumbodinosaurs.mcdevlib.OneDotTwelve.gui.radialmenu.interfaces.IInteractive;
import com.jumbodinosaurs.mcdevlib.OneDotTwelve.gui.radialmenu.util.Circle2D;
import com.jumbodinosaurs.mcdevlib.OneDotTwelve.util.minecraft.GameHelper;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.text.ITextComponent;

import java.io.IOException;
import java.util.ArrayList;

public class InformationInputMenu extends GuiScreen
{
    private ArrayList<Button> buttons;
    private Circle2D mainCircle;
    
    
    public InformationInputMenu(ArrayList<Button> buttons)
    {
        this.buttons = buttons;
        this.mainCircle = new Circle2D(RadialMenuUtil.getMainRadius());
    }
    
    
    @Override
    public void drawScreen(int mouseX, int mouseZ, float partialTicks)
    {
        super.drawScreen(mouseX, mouseZ, partialTicks);
        drawDefaultBackground();
        ScaledResolution resolution = new ScaledResolution(GameHelper.getInstance());
        int centerX = (resolution.getScaledWidth() / 2);
        int centerZ = (resolution.getScaledHeight() / 2);
        Point2D centerPoint = new Point2D(centerX, centerZ);
        Point2D mousePoint = new Point2D(mouseX, mouseZ);
        
        
        for(int i = 0; i < this.buttons.size(); i++)
        {
            Button current = this.buttons.get(i);
            current.draw(centerPoint);
            if(current.isInClickableSpace(mousePoint))
            {
                if(current instanceof IInteractive)
                {
                    ((IInteractive)this.buttons.get(i)).onHover();
                }
            }
            
        }
        this.mainCircle.draw(centerPoint);
    }
    
    @Override
    protected void mouseClicked(int mouseX, int mouseZ, int mouseButton)
            throws IOException
    {
        super.mouseClicked(mouseX, mouseZ, mouseButton);
        
        ScaledResolution resolution = new ScaledResolution(GameHelper.getInstance());
        int centerX = (resolution.getScaledWidth() / 2);
        int centerZ = (resolution.getScaledHeight() / 2);
        Point2D mousePoint = new Point2D(mouseX, mouseZ);
        for(int i = 0; i < this.buttons.size(); i++)
        {
            Button current = this.buttons.get(i);
            current.onScreenClick(mousePoint, mouseButton);
        }
        
    }
    
    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException
    {
        super.keyTyped(typedChar, keyCode);
        for(int i = 0 ; i < this.buttons.size(); i++)
        {
            Button current = this.buttons.get(i);
            if(current instanceof CenteredTextField)
            {
                ((CenteredTextField) current).getTextField().textboxKeyTyped(typedChar, keyCode);
            }
        }
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
    
    public ArrayList<Button> getButtons()
    {
        return buttons;
    }
    
    @Override
    public void updateScreen()
    {
        super.updateScreen();
        for(int i = 0 ; i < this.buttons.size(); i++)
        {
            Button current = this.buttons.get(i);
            if(current instanceof CenteredTextField)
            {
                ((CenteredTextField) current).getTextField().updateCursorCounter();
            }
        }
    }
}