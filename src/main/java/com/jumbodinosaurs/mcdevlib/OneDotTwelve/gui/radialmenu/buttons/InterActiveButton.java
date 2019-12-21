package com.jumbodinosaurs.mcdevlib.OneDotTwelve.gui.radialmenu.buttons;


import com.jumbodinosaurs.mcdevlib.OneDotTwelve.gui.radialmenu.objects.LabelCon;
import com.jumbodinosaurs.mcdevlib.OneDotTwelve.util.objects.Point;


public abstract class InterActiveButton extends IconButton
{
    protected int baseSize;
    protected int displaySize;
    protected boolean shouldGrow;
    
    public InterActiveButton(LabelCon labelCon, int baseSize)
    {
        super(labelCon);
        this.baseSize = baseSize;
    }
    
    @Override
    public void draw(Point screenCenter)
    {
        if(this.shouldGrow && this.displaySize < this.baseSize + 4)
        {
            this.displaySize++;
        }
        else if(!this.shouldGrow)
        {
            this.displaySize = this.baseSize;
            
        }
        this.shouldGrow = false;
    }
    
    @Override
    public void onHover()
    {
        shouldGrow = true;
    }
}
