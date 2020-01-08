package com.jumbodinosaurs.mcdevlib.OneDotTwelve.commands;

import com.jumbodinosaurs.devlib.commands.CommandWithParameters;
import com.jumbodinosaurs.devlib.commands.MessageResponse;
import com.jumbodinosaurs.mcdevlib.OneDotTwelve.retentions.Retention;
import com.jumbodinosaurs.mcdevlib.OneDotTwelve.retentions.RetentionManager;

public class ToggleRetention extends CommandWithParameters
{
    
    
    
    @Override
    public MessageResponse getExecutedMessage()
    {
        MessageResponse response = null;
        if(this.getParameters().size() > 0)
        {
            String retentionToToggle = this.getParameters().get(0).getParameter();
            boolean toggled = false;
            for(Retention retention: RetentionManager.getLoadedRetentions())
            {
                if(retention.getRetentionIdentifier().equals(retentionToToggle))
                {
                    toggled = true;
                    retention.toggleActive();
                    response = new MessageResponse(retentionToToggle + " is now " + (retention.isActive()? "Enabled":
                                                                                     "Disabled"));
                    break;
                }
            }
          
            if(!toggled)
            {
                response = new MessageResponse("No Retention Named: " + retentionToToggle);
            }
            
        }
        else
        {
            response = new MessageResponse("No Retention given to Toggle.");
        }
    
        RetentionManager.saveRetentions();
        return response;
    }
    
    @Override
    public String getHelpMessage()
    {
        return "Enter " + getCommand() + " followed by the name of the retention you'd like to turn on or off.\n" +
                       "    Example ~"+ getCommand() + " ModMenu";
    }
}
