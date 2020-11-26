package com.jumbodinosaurs.mcdevlib.OneDotTwelve.retentions.subclasses;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jumbodinosaurs.devlib.json.GsonUtil;
import com.jumbodinosaurs.devlib.util.GeneralUtil;
import com.jumbodinosaurs.mcdevlib.OneDotTwelve.DevLibInitializer;
import com.jumbodinosaurs.mcdevlib.OneDotTwelve.bot.pathfinding.util.ChunkCoord;
import com.jumbodinosaurs.mcdevlib.OneDotTwelve.bot.pathfinding.util.RegionCoord;
import com.jumbodinosaurs.mcdevlib.OneDotTwelve.bot.pathfinding.util.WayPoint;
import com.jumbodinosaurs.mcdevlib.OneDotTwelve.events.PacketEvent;
import com.jumbodinosaurs.mcdevlib.OneDotTwelve.gui.radialmenu.ResourceLocationUtil;
import com.jumbodinosaurs.mcdevlib.OneDotTwelve.gui.radialmenu.interfaces.IDisplayable;
import com.jumbodinosaurs.mcdevlib.OneDotTwelve.gui.util.SquareList;
import com.jumbodinosaurs.mcdevlib.OneDotTwelve.retentions.BindableRentention;
import com.jumbodinosaurs.mcdevlib.OneDotTwelve.util.minecraft.PlayerHelper;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.SPacketChunkData;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.world.ChunkEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

public class NewChunks extends BindableRentention implements IDisplayable
{
    private transient SquareList squareList = new SquareList(true, 16);
    private transient ConcurrentHashMap<String, RegionCoord> loadedRegionCoords = new ConcurrentHashMap<String, RegionCoord>();
    private transient ConcurrentHashMap<String, ChunkCoord> playerLoadedChunks = new ConcurrentHashMap<String, ChunkCoord>();
    private transient ConcurrentHashMap<String, ChunkCoord> newChunksThisSession = new ConcurrentHashMap<String, ChunkCoord>();
    
    
    @SubscribeEvent
    public void onPacket(PacketEvent.Receive event)
    {
        if(isActive())
        {
            Packet packet = event.getPacket();
            if(packet instanceof SPacketChunkData)
            {
                if(!((SPacketChunkData) packet).isFullChunk())
                {
                    //Get The New Chunk
                    int chunkX, chunkZ;
                    chunkX = ((SPacketChunkData) packet).getChunkX();
                    chunkZ = ((SPacketChunkData) packet).getChunkZ();
                    ChunkCoord newChunk = new ChunkCoord(chunkX, chunkZ);
                    this.newChunksThisSession.put(newChunk.toString(), newChunk);
                    //Render The New Chunk
                    this.addNewChunkToSquareList(newChunk, Color.YELLOW);
                    
                    
                    //Save The New Chunk
                    RegionCoord playersRegionCoord = new RegionCoord(newChunk);
                    File loadedChunksJson = getLoadedChunksJson(playersRegionCoord);
                    ArrayList<ChunkCoord> savedChunksLoadedByPlayer = GsonUtil.readList(loadedChunksJson,
                                                                                        ChunkCoord.class,
                                                                                        new TypeToken<ArrayList<ChunkCoord>>() {},
                                                                                        false);
                    if(savedChunksLoadedByPlayer == null)
                    {
                        savedChunksLoadedByPlayer = new ArrayList<ChunkCoord>();
                    }
                    savedChunksLoadedByPlayer.add(newChunk);
                    String contents = new Gson().toJson(savedChunksLoadedByPlayer);
                    GeneralUtil.writeContents(loadedChunksJson, contents, false);
                    
                }
            }
        }
    }
    
    @SubscribeEvent
    public void onChunkLoad(ChunkEvent.Load event)
    {
        if(isActive())
        {
            int chunkX, chunkZ;
            chunkX = event.getChunk().x;
            chunkZ = event.getChunk().z;
            ChunkCoord eventChunk = new ChunkCoord(chunkX, chunkZ);
            RegionCoord eventRegion = new RegionCoord(eventChunk);
            
            
            
            /* When ever a chunk loads we need to Load it's region file if we haven't yet
             * and add those chunks to the playerloaded chunks hashmap
             * */
            if(this.loadedRegionCoords.containsKey(eventRegion.toString()))
            {
                return;
            }
            
            this.loadRegionFile(eventRegion);
            /* We now need to render a orange square under the event chunk if
             * it's a chunk that was loaded by the player previously
             */
            
            
            //TODO Cull refresh times
            
            /* To keep the new chunks module from reaching the draw limit we need to
             * remove chunks from the square list
             * To help us we set a radius of how far a chunk can be for us to still render it
             *
             * We also need to remove chunks from the loadedplayer chunks list to keep it small
             * to do this we can cull by regions that are loaded and far enough away
             *
             * To kill Two birds with one stone rather than remove chunks by radius we can remove by a
             * region radius
             * */
            
            ChunkCoord playersChunkCoord = PlayerHelper.getPlayerPositionAsWayPoint().toChunkCoord();
            RegionCoord playersRegionCoord = new RegionCoord(playersChunkCoord);
            int regionCullRadius = 4;
            for(RegionCoord region : this.loadedRegionCoords.values())
            {
                if(region.getEuclideanDistance(playersRegionCoord) > regionCullRadius)
                {
                    /*
                     * Now we need to get all the chunk coords in the region
                     * and remove them from square list and from the Playerloaded chunks list*/
                    ArrayList<ChunkCoord> chunkCoords = getChunksLoaded(region);
                    if(chunkCoords != null)
                    {
                        for(ChunkCoord chunkCoord : chunkCoords)
                        {
                            this.removeNewChunkFromSquareList(chunkCoord);
                            this.playerLoadedChunks.remove(chunkCoord.toString());
                        }
                    }
                    this.loadedRegionCoords.remove(region.toString());
                    
                }
            }
            
            
        }
    }
    
