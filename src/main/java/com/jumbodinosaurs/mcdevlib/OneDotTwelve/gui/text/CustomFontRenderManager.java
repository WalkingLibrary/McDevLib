package com.jumbodinosaurs.mcdevlib.OneDotTwelve.gui.text;

import com.jumbodinosaurs.mcdevlib.OneDotTwelve.util.minecraft.GameHelper;
import net.minecraft.util.ResourceLocation;

public class CustomFontRenderManager
{
    private static ResourceLocation customFont = new ResourceLocation("client", "ascii.png");
    public static ScaledFontRenderer scaleableRender = new ScaledFontRenderer(GameHelper.getInstance().gameSettings,
                                                                              customFont,
                                                                              GameHelper.getInstance().renderEngine,
                                                                              false);
    
}
