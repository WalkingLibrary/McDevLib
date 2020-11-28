package com.jumbodinosaurs.mcdevlib.OneDotTwelve.bot.pathfinding.util;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;

public class WayPoint
{
    private double x, y, z;
    
    public WayPoint(double x, double y, double z)
    {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    public WayPoint(BlockPos pos)
    {
        this.x = pos.getX();
        this.y = pos.getY();
        this.z = pos.getZ();
    }
    
    //Get Aligned and toBlockPos might look a bit over complicated but in order to keep everything aligned with
    // minecrafts coordinate system you go to do some extra
    public static BlockPos toBlockPos(WayPoint wayPoint)
    {
        WayPoint wayPointChopped = wayPoint.chop();
        if(!wayPoint.equals(wayPointChopped))
        {
            int x, y, z;
            x = (int) wayPointChopped.getX();
            z = (int) wayPointChopped.getZ();
            y = (int) wayPointChopped.getY();
            Quadrant quadrantOffSet = PathFindingUtil.getQuadrant(wayPointChopped);
            
            if(wayPointChopped.getX() == 0)
            {
                if(Math.abs(wayPoint.getX()) != wayPoint.getX())
                {
                    x = -1;
                }
                else
                {
                    x = 0;
                }
            }
            else
            {
                x = x + quadrantOffSet.x;
            }
            
            if(wayPointChopped.getZ() == 0)
            {
                if(Math.abs(wayPoint.getZ()) != wayPoint.getZ())
                {
                    z = -1;
                }
                else
                {
                    z = 0;
                }
            }
            else
            {
                z = z + quadrantOffSet.z;
            }
            return new BlockPos(x, y, z);
        }
        else
        {
            return new BlockPos(wayPoint.getX(), wayPoint.getY(), wayPoint.getZ());
        }
    }
    
    public WayPoint translateBy(WayPoint wayPoint)
    {
        return new WayPoint(this.x + wayPoint.getX(), this.y + wayPoint.getY(), this.z + wayPoint.getZ());
    }
    
    public WayPoint getEntityTranslated(Entity entity, float partialTicks)
    {
        double translatedPosX = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * partialTicks;
        double translatedPosY = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * partialTicks;
        double translatedPosZ = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * partialTicks;
        
        WayPoint translatePoint = new WayPoint(translatedPosX - entity.posX,
                                               translatedPosY - entity.posY,
                                               translatedPosZ - entity.posZ);
        
        return translateBy(translatePoint);
    }
    
    //Extra
    public static WayPoint getAligned(WayPoint wayPoint)
    {
        WayPoint wayPointChopped = wayPoint.chop();
        if(!wayPoint.equals(wayPointChopped))
        {
            int x, y, z;
            x = (int) wayPointChopped.getX();
            z = (int) wayPointChopped.getZ();
            y = (int) wayPointChopped.getY();
            Quadrant quadrantOffSet = PathFindingUtil.getQuadrant(wayPointChopped);
            
            if(wayPointChopped.getX() == 0)
            {
                if(Math.abs(wayPoint.getX()) != wayPoint.getX())
                {
                    x = -1;
                }
                else
                {
                    x = 0;
                }
            }
            else
            {
                x = x + quadrantOffSet.x;
            }
            
            if(wayPointChopped.getZ() == 0)
            {
                if(Math.abs(wayPoint.getZ()) != wayPoint.getZ())
                {
                    z = -1;
                }
                else
                {
                    z = 0;
                }
            }
            else
            {
                z = z + quadrantOffSet.z;
            }
            return new WayPoint(x, y, z);
        }
        else
        {
            return wayPoint;
        }
    }
    
    public double getX()
    {
        return x;
    }
    
    public void setX(double x)
    {
        this.x = x;
    }
    
    public double getY()
    {
        return y;
    }
    
    public void setY(double y)
    {
        this.y = y;
    }
    
    public double getZ()
    {
        return z;
    }
    
    public void setZ(double z)
    {
        this.z = z;
    }
    
    public WayPoint copy()
    {
        return new WayPoint(this.x, this.y, this.z);
    }
    
    @Override
    public String toString()
    {
        return "{ X: " + this.x + ", Y: " + this.y + ", Z: " + this.z + "}";
    }
    
    public String toHashString()
    {
        return "{ X: " + this.x + ", Y: " + this.y + ", Z: " + this.z + "}";
    }
    
    public WayPoint getOneAbove()
    {
        
        return new WayPoint(this.x, this.y + 1, this.z);
    }
    
    public ArrayList<WayPoint> getNeighbors3D()
    {
        ArrayList<WayPoint> neighbors = new ArrayList<WayPoint>();
        for(Direction direction : Direction.values())
        {
            if(!direction.equals(Direction.SAMEPOINT))
            {
                neighbors.add(new WayPoint(this.x + direction.x, this.y, this.z + direction.z));
                if(this.y + 1 < 257)
                {
                    neighbors.add(new WayPoint(this.x + direction.x, this.y + 1, this.z + direction.z));
                }
                if(this.y - 1 > -1)
                {
                    neighbors.add(new WayPoint(this.x + direction.x, this.y - 1, this.z + direction.z));
                }
                
            }
            else
            {
                if(this.y + 1 < 257)
                {
                    neighbors.add(new WayPoint(this.x, this.y + 1, this.z));
                }
                if(this.y - 1 > -1)
                {
                    neighbors.add(new WayPoint(this.x, this.y - 1, this.z));
                }
            }
        }
        return neighbors;
    }
    
    public ArrayList<WayPoint> getNeighborsAligned3D(boolean alignedToMiddle)
    {
        if(alignedToMiddle)
        {
            return this.toMiddleCoord().getNeighbors3D();
        }
        return this.toAlignedCoord().getNeighbors3D();
    }
    
    public WayPoint getOneBelow()
    {
        return new WayPoint(this.x, this.y - 1, this.z);
    }
    
    public boolean isSameBlockPos(WayPoint wayPoint)
    {
        return this.toBlockPos().equals(wayPoint.toBlockPos());
    }
    
    public WayPoint chop()
    {
        return new WayPoint((int) this.x, (int) this.y, (int) this.z);
    }
    
    public WayPoint toMiddleCoord()
    {
        return PathFindingUtil.getBlockMiddle(this);
    }
    
    public BlockPos toBlockPos()
    {
        return WayPoint.toBlockPos(this);
    }
    
    /*Maybe Look at the wiki next time you big dumb
     * https://en.wikipedia.org/wiki/Euclidean_distance
     * */
    public double getNoSqRtEuclideanDistance(WayPoint wayPoint)
    {
        return Math.pow(getX() - wayPoint.getX(), 2) + (Math.pow(getZ() - wayPoint.getZ(),
                                                                 2)) + (Math.pow(getY() - wayPoint.getY(), 2));
    }
    
    /*Maybe Look at the wiki next time you big dumb
     * https://en.wikipedia.org/wiki/Euclidean_distance
     * */
    public double getEuclideanDistance(WayPoint wayPoint)
    {
        return Math.sqrt(Math.pow(getX() - wayPoint.getX(), 2) + (Math.pow(getZ() - wayPoint.getZ(), 2)) + (Math.pow(
                getY() - wayPoint.getY(),
                2)));
    }
    
    public boolean equals(WayPoint wayPoint)
    {
        return this.x == wayPoint.getX() && this.z == wayPoint.getZ() && this.y == wayPoint.getY();
    }
    
    public ChunkCoord toChunkCoord()
    {
        int xCoord, zCoord;
        xCoord = (int) ((this.x - (this.x % 16)) / 16);
        zCoord = (int) ((this.z - (this.z % 16)) / 16);
        return new ChunkCoord(xCoord, zCoord);
    }
    
    public WayPoint toAlignedCoord()
    {
        return WayPoint.getAligned(this);
    }
    
    public WayPoint addX(double x)
    {
        return new WayPoint(this.x + x, this.y, this.z);
    }
    
    
    public WayPoint addZ(double z)
    {
        return new WayPoint(this.x, this.y, this.z + z);
    }
    
    public WayPoint addY(double y)
    {
        return new WayPoint(this.x, this.y + y, this.z);
    }
}
