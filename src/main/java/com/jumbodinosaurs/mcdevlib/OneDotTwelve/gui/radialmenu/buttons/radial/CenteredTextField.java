package com.jumbodinosaurs.mcdevlib.OneDotTwelve.gui.radialmenu.buttons.radial;

import com.jumbodinosaurs.devlib.util.objects.Point2D;
import com.jumbodinosaurs.mcdevlib.OneDotTwelve.gui.radialmenu.buttons.Button;
import com.jumbodinosaurs.mcdevlib.OneDotTwelve.gui.radialmenu.buttons.ButtonUtil;
import com.jumbodinosaurs.mcdevlib.OneDotTwelve.gui.radialmenu.util.Label;
import com.jumbodinosaurs.mcdevlib.OneDotTwelve.gui.util.TextDrawer;
import com.jumbodinosaurs.mcdevlib.OneDotTwelve.util.minecraft.GameHelper;
import net.minecraft.client.gui.GuiTextField;

public class CenteredTextField extends Button
{
    private Label label;
    private GuiTextField textField;
    private int zOffSet;
    
    public CenteredTextField(Label label, int zOffSet)
    {
        this.label = label;
        this.textField = new GuiTextField(0,
                                          GameHelper.getFontRender(),
                                          0,
                                          0,
                                          ButtonUtil.getDefaultButtonWidth(),
                                          ButtonUtil.getDefaultButtonHeight());
        this.textField.setMaxStringLength(40);
        this.textField.setText("");
        this.textField.setFocused(false);
        this.zOffSet = zOffSet;
    }
    
    @Override
    public void onClick(int btn)
    {
        //
    }
    
    
    @Override
    public boolean isInClickableSpace(Point2D mouseLocation)
    {
        return false;
    }
    
    @Override
    public void onScreenClick(Point2D mouseLocation, int btn)
    {
        super.onScreenClick(mouseLocation, btn);
        this.textField.mouseClicked((int) mouseLocation.getX(), (int) mouseLocation.getZ(), btn);
    }
    
    @Override
    public void draw(Point2D screenCenter)
    {
        
        int centerX = (int) screenCenter.getX();
        int centerZ = (int) screenCenter.getZ();
        int labelDrawX, labelDrawZ;
        
        //In order to draw a Icon Centered You'll need To offset it by the width or height / 2
        
        int xOffSetAmount = (this.textField.getWidth() / 2);
        this.textField.x = centerX - xOffSetAmount;
        this.textField.y = centerZ + this.zOffSet;
        this.textField.drawTextBox();
        
        labelDrawX = centerX - xOffSetAmount;
        labelDrawZ = centerZ + this.zOffSet;
    
        try
        {
            //added the two so it avoids the icon
            TextDrawer.drawStringXEndPoint(this.label.getLabel(), labelDrawX, labelDrawZ
                    , "faf3f2");
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
