package com.jumbodinosaurs.mcdevlib.OneDotTwelve.retentions.subclasses;


import com.jumbodinosaurs.mcdevlib.OneDotTwelve.bot.pathfinding.util.WayPoint;
import com.jumbodinosaurs.mcdevlib.OneDotTwelve.gui.radialmenu.ResourceLocationUtil;
import com.jumbodinosaurs.mcdevlib.OneDotTwelve.gui.radialmenu.interfaces.IDisplayable;
import com.jumbodinosaurs.mcdevlib.OneDotTwelve.gui.text.CustomFontRenderManager;
import com.jumbodinosaurs.mcdevlib.OneDotTwelve.gui.text.ScaledFontRenderer;
import com.jumbodinosaurs.mcdevlib.OneDotTwelve.retentions.BindableRentention;
import com.jumbodinosaurs.mcdevlib.OneDotTwelve.util.minecraft.GameHelper;
import com.jumbodinosaurs.mcdevlib.OneDotTwelve.util.minecraft.PlayerHelper;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class LargerNameTags extends BindableRentention implements IDisplayable
{
    @Override
    public String getModID()
    {
        return "DevLib";
    }
    
    @Override
    public void onPress()
    {
        defaultPress();
    }
    
    @SubscribeEvent
    public void onRenderOverLayEvent(RenderLivingEvent.Specials.Pre<EntityLivingBase> renderGameOverlayEvent)
    {
        if(isActive())
        {
            if(renderGameOverlayEvent.getEntity() instanceof EntityPlayer)
            {
                renderGameOverlayEvent.setCanceled(true);
            }
        }
    }
    
    @SubscribeEvent
    public void onRenderNameTag(RenderLivingEvent.Post<EntityPlayer> renderLivingEvent)
    {
        if(isActive())
        {
            if(renderLivingEvent.getEntity() instanceof EntityPlayer)
            {
                drawNameTag((EntityPlayer) renderLivingEvent.getEntity(),
                            renderLivingEvent.getX(),
                            renderLivingEvent.getY(),
                            renderLivingEvent.getZ());
            }
        }
    }
    
    public void drawNameTag(EntityPlayer entityPlayer, double x, double y, double z)
    {
        boolean isThirdPersonFrontal = GameHelper.getInstance().getRenderManager().options.thirdPersonView == 2;
        float playerHeight = entityPlayer.height + 0.5F;
        y = y + playerHeight;
        
        float viewerYaw = GameHelper.getInstance().getRenderManager().playerViewY;
        float viewerPitch = GameHelper.getInstance().getRenderManager().playerViewX;
        
        ScaledFontRenderer fontRendererIn = CustomFontRenderManager.scaleableRender;
        int scale;
        WayPoint otherGuy = new WayPoint(entityPlayer.posX, entityPlayer.posY, entityPlayer.posZ);
        scale = (int) PlayerHelper.getPlayerPositionAsWayPoint().getNoSqRtEuclideanDistance(otherGuy) / 8;
        
        
        int maxScale = 50;
        if(scale > maxScale)
        {
            scale = maxScale;
        }
        
        
        fontRendererIn.setScale(scale);
        String name = entityPlayer.getName();
        
        GlStateManager.pushMatrix();
        GlStateManager.translate(x, y, z);
        GlStateManager.glNormal3f(0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(-viewerYaw, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate((float) (isThirdPersonFrontal ? -1 : 1) * viewerPitch, 1.0F, 0.0F, 0.0F);
        GlStateManager.scale(-0.025F, -0.025F, 0.025F);
        GlStateManager.disableLighting();
        GlStateManager.depthMask(false);
        GlStateManager.disableDepth();
    
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA,
                                            GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA,
                                            GlStateManager.SourceFactor.ONE,
                                            GlStateManager.DestFactor.ZERO);
        int nameWidthHalfed = fontRendererIn.getStringWidth(name) / 2;
        int nameHeight = 8 * -fontRendererIn.getScale();
        int verticalShift = 0;
        if(scale <= ScaledFontRenderer.drawRegularThreshold)
        {
        
        
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA,
                                                GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA,
                                                GlStateManager.SourceFactor.ONE,
                                                GlStateManager.DestFactor.ZERO);
            GlStateManager.disableTexture2D();
            Tessellator tessellator = Tessellator.getInstance();
            BufferBuilder bufferbuilder = tessellator.getBuffer();
            bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
            bufferbuilder.pos(-nameWidthHalfed - 1, -1 + verticalShift, 0.0D)
                         .color(0.0F, 0.0F, 0.0F, 0.25F)
                         .endVertex();
            bufferbuilder.pos(-nameWidthHalfed - 1, 8 + verticalShift, 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
            bufferbuilder.pos(nameWidthHalfed + 1, 8 + verticalShift, 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
            bufferbuilder.pos(nameWidthHalfed + 1, -1 + verticalShift, 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
            tessellator.draw();
            GlStateManager.enableTexture2D();
        }
        else
        {
            GlStateManager.disableTexture2D();
            Tessellator tessellator = Tessellator.getInstance();
            BufferBuilder bufferbuilder = tessellator.getBuffer();
            bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
            bufferbuilder.pos(-nameWidthHalfed - 1, nameHeight + verticalShift, 0.0D)
                         .color(0.0F, 0.0F, 0.0F, 0.25F)
                         .endVertex();
            bufferbuilder.pos(-nameWidthHalfed - 1, 0, 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
            bufferbuilder.pos(nameWidthHalfed + 1, 0, 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
            bufferbuilder.pos(nameWidthHalfed + 1, nameHeight + verticalShift, 0.0D)
                         .color(0.0F, 0.0F, 0.0F, 0.25F)
                         .endVertex();
            tessellator.draw();
        
            GlStateManager.enableTexture2D();
        
        }
        
        
        GlStateManager.depthMask(true);
        
        fontRendererIn.drawString(name, -fontRendererIn.getStringWidth(name) / 2, verticalShift, -1);
        GlStateManager.enableDepth();
        GlStateManager.enableLighting();
        GlStateManager.disableBlend();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.popMatrix();
    }
    
    
    @Override
    public String getInfoMessage()
    {
        return "Scales Name Tags to Larger Sizes";
    }
    
    @Override
    public ResourceLocation getIcon()
    {
        return ResourceLocationUtil.clairvoyanceIcon;
    }
    
    @Override
    public String getLabel()
    {
        return "Name Tags";
    }
    
    @Override
    public void toggle()
    {
        defaultPress();
    }
}
