package com.jumbodinosaurs.mcdevlib.OneDotTwelve.gui.radialmenu.buttons.radial;


import com.jumbodinosaurs.devlib.util.objects.Point2D;
import com.jumbodinosaurs.mcdevlib.OneDotTwelve.gui.radialmenu.util.LabelCon;
import com.jumbodinosaurs.mcdevlib.OneDotTwelve.gui.util.TextDrawer;
import com.jumbodinosaurs.mcdevlib.OneDotTwelve.util.minecraft.GameHelper;
import net.minecraft.client.gui.Gui;
import org.lwjgl.opengl.GL11;

public class CenteredCircularIconButton extends CircularButton
{
    
    private int zOffSet;
    
    public CenteredCircularIconButton(LabelCon labelCon, int radius, int zOffSet)
    {
        super(labelCon, radius);
        this.zOffSet = zOffSet;
    }
    
    @Override
    public void updateCenterPoint(Point2D screenCenter)
    {
        int centerX = (int) screenCenter.getX();
        int centerZ = (int) screenCenter.getZ();
        centerZ += this.zOffSet;
        setCenterPoint(new Point2D(centerX, centerZ));
    }
    
    @Override
    public void draw(Point2D screenCenter)
    {
        super.draw(screenCenter);
        int centerX = (int) screenCenter.getX();
        int centerZ = (int) screenCenter.getZ();
        centerZ += this.zOffSet;
        
        int iconDrawX, iconDrawZ;
        int labelDrawX, labelDrawZ;
        
        //In order to draw a Icon Centered You'll need To offset it by the width or height / 2
        int offsetAmount = (getDisplaySize() / 2);
        
        //Icon X and z
        iconDrawX = centerX;
        iconDrawZ = centerZ;
        
        // Label X and z
        labelDrawX = centerX;
        // To display the label below the Icon You'll need the add the width of the icon
        labelDrawZ = centerZ + offsetAmount;
        
        
        //offset icon for size
        iconDrawX -= offsetAmount;
        iconDrawZ -= offsetAmount;
        
        
        GL11.glColor3d(255, 255, 255);
        try
        {
            //added the two so it avoids the icon
            TextDrawer.drawStringCentered(labelCon.getLabel(), labelDrawX, labelDrawZ + 2, "faf3f2");
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
                                                    iconDrawZ,
                                                    0,
                                                    0,
                                                    getDisplaySize(),
                                                    getDisplaySize(),
                                                    getDisplaySize(),
                                                    getDisplaySize());
        }
    }
    
    
    
}
