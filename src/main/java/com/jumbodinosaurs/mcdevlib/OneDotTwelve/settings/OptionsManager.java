package com.jumbodinosaurs.mcdevlib.OneDotTwelve.settings;

import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import com.jumbodinosaurs.devlib.util.GeneralUtil;
import com.jumbodinosaurs.devlib.util.GsonUtil;
import com.jumbodinosaurs.devlib.util.ReflectionUtil;
import com.jumbodinosaurs.mcdevlib.OneDotTwelve.DevLibInitializer;

import java.io.File;
import java.util.ArrayList;

public class OptionsManager
{
    public static File optionsJson = GeneralUtil.checkFor(DevLibInitializer.modDir, "options.json");
    private static ArrayList<Option> loadedOptions;
    
    
    
    
    
    public static void initialiseSettings()
    {
        /*
         * Loading The Settings
         * Read and Load in options.json into respective Option Objects
         * Check Runtime ClassPath for New Options
         * Add and Combine The New option objects with the loaded Option objects To loadedOption
         * Save The New loadedOptionsList
         * */
        
        //Read and Load in options.json into respective Option Objects
        loadedOptions = loadOptions();
        if(loadedOptions == null)
        {
            loadedOptions = new ArrayList<Option>();
        }
        //Check Runtime ClassPath for New Options
        //Add and Combine The New option objects with the loaded Option objects To loadedOption
        checkForMissingOptions();
        
        //Save The New loadedOptionsList
        saveOptions(loadedOptions);
        
    }
    
    
    private static ArrayList<Option> loadOptions() throws JsonParseException
    {
    
        ArrayList<Option> options = new ArrayList<Option>();
        try
        {
            options = GsonUtil.readObjectHoldersList(optionsJson, Option.class,
                                                        new TypeToken<ArrayList<Option>>(){});
        }
        catch(JsonParseException e)
        {
            System.out.println("Error Parsing Option Data Throwing out data");
        }
    
        if(options != null)
        {
            for(Option option : options)
            {
                option.setType(option.getClass().getSimpleName());
            }
        }
        return options;
        
    }
    
    private static void checkForMissingOptions()
    {
        
        ArrayList<String> capturedClasses = new ArrayList<String>();
        for(Option option : loadedOptions)
        {
            capturedClasses.add(option.getIdentifier());
        }
        
        for(Class classType : ReflectionUtil.getSubClasses(Option.class))
        {
            if(!capturedClasses.contains(classType.getSimpleName()))
            {
                try
                {
                    Option newOption = (Option) classType.newInstance();
                    newOption.setType(newOption.getIdentifier());
                    newOption.setActive(newOption.getDefaultState());
                    loadedOptions.add(newOption);
                }
                catch(Exception e)
                {
                    System.out.println(classType.getSimpleName() + " failed to load.");
                }
            }
        }
        
    }
    
    public static void saveOptions(ArrayList<Option> options)
    {
        GsonUtil.saveObjectsToHolderList(optionsJson, options, Option.class);
    }
    
    
    public static ArrayList<Option> getLoadedOptions()
    {
        return loadedOptions;
    }
    
    public static Option getOption(String canonicalName)
    {
        for(Option option: getLoadedOptions())
        {
            if(option.getClass().getCanonicalName().equals(canonicalName))
            {
                return option;
            }
        }
        return null;
    }
}
