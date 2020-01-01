package com.jumbodinosaurs.mcdevlib.OneDotTwelve.gui.radialmenu.buttons.radial;


import com.jumbodinosaurs.mcdevlib.OneDotTwelve.gui.radialmenu.util.LabelCon;
import com.jumbodinosaurs.mcdevlib.OneDotTwelve.gui.util.TextDrawer;
import com.jumbodinosaurs.mcdevlib.OneDotTwelve.util.minecraft.GameHelper;
import com.jumbodinosaurs.mcdevlib.OneDotTwelve.util.objects.Point;
import net.minecraft.client.gui.Gui;
import org.lwjgl.opengl.GL11;

public class CenteredCircularIconButton extends CircularButton
{
    
    private int yOffSet;
    
    public CenteredCircularIconButton(LabelCon labelCon, int radius, int yOffSet)
    {
        super(labelCon, radius);
        this.yOffSet = yOffSet;
    }
    
    @Override
    public void updateCenterPoint(Point screenCenter)
    {
        int centerX = (int) screenCenter.getX();
        int centerY = (int) screenCenter.getY();
        centerY += this.yOffSet;
        setCenterPoint(new Point(centerX, centerY));
    }
    
    @Override
    public void draw(Point screenCenter)
    {
        super.draw(screenCenter);
        int centerX = (int) screenCenter.getX();
        int centerY = (int) screenCenter.getY();
        centerY += this.yOffSet;
        
        int iconDrawX, iconDrawY;
        int labelDrawX, labelDrawY;
        
        //In order to draw a Icon Centered You'll need To offset it by the width or height / 2
        int offsetAmount = (getDisplaySize() / 2);
        
        //Icon X and Y
        iconDrawX = centerX;
        iconDrawY = centerY;
        
        // Label X and Y
        labelDrawX = centerX;
        // To display the label below the Icon You'll need the add the width of the icon
        labelDrawY = centerY + offsetAmount;
        
        
        //offset icon for size
        iconDrawX -= offsetAmount;
        iconDrawY -= offsetAmount;
        
        
        GL11.glColor3d(255, 255, 255);
        try
        {
            //added the two so it avoids the icon
            TextDrawer.drawStringCentered(labelCon.getLabel(), labelDrawX, labelDrawY + 2, "faf3f2");
        }
        catch(NumberFormatException e)
        {
            //because saying exception is like saying lag in chat
            System.out.println("Exception");
        }
        
        
        if(labelCon.getResourceLocation() != null)
        {
            
            GL11.glColor3d(255, 255, 255);
            GameHelper.getInstance().getTextureManager().bindTexture(labelCon.getResourceLocation());
            Gui.drawModalRectWithCustomSizedTexture(iconDrawX,
                                                    iconDrawY,
                                                    0,
                                                    0,
                                                    getDisplaySize(),
                                                    getDisplaySize(),
                                                    getDisplaySize(),
                                                    getDisplaySize());
        }
    }
    
    
    
}