    @Override
    public ResourceLocation getIcon()
    {
        return ResourceLocationUtil.prismIcon;
    }
    
    @Override
    public String getLabel()
    {
        return "New Chunks";
    }
    
    @Override
    public void toggle()
    {
        defaultPress();
    }
    
    @Override
    public void toggleActive()
    {
        super.toggleActive();
        if(isActive())
        {
            /*When activated we need to loaded the chunks close to the player*/
            this.newChunksThisSession = new ConcurrentHashMap<String, ChunkCoord>();
            this.playerLoadedChunks = new ConcurrentHashMap<String, ChunkCoord>();
            this.loadedRegionCoords = new ConcurrentHashMap<String, RegionCoord>();
            ChunkCoord playersChunkCoord = PlayerHelper.getPlayerPositionAsWayPoint().toChunkCoord();
            RegionCoord playersRegionCoord = new RegionCoord(playersChunkCoord);
            ArrayList<RegionCoord> adjacentRegions = new ArrayList<RegionCoord>();
            adjacentRegions.add(playersRegionCoord.addX(1));
            adjacentRegions.add(playersRegionCoord.addX(-1));
            adjacentRegions.add(playersRegionCoord.addZ(1));
            adjacentRegions.add(playersRegionCoord.addZ(-1));
            adjacentRegions.add(playersRegionCoord.addX(1).addZ(-1));
            adjacentRegions.add(playersRegionCoord.addX(-1).addZ(1));
            adjacentRegions.add(playersRegionCoord.addX(1).addZ(1));
            adjacentRegions.add(playersRegionCoord.addX(-1).addZ(-1));
            adjacentRegions.add(playersRegionCoord);
            for(RegionCoord regionCoord : adjacentRegions)
            {
                this.loadRegionFile(regionCoord);
            }
            for(ChunkCoord chunkCoord : this.playerLoadedChunks.values())
            {
                
                this.addNewChunkToSquareList(chunkCoord, Color.cyan);
            }
        }
        else
        {
            this.squareList.eraseAll();
        }
        
    }
    
    /*When called this will load the given region file into loadedRegions
     * and all of it's old NewChunks int player loaded Chunks
     * Note Adding chunks to the square list is not done here*/
    public void loadRegionFile(RegionCoord regionCoord)
    {
        if(!this.loadedRegionCoords.containsKey(regionCoord.toString()))
        {
            ArrayList<ChunkCoord> chunkCoords = getChunksLoaded(regionCoord);
            if(chunkCoords != null)
            {
                for(ChunkCoord chunkCoord : chunkCoords)
                {
                    this.playerLoadedChunks.put(chunkCoord.toString(), chunkCoord);
                    if(!this.newChunksThisSession.containsKey(chunkCoord.toString()))
                    {
                        this.addNewChunkToSquareList(chunkCoord, Color.cyan);
                    }
                }
            }
            this.loadedRegionCoords.put(regionCoord.toString(), regionCoord);
        }
        
    }
    
    
    public void addNewChunkToSquareList(ChunkCoord chunkCoord, Color color)
    {
        WayPoint tempWaypoint = new WayPoint(chunkCoord.getX() * 16, -1, chunkCoord.getZ() * 16);
        this.squareList.add(tempWaypoint, color);
    }
    
    
    public void removeNewChunkFromSquareList(ChunkCoord chunkCoord)
    {
        WayPoint tempWaypoint = new WayPoint(chunkCoord.getX() * 16, -1, chunkCoord.getZ() * 16);
        this.squareList.remove(tempWaypoint);
    }
    
    
    public ArrayList<ChunkCoord> getChunksLoaded(RegionCoord regionCoord)
    {
        File loadedChunksJson = getLoadedChunksJson(regionCoord);
        
        return GsonUtil.readList(loadedChunksJson, ChunkCoord.class, new TypeToken<ArrayList<ChunkCoord>>() {}, false);
    }
    
    
    public File getLoadedChunksJson(RegionCoord regionCoord)
    {
        String loadedChunksDirName = "Loaded Chunks";
        File loadedChunksDir = GeneralUtil.checkFor(DevLibInitializer.modDir, loadedChunksDirName);
        
        String connectionName = ConnectionUtil.getIp() == null ? "Single Player" : ConnectionUtil.getIp();
        File connectionDir = GeneralUtil.checkFor(loadedChunksDir, connectionName, true);
        File dimensionDir = GeneralUtil.checkFor(connectionDir, PlayerHelper.getPlayer().dimension + "");
        String regionNameFormat = "r[" + regionCoord.getX() + ", " + regionCoord.getZ() + "]";
        File regionDir = GeneralUtil.checkFor(dimensionDir, regionNameFormat);
        String loadedChunksJsonName = "loadedChunk.json";
        return GeneralUtil.checkFor(regionDir, loadedChunksJsonName);
    }
    
    @Override
    public String getModID()
    {
        return "DevLib";
    }
    
    @Override
    public void onPress()
    {
        defaultPress();
    }
    
    @Override
    public String getInfoMessage()
    {
        return "Aids the Player in following chunk trails";
    }
    
    
}
