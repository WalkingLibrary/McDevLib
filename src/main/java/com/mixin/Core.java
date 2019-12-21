package com.mixin;

import org.spongepowered.asm.launch.MixinBootstrap;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.Mixins;

public class Core
{
    public Core()
    
    {
        MixinBootstrap.init();
        Mixins.addConfiguration("mixinsDev.json");
        MixinEnvironment.getDefaultEnvironment().setObfuscationContext("searge");
    }
}
