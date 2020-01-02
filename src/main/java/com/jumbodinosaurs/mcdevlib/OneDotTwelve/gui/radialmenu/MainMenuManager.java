package com.jumbodinosaurs.mcdevlib.OneDotTwelve.gui.radialmenu;

import com.jumbodinosaurs.devlib.util.ReflectionUtil;
import com.jumbodinosaurs.mcdevlib.OneDotTwelve.gui.radialmenu.buttons.radial.RadialButton;
import com.jumbodinosaurs.mcdevlib.OneDotTwelve.gui.radialmenu.buttons.radial.mainmenu.DefaultCenterButton;
import com.jumbodinosaurs.mcdevlib.OneDotTwelve.gui.radialmenu.buttons.radial.mainmenu.RadialMainMenuButton;
import com.jumbodinosaurs.mcdevlib.OneDotTwelve.gui.radialmenu.util.RadialButtonList;

import java.util.ArrayList;

public class MainMenuManager
{
    
    private static final String centerButtonCategory = "MAIN MENU";
    private static RadialMenu mainMenu;
    
    public static void refreshMenu()
    {
        /* Initializing the Menu
         * Get and Set The Center Button
         * Get and Add all Non Center Buttons
         *
         * Button Refresh Ability????
         */
        
        
        RadialButtonList mainMenuButtonList = new RadialButtonList(new ArrayList<RadialButton>());
        mainMenu = new RadialMenu(mainMenuButtonList);
    
        mainMenu.getButtons().setCenterButton(getCenterButton().getRadialButton());
        //Get and Add all Non Center Buttons
        for(RadialButton button : getNonCenteredMainMenuButtons())
        {
            mainMenuButtonList.add(button);
        }
    
        //Get and Set The Center Button
        
    
    }
    
    private static RadialMainMenuButton getCenterButton()
    {
        /* Getting the Center Button
         * Get all the center buttons with the category of centerButtonCategory
         * Return the center button with the highest priority value or Default if no other center buttons
         */
        
        //Get all the center buttons with the category of centerButtonCategory
        ArrayList<RadialMainMenuButton> centerButtons = new ArrayList<RadialMainMenuButton>();
        for(RadialMainMenuButton mainMenuButton : getMainMenuButtons())
        {
            if(mainMenuButton.getCategory().equals(MainMenuManager.centerButtonCategory))
            {
                centerButtons.add(mainMenuButton);
            }
        }
        
        //Return the center button with the highest priority value or Default if no other center buttons
        RadialMainMenuButton highestPriorityButton = getHighestPriority(centerButtons,
                                                                        MainMenuManager.centerButtonCategory);
        
        return highestPriorityButton == null ? new DefaultCenterButton(): highestPriorityButton;
    }
    
    private static ArrayList<RadialButton> getNonCenteredMainMenuButtons()
    {
        /* Getting the Non-CenteredMainMenuButtons
         * Go over the list of all Implementations of RadialMainMenuButton
         * Add all Non-Center Category Buttons to a List
         * Get a list of all Categories
         * Get and add each of the highest Priority Buttons for there respective Categories to a return value list
         * Return this list
         */
    
    
        ArrayList<RadialMainMenuButton> nonCenterButtons = new ArrayList<RadialMainMenuButton>();
        //Go over the list of all Implementations of RadialMainMenuButton
        for(RadialMainMenuButton mainMenuButton : getMainMenuButtons())
        {
            //Add all Non-Center Category Buttons to a List
            if(!mainMenuButton.getCategory().equals(MainMenuManager.centerButtonCategory))
            {
                nonCenterButtons.add(mainMenuButton);
            }
        }
        
        //Get a list of all Categories
        ArrayList<String> categories = new ArrayList<String>();
        for(RadialMainMenuButton nonCenterButton: nonCenterButtons)
        {
            if(!categories.contains(nonCenterButton.getCategory()))
            {
                categories.add(nonCenterButton.getCategory());
            }
        }
        
        
        //Get and add each of the highest Priority Buttons for there respective Categories to a return value list
        ArrayList<RadialButton> nonCenteredMainMenuButtons = new ArrayList<RadialButton>();
        for(String category: categories)
        {
            nonCenteredMainMenuButtons.add(getHighestPriority(nonCenterButtons, category).getRadialButton());
        }
        
        //Return this list
        return nonCenteredMainMenuButtons;
    }
    
    private static RadialMainMenuButton getHighestPriority(ArrayList<RadialMainMenuButton> buttons,
                                                          String specifiedCategory)
    {
        /* Note: Can Return a null Value if no buttons in the list are under the specifiedCategory
         * Getting the Highest Priority Button of a specified Category within a given list
         * Make a return variable
         * Iterate Over the given list
         * Assign the return value to the current button in the list if higher priority
         */
        
        // Make a return variable
        RadialMainMenuButton highestPriorityButton = null;
        //Iterate Over the given list
        for(RadialMainMenuButton button : buttons)
        {
            if(button.getCategory().equals(specifiedCategory))
            {
                //Assign the return value to the current button in the list if higher priority
                if(highestPriorityButton == null || highestPriorityButton.getDefaultPriority() < button.getDefaultPriority())
                {
                    highestPriorityButton = button;
                }
            }
        }
        
        return highestPriorityButton;
        
    }
    
   
    
    private static ArrayList<RadialMainMenuButton> getMainMenuButtons()
    {
        /* Getting the Main Menu Buttons
         * Get all Implementations of RadialMainMenuButton
         * Make and add an Instance of the Implementation
         */
        
        ArrayList<RadialMainMenuButton> mainMenuButtons = new ArrayList<RadialMainMenuButton>();
        //Get all Implementations of RadialMainMenuButton
        for(Class classType : ReflectionUtil.getSubClasses(RadialMainMenuButton.class))
        {
            try
            {
                //Make and add an Instance of the Implementation
                mainMenuButtons.add((RadialMainMenuButton) classType.newInstance());
            }
            catch(ReflectiveOperationException e)
            {
                e.printStackTrace();
            }
        }
        return mainMenuButtons;
    }
    
    public static RadialMenu getMainMenu()
    {
        return mainMenu;
    }
    
    public static String getCenterButtonCategory()
    {
        return centerButtonCategory;
    }
}
