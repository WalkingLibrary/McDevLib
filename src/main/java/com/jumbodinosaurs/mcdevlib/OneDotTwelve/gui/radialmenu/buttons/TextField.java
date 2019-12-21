package com.jumbodinosaurs.mcdevlib.OneDotTwelve.gui.radialmenu.buttons;

import com.jumbodinosaurs.mcdevlib.OneDotTwelve.gui.radialmenu.TextDrawer;
import com.jumbodinosaurs.mcdevlib.OneDotTwelve.gui.radialmenu.objects.LabelCon;
import com.jumbodinosaurs.mcdevlib.OneDotTwelve.util.minecraft.GameHelper;
import com.jumbodinosaurs.mcdevlib.OneDotTwelve.util.objects.Point;
import net.minecraft.client.gui.GuiTextField;

public class TextField extends IconButton
{
    private GuiTextField textField;
    private int yOffSet;
    
    public TextField(LabelCon labelCon, int yOffSet)
    {
        super(labelCon);
        this.textField = new GuiTextField(0,
                                          GameHelper.getFontRender(),
                                          0,
                                          0,
                                          ButtonUtil.getDefaultButtonWidth(),
                                          ButtonUtil.getDefaultButtonHeight());
        this.textField.setMaxStringLength(40);
        this.textField.setText("");
        this.textField.setFocused(false);
    }
    
    @Override
    public void onClick(int btn)
    {
        this.labelCon.getAction().onClick();
    }
    
    
    @Override
    public boolean isInClickableSpace(Point mouseLocation)
    {
        return false;
    }
    
    @Override
    public void onScreenClick(Point mouseLocation, int btn)
    {
        super.onScreenClick(mouseLocation, btn);
        this.textField.mouseClicked((int) mouseLocation.getX(), (int) mouseLocation.getY(), btn);
    }
    
    @Override
    public void draw(Point screenCenter)
    {
        this.textField.x = (int) screenCenter.getX();
        this.textField.y = (int) screenCenter.getY() + yOffSet;
        this.textField.drawTextBox();
        
        try
        {
            //added the two so it avoids the icon
            TextDrawer.drawStringXEndPoint(labelCon.getLabel(),
                                           (int) screenCenter.getX(),
                                           (int) screenCenter.getY() + yOffSet,
                                           "faf3f2");
        }
        catch(NumberFormatException e)
        {
            e.printStackTrace();
        }
    }
    
    public GuiTextField getTextField()
    {
        return textField;
    }
}
