package com.jumbodinosaurs.mcdevlib.OneDotTwelve.commands;

import com.jumbodinosaurs.devlib.commands.CommandWithParameters;
import com.jumbodinosaurs.devlib.commands.MessageResponse;
import com.jumbodinosaurs.mcdevlib.OneDotTwelve.retentions.Retention;
import com.jumbodinosaurs.mcdevlib.OneDotTwelve.retentions.RetentionManager;

import java.lang.reflect.Field;

public class ListRetentions extends CommandWithParameters
{
    
    @Override
    public MessageResponse getExecutedMessage()
    {
        MessageResponse response = null;
        if(this.getParameters().size() > 0)
        {
            String identifier = this.getParameters().get(0).getParameter();
        
            for(Retention retention: RetentionManager.getLoadedRetentions())
            {
                if(retention.getRetentionIdentifier().equals(identifier))
                {
                    Field[] fields = retention.getClass().getFields();
                    String fieldMessage = "Fields:";
                    for(Field field: fields)
                    {
                        fieldMessage += "\n" + field.getName();
                    }
                    response = new MessageResponse(fieldMessage);
                }
            }
        
            if(response == null)
            {
                response = new MessageResponse("No Retention or Command Named: " + identifier);
            }
        
        }
        else
        {
            String retentionsToList = "Retentions:";
            for(Retention retention: RetentionManager.getLoadedRetentions())
            {
                retentionsToList += "\n" + retention.getRetentionIdentifier();
            }
            response = new MessageResponse(retentionsToList);
        }
        return response;
    }
    
    @Override
    public String getHelpMessage()
    {
        return "Enter " + getCommand() + " to list all the available retentions";
    }
}
