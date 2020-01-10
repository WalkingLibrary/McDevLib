package com.mixin;




import com.jumbodinosaurs.mcdevlib.OneDotTwelve.events.PacketEvent;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.concurrent.GenericFutureListener;
import net.minecraft.network.INetHandler;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraftforge.common.MinecraftForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;
import java.util.concurrent.Future;

@Mixin(NetworkManager.class)
public abstract class MixinNetworkManager extends SimpleChannelInboundHandler <Packet<? >> {
    private boolean sendPackets = true;
    @Shadow
    protected abstract void dispatchPacket(final Packet<?> inPacket, @Nullable final GenericFutureListener<? extends Future<? super Void>>[] futureListeners);
    
    @Redirect(method = "channelRead0", at = @At(value = "INVOKE", target = "net/minecraft/network/Packet.processPacket(Lnet/minecraft/network/INetHandler;)V"))
    private void channelRead0$processPacket(Packet<?> packetIn, INetHandler handler) {
        PacketEvent event = new PacketEvent.Receive(packetIn);
        MinecraftForge.EVENT_BUS.post(event);
        if (event.isCanceled())
            return;
        
        ((Packet<INetHandler>) event.getPacket()).processPacket(handler);
    }
    
    @Redirect(method = "sendPacket", at = @At(value = "INVOKE", target = "net/minecraft/network/NetworkManager.dispatchPacket(Lnet/minecraft/network/Packet;[Lio/netty/util/concurrent/GenericFutureListener;)V"))
    private void sendPacket$dispatchPacket(NetworkManager networkManager, Packet<?> packetIn, @Nullable final GenericFutureListener<? extends Future<?super Void>>[] futureListeners) {
        PacketEvent event = new PacketEvent.Send(packetIn);
        MinecraftForge.EVENT_BUS.post(event);
        if (event.isCanceled())
            return;
        
        this.dispatchPacket(event.getPacket(), (GenericFutureListener[])futureListeners);
    }
    
    @Redirect(method = "sendPacket", at = @At(value = "INVOKE", target = "net/minecraft/network/NetworkManager.isChannelOpen()Z"))
    private boolean sendPacket$isChannelOpen(NetworkManager networkManager) {
        return this.sendPackets && networkManager.isChannelOpen();
    }
    
    @Inject(method = "flushOutboundQueue", at = @At("HEAD"), cancellable = true)
    private void flushOutboundQueue(CallbackInfo ci) {
        if (!sendPackets)
            ci.cancel();
    }
}