package com.jumbodinosaurs.mcdevlib.OneDotTwelve.gui.radialmenu.interfaces;

public interface IInteractive
{
    boolean shouldGrow();
    
    int getBaseSize();
    
    int getDisplaySize();
    
    int getMaxSize();
    
    void setMaxSize(int maxSize);
    
    void setDisplaySize(int displaySize);
    
    void setBaseSize(int baseSize);
    
    void setShouldGrow(boolean shouldGrow);
    
    default void onHover()
    {
        setShouldGrow(true);
    }
}
