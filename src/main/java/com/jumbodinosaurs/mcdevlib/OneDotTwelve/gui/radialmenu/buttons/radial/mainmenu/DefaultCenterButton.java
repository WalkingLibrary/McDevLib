package com.jumbodinosaurs.mcdevlib.OneDotTwelve.gui.radialmenu.buttons.radial.mainmenu;

import com.jumbodinosaurs.mcdevlib.OneDotTwelve.gui.radialmenu.MainMenuManager;
import com.jumbodinosaurs.mcdevlib.OneDotTwelve.gui.radialmenu.RadialMenuUtil;
import com.jumbodinosaurs.mcdevlib.OneDotTwelve.gui.radialmenu.ResourceLocationUtil;
import com.jumbodinosaurs.mcdevlib.OneDotTwelve.gui.radialmenu.buttons.radial.RadialButton;
import com.jumbodinosaurs.mcdevlib.OneDotTwelve.gui.radialmenu.util.LabelCon;
import net.minecraft.util.ResourceLocation;

public class DefaultCenterButton extends RadialMainMenuButton
{
    @Override
    public RadialButton getRadialButton()
    {
        ResourceLocation devLibIcon = ResourceLocationUtil.devLibIcon;
        LabelCon defaultLabelCon = new LabelCon(MainMenuManager.getCenterButtonCategory(), devLibIcon, RadialMenuUtil.getEmptyAction());
        return RadialMenuUtil.getCenterButton(defaultLabelCon);
    }
    
    @Override
    public int getDefaultPriority()
    {
        return Integer.MIN_VALUE;
    }
}
