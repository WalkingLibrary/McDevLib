package com.jumbodinosaurs.mcdevlib.OneDotTwelve.retentions;

import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import com.jumbodinosaurs.devlib.json.GsonUtil;
import com.jumbodinosaurs.devlib.reflection.ReflectionUtil;
import com.jumbodinosaurs.devlib.util.GeneralUtil;
import com.jumbodinosaurs.mcdevlib.OneDotTwelve.DevLibInitializer;

import java.io.File;
import java.util.ArrayList;

public class RetentionManager
{
    
    public static File retentionMemory = GeneralUtil.checkFor(DevLibInitializer.modDir, "retentionMemory.json");
    private static ArrayList<Retention> loadedRetentions;
    
    
    public static void initializeRetentions()
    {
        //initCommands and retentions
        loadedRetentions = loadRetentions();
        if(loadedRetentions == null)
        {
            loadedRetentions = new ArrayList<Retention>();
        }
        checkForMissingRetentions();
        saveRetentions(loadedRetentions);
        /*
         * In order for Forge Events to fire in there respective Classes they need to be added to
         * the forge event bus.*/
        for(Retention retention : loadedRetentions)
        {
            retention.register();
        }
    }
    
    public static ArrayList<Retention> getLoadedRetentions()
    {
        return loadedRetentions;
    }
    
    
    
    
    private static void checkForMissingRetentions()
    {
        
        ArrayList<String> capturedClasses = new ArrayList<String>();
        for(Retention retention : loadedRetentions)
        {
            capturedClasses.add(retention.getRetentionIdentifier());
        }
        
        for(Class classType : ReflectionUtil.getSubClasses(Retention.class))
        {
            if(!capturedClasses.contains(classType.getSimpleName()))
            {
                try
                {
                    Retention newRetention = (Retention) classType.newInstance();
                    newRetention.setType(newRetention.getRetentionIdentifier());
                    loadedRetentions.add(newRetention);
                }
                catch(Exception e)
                {
                    System.out.println(classType.getSimpleName() + " failed to load.");
                }
            }
        }
    }
    
    
    private static ArrayList<Retention> loadRetentions() throws JsonParseException
    {
        ArrayList<Retention> retentions = new ArrayList<Retention>();
        try
        {
            retentions = GsonUtil.readObjectHoldersList(retentionMemory, Retention.class,
                                                        new TypeToken<ArrayList<Retention>>(){});
        }
        catch(JsonParseException e)
        {
            System.out.println("Error Parsing Retention Data Throwing out data");
        }
        
        if(retentions != null)
        {
            for(Retention retention : retentions)
            {
                retention.setType(retention.getRetentionIdentifier());
            }
        }
        return retentions;
    }
    
    public static void saveRetentions()
    {
        saveRetentions(loadedRetentions);
    }
    
    private static void saveRetentions(ArrayList<Retention> retentions)
    {
        GsonUtil.saveObjectsToHolderList(retentionMemory, retentions, Retention.class);
    }
}
