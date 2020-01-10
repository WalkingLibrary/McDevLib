package com.mixin;

import com.jumbodinosaurs.devlib.util.GeneralUtil;
import com.jumbodinosaurs.mcdevlib.OneDotTwelve.DevLibInitializer;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
import org.spongepowered.asm.launch.MixinBootstrap;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.Mixins;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Map;

public class Core implements IFMLLoadingPlugin
{
    
    public static final String mixinsSpecialFileName = "poop.txt";
    
    public Core()
    {
        MixinBootstrap.init();
        Mixins.addConfiguration("mixinsDevDummy.json");
        //TODO add Specified Mixins Configuration Loading System
        
        /* Because Mixins is loaded in the Initialise Tweaker Stage We can not utilize some of
         * our libraries and dependencies - Reason for the unsightly IO
         *
         * Reading the Special Mixin File
         * Note: The reason we make a separate special file for the Mixin Option
         *  is because not all the libraries and dependencies are loaded in the classpath
         *  when this bit of code is run. Specifically GSON which is the main library used to
         *  load the options save data. To make life easy we simple right true or false to
         *  poop.txt and read that.
         */
        File dotMinecraftFolder = new File(System.getProperty("user.dir"));
        
        String optionsFilePathLocalPath = File.separator + DevLibInitializer.modDirName + File.separator + mixinsSpecialFileName;
        
        File optionsFile = GeneralUtil.checkForLocalPath(dotMinecraftFolder, optionsFilePathLocalPath);
        
        String specialSettings = GeneralUtil.scanFileContents(optionsFile);
        
        specialSettings = specialSettings.trim();
        specialSettings = specialSettings.toLowerCase();
        
        boolean shouldLoadMixins = true;
    
        if(specialSettings.equals("false"))
        {
            shouldLoadMixins = false;
        }
        
        if(shouldLoadMixins)
        {
            Mixins.addConfiguration("mixinsDev.json");
        }
        
        MixinEnvironment.getDefaultEnvironment().setObfuscationContext("searge");
    }
    
    public static void writeContents(File fileToWrite, String contents, boolean append)
    {
        try
        {
            PrintWriter output = new PrintWriter(new FileOutputStream(fileToWrite, append));
            output.write(contents);
            output.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            System.out.println("Error writing to file");
        }
    }
    
    @Override
    public String[] getASMTransformerClass()
    {
        return new String[0];
    }
    
    @Override
    public String getModContainerClass()
    {
        return null;
    }
    
    @Override
    public String getSetupClass()
    {
        return null;
    }
    
    @Override
    public void injectData(Map<String, Object> data)
    {
    }
    
    @Override
    public String getAccessTransformerClass()
    {
        return null;
    }
}
