package com.jumbodinosaurs.mcdevlib.OneDotTwelve.util.minecraft;

import com.jumbodinosaurs.mcdevlib.OneDotTwelve.retentions.BindableRentention;
import com.jumbodinosaurs.mcdevlib.OneDotTwelve.retentions.Retention;
import com.jumbodinosaurs.mcdevlib.OneDotTwelve.retentions.RetentionManager;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;

public class DevLibKeyBindManager
{
    public static ArrayList<KeyBinding> clientBindings = new ArrayList<KeyBinding>();
    
    
    public static void registerKeyBinds()
    {
    
        for(Retention retention: RetentionManager.getLoadedRetentions())
        {
            if(retention instanceof BindableRentention && ((BindableRentention)retention).getBinding() != null)
            {
    
                ClientRegistry.registerKeyBinding(((BindableRentention)retention).getBinding());
            }
        }
    }
    
    public static void registerKeyBinds(KeyBinding keyBinding)
    {
        ClientRegistry.registerKeyBinding(keyBinding);
    }
    
    
    public static int mapStringToKeyCode(String code)
    {
        code = code.toUpperCase();
        ArrayList<String> keysMapped = getMappedKeyList();
        int count = 0;
        for(String key: keysMapped)
        {
            if(code.equals(key))
            {
                return count;
            }
            count++;
        }
        return 0;//0 is the same as none in keyboard lwjgl
    }
    
    
    public static ArrayList<String> getMappedKeyList()
    {
        ArrayList<String> keysMapped = new ArrayList<String>();
        for(int i = 0; i < 256; i++)
        {
            keysMapped.add(Keyboard.getKeyName(i));
        }
        return  keysMapped;
    }
    
    
}
