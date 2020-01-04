package com.jumbodinosaurs.mcdevlib.OneDotTwelve.retentions;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import com.jumbodinosaurs.devlib.util.GeneralUtil;
import com.jumbodinosaurs.devlib.util.ReflectionUtil;
import com.jumbodinosaurs.mcdevlib.OneDotTwelve.DevLibInitializer;

import java.io.File;
import java.lang.reflect.Type;
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
        for(Retention retention: loadedRetentions)
        {
            retention.register();
        }
    }
    
    public static ArrayList<Retention> getLoadedRetentions()
    {
        return loadedRetentions;
    }
    
    
    
    private static ArrayList<Retention> loadRetentions() throws JsonParseException
    {
        
        Type typeToken = new TypeToken<ArrayList<Retention>>() {}.getType();
        String retentionData = GeneralUtil.scanFileContents(retentionMemory);
        GsonBuilder builder = new GsonBuilder().registerTypeAdapterFactory(GeneralUtil.getContextRuntimeTypeAdapterFactory(retentionData,
                                                                                               Retention.class));
        Gson gson = builder.create();
        ArrayList<Retention> retentions = gson.fromJson(retentionData, typeToken);
        if(retentions != null)
        {
            for(Retention retention : retentions)
            {
                retention.setType(retention.getRetentionIdentifier());
            }
        }
        return retentions;
        
    }
    
    private static void checkForMissingRetentions()
    {
        
        ArrayList<String> capturedClasses = new ArrayList<String>();
        for(Retention retention: loadedRetentions)
        {
            capturedClasses.add(retention.getRetentionIdentifier());
        }
        
        for(Class classType: ReflectionUtil.getSubClasses(Retention.class))
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
    
    private static void saveRetentions(ArrayList<Retention> retentions)
    {
        GsonBuilder builder = new GsonBuilder().setPrettyPrinting()
                                               .registerTypeAdapterFactory(GeneralUtil.getClassPathRuntimeTypeAdapterFactory(Retention.class));
        Gson gson = builder.create();
        String retentionsToString = gson.toJson(retentions);
        GeneralUtil.writeContents(retentionMemory, retentionsToString, false);
    }
}
