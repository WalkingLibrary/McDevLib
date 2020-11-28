package com.jumbodinosaurs.mcdevlib.OneDotTwelve.retentions.subclasses;

import com.jumbodinosaurs.mcdevlib.OneDotTwelve.bot.pathfinding.util.WayPoint;
import com.jumbodinosaurs.mcdevlib.OneDotTwelve.gui.objects.Line;
import com.jumbodinosaurs.mcdevlib.OneDotTwelve.gui.objects.TranslatedLine;
import com.jumbodinosaurs.mcdevlib.OneDotTwelve.gui.objects.TraslationPoint;
import com.jumbodinosaurs.mcdevlib.OneDotTwelve.gui.radialmenu.ResourceLocationUtil;
import com.jumbodinosaurs.mcdevlib.OneDotTwelve.gui.radialmenu.interfaces.IDisplayable;
import com.jumbodinosaurs.mcdevlib.OneDotTwelve.retentions.BindableRentention;
import com.jumbodinosaurs.mcdevlib.OneDotTwelve.util.minecraft.PlayerHelper;
import com.jumbodinosaurs.mcdevlib.OneDotTwelve.util.minecraft.WorldHelper;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.awt.*;
import java.util.HashMap;

public class Tracers extends BindableRentention implements IDisplayable
{
    
    private transient HashMap<String, TranslatedLine> lines = new HashMap<String, TranslatedLine>();
    
    public Tracers(KeyBinding binding)
    {
        super(binding);
    }
    
    public Tracers(boolean active, KeyBinding binding)
    {
        super(active, binding);
    }
    
    public Tracers()
    {
    }
    
    @SubscribeEvent
    public void onDraw(RenderWorldLastEvent worldLastEvent)
    {
        for(Line line : lines.values())
        {
            DrawManager.drawLine(line, worldLastEvent.getPartialTicks());
        }
    }
    
    @SubscribeEvent
    public void onTick(TickEvent tickEvent)
    {
        boolean shouldRemove = true;
        for(String playerName : lines.keySet())
        {
            for(EntityPlayer otherGuy : WorldHelper.getPlayerEntities())
            {
                if(otherGuy.getName().equals(playerName))
                {
                    shouldRemove = false;
                }
            }
            
            if(shouldRemove || !isActive())
            {
                DrawManager.getDrawManager().removeLine(lines.remove(playerName));
            }
        }
        
        if(isActive())
        {
            
            if(WorldHelper.getWorld() != null)
            {
                //Draw A Line to them
                for(EntityPlayer otherGuy : WorldHelper.getPlayerEntities())
                {
                    
                    
                    //Add the line to the list
                    if(!otherGuy.getName().equals(PlayerHelper.getPlayer().getName()))
                    {
                        
                        
                        TraslationPoint playersPos = new TraslationPoint(PlayerHelper.getPlayersCrossAirWayPoint(),
                                                                         PlayerHelper.getPlayer());
                        WayPoint otherGuysWayPoint = new WayPoint(otherGuy.posX, otherGuy.posY, otherGuy.posZ);
                        TraslationPoint otherGuysPos = new TraslationPoint(otherGuysWayPoint, otherGuy);
                        
                        TranslatedLine translatedLine = new TranslatedLine(playersPos, otherGuysPos, Color.cyan);
                        
                        if(!lines.containsKey(otherGuy.getName()))
                        {
                            lines.put(otherGuy.getName(), translatedLine);
                        }
                        lines.get(otherGuy.getName()).setPointOne(playersPos);
                        lines.get(otherGuy.getName()).setPointTwo(otherGuysPos);
                    }
                    
                }
                
            }
            
            
        }
    }
    
    @Override
    public String getInfoMessage()
    {
        return "Draws A Line To Nearby Players";
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
    public ResourceLocation getIcon()
    {
        return ResourceLocationUtil.clairvoyanceIcon;
    }
    
    @Override
    public String getLabel()
    {
        return "Tracers";
    }
    
    @Override
    public void toggle()
    {
        this.defaultPress();
    }
}
