package me.kubbidev.legacychat.mixins;

import me.kubbidev.legacychat.LegacyChatMod;
import net.minecraft.client.toast.SystemToast;
import net.minecraft.client.toast.Toast;
import net.minecraft.client.toast.ToastManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ToastManager.class)
public class ToastManagerMixin {

    @Inject(method = "add", at = @At("HEAD"), cancellable = true)
    private void legacychat$add(Toast toast, CallbackInfo ci) {
        if (toast.getType() == SystemToast.Type.UNSECURE_SERVER_WARNING) {
            ci.cancel();
            LegacyChatMod.LOGGER.info("legacychat$add unsecure server warning cancelled");
        }
    }
}
