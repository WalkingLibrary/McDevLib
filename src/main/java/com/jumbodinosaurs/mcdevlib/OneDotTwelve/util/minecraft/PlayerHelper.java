package com.jumbodinosaurs.mcdevlib.OneDotTwelve.util.minecraft;

import com.jumbodinosaurs.devlib.math.DevLibMathUtil;
import com.jumbodinosaurs.mcdevlib.OneDotTwelve.bot.pathfinding.util.MinecraftPoint3D;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.Vec3d;

public class PlayerHelper
{
    
    
    public static void setSprinting(boolean isSprinting)
    {
        getPlayer().setSprinting(true);
    }
    
    public static void waitForPlayerToStopFalling()
    {
        while(PlayerHelper.isPlayerFalling())
        {
            try
            {
                Thread.sleep(1);
            }
            catch(InterruptedException e)
            {
            
            }
        }
    }
    
    public static boolean isPlayerFalling()
    {
        //A Constant Motion of -0.0784000015258789 is applied to the player even when they are on a solid block
        return getMotionY() < -0.0784000015258789;
    }
    
    public static boolean isPlayerClimbing()
    {
        //A Constant Motion of -0.0784000015258789 is applied to the player even when they are on a solid block
        return getMotionY() > -0.0784000015258789;
    }
    
    public static void rightClick(MinecraftPoint3D point)
    {
    
        EnumActionResult enumactionresult = getPlayerController().processRightClickBlock(getPlayer(),
                                                                                         WorldHelper.getWorld(),
                                                                                         point.toBlockPos(),
                                                                                         getFacing(),
                                                                                         getLookVector(),
                                                                                         EnumHand.MAIN_HAND);
    
        if(enumactionresult == EnumActionResult.SUCCESS)
        {
            getPlayer().swingArm(EnumHand.MAIN_HAND);
        }
    }
    
    
    public static Vec3d getLookVector()
    {
        float partialTicks = Minecraft.getMinecraft().getRenderPartialTicks();
        return getPlayer().rayTrace(getPlayerController().getBlockReachDistance(), partialTicks).hitVec;
    }
    
    public static EnumFacing getFacing()
    {
        float partialTicks = Minecraft.getMinecraft().getRenderPartialTicks();
        return getPlayer().rayTrace(getPlayerController().getBlockReachDistance(), partialTicks).sideHit;
    }
    
    
    public static double getRoundedX()
    {
        return DevLibMathUtil.roundAvoid(getPlayer().posX, 4);
    }
    
    public static double getRoundedY()
    {
        return DevLibMathUtil.roundAvoid(getPlayer().posY, 4);
    }
    
    public static double getRoundedZ()
    {
        return DevLibMathUtil.roundAvoid(getPlayer().posZ, 4);
    }
    
    public static double getMotionX()
    {
        return getPlayer().motionX;
    }
    
    public static void setMotionX(double newMotion)
    {
        getPlayer().motionX = newMotion;
    }
    
    public static double getMotionZ()
    {
        return getPlayer().motionZ;
    }
    
    public static void setMotionZ(double newMotion)
    {
        getPlayer().motionZ = newMotion;
    }
    
    public static double getMotionY()
    {
        return getPlayer().motionY;
    }
    
    public static double getX()
    {
        return getPlayer().posX;
    }
    
    public static double getY()
    {
        return getPlayer().posY;
    }
    
    public static double getZ()
    {
        return getPlayer().posZ;
    }
    
    
    public static MinecraftPoint3D getPlayerPositionAsMinecraftPoint3DSolidY()
    {
        return new MinecraftPoint3D(getX(), DevLibMathUtil.round(getY()), getZ());
    }
    
    public static MinecraftPoint3D getPlayerPositionAsMinecraftPoint3DROUNDED()
    {
        return new MinecraftPoint3D(getRoundedX(), getRoundedY(), getRoundedZ());
    }
    
    public static MinecraftPoint3D getPlayerPositionAsMinecraftPoint3D()
    {
        return new MinecraftPoint3D(getX(), getY(), getZ());
    }
    
    
    public static boolean safeToCheck()
    {
        return Minecraft.getMinecraft() != null && Minecraft.getMinecraft().player != null;
    }
    
    public static int getHealth()
    {
        return (int) getPlayer().getHealth();
    }
    
    public static String getCoordinates()
    {
        String coords = "";
        coords += "X:" + ((int) getPlayer().posX) + " ";
        coords += "Y:" + ((int) getPlayer().posY) + " ";
        coords += "Z:" + ((int) getPlayer().posZ);
        return coords;
    }
    
    public static EntityPlayerSP getPlayer()
    {
        return GameHelper.getInstance().player;
    }
    
    public static Entity getPlayersRidingEntity()
    {
        return getPlayer().getRidingEntity();
    }
    
    public static PlayerControllerMP getPlayerController()
    {
        return GameHelper.getInstance().playerController;
    }
    
    
}
