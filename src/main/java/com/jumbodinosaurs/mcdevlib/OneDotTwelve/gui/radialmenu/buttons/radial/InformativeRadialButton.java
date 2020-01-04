package com.jumbodinosaurs.mcdevlib.OneDotTwelve.gui.radialmenu.buttons.radial;

import com.jumbodinosaurs.mcdevlib.OneDotTwelve.gui.radialmenu.interfaces.IInformative;
import com.jumbodinosaurs.mcdevlib.OneDotTwelve.gui.radialmenu.util.LabelCon;

public class InformativeRadialButton extends RadialButton
{
    private IInformative informativeObject;
    
    public InformativeRadialButton(boolean drawCentered,
                                   LabelCon labelCon,
                                   int baseSize,
                                   IInformative informativeObject)
    {
        super(drawCentered, labelCon, baseSize);
        this.informativeObject = informativeObject;
    }
    
    public String getToolTip()
    {
        return this.informativeObject.getDisplayInfo();
    }
}
