package com.jumbodinosaurs.mcdevlib.OneDotTwelve.gui.util;


import com.jumbodinosaurs.mcdevlib.OneDotTwelve.bot.pathfinding.util.WayPoint;
import com.jumbodinosaurs.mcdevlib.OneDotTwelve.gui.objects.Cube;
import com.jumbodinosaurs.mcdevlib.OneDotTwelve.util.minecraft.WorldHelper;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.Chunk;

import java.awt.*;
import java.util.ArrayList;

public class CubeList
{
    private ArrayList<Cube> cubes = new ArrayList<Cube>();
    private boolean alignWithGrid;
    
    public CubeList(boolean alignWithGrid)
    {
        this.alignWithGrid = alignWithGrid;
    }
    
    
    public  void add(WayPoint wayPoint, Color color)
    {
    
        wayPoint = this.transformWayPoint(wayPoint);
        Cube newCube = new Cube(wayPoint, true, color);
        //Check for duplicates
        boolean needToAdd = true;
        for(int i = 0 ; i < this.cubes.size(); i++)
        {
            Cube currentCube = this.cubes.get(i);
            if(currentCube.equals(newCube))
            {
                needToAdd = false;
                break;
            }
        }
        if(needToAdd)
        {
            newCube.generate();
            newCube.draw();
            cubes.add(newCube);
        }
    }
    
    public  void remove(WayPoint wayPoint, Color color)
    {
        wayPoint = this.transformWayPoint(wayPoint);
        Cube newCube = new Cube(wayPoint, true, color);
        //Check for duplicates
        for(int i = 0 ; i < this.cubes.size(); i++)
        {
            Cube currentCube = this.cubes.get(i);
            if(currentCube.equals(newCube))
            {
                currentCube.erase();
                this.cubes.remove(i);
                i--;
            }
        }
    }
    
    public  void remove(Block block)
    {
        for(int i = 0 ; i < this.cubes.size(); i++)
        {
            Cube cube = this.cubes.get(i);
            BlockPos cubesLocation = cube.getContextPoint().toBlockPos();
            Block blockAtCubePos = WorldHelper.getBlockAt(cubesLocation);
            Block air = Block.REGISTRY.getObjectById(0);
            if(blockAtCubePos.getUnlocalizedName().equals(block.getUnlocalizedName()) || blockAtCubePos.getUnlocalizedName().equals(air.getUnlocalizedName()))
            {
                cube.erase();
                this.cubes.remove(i);
                i--;
            }
        }
    }
    
    public   void remove(Chunk chunk)
    {
        int x, z;
        x = chunk.x;
        z = chunk.z;
        //for each highlighted cube
        for(int i = 0; i < cubes.size(); i++)
        {
            Cube cube = cubes.get(i);
            WayPoint contextPoint = cube.getContextPoint();
            //if the highlighted cube is in the unloaded chunks x range
            if(contextPoint.getX() > x && contextPoint.getX() < x + 16)
            {
                
                //if the highlighted cube is in the unloaded chunks z range
                if(contextPoint.getZ() > z && contextPoint.getZ() < z + 16)
                {
                    cube.erase();
                    cubes.remove(cube);
                    i--;
                }
            }
        }
    }
    
    public  void updateBlock(Block block, Color color)
    {
        for(int i = 0 ; i < this.cubes.size(); i++)
        {
            Cube cube = this.cubes.get(i);
            BlockPos cubesLocation = cube.getContextPoint().toBlockPos();
            Block blockAtCubePos = WorldHelper.getBlockAt(cubesLocation);
            if(blockAtCubePos.getUnlocalizedName().equals(block.getUnlocalizedName()))
            {
                cube.erase();
                cube.setColor(color);
                cube.draw();
            }
        }
    }
    
    public  void remove(WayPoint wayPoint)
    {
        wayPoint = this.transformWayPoint(wayPoint);
        for(int i = 0; i < this.cubes.size(); i++)
        {
            Cube cube = this.cubes.get(i);
            if(cube.getContextPoint().equals(wayPoint.toAlignedCoord()))
            {
                cube.erase();
                cubes.remove(i);
                i--;
            }
        }
    }
    
    public  void eraseAll()
    {
        for(int i = 0 ; i < this.cubes.size(); i++)
        {
            Cube cube = this.cubes.get(i);
            cube.erase();
            this.cubes.remove(i);
            i--;
        }
    }
    
    public  WayPoint transformWayPoint(WayPoint wayPoint)
    {
        if(this.alignWithGrid)
        {
            return wayPoint.toAlignedCoord();
        }
        else
        {
            return  wayPoint;
        }
    }
}
