package com.jumbodinosaurs.mcdevlib.OneDotTwelve.gui.radialmenu.buttons.radial;


import com.jumbodinosaurs.devlib.util.objects.Point2D;
import com.jumbodinosaurs.mcdevlib.OneDotTwelve.gui.radialmenu.RadialMenuUtil;
import com.jumbodinosaurs.mcdevlib.OneDotTwelve.gui.radialmenu.interfaces.IClickableRadial;
import com.jumbodinosaurs.mcdevlib.OneDotTwelve.gui.radialmenu.util.LabelCon;
import com.jumbodinosaurs.mcdevlib.OneDotTwelve.gui.util.TextDrawer;
import com.jumbodinosaurs.mcdevlib.OneDotTwelve.util.minecraft.GameHelper;
import org.lwjgl.opengl.GL11;

public class RadialButton extends CircularButton implements IClickableRadial
{
    protected int listSize;
    protected int listPosition;
    protected int subCircleRadius;
    protected int circleRadius;
    protected boolean drawCentered;
    
    public RadialButton(boolean drawCentered, LabelCon labelCon, int baseSize)
    {
        super(labelCon, baseSize);
        this.drawCentered = drawCentered;
        this.subCircleRadius = RadialMenuUtil.getSubRadius();
        this.circleRadius = RadialMenuUtil.getMainRadius();
        this.listSize = 1;
    }
    
    
    @Override
    public void updateCenterPoint(Point2D screenCenter)
    {
        setCenterPoint(screenCenter);
    }
    
    @Override
    public boolean isInClickableSpace(Point2D mouseLocation)
    {
        double distanceDifference = getCenterPoint().getEuclideanDistance(mouseLocation);
        if(drawCentered)
        {
            return !(distanceDifference > subCircleRadius);
        }
        
        
        if(distanceDifference > circleRadius || distanceDifference < subCircleRadius)
        {
            return false;
        }
        
        if(distanceDifference == 0)
        {
            return false;
        }
        
        double zTranslation = getCenterPoint().getZ();
        double clickedPointsX = mouseLocation.getX();
        double clickedPointsZ = mouseLocation.getZ();
        double radius = distanceDifference;
        double revertToDegreesAmount = 180 / Math.PI;
        double pointsDegree = (Math.asin((clickedPointsZ - zTranslation) / radius) * revertToDegreesAmount);
        
        if(clickedPointsX >= getCenterPoint().getX())
        {
            pointsDegree += 90;
        }
        else if(clickedPointsX < getCenterPoint().getX())
        {
            pointsDegree = -pointsDegree;
            pointsDegree += 270;
        }
        return getFirstDegree() <= pointsDegree && pointsDegree < getSecondDegree();
    }
    
    public int getFirstDegree()
    {
        if(listSize == 0)
        {
            listSize = 1;
        }
        int spliceAmount = 360 / listSize;
        return spliceAmount * (listPosition);
    }
    
