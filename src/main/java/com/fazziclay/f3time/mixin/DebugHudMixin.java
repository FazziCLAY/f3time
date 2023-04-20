package com.fazziclay.f3time.mixin;

import com.fazziclay.f3time.F3Time;
import net.minecraft.client.gui.hud.DebugHud;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(DebugHud.class)
public class DebugHudMixin {
	@Inject(at = @At("RETURN"), method = "getLeftText")
	private void getLeftText(CallbackInfoReturnable<List<String>> info) {
		if (F3Time.getConfig().isEnabled()) {
			List<String> r = info.getReturnValue();
			r.add(F3Time.genText());
		}
	}
}
