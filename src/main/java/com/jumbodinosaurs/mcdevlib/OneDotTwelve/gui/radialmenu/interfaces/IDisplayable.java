package com.jumbodinosaurs.mcdevlib.OneDotTwelve.gui.radialmenu.interfaces;


import com.jumbodinosaurs.mcdevlib.OneDotTwelve.gui.radialmenu.buttons.ButtonAction;
import com.jumbodinosaurs.mcdevlib.OneDotTwelve.util.minecraft.GameHelper;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;

public interface IDisplayable
{
    ResourceLocation getIcon();
    
    String getLabel();
    
    void toggle();
    
    default ButtonAction getAction(GuiScreen drawMenu)
    {
      return new ButtonAction() {
          @Override
          public void onClick()
          {
              toggle();
              GameHelper.getInstance().displayGuiScreen((GuiScreen)null);
          }
      };
    };
}
