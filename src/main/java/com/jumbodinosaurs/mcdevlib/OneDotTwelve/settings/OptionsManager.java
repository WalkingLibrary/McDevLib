package com.jumbodinosaurs.mcdevlib.OneDotTwelve.settings;

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
        
        Type typeToken = new TypeToken<ArrayList<Option>>() {}.getType();
        String optionData = GeneralUtil.scanFileContents(optionsJson);
        GsonBuilder builder = new GsonBuilder().registerTypeAdapterFactory(GeneralUtil.getContextRuntimeTypeAdapterFactory(
                optionData,
                Option.class));
        Gson gson = builder.create();
        ArrayList<Option> options = gson.fromJson(optionData, typeToken);
        if(options != null)
        {
            for(Option option : options)
            {
                option.setType(option.getIdentifier());
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
        GsonBuilder builder = new GsonBuilder().setPrettyPrinting()
                                               .registerTypeAdapterFactory(GeneralUtil.getClassPathRuntimeTypeAdapterFactory(
                                                       Option.class));
        Gson gson = builder.create();
        String retentionsToString = gson.toJson(options);
        GeneralUtil.writeContents(optionsJson, retentionsToString, false);
    }
    
    
    public static ArrayList<Option> getLoadedOptions()
    {
        return loadedOptions;
    }
    
    public static Option getOption(String simpleName)
    {
        for(Option option: getLoadedOptions())
        {
            if(option.getType().equals(simpleName))
            {
                return option;
            }
        }
        return null;
    }
}