    public int getSecondDegree()
    {
        if(listSize == 0)
        {
            listSize = 1;
        }
        int spliceAmount = 360 / listSize;
        return spliceAmount * (listPosition + 1);
    }
    
    
    @Override
    public void draw(Point2D screenCenter)
    {
        super.draw(screenCenter);
        int centerX = (int) screenCenter.getX();
        int centerZ = (int) screenCenter.getZ();
        
        int iconDrawX, iconDrawY;
        int labelDrawX, labelDrawY;
        int offsetAmount = (getDisplaySize() / 2);
        if(drawCentered)
        {
            
            iconDrawX = centerX;
            iconDrawY = centerZ;
            
            labelDrawX = iconDrawX;
            //offset for icon size
            labelDrawY = iconDrawY + offsetAmount;
            
            //offset icon for size
            iconDrawX -= offsetAmount;
            iconDrawY -= offsetAmount;
        }
        else
        {
            
            int drawDegree = (getSecondDegree()) - (Math.abs(getSecondDegree() - getFirstDegree()) / 2);
            drawDegree += 270;
            int drawRadius = (circleRadius - subCircleRadius) - 5;
            double radiansConversion = Math.PI / 180;
            /*
             *   lastX = (radius * Math.cos(radium)) + x;
             *   latsY = (radius * Math.sin(radium)) + y;
             * */
            iconDrawX = (int) (drawRadius * Math.cos(drawDegree * radiansConversion)) + centerX;
            iconDrawY = (int) (drawRadius * Math.sin(drawDegree * radiansConversion)) + centerZ;
            
            labelDrawX = iconDrawX;
            
            //offset for icon size
            labelDrawY = iconDrawY + offsetAmount;
            
            //offset icon for size
            
            iconDrawX -= offsetAmount;
            iconDrawY -= offsetAmount;
            
            
            drawFirstDegreeLine(screenCenter);
            GL11.glColor3d(255, 255, 255);
            try
            {
                //added the two so it avoids the icon
                TextDrawer.drawStringCentered(labelCon.getLabel(), labelDrawX, labelDrawY + 2, "faf3f2");
            }
            catch(NumberFormatException e)
            {
                System.out.println("Exception");
            }
            
        }
        
        
        if(labelCon.getResourceLocation() != null)
        {
            
            GL11.glColor3d(255, 255, 255);
            GameHelper.getInstance().getTextureManager().bindTexture(labelCon.getResourceLocation());
            drawIcon(iconDrawX + .5,
                                                    iconDrawY,
                                                    0,
                                                    0,
                                                    getDisplaySize(),
                                                    getDisplaySize(),
                                                    getDisplaySize(),
                                                    getDisplaySize());
        }
        //868a38
        //faf3f2
        
        
        //~setValue RenderActiveRetentions color ffaaff
        
    }
    
    public void drawFirstDegreeLine(Point2D center)
    {
        /* Drawing the First Degree Line
         * Get Two Points To Draw The Line
         * Use GL11 To Draw The Line
         *
         * */
        
        
        //Get Two Points To Draw The Line
        /*
         * MATH:
         * Cos(An Angle) * radius = XPoint on the Circle
         * Sin(An Angle) * radius = YPoint on the Circle
         * To give a gap between the Inner And Outer Circle We take and add removedAmount to the radius
         * We also Translate The Circle based off of our Center x and y
         * We add 270 to the first degree so that the Line starts at the top for degree 0
         *  */
        double innerX, innerZ, outerX, outerZ;
        double radiansConversion = Math.PI / 180;
        int removedAmount = 3;
        innerX = (((subCircleRadius + removedAmount) * Math.cos((getFirstDegree() + 270) * radiansConversion)) +
                  center.getX());
        innerZ = (((subCircleRadius + removedAmount) * Math.sin((getFirstDegree() + 270) * radiansConversion)) +
                  center.getZ());
        outerX = (((circleRadius - removedAmount) * Math.cos((getFirstDegree() + 270) * radiansConversion)) +
                  center.getX());
        outerZ = (((circleRadius - removedAmount) * Math.sin((getFirstDegree() + 270) * radiansConversion)) +
                  center.getZ());
        
        
        //Use GL11 To Draw The Line
        GL11.glPushMatrix();
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDepthMask(true);
        GL11.glLineWidth(100);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBegin(GL11.GL_LINES);
        GL11.glColor3d(0, 0, 0);
        GL11.glVertex2d(innerX, innerZ);
        GL11.glVertex2d(outerX, outerZ);
        GL11.glEnd();
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDepthMask(false);
        GL11.glPopMatrix();
        
    }
    
    
    
    public int getListSize()
    {
        return listSize;
    }
    
    public void setListSize(int listSize)
    {
        this.listSize = listSize;
    }
    
    public int getListPosition()
    {
        return listPosition;
    }
    
    public void setListPosition(int listPosition)
    {
        this.listPosition = listPosition;
    }
    
}
