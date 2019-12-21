package com.jumbodinosaurs.mcdevlib.OneDotTwelve.gui.radialmenu.objects;


import com.jumbodinosaurs.mcdevlib.OneDotTwelve.gui.radialmenu.RadialMenuHelper;
import com.jumbodinosaurs.mcdevlib.OneDotTwelve.gui.radialmenu.buttons.Button;
import com.jumbodinosaurs.mcdevlib.OneDotTwelve.gui.radialmenu.buttons.TextField;
import com.jumbodinosaurs.mcdevlib.OneDotTwelve.util.minecraft.GameHelper;
import com.jumbodinosaurs.mcdevlib.OneDotTwelve.util.objects.Point;
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
        
        
        for(int i = 0; i < this.buttons.size(); i++)
        {
            Button current = this.buttons.get(i);
            current.draw(centerPoint);
            if(current.isInClickableSpace(mousePoint))
            {
                this.buttons.get(i).onHover();
            }
            
        }
        this.mainCircle.draw(centerPoint);
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
        for(int i = 0; i < this.buttons.size(); i++)
        {
            Button current = this.buttons.get(i);
            current.onScreenClick(centerPoint, mouseButton);
        }
        
    }
    
    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException
    {
        super.keyTyped(typedChar, keyCode);
        for(int i = 0; i < this.buttons.size(); i++)
        {
            Button current = this.buttons.get(i);
            if(current instanceof TextField)
            {
                ((TextField) current).getTextField().textboxKeyTyped(typedChar, keyCode);
            }
        }
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
    
    public ArrayList<Button> getButtons()
    {
        return buttons;
    }
    
    @Override
    public void updateScreen()
    {
        super.updateScreen();
        for(int i = 0; i < this.buttons.size(); i++)
        {
            Button current = this.buttons.get(i);
            if(current instanceof TextField)
            {
                ((TextField) current).getTextField().updateCursorCounter();
            }
        }
    }
}