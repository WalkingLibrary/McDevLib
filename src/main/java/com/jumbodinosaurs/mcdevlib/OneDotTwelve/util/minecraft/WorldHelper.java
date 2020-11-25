package com.jumbodinosaurs.mcdevlib.OneDotTwelve.util.minecraft;

import com.jumbodinosaurs.mcdevlib.OneDotTwelve.bot.pathfinding.util.Direction;
import com.jumbodinosaurs.mcdevlib.OneDotTwelve.bot.pathfinding.util.MinecraftPoint3D;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.Chunk;

import java.util.ArrayList;

public class WorldHelper
{
    
    
    public static WorldClient getWorld()
    {
        return GameHelper.getInstance().world;
    }
    
    public static boolean worldContains(Block blockType)
    {
        for(Block block : getLoadedBlocks())
        {
            if(block.equals(blockType))
            {
                return true;
            }
        }
        return false;
        
    }
    
    public static ArrayList<TileEntity> getTileEntitys()
    {
        ArrayList<TileEntity> tileEntities = new ArrayList<TileEntity>();
        tileEntities.addAll(getWorld().loadedTileEntityList);
        return tileEntities;
    }
    
    public static ArrayList<EntityPlayer> getPlayerEntities()
    {
        ArrayList<EntityPlayer> tileEntities = new ArrayList<EntityPlayer>();
        tileEntities.addAll(getWorld().playerEntities);
        return tileEntities;
    }
    
    public static boolean isLoaded(MinecraftPoint3D wayPoint)
    {
        for(Chunk chunk : getLoadedChunks())
        {
            if(wayPoint.toChunkCoord().equals(chunk.getPos()))
            {
                return true;
            }
        }
        return false;
    }
    
    public static ArrayList<Block> getLoadedBlocks()
    {
        ArrayList<Block> blocks = new ArrayList<Block>();
        ArrayList<Chunk> chunks = getLoadedChunks();
        System.out.println("Chunk Size: " + chunks.size());
        for(Chunk chunk : chunks)
        {
            int x, z;
            x = chunk.x;
            z = chunk.z;
            for(int i = 0; i < 16; i++)
            {
                for(int c = 0; c < 16; c++)
                {
                    for(int j = 0; j < 256; j++)
                    {
                        BlockPos pos = new BlockPos((x * 16) + i, j, (z * 16) + c);
                        blocks.add(chunk.getBlockState(pos).getBlock());
                        
                    }
                }
            }
        }
        return blocks;
    }
    
    
    public static Chunk getPlayersChunk()
    {
        return getWorld().getChunkFromBlockCoords(MinecraftPoint3D.toBlockPos(PlayerHelper.getPlayerPositionAsWayPointROUNDED()));
    }
    
    
    public synchronized static MinecraftPoint3D getClosest(MinecraftPoint3D contextPoint, Block block)
    {
        ArrayList<MinecraftPoint3D> nodePoints = new ArrayList<MinecraftPoint3D>();
        for(Chunk chunk : getLoadedChunks())
        {
            if(chunk != null)
            {
                nodePoints.addAll(checkChunk(chunk, block));
            }
        }
        MinecraftPoint3D closestBlock = null;
        for(MinecraftPoint3D pointToCheck : nodePoints)
        {
            if(closestBlock == null ||
               (closestBlock != null &&
                pointToCheck.getNoSqRtEuclideanDistance(contextPoint) <
                closestBlock.getNoSqRtEuclideanDistance(contextPoint)))
            {
                closestBlock = pointToCheck;
            }
        }
        return closestBlock;
    }
    
