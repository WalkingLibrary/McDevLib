package com.jumbodinosaurs.mcdevlib.OneDotTwelve.bot.util;

import com.jumbodinosaurs.mcdevlib.OneDotTwelve.util.minecraft.PlayerHelper;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class MovementHelper
{
    
    //MAX Motion is .33
    //Difference between current motion and max motion /2
    //Have a target motion and the motion to apply is dependent on
    //the current motion the difference between the target motion and current motion
    
    public static final double maxJumpHeight = 1.2522033402537236D;
    //Momentum manager?
    private final static double maxWalkingMotion = 0.15775378809719966;
    //.11
    //.20
    private final static double maxSprintMotion = 0.15320929721230958;
    private final static double maxJumpMotion = 0.30320929721230958;
    private final static double maxSprintJumpDistance = 3.2864;
    private final static double maxWalkingJumpDistance = 2.5067;
    
    
    public static int ticksSinceJump = 0;
    public static int maxUsefulJumpTicks = 110;
    //.15
    //.23
    
    public static double getMotionDifference(double axisDifference)
    {
        return Math.abs(axisDifference) * .206135;
    }
    
    public static double getDifference(double motion)
    {
        return Math.abs(motion) / .206135;
    }
    
    public static double getMaxWalkingMotion()
    {
        return maxWalkingMotion;
    }
    
    public static double getMaxSprintMotion()
    {
        return maxSprintMotion;
    }
    
    public static double getNewMotion(double targetMotion, double currentMotion)
    {
        double motionDifference = targetMotion - currentMotion;
        if(motionDifference < .008)
        {
            return targetMotion;
        }
        else
        {
            return (currentMotion + (motionDifference / 3)) + .000001;
        }
    }
    
    public static double getMaxJumpMotion(boolean sprint)
    {
        
        if(sprint)
        {
            if(ticksSinceJump > maxUsefulJumpTicks)
            {
                return getMaxSprintMotion();
            }
            else
            {
                double slope = -0.003088533 / maxUsefulJumpTicks;
                double yIntercept = maxJumpMotion;
                return ((ticksSinceJump / 5) * slope) + yIntercept;
            }
        }
        else
        {
            if(ticksSinceJump > maxUsefulJumpTicks)
            {
                return getMaxWalkingMotion();
            }
            else
            {
                double slope = 0.05185115 / maxUsefulJumpTicks;
                double yIntercept = getMaxWalkingMotion();
                return ((ticksSinceJump / 10) * slope) + yIntercept;
            }
        }
    }
    
    
    public static double getMaxJumpDistance(boolean sprint)
    {
        if(sprint)
        {
            return maxSprintJumpDistance;
        }
        return maxWalkingJumpDistance;
    }
    
    
    @SubscribeEvent
    public synchronized void onJump(LivingEvent.LivingJumpEvent event)
    {
        
        if(event.getEntity().equals(PlayerHelper.getPlayer()))
        {
            ticksSinceJump = 0;
        }
    }
    
    @SubscribeEvent
    public synchronized void onTick(TickEvent event)
    {
        if(ticksSinceJump <= maxUsefulJumpTicks)
        {
            MovementHelper.ticksSinceJump++;
        }
    }
}
