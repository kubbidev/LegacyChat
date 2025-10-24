package me.kubbidev.legacychat.mixins;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.toast.SystemToast;
import net.minecraft.client.toast.Toast;
import net.minecraft.client.toast.ToastManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(ToastManager.class)
public class ToastManagerMixin {

    @Inject(method = "add(Lnet/minecraft/client/toast/Toast;)V", at = @At("HEAD"), cancellable = true)
    private void legacychat$add(Toast toast, CallbackInfo ci) {
        if (toast.getType() == SystemToast.Type.UNSECURE_SERVER_WARNING) {
            ci.cancel();
        }
    }
}
