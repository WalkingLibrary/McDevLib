package com.jumbodinosaurs.mcdevlib.OneDotTwelve.retentions;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;

public abstract class BindableRentention extends Retention
{
    
  
    
    private transient KeyBinding binding;
    private int keyCode = 0;
    
    
    public BindableRentention(KeyBinding binding)
    {
        this.binding = binding;
        this.keyCode = binding.getKeyCode();
    }
    
    public BindableRentention(boolean active,
                              KeyBinding binding)
    {
        super(active);
        this.binding = binding;
        this.keyCode = binding.getKeyCode();
    }
    
    public BindableRentention()
    {
    }
    
    public abstract String getModID();
    
    
    @Override
    public void register()
    {
        super.register();
        this.binding = new KeyBinding(this.getRetentionIdentifier(), this.keyCode, getModID());
    }
    
    public abstract void onPress();
    
    public void defaultPress()
    {
        toggleActive();
    }
    
    
    public void bind(int keycode)
    {
        
        if(this.binding != null)
        {
            this.binding.setKeyModifierAndCode(net.minecraftforge.client.settings.KeyModifier.getActiveModifier(), keycode);
        }
        else
        {
            this.binding = new KeyBinding(this.getRetentionIdentifier(), keycode,
                                          getModID());
            ClientRegistry.registerKeyBinding(this.binding);
        }
        
    }
    
    
    public KeyBinding getBinding()
    {
        return binding;
    }
    
    
}
