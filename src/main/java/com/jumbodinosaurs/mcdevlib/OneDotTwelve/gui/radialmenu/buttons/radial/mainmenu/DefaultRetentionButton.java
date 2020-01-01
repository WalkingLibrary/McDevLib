package com.jumbodinosaurs.mcdevlib.OneDotTwelve.gui.radialmenu.buttons.radial.mainmenu;

import com.jumbodinosaurs.mcdevlib.OneDotTwelve.gui.radialmenu.MainMenuManager;
import com.jumbodinosaurs.mcdevlib.OneDotTwelve.gui.radialmenu.RadialMenu;
import com.jumbodinosaurs.mcdevlib.OneDotTwelve.gui.radialmenu.RadialMenuUtil;
import com.jumbodinosaurs.mcdevlib.OneDotTwelve.gui.radialmenu.ResourceLocationUtil;
import com.jumbodinosaurs.mcdevlib.OneDotTwelve.gui.radialmenu.buttons.ButtonAction;
import com.jumbodinosaurs.mcdevlib.OneDotTwelve.gui.radialmenu.buttons.radial.RadialButton;
import com.jumbodinosaurs.mcdevlib.OneDotTwelve.gui.radialmenu.util.LabelCon;
import com.jumbodinosaurs.mcdevlib.OneDotTwelve.retentions.RetentionManager;
import com.jumbodinosaurs.mcdevlib.OneDotTwelve.util.minecraft.GameHelper;
import net.minecraft.util.ResourceLocation;

public class DefaultRetentionButton extends RadialMainMenuButton
{
    
    protected static final String category = "Retentions";
    
    @Override
    public RadialButton getRadialButton()
    {
        ResourceLocation devLibIcon = ResourceLocationUtil.retentionIcon;
        LabelCon defaultRetentionLabelCon = new LabelCon(category, devLibIcon,  getDefaultAction());
        return RadialMenuUtil.getRadialButton(defaultRetentionLabelCon);
    }
    
    public ButtonAction getDefaultAction()
    {
        return new ButtonAction() {
            @Override
            public void onClick()
            {
                RadialMenu retentionMenu = RadialMenuUtil.getMenu(MainMenuManager.getMainMenu(), RetentionManager.getLoadedRetentions(),
                                                                  0);
                retentionMenu.getButtons().setCenterButton(RadialMenuUtil.getCenterButton(getRadialButton().getLabelCon()));
                GameHelper.getInstance().displayGuiScreen(retentionMenu);
            }
        };
    }
    
    @Override
    public int getDefaultPriority()
    {
        return Integer.MIN_VALUE;
    }
}
