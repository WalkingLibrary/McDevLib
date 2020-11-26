package com.jumbodinosaurs.mcdevlib.OneDotTwelve.retentions.subclasses;

import com.jumbodinosaurs.devlib.util.objects.ClientTimer;
import com.jumbodinosaurs.mcdevlib.OneDotTwelve.gui.radialmenu.ResourceLocationUtil;
import com.jumbodinosaurs.mcdevlib.OneDotTwelve.gui.radialmenu.interfaces.IDisplayable;
import com.jumbodinosaurs.mcdevlib.OneDotTwelve.retentions.BindableRentention;
import com.jumbodinosaurs.mcdevlib.OneDotTwelve.util.minecraft.PlayerHelper;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AntiAFK extends BindableRentention implements IDisplayable
{
    //5000 = 5 seconds you do the math
    private transient final ClientTimer timer = new ClientTimer(5000, new ComponentsListener());
    
    public AntiAFK(KeyBinding binding)
    {
        super(binding);
    }
    
    public AntiAFK(boolean active, KeyBinding binding)
    {
        super(active, binding);
    }
    
    public AntiAFK()
    {
    }
    
    @Override
    public String getModID()
    {
        return "DevLib";
    }
    
    
    public void register()
    {
        super.register();
        timer.start();
    }
    
    
    @Override
    public String getInfoMessage()
    {
        return "Hopefully Keeps the player from getting kicked from the server";
    }
    
    @Override
    public String getRetentionIdentifier()
    {
        return this.getClass().getSimpleName();
    }
    
    @Override
    public void onPress()
    {
        defaultPress();
    }
    
    private class ComponentsListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            if(isActive() && PlayerHelper.safeToCheck())
            {
                
                PlayerHelper.getPlayer().swingArm(EnumHand.MAIN_HAND);
                PlayerHelper.getPlayer().jump();
            }
        }
    }
    
    @Override
    public ResourceLocation getIcon()
    {
        return ResourceLocationUtil.movementIcon;
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
