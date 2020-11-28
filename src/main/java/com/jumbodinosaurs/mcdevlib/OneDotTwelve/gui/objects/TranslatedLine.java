package com.jumbodinosaurs.mcdevlib.OneDotTwelve.gui.objects;

import java.awt.*;

public class TranslatedLine extends Line
{
    
    
    public TranslatedLine(TraslationPoint pointOne, TraslationPoint pointTwo, Color color)
    {
        super(pointOne, pointTwo, color);
    }
    
    public Line getTranslateLine(float partialTicks)
    {
        TraslationPoint translatePointOne = (TraslationPoint) getPointOne();
        TraslationPoint translatePointTwo = (TraslationPoint) getPointTwo();
        translatePointOne = translatePointOne.getEntityTranslated(translatePointOne.getTranslateEntity(), partialTicks);
        translatePointTwo = translatePointTwo.getEntityTranslated(translatePointTwo.getTranslateEntity(), partialTicks);
        return new Line(translatePointOne, translatePointTwo, this.getColor());
    }
}
