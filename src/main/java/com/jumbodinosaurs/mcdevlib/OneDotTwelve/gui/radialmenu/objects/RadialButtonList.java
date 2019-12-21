package com.jumbodinosaurs.mcdevlib.OneDotTwelve.gui.radialmenu.objects;


import com.jumbodinosaurs.mcdevlib.OneDotTwelve.gui.radialmenu.buttons.RadialButton;

import java.util.ArrayList;

public class RadialButtonList
{
    RadialButton centerButton;
    ArrayList<RadialButton> buttons = new ArrayList<RadialButton>();
    
    
    public RadialButtonList(ArrayList<RadialButton> buttons)
    {
        this.buttons = buttons;
        updateButtons();
    }
    
    
    public void updateButtons()
    {
        for(int i = 0; i < buttons.size(); i++)
        {
            buttons.get(i).setListSize(buttons.size() - (centerButton != null ? 1 : 0));
            buttons.get(i).setListPosition(i);
        }
    }
    
    public void add(RadialButton button)
    {
        add((centerButton != null ? this.buttons.size() - 1 : this.buttons.size()), button);
    }
    
    public void add(int index, RadialButton button)
    {
        buttons.add(index, button);
        updateButtons();
    }
    
    public void remove(RadialButton button)
    {
        buttons.remove(button);
        updateButtons();
    }
    
    public ArrayList<RadialButton> getButtons()
    {
        return buttons;
    }
    
    public RadialButton getCenterButton()
    {
        return centerButton;
    }
    
    public void setCenterButton(RadialButton centerButton)
    {
        this.centerButton = centerButton;
        add(this.buttons.size(), centerButton);
    }
    
    
}
