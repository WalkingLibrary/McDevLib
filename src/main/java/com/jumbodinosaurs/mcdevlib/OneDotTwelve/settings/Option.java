package com.jumbodinosaurs.mcdevlib.OneDotTwelve.settings;

import com.jumbodinosaurs.mcdevlib.OneDotTwelve.gui.radialmenu.buttons.ButtonAction;
import com.jumbodinosaurs.mcdevlib.OneDotTwelve.gui.radialmenu.interfaces.IDisplayable;
import com.jumbodinosaurs.mcdevlib.OneDotTwelve.gui.radialmenu.interfaces.IInformative;
import net.minecraft.client.gui.GuiScreen;

public abstract class Option implements IDisplayable, IInformative
{
    private boolean isActive;
    private String type;
    
    public boolean isActive()
    {
        return isActive;
    }
    
    public void setActive(boolean active)
    {
        isActive = active;
    }
    
    public abstract boolean getDefaultState();
    
    @Override
    public void toggle()
    {
        this.isActive = !isActive;
        OptionsManager.saveOptions(OptionsManager.getLoadedOptions());
    }
    
    @Override
    public String getLabel()
    {
        return this.getClass().getSimpleName();
    }
    
    @Override
    public ButtonAction getAction(GuiScreen drawMenu)
    {
        return new ButtonAction() {
            @Override
            public void onClick()
            {
                toggle();
            }
        };
    }
    
    
    public String getIdentifier()
    {
        return this.getClass().getSimpleName();
    }
    
    public String getType()
    {
        return type;
    }
    
    public void setType(String type)
    {
        this.type = type;
    }
}
