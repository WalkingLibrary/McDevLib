package com.jumbodinosaurs.mcdevlib.OneDotTwelve.retentions;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import com.google.typeadapters.gson.RuntimeTypeAdapterFactory;
import com.jumbodinosaurs.devlib.util.GeneralUtil;
import com.jumbodinosaurs.mcdevlib.OneDotTwelve.util.minecraft.GameHelper;
import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfoList;
import io.github.classgraph.ScanResult;

import java.io.File;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class RetentionManager
{
    
    public static File modDir = GeneralUtil.checkFor(GameHelper.minecraftDir, "Omega Utility");
    public static File retentionMemory = GeneralUtil.checkFor(modDir, "retentionMemory.json");
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
        for(Retention retention: loadedRetentions)
        {
            retention.register();
        }
    }
    
    public static ArrayList<Retention> getLoadedRetentions()
    {
        return loadedRetentions;
    }
    
    
    private static RuntimeTypeAdapterFactory<Retention> getRetentionAdapter(String context)
    {
        RuntimeTypeAdapterFactory<Retention> adapterFactory = RuntimeTypeAdapterFactory.of(Retention.class, "type");
        for(Class classType : getRetentionSubClasses())
        {
            if(context.contains(classType.getSimpleName()))
            {
                adapterFactory.registerSubtype(classType, classType.getSimpleName());
            }
        }
        return adapterFactory;
    }
    
    private static RuntimeTypeAdapterFactory<Retention> getDefaultRetentionAdapter()
    {
        
        RuntimeTypeAdapterFactory<Retention> adapterFactory = RuntimeTypeAdapterFactory.of(Retention.class, "type");
        for(Class classType : getRetentionSubClasses())
        {
            adapterFactory.registerSubtype(classType, classType.getSimpleName());
        }
        return adapterFactory;
    }
    
    private static ArrayList<Class> getRetentionSubClasses()
    {
        try(ScanResult scanResult = new ClassGraph().enableAllInfo().scan())
        {
            ClassInfoList controlClasses = scanResult.getSubclasses(Retention.class.getCanonicalName());
            List<Class<?>> controlClassRefs = controlClasses.loadClasses();
            ArrayList<Class> classes = new ArrayList<Class>();
            for(Class classType : controlClassRefs)
            {
                try
                {
                    if(!Modifier.isAbstract(classType.getModifiers()))
                    {
                        classes.add(Class.forName(classType.getCanonicalName()));
                    }
                }
                catch(ClassNotFoundException e)
                {
                    System.out.println("Could Not find the Class " + classType.getSimpleName());
                }
            }
            return classes;
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return new ArrayList<Class>();
    }
    
    private static ArrayList<Retention> loadRetentions() throws JsonParseException
    {
        
        Type typeToken = new TypeToken<ArrayList<Retention>>() {}.getType();
        String retentionData = GeneralUtil.scanFileContents(retentionMemory);
        GsonBuilder builder = new GsonBuilder().registerTypeAdapterFactory(getRetentionAdapter(retentionData));
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
        
        for(Class classType: getRetentionSubClasses())
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
                                               .registerTypeAdapterFactory(getDefaultRetentionAdapter());
        Gson gson = builder.create();
        String retentionsToString = gson.toJson(retentions);
        GeneralUtil.writeContents(retentionMemory, retentionsToString, false);
    }
}
