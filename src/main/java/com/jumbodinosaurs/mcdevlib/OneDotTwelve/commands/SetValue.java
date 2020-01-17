package com.jumbodinosaurs.mcdevlib.OneDotTwelve.commands;

import com.jumbodinosaurs.devlib.commands.CommandWithParameters;
import com.jumbodinosaurs.devlib.commands.MessageResponse;
import com.jumbodinosaurs.devlib.util.ReflectionUtil;
import com.jumbodinosaurs.mcdevlib.OneDotTwelve.retentions.Retention;
import com.jumbodinosaurs.mcdevlib.OneDotTwelve.retentions.RetentionManager;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class SetValue extends CommandWithParameters
{
    
    
    
    @Override
    public MessageResponse getExecutedMessage()
    {
        MessageResponse response = null;
        if(this.getParameters().size() > 2)
        {
            String retentionToSet = this.getParameters().get(0).getParameter();
            String fieldToChange = this.getParameters().get(1).getParameter();
            String value = this.getParameters().get(2).getParameter();
            
            for(Retention retention: RetentionManager.getLoadedRetentions())
            {
                if(retention.getRetentionIdentifier().equals(retentionToSet))
                {
                    
                        //gotta do the reflections flexin on you
                        Class<?> superClass = retention.getClass().getSuperclass();
                        ArrayList<Class<?>> superClasses = new ArrayList<Class<?>>();
                        superClasses.add(retention.getClass());
                        while(!superClass.equals(Retention.class.getSuperclass()))
                        {
                            superClasses.add(superClass);
                            superClass = superClass.getSuperclass();
                        }
    
                        for(Class<?> classType:superClasses)
                        {
                            System.out.println(classType.getSimpleName());
                            try
                            {
                                Field field = classType.getDeclaredField(fieldToChange);
                                boolean success = ReflectionUtil.set(field, retention, value, field.getType());
                                if(success)
                                {
                                    response = new MessageResponse(retentionToSet + "'s field named " + fieldToChange + " has " + "been changed to " + value);
                                    break;
                                }
                                else
                                {
                                    response = new MessageResponse("Could not set field value");
                                    break;
                                }
                            }
                            catch(NoSuchFieldException e)
                            {
                                response =
                                        new MessageResponse(retentionToSet + " does not contain a field named " + fieldToChange);
                            }
                        }
                    
                    break;
                }
            }
        
            if(response == null)
            {
                response = new MessageResponse("No Retention Named: " + retentionToSet);
            }
        
        }
        else
        {
            response = new MessageResponse("No Retention given to Change.");
        }
    
        RetentionManager.saveRetentions();
        return response;
    }
    
    @Override
    public String getHelpMessage()
    {
        return "Allows The user to set Retention field values. Example ~" + getCommand() + " AutoLog threshold 5";
    }
}
