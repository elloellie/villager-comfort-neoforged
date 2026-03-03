package net.elleoellie.villagercomfort.screens;

import net.elleoellie.villagercomfort.dataattachment.ComfortInfoRecord;
import net.minecraft.client.Minecraft;

public class ScreenHandler {
    public static void openComfortScreen(ComfortInfoRecord comfortInfoRecord) {
        Minecraft.getInstance().forceSetScreen(new ComfortScreen((comfortInfoRecord)));
    }
}