    public synchronized static MinecraftPoint3D getClosest(MinecraftPoint3D contextPoint,
                                                           Block block,
                                                           ArrayList<MinecraftPoint3D> pointsToAvoid)
    {
        ArrayList<MinecraftPoint3D> nodePoints = new ArrayList<MinecraftPoint3D>();
        for(Chunk chunk : getLoadedChunks())
        {
            if(chunk != null)
            {
                nodePoints.addAll(checkChunk(chunk, block));
            }
        }
        MinecraftPoint3D closestBlock = null;
        for(MinecraftPoint3D pointToCheck : nodePoints)
        {
            if(closestBlock == null ||
               (closestBlock != null &&
                pointToCheck.getNoSqRtEuclideanDistance(contextPoint) <
                closestBlock.getNoSqRtEuclideanDistance(contextPoint)))
            {
                boolean isInPointsToAvoid = false;
                for(int i = 0; i < pointsToAvoid.size(); i++)
                {
                    MinecraftPoint3D pointToAvoid = pointsToAvoid.get(i);
                    if(pointToCheck.toAlignedCoord().equals(pointToAvoid.toAlignedCoord()))
                    {
                        isInPointsToAvoid = true;
                    }
                }
                if(!isInPointsToAvoid)
                {
                    closestBlock = pointToCheck;
                }
            }
        }
        return closestBlock;
    }
    
    
    public synchronized static MinecraftPoint3D getClosestFullyGrownCrop(MinecraftPoint3D contextPoint,
                                                                         ArrayList<MinecraftPoint3D> pointsToAvoid)
    {
        Block crops = Block.REGISTRY.getObjectById(59);
        ArrayList<MinecraftPoint3D> nodePoints = new ArrayList<MinecraftPoint3D>();
        for(Chunk chunk : getLoadedChunks())
        {
            if(chunk != null)
            {
                nodePoints.addAll(checkChunk(chunk, crops));
            }
        }
        
        MinecraftPoint3D closestBlock = null;
        for(MinecraftPoint3D pointToCheck : nodePoints)
        {
            if(closestBlock == null ||
               (closestBlock != null &&
                pointToCheck.getNoSqRtEuclideanDistance(contextPoint) <
                closestBlock.getNoSqRtEuclideanDistance(contextPoint)))
            {
                if((((BlockCrops) crops).isMaxAge(getBlockStateAt(pointToCheck.toBlockPos()))))
                {
                    boolean isInPointsToAvoid = false;
                    for(int i = 0; i < pointsToAvoid.size(); i++)
                    {
                        MinecraftPoint3D pointToAvoid = pointsToAvoid.get(i);
                        if(pointToCheck.toAlignedCoord().equals(pointToAvoid.toAlignedCoord()))
                        {
                            isInPointsToAvoid = true;
                        }
                    }
                    if(!isInPointsToAvoid)
                    {
                        closestBlock = pointToCheck;
                    }
                }
            }
        }
        return closestBlock;
    }
    
    public static IBlockState getBlockStateAt(BlockPos pos)
    {
        return getWorld().getBlockState(pos);
    }
    
    
    public static ArrayList<Chunk> getNeighbors(Chunk chunk)
    {
        int x, z;
        x = chunk.x;
        z = chunk.z;
        
        ArrayList<Chunk> neighbors = new ArrayList<Chunk>();
        for(Direction direction : Direction.values())
        {
            neighbors.add(getWorld().getChunkFromChunkCoords(x + direction.x, z + direction.z));
        }
        return neighbors;
    }
    
    
    public static ArrayList<Chunk> getLoadedChunks()
    {
        ArrayList<Chunk> loadedChunks = new ArrayList<Chunk>();
        loadedChunks.add(getPlayersChunk());
        int playerChunkX, playerChunkZ, renderDistance;
        playerChunkX = getPlayersChunk().x;
        playerChunkZ = getPlayersChunk().z;
        renderDistance = GameHelper.getInstance().gameSettings.renderDistanceChunks;
        
        for(int i = playerChunkX - renderDistance; i < playerChunkX + renderDistance; i++)
        {
            for(int c = playerChunkZ - renderDistance; c < playerChunkZ + renderDistance; c++)
            {
                Chunk tempChunk = getWorld().getChunkFromChunkCoords(i, c);
                if(tempChunk != null)
                {
                    loadedChunks.add(tempChunk);
                }
            }
        }
        return loadedChunks;
    }
    
    
    public static Block getBlockAt(BlockPos pos)
    {
        return getWorld().getBlockState(pos).getBlock();
    }
    
    
    public static ArrayList<MinecraftPoint3D> checkChunk(Chunk chunk, Block block)
    {
        ArrayList<MinecraftPoint3D> points = new ArrayList<MinecraftPoint3D>();
        int x, z;
        x = chunk.x;
        z = chunk.z;
        for(int i = 0; i < 16; i++)
        {
            for(int c = 0; c < 16; c++)
            {
                for(int y = 0; y < 256; y++)
                {
                    BlockPos blockPos = new BlockPos((x * 16) + i, y, (z * 16) + c);
                    Block currentBlock = chunk.getBlockState(blockPos).getBlock();
                    if(currentBlock.getUnlocalizedName().equals(block.getUnlocalizedName()))
                    {
                        points.add(new MinecraftPoint3D(blockPos));
                    }
                    
                }
            }
        }
        return points;
    }
    
    
}
