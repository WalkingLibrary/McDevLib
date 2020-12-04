package com.jumbodinosaurs.mcdevlib.OneDotTwelve.gui.text;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.util.ResourceLocation;

public class ScaledFontRenderer extends FontRenderer
{
    
    public static final int DEFAULT_SCALE = 9;
    public static int drawRegularThreshold = 1;
    private int scale = DEFAULT_SCALE;
    
    public ScaledFontRenderer(GameSettings gameSettingsIn,
                              ResourceLocation location,
                              TextureManager textureManagerIn,
                              boolean unicode)
    {
        super(gameSettingsIn, location, textureManagerIn, unicode);
    }
    
    @Override
    protected float renderDefaultChar(int ch, boolean italic)
    {
        if(this.scale <= drawRegularThreshold)
        {
            return super.renderDefaultChar(ch, italic);
        }
        int spriteStartPlaceX = ch % 16 * 8;
        int spriteStartPlaceY = ch / 16 * 8;
        bindTexture(this.locationFontTexture);
        int characterLength = this.charWidth[ch];
        int defaultSpacing = italic ? 1 : 0;
        float characterSpriteWidth = (float) characterLength - 0.01F;
        float characterSpriteHeight = 7.99F;
        characterLength = characterLength * scale;
        int characterSpacing = (defaultSpacing + 1) * scale;
        float characterRenderHeight = characterSpriteHeight * scale;
    
        GlStateManager.glBegin(5);
        GlStateManager.glTexCoord2f((float) spriteStartPlaceX / 128.0F, (float) spriteStartPlaceY / 128.0F);
    
        GlStateManager.glVertex3f(this.posX, this.posY - characterRenderHeight, 0.0F);
    
        GlStateManager.glTexCoord2f((float) spriteStartPlaceX / 128.0F, ((float) spriteStartPlaceY + 7.99F) / 128.0F);
    
        GlStateManager.glVertex3f(this.posX, this.posY, 0.0F);
    
        GlStateManager.glTexCoord2f(((float) spriteStartPlaceX + characterSpriteWidth - 1.0F) / 128.0F,
                                    (float) spriteStartPlaceY / 128.0F);
    
        GlStateManager.glVertex3f(this.posX + characterLength - characterSpacing,
                                  this.posY - characterRenderHeight,
                                  0.0F);
    
        GlStateManager.glTexCoord2f(((float) spriteStartPlaceX + characterSpriteWidth - 1.0F) / 128.0F,
                                    ((float) spriteStartPlaceY + characterSpriteHeight) / 128.0F);
    
        GlStateManager.glVertex3f(this.posX + characterLength - characterSpacing, this.posY, 0.0F);
    
        GlStateManager.glEnd();
        return (float) characterLength;
    }
    
    @Override
    public int getStringWidth(String text)
    {
        if(this.scale <= drawRegularThreshold)
        {
            return super.getStringWidth(text);
        }
        if(text == null)
        {
            return 0;
        }
        else
        {
            int i = 0;
            boolean flag = false;
            
            for(int j = 0; j < text.length(); ++j)
            {
                char c0 = text.charAt(j);
                int k = this.getCharWidth(c0);
                
                if(k < 0 && j < text.length() - 1)
                {
                    ++j;
                    c0 = text.charAt(j);
                    
                    if(c0 != 'l' && c0 != 'L')
                    {
                        if(c0 == 'r' || c0 == 'R')
                        {
                            flag = false;
                        }
                    }
                    else
                    {
                        flag = true;
                    }
                    
                    k = 0;
                }
    
                i += k;
                
                if(flag && k > 0)
                {
                    i++;
                }
            }
        
            return i * this.scale;
        }
    }
    
    public int getScale()
    {
        return scale;
    }
    
    public void setScale(int scale)
    {
        this.scale = scale;
    }
}
