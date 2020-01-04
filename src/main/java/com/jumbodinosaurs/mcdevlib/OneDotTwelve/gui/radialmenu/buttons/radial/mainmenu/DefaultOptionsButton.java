package com.jumbodinosaurs.mcdevlib.OneDotTwelve.gui.radialmenu.buttons.radial.mainmenu;

import com.jumbodinosaurs.mcdevlib.OneDotTwelve.gui.radialmenu.MainMenuManager;
import com.jumbodinosaurs.mcdevlib.OneDotTwelve.gui.radialmenu.RadialMenu;
import com.jumbodinosaurs.mcdevlib.OneDotTwelve.gui.radialmenu.RadialMenuUtil;
import com.jumbodinosaurs.mcdevlib.OneDotTwelve.gui.radialmenu.ResourceLocationUtil;
import com.jumbodinosaurs.mcdevlib.OneDotTwelve.gui.radialmenu.buttons.ButtonAction;
import com.jumbodinosaurs.mcdevlib.OneDotTwelve.gui.radialmenu.buttons.radial.RadialButton;
import com.jumbodinosaurs.mcdevlib.OneDotTwelve.gui.radialmenu.util.LabelCon;
import com.jumbodinosaurs.mcdevlib.OneDotTwelve.settings.OptionsManager;
import com.jumbodinosaurs.mcdevlib.OneDotTwelve.util.minecraft.GameHelper;
import net.minecraft.util.ResourceLocation;

public class DefaultOptionsButton extends RadialMainMenuButton
{
    
    protected static final String category = "Options";
    
    @Override
    public RadialButton getRadialButton()
    {
        ResourceLocation optionsIcon = ResourceLocationUtil.optionsIcon;
        LabelCon defaultLabelCon = new LabelCon(category, optionsIcon, getDefaultAction());
        return RadialMenuUtil.getRadialButton(defaultLabelCon);
    }
    
    public ButtonAction getDefaultAction()
    {
        return new ButtonAction() {
            @Override
            public void onClick()
            {
                RadialMenu optionMenu = RadialMenuUtil.getMenu(MainMenuManager.getMainMenu(),
                                                                  OptionsManager.getLoadedOptions(),
                                                                  0);
                optionMenu.getButtons().setCenterButton(RadialMenuUtil.getCenterButton(getRadialButton().getLabelCon()));
                GameHelper.getInstance().displayGuiScreen(optionMenu);
            }
        };
    }
    
    @Override
    public int getDefaultPriority()
    {
        return Integer.MIN_VALUE;
    }
}
