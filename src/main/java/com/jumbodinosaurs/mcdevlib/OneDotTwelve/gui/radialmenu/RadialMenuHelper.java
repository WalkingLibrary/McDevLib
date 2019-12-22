package com.jumbodinosaurs.mcdevlib.OneDotTwelve.gui.radialmenu;


import com.jumbodinosaurs.mcdevlib.OneDotTwelve.gui.radialmenu.buttons.ButtonAction;
import com.jumbodinosaurs.mcdevlib.OneDotTwelve.gui.radialmenu.buttons.RadialButton;
import com.jumbodinosaurs.mcdevlib.OneDotTwelve.gui.radialmenu.interfaces.IDisplayable;
import com.jumbodinosaurs.mcdevlib.OneDotTwelve.gui.radialmenu.objects.LabelCon;
import com.jumbodinosaurs.mcdevlib.OneDotTwelve.gui.radialmenu.objects.RadialButtonList;
import com.jumbodinosaurs.mcdevlib.OneDotTwelve.gui.radialmenu.objects.RadialMenu;
import com.jumbodinosaurs.mcdevlib.OneDotTwelve.util.minecraft.GameHelper;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;

public class RadialMenuHelper
{
    public static final int size = 35;
    public static final int subRadius = size;
    public static final int mainRadius = (int) (size * 3.5);
    public static final int iconSize = (int) (size * .75);
    
    
    public static RadialMenu getMenu(RadialMenu parentMenu, ArrayList<?> objects, int index)
    {
        ArrayList<RadialButton> radialButtons = new ArrayList<RadialButton>();
        RadialButtonList buttonList = new RadialButtonList(radialButtons);
        RadialMenu menu = new RadialMenu(buttonList);
        int maxLabelConAmount = 6;
        boolean needsNextButton = false;
        for(int i = index; i < objects.size(); i++)
        {
            Object current = objects.get(i);
            if(current instanceof IDisplayable)
            {
                LabelCon labelCon = new LabelCon(((IDisplayable) current).getIcon(),
                                                 ((IDisplayable) current).getLabel(),
                                                 ((IDisplayable) current).getAction(menu));
                RadialButton button = new RadialButton(false, labelCon, iconSize);
                radialButtons.add(button);
            }
            
            if(radialButtons.size() == 6)
            {
                needsNextButton = true;
                break;
            }
        }
        
        
        RadialButton backButton = getBackButton(parentMenu);
        buttonList.add(backButton);
        
        if(needsNextButton)
        {
            RadialButton nextButton = getNextButton(getMenu(menu, objects, index + maxLabelConAmount + 1));
            buttonList.add(0, nextButton);
        }
        return menu;
        
    }
    
    
    public static ButtonAction getEmptyAction()
    {
        return new ButtonAction()
        {
            @Override
            public void onClick()
            {
            
            }
        };
    }
    
    
    public static RadialButton getCenterButton(LabelCon labelCon)
    {
        return new RadialButton(true, labelCon, (int) (iconSize * 2.5))
        {
            @Override
            public void onClick(int btn)
            {
                labelCon.getAction().onClick();
            }
        };
    }
    
    public static RadialButton getNextButton(RadialMenu subMenu)
    {
        ResourceLocation rightArrowIcon = ResourceLocationHelper.arrowRightIcon;
        ButtonAction nextAction = new ButtonAction()
        {
            @Override
            public void onClick()
            {
                GameHelper.getInstance().displayGuiScreen(subMenu);
            }
        };
        LabelCon labelCon = new LabelCon(rightArrowIcon, "Next Page", nextAction);
        RadialButton nextButton = new RadialButton(false, labelCon, iconSize);
        return nextButton;
    }
    
    
    public static RadialButton getBackButton(RadialMenu parentMenu)
    {
        ResourceLocation backArrowIcon = ResourceLocationHelper.arrowLeftIcon;
        ButtonAction backAction = new ButtonAction()
        {
            @Override
            public void onClick()
            {
                GameHelper.getInstance().displayGuiScreen(parentMenu);
            }
        };
        LabelCon labelCon = new LabelCon(backArrowIcon, "Back", backAction);
        RadialButton backButton = new RadialButton(false, labelCon, iconSize);
        return backButton;
    }
    
    
    public static int getSize()
    {
        return size;
    }
    
    
    public static int getSubRadius()
    {
        return subRadius;
    }
    
    
    public static int getMainRadius()
    {
        return mainRadius;
    }
    
    
    public static int getIconSize()
    {
        return iconSize;
    }
    
}
