package net.elleoellie.villagercomfort.screens;

import com.mojang.blaze3d.systems.RenderSystem;
import net.elleoellie.villagercomfort.VillagerComfort;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Inventory;

import static net.elleoellie.villagercomfort.dataattachment.ComfortData.BEDROOM_SIZE;
import static net.elleoellie.villagercomfort.dataattachment.ComfortData.COMFORT;

public class ComfortScreenPlusMenu extends AbstractContainerScreen<ComfortMenu> {
    //    private TabManager tabManager;
//    public Tab trading;
//    public Tab comfort;
//    Tab[] tabs = new Tab[]{trading, comfort};
    private static final ResourceLocation BACKGROUND = ResourceLocation.fromNamespaceAndPath(VillagerComfort.MOD_ID, "textures/gui/villagercomfortbackground.png");
    private static final ResourceLocation BAD_BAR = ResourceLocation.fromNamespaceAndPath(VillagerComfort.MOD_ID, "textures/gui/bars/comfort_aspect_bar_bad.png");
    private static final ResourceLocation COMFORT_BAR = ResourceLocation.withDefaultNamespace("container/villager/experience_bar_background");

    private static final String BEDROOM_SIZE_STRING = "gui.villagercomfort.bedroom_size";

    private final Villager villager;

    public ComfortScreenPlusMenu(ComfortMenu pMenu, Inventory pPlayerInventory, Component title) {
        super(pMenu, pPlayerInventory, title);
        this.villager = pMenu.villager;
        this.imageWidth = 276;
        this.inventoryLabelX = 107;
        Font font;
    }


    @Override
    protected void init() {
        super.init();
        titleLabelX = 72;
        titleLabelY = 12;

        inventoryLabelX = 1000;
    }

    @Override
    protected void renderBg(GuiGraphics pGuiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        pGuiGraphics.blit(BACKGROUND, x, y, 0.0F, 0.0F, this.imageWidth, this.imageHeight, 512, 256);
    }

    private void renderComfortBar(GuiGraphics guiGraphics, int posX, int posY){
        int comfort = villager.getData(COMFORT);
        int bedroomSize = villager.getData(BEDROOM_SIZE);
        guiGraphics.blitSprite(COMFORT_BAR, posX+ 136, posY+ 16, 0, 102, 5);
    }
}