package com.jumbodinosaurs.mcdevlib.OneDotTwelve.retentions;

import net.minecraftforge.common.MinecraftForge;

public abstract class Retention
{
    
    
    public boolean active;
    private String type;
    
    
    public Retention()
    {
        this.active = false;
    }
    
    public Retention(boolean active)
    {
        this.active = active;
    }
    
    
    public abstract String getInfoMessage();
    
    public void toggleActive()
    {
        RetentionManager.saveRetentions();
        this.active = !this.active;
    }
    
    public boolean isActive()
    {
        return active;
    }
    
    public void setActive(boolean active)
    {
        this.active = active;
    }
    
    public void register()
    {
      MinecraftForge.EVENT_BUS.register(this);
    }
    
    public String getRetentionIdentifier()
    {
        return this.getClass().getSimpleName();
    }
    
  
    public void setType(String type)
    {
        this.type = type;
    }
}
