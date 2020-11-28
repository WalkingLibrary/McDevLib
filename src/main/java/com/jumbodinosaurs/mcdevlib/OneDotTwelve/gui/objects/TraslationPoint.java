package com.jumbodinosaurs.mcdevlib.OneDotTwelve.gui.objects;

import com.jumbodinosaurs.mcdevlib.OneDotTwelve.bot.pathfinding.util.WayPoint;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;

public class TraslationPoint extends WayPoint
{
    private Entity translateEntity;
    
    public TraslationPoint(double x, double y, double z, Entity translateEntity)
    {
        super(x, y, z);
        this.translateEntity = translateEntity;
    }
    
    public TraslationPoint(WayPoint wayPoint, Entity translateEntity)
    {
        super(wayPoint.getX(), wayPoint.getY(), wayPoint.getZ());
        this.translateEntity = translateEntity;
    }
    
    public TraslationPoint(BlockPos pos, Entity translateEntity)
    {
        super(pos);
        this.translateEntity = translateEntity;
    }
    
    public Entity getTranslateEntity()
    {
        return translateEntity;
    }
    
    public void setTranslateEntity(Entity translateEntity)
    {
        this.translateEntity = translateEntity;
    }
    
    @Override
    public TraslationPoint getEntityTranslated(Entity entity, float partialTicks)
    {
        WayPoint translated = super.getEntityTranslated(entity, partialTicks);
        return new TraslationPoint(translated.getX(), translated.getY(), translated.getZ(), entity);
    }
}
