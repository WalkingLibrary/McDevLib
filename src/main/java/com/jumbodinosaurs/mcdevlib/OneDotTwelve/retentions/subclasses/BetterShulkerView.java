package com.jumbodinosaurs.mcdevlib.OneDotTwelve.retentions.subclasses;


import com.jumbodinosaurs.mcdevlib.OneDotTwelve.gui.radialmenu.ResourceLocationUtil;
import com.jumbodinosaurs.mcdevlib.OneDotTwelve.gui.radialmenu.interfaces.IDisplayable;
import com.jumbodinosaurs.mcdevlib.OneDotTwelve.retentions.BindableRentention;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemShulkerBox;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderTooltipEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class BetterShulkerView extends BindableRentention implements IDisplayable
{
    private static ResourceLocation WIDGET = new ResourceLocation("client", "shulker_widget.png");
    
    public BetterShulkerView(KeyBinding binding)
    {
        super(binding);
    }
    
    public BetterShulkerView(boolean active, KeyBinding binding)
    {
        super(active, binding);
    }
    
    public BetterShulkerView()
    {
    }
    
    @Override
    public String getModID()
    {
        return "DevLib";
    }
    
    
    @SubscribeEvent
    public void renderTooltip(RenderTooltipEvent.PostText event)
    {
        if(this.isActive())
        {
            if(event.getStack() != null &&
               event.getStack().getItem() instanceof ItemShulkerBox &&
               event.getStack().hasTagCompound())
            {
                System.out.println(event);
                NBTTagCompound cmp = event.getStack().getTagCompound().getCompoundTag("BlockEntityTag");
                
                if(cmp != null && cmp.hasKey("Items", 9))
                {
                    ItemStack currentBox = event.getStack();
                    int currentX = event.getX() - 5;
                    int currentY = event.getY() - 70;
                    
                    int texWidth = 172;
                    int texHeight = 64;
                    
                    if(currentY < 0)
                    {
                        currentY = event.getY() + event.getLines().size() * 10 + 5;
                    }
                    
                    ScaledResolution res = new ScaledResolution(Minecraft.getMinecraft());
                    int right = currentX + texWidth;
                    if(right > res.getScaledWidth())
                    {
                        currentX -= (right - res.getScaledWidth());
                    }
                    
                    GlStateManager.pushMatrix();
                    RenderHelper.enableStandardItemLighting();
                    GlStateManager.enableRescaleNormal();
                    GlStateManager.color(1F, 1F, 1F);
                    GlStateManager.translate(0, 0, 700);
                    
                    Minecraft mc = Minecraft.getMinecraft();
                    mc.getTextureManager().bindTexture(WIDGET);
                    
                    RenderHelper.disableStandardItemLighting();
                    
                    
                    Gui.drawModalRectWithCustomSizedTexture(currentX, currentY, 0, 0, texWidth, texHeight, 256, 256);
                    
                    GlStateManager.color(1F, 1F, 1F);
                    
                    NonNullList<ItemStack> itemList = NonNullList.withSize(27, ItemStack.EMPTY);
                    ItemStackHelper.loadAllItems(cmp, itemList);
                    
                    RenderItem render = mc.getRenderItem();
                    
                    RenderHelper.enableGUIStandardItemLighting();
                    GlStateManager.enableDepth();
                    int i = 0;
                    for(ItemStack itemstack : itemList)
                    {
                        int xp = currentX + 6 + (i % 9) * 18;
                        int yp = currentY + 6 + (i / 9) * 18;
                        
                        if(!itemstack.isEmpty())
                        {
                            render.renderItemAndEffectIntoGUI(itemstack, xp, yp);
                            render.renderItemOverlays(mc.fontRenderer, itemstack, xp, yp);
                        }
                        i++;
                    }
                    
                    GlStateManager.disableDepth();
                    GlStateManager.disableRescaleNormal();
                    GlStateManager.popMatrix();
                }
            }
        }
    }
    
    @Override
    public String getInfoMessage()
    {
        return "When Active the Retention will show the contents of a Shulker box while the player hovers over it.";
    }
    
    @Override
    public void onPress()
    {
        defaultPress();
    }
    
    
    @Override
    public String getRetentionIdentifier()
    {
        return this.getClass().getSimpleName();
    }
    
    
    @Override
    public ResourceLocation getIcon()
    {
        return ResourceLocationUtil.clairvoyanceIcon;
    }
    
    @Override
    public String getLabel()
    {
        return "ShulkerView";
    }
    
    @Override
    public void toggle()
    {
        onPress();
    }
}
