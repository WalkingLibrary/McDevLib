package com.jumbodinosaurs.mcdevlib.OneDotTwelve.commands;

import com.jumbodinosaurs.devlib.commands.CommandWithParameters;
import com.jumbodinosaurs.devlib.commands.MessageResponse;
import com.jumbodinosaurs.mcdevlib.OneDotTwelve.retentions.BindableRentention;
import com.jumbodinosaurs.mcdevlib.OneDotTwelve.retentions.Retention;
import com.jumbodinosaurs.mcdevlib.OneDotTwelve.retentions.RetentionManager;
import com.jumbodinosaurs.mcdevlib.OneDotTwelve.util.minecraft.DevLibKeyBindManager;
import org.lwjgl.input.Keyboard;

public class Bind extends CommandWithParameters
{
    //Retentions in FMLInitilizer
    
 
    
    @Override
    public MessageResponse getExecutedMessage()
    {
        MessageResponse response = null;
        if(this.getParameters().size() > 1)
        {
            String retentionToToggle = this.getParameters().get(0).getParameter();
            String keyToBindTo = this.getParameters().get(1).getParameter();
            int keyCode = DevLibKeyBindManager.mapStringToKeyCode(keyToBindTo);
            if(keyCode != -1)
            {
                boolean retentionExists = false;
                for(Retention retention : RetentionManager.getLoadedRetentions())
                {
                    if(retention.getRetentionIdentifier().equals(retentionToToggle))
                    {
                        retentionExists = true;
                        ((BindableRentention) retention).bind(keyCode);
                        response = new MessageResponse(retentionToToggle + " is now binded to " + Keyboard.getKeyName(keyCode));
                        break;
                    }
                }
    
                if(!retentionExists)
                {
                    response = new MessageResponse("No Retention Named: " + retentionToToggle);
                }
            }
            else
            {
                response = new MessageResponse("Problem Binding to " + keyToBindTo + ".");
            }
        }
        else
        {
            response = new MessageResponse("No Retention given to Bind.");
        }
        RetentionManager.saveRetentions();
        return response;
    }
    
    @Override
    public String getHelpMessage()
    {
        return "Enter ~" + getCommand() + " followed by the Name of the Retention and the key you wish to bind. This " +
                       "will bind that retention to that key. Example ~"+ getCommand() + " AntiAFK g";
    }
}
