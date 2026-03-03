package net.elleoellie.villagercomfort.core.mixin;

import net.minecraft.client.gui.components.TabButton;
import net.minecraft.client.gui.components.tabs.Tab;
import net.minecraft.client.gui.components.tabs.TabManager;
import net.minecraft.client.gui.screens.inventory.MerchantScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({MerchantScreen.class})
public class MerchantScreenMixin {

    @Inject(
            method = "init",
            at = @At("TAIL"))
            protected void init(CallbackInfo ci){
        Tab tradingTab;
        Tab comfortTab;
        TabManager tabManager;

        addRenderableWidget(new TabButton(tabManager, tradingTab, 4, 4));
        this.addRenderableWidget(new TabButton((tabManager, comfortTab, 4, 4)));
    }
}
