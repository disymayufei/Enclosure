package com.github.zly2006.enclosure.client.mixin;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import net.fabricmc.fabric.api.event.registry.RegistryAttributeHolder;
import net.fabricmc.fabric.impl.registry.sync.RegistrySyncManager;
import net.fabricmc.fabric.impl.registry.sync.RemappableRegistry;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

@Mixin(RegistrySyncManager.class)
public class MixinRegistrySyncManager {

    @Inject(method = "apply", at = @At(value = "INVOKE", target = "Lnet/fabricmc/fabric/impl/registry/sync/RemappableRegistry;remap(Ljava/lang/String;Lit/unimi/dsi/fastutil/objects/Object2IntMap;Lnet/fabricmc/fabric/impl/registry/sync/RemappableRegistry$RemapMode;)V"), locals = LocalCapture.CAPTURE_FAILSOFT, remap = false)
    private static void apply(Map<Identifier, Object2IntMap<Identifier>> map, RemappableRegistry.RemapMode mode, CallbackInfo ci, Set containedRegistries, Iterator var3, Identifier registryId, Object2IntMap registryMap, Registry registry, RegistryAttributeHolder attributeHolder, Object2IntMap<Identifier> idMap){
        final Identifier legacySmithing = new Identifier("minecraft", "legacy_smithing");
        final Identifier smithing = new Identifier("minecraft", "smithing");

        if(idMap.containsKey(legacySmithing) && idMap.containsKey(smithing)){
            idMap.put(smithing, idMap.getInt(legacySmithing));
            idMap.removeInt(legacySmithing);
        }
    }
}
