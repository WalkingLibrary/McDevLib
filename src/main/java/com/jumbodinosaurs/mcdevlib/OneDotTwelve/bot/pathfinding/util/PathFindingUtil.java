package com.jumbodinosaurs.mcdevlib.OneDotTwelve.bot.pathfinding.util;


import net.minecraft.util.math.BlockPos;

public class PathFindingUtil
{
    
    
    //returns the direction from point one to point two.
    public static Direction getDirectionDifference(MinecraftPoint3D pointOne, MinecraftPoint3D pointTwo)
    {
        double z = pointOne.getZ() - pointTwo.getZ();
        double x = pointOne.getX() - pointTwo.getX();
    
        if(z != 0)
        {
            if(z != Math.abs(z))
            {
                z = -1;
            }
            else
            {
                z = 1;
            }
        }
        
        
        if(x != 0)
        {
            
            if(x != Math.abs(x))
            {
                x = -1;
            }
            else
            {
                x = 1;
            }
        }
        
        //Invert it because we are subtracting the first point from the second point
        //but really we want the direction from point one to point two.
        x = x * -1;
        z = z * -1;
        
        for(Direction direction : Direction.values())
        {
            if(direction.x == ((int) x) && direction.z == ((int) z))
            {
                return direction;
            }
        }
        return null;
    }
    
    
    public static MinecraftPoint3D getDirectionalEdgeWayPoint(MinecraftPoint3D wayPoint,
                                                              Direction direction,
                                                              double middleOffset)
    {
        MinecraftPoint3D pointToReturn;
        double x, z;
        
        if(wayPoint.getX() == 0)
        {
            x = (.5 + Math.abs(middleOffset)) * direction.x;
        }
        else
        {
            x = getDirectionalEdgePoint(wayPoint.getX(), direction.x, middleOffset);
        }
        
        if(wayPoint.getZ() == 0)
        {
            z = (.5 + Math.abs(middleOffset)) * direction.z;
        }
        else
        {
            z = getDirectionalEdgePoint(wayPoint.getZ(), direction.z, middleOffset);
        }
        
        
        return new MinecraftPoint3D(x == 0 ? .5 : x, wayPoint.getY(), z == 0 ? .5 : z);
        
        
    }
    
    //Base shouldnt be zero or you'll make your computer a black whole
    public static double getDirectionalEdgePoint(double base, double directionModifier, double middleOffset)
    {
        double modifier = base / Math.abs(base);
        double point = ((int) Math.abs(base)) + (.5 + (modifier * middleOffset * directionModifier));
        point = point * modifier;
        return point;
    }
    
    
    //.7
    //.3
    public static MinecraftPoint3D getDirectionalEdgeWayPointDefault(MinecraftPoint3D wayPoint, Direction direction)
    {
        MinecraftPoint3D pointToReturn;
        double x, z;
        
        if(wayPoint.getX() == 0)
        {
            x = .7 * direction.x;
        }
        else
        {
            x = getDirectionalEdgePointDefault(wayPoint.getX(), direction.x);
        }
        
        if(wayPoint.getZ() == 0)
        {
            z = .7 * direction.z;
        }
        else
        {
            z = getDirectionalEdgePointDefault(wayPoint.getZ(), direction.z);
        }
        
        
        return new MinecraftPoint3D(x == 0 ? .5 : x, wayPoint.getY(), z == 0 ? .5 : z);
        
        
    }
    
    //Base shouldnt be zero or you'll make your computer a black whole
    public static double getDirectionalEdgePointDefault(double base, double directionModifier)
    {
        double modifier = base / Math.abs(base);
        double point = ((int) Math.abs(base)) + (.5 + (modifier * .2 * directionModifier));
        point = point * modifier;
        return point;
    }
    
    
    public static MinecraftPoint3D getWayPointInDirectionFrom(MinecraftPoint3D contextPoint,
                                                              Direction direction,
                                                              double amount)
    {
        double x, y, z;
        x = contextPoint.getX() + (direction.x * amount);
        y = contextPoint.getY();
        z = contextPoint.getZ() + (direction.z * amount);
        MinecraftPoint3D newPoint = new MinecraftPoint3D(x, y, z);
        return newPoint;
    }
    
    public static MinecraftPoint3D getBlockMiddle(MinecraftPoint3D wayPoint)
    {
        MinecraftPoint3D wayPointTemp = wayPoint.toAlignedCoord();
        double x, z;
        x = (int) wayPointTemp.getX();
        z = (int) wayPointTemp.getZ();
        
        //Sign doesn't matter here because of the way we align waypoints with minecrafts blocks pos
        x = x + .5;
        z = z + .5;
        
        return new MinecraftPoint3D(x, wayPoint.getY(), z);
        
    }
    
    
    public static Quadrant getQuadrant(MinecraftPoint3D wayPoint)
    {
        boolean isXPositive, isZPositive;
        isXPositive = wayPoint.getX() >= 0;
        isZPositive = wayPoint.getZ() >= 0;
        int x = -1;
        int z = -1;
        if(isXPositive)
        {
            x = 0;
        }
        if(isZPositive)
        {
            z = 0;
        }
        for(Quadrant quadrant : Quadrant.values())
        {
            if(x == quadrant.x && z == quadrant.z)
            {
                return quadrant;
            }
        }
        //Default
        return Quadrant.POSPOS;
    }
    
    public static Quadrant getQuadrant(BlockPos pos)
    {
        boolean isXPositive, isZPositive;
        isXPositive = pos.getX() >= 0;
        isZPositive = pos.getZ() >= 0;
        int x = -1;
        int z = -1;
        if(isXPositive)
        {
            x = 0;
        }
        if(isZPositive)
        {
            z = 0;
        }
        for(Quadrant quadrant : Quadrant.values())
        {
            if(x == quadrant.x && z == quadrant.z)
            {
                return quadrant;
            }
        }
        //Default
        return Quadrant.POSPOS;
    }
    
    
}
