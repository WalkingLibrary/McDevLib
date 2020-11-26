package com.jumbodinosaurs.mcdevlib.OneDotTwelve.retentions.subclasses;


import com.jumbodinosaurs.mcdevlib.OneDotTwelve.retentions.BindableRentention;
import com.jumbodinosaurs.mcdevlib.OneDotTwelve.retentions.Retention;
import com.jumbodinosaurs.mcdevlib.OneDotTwelve.retentions.RetentionManager;
import com.jumbodinosaurs.mcdevlib.OneDotTwelve.retentions.TextRenderingRetentions;

public class ToggleTextRenderingRetentions extends BindableRentention
{
    
    
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
    public void toggleActive()
    {
        if(isActive())
        {
            for(Retention retention : RetentionManager.getLoadedRetentions())
            {
                if(retention instanceof TextRenderingRetentions)
                {
                    retention.setActive(false);
                }
            }
        }
        else
        {
            
            for(Retention retention : RetentionManager.getLoadedRetentions())
            {
                if(retention instanceof TextRenderingRetentions)
                {
                    retention.setActive(true);
                }
            }
        }
        super.toggleActive();
        
    }
    
    @Override
    public String getInfoMessage()
    {
        return "When activated this retention will toggle all text rendering retentions to either on or off.";
    }
    
    @Override
    public String getRetentionIdentifier()
    {
        return this.getClass().getSimpleName();
    }
}
