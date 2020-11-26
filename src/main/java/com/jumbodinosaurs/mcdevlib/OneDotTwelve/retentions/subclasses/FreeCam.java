package com.jumbodinosaurs.mcdevlib.OneDotTwelve.retentions.subclasses;


import com.jumbodinosaurs.mcdevlib.OneDotTwelve.events.PacketEvent;
import com.jumbodinosaurs.mcdevlib.OneDotTwelve.gui.radialmenu.ResourceLocationUtil;
import com.jumbodinosaurs.mcdevlib.OneDotTwelve.gui.radialmenu.interfaces.IDisplayable;
import com.jumbodinosaurs.mcdevlib.OneDotTwelve.retentions.BindableRentention;
import com.jumbodinosaurs.mcdevlib.OneDotTwelve.util.minecraft.GameHelper;
import com.jumbodinosaurs.mcdevlib.OneDotTwelve.util.minecraft.PlayerHelper;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.network.play.client.CPacketInput;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.server.SPacketPlayerPosLook;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class FreeCam extends BindableRentention implements IDisplayable
{
    //Thank you forge haxs
    public static int speed = 1;
    public static double posX, posY, posZ;
    public static float pitch, yaw;
    
    public static double startPosX, startPosY, startPosZ;
    public static float startPitch, startYaw;
    
    public static EntityOtherPlayerMP clonedPlayer;
    
    public static boolean isRidingEntity;
    public static Entity ridingEntity;
    
    public FreeCam(KeyBinding binding)
    {
        super(binding);
    }
    
    public FreeCam(boolean active, KeyBinding binding)
    {
        super(active, binding);
    }
    
    public FreeCam()
    {
    }
    
    @Override
    public String getModID()
    {
        return "DevLib";
    }
    
    
    @Override
    public void onPress()
    {
        
        
        if(this.isActive())
        {
            
            toggleActive();
            if(PlayerHelper.getPlayer() != null)
            {
                PlayerHelper.getPlayer().setPositionAndRotation(posX, posY, posZ, yaw, pitch);
                GameHelper.getInstance().world.removeEntityFromWorld(-100);
                clonedPlayer = null;
                posX = posY = posZ = 0.D;
                pitch = yaw = 0.f;
                PlayerHelper.getPlayer().capabilities.isFlying = false; //getModManager().getMod("ElytraFlight").isEnabled();
                PlayerHelper.getPlayer().capabilities.setFlySpeed(0.05f);
                PlayerHelper.getPlayer().noClip = false;
                PlayerHelper.getPlayer().motionX = PlayerHelper.getPlayer().motionY = PlayerHelper.getPlayer().motionZ = 0.f;
                
                if(isRidingEntity)
                {
                    PlayerHelper.getPlayer().startRiding(ridingEntity, true);
                }
            }
            
            
        }
        else
        {
            
            
            toggleActive();
            
            if(PlayerHelper.getPlayer() != null)
            {
                isRidingEntity = PlayerHelper.getPlayer().getRidingEntity() != null;
                
                if(PlayerHelper.getPlayer().getRidingEntity() == null)
                {
                    posX = PlayerHelper.getPlayer().posX;
                    posY = PlayerHelper.getPlayer().posY;
                    posZ = PlayerHelper.getPlayer().posZ;
                }
                else
                {
                    ridingEntity = PlayerHelper.getPlayer().getRidingEntity();
                    PlayerHelper.getPlayer().dismountRidingEntity();
                }
                
                pitch = PlayerHelper.getPlayer().rotationPitch;
                yaw = PlayerHelper.getPlayer().rotationYaw;
                
                clonedPlayer = new EntityOtherPlayerMP(GameHelper.getInstance().world,
                                                       GameHelper.getInstance().getSession().getProfile());
                clonedPlayer.copyLocationAndAnglesFrom(PlayerHelper.getPlayer());
                clonedPlayer.rotationYawHead = PlayerHelper.getPlayer().rotationYawHead;
                GameHelper.getInstance().world.addEntityToWorld(-100, clonedPlayer);
                
                PlayerHelper.getPlayer().capabilities.isFlying = true;
                PlayerHelper.getPlayer().capabilities.setFlySpeed(speed / 100f);
                PlayerHelper.getPlayer().noClip = true;
            }
        }
    }
    
    @SubscribeEvent
    public void onReceived(PacketEvent.Receive event)
    {
        if(event.getPacket() instanceof SPacketPlayerPosLook && this.isActive())
        {
            SPacketPlayerPosLook packet = (SPacketPlayerPosLook) event.getPacket();
            startPosX = packet.getX();
            startPosY = packet.getY();
            startPosZ = packet.getZ();
            startPitch = packet.getPitch();
            startYaw = packet.getYaw();
        }
    }
    
    @SubscribeEvent
    public void onSend(PacketEvent.Send event)
    {
        if(this.isActive())
        {
            if(event.getPacket() instanceof CPacketPlayer ||
               event.getPacket() instanceof CPacketInput && this.isActive())
            {
                event.setCanceled(true);
            }
        }
    }
    
    @SubscribeEvent
    public void onLocalPlayerUpdate(LivingEvent event)
    {
        if(PlayerHelper.getPlayer() != null && this.isActive())
        {
            PlayerHelper.getPlayer().capabilities.isFlying = true;
            PlayerHelper.getPlayer().capabilities.setFlySpeed(0.05f);
            PlayerHelper.getPlayer().noClip = true;
            PlayerHelper.getPlayer().onGround = false;
            PlayerHelper.getPlayer().fallDistance = 0;
        }
    }
    
    @SubscribeEvent
    public void onWorldLoad(WorldEvent.Load event)
    {
        if(this.isActive())
        {
            posX = startPosX;
            posY = startPosY;
            posZ = startPosZ;
            pitch = startPitch;
            yaw = startYaw;
        }
    }
    
    @Override
    public String getInfoMessage()
    {
        return "Allows the player to freely clip around.";
    }
    
    
    @Override
    public String getRetentionIdentifier()
    {
        return this.getClass().getSimpleName();
    }
    
    @Override
    public ResourceLocation getIcon()
    {
        return ResourceLocationUtil.clairvoyanceIcon;
    }
    
    @Override
    public String getLabel()
    {
        return getRetentionIdentifier();
    }
    
    @Override
    public void toggle()
    {
        onPress();
    }
}

