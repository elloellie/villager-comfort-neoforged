package net.elleoellie.villagercomfort.screens;

import com.mojang.blaze3d.systems.RenderSystem;
import net.elleoellie.villagercomfort.VillagerComfort;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.*;
import net.minecraft.client.gui.components.ImageWidget;
import net.minecraft.client.gui.components.tabs.Tab;
import net.minecraft.client.gui.components.tabs.TabManager;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.gui.components.tabs.TabNavigationBar;
import net.minecraft.world.entity.npc.Villager;

import java.util.function.Consumer;

public class ComfortScreen extends Screen {
//    private TabManager tabManager;
//    public Tab trading;
//    public Tab comfort;
//    Tab[] tabs = new Tab[]{trading, comfort};
    private static final ResourceLocation BACKGROUND = ResourceLocation.fromNamespaceAndPath(VillagerComfort.MOD_ID, "textures/gui/comfortvaluesbackground.png");
    private static final ResourceLocation BAD_BAR = ResourceLocation.fromNamespaceAndPath(VillagerComfort.MOD_ID, "textures/gui/bars/comfort_aspect_bar_bad.png");
    private static final ResourceLocation COMFORT_BAR = ResourceLocation.fromNamespaceAndPath(VillagerComfort.MOD_ID, "textures/gui/comfort_bar.png");

    private static final String BEDROOM_SIZE_STRING = "gui.villagercomfort.bedroom_size";

    // int barYPos =
    int barXPos;
    Villager villager;
    Font font;
    int x = (width - 256) / 2;
    int y = (height - 256) / 2;
    Button.OnPress onPress;

    void drawComfortBar(GuiGraphics graphics) {
        int comfortBarMargin = (this.width - 162)/2;
        graphics.blitSprite(COMFORT_BAR, 0 + comfortBarMargin, 24, 0, 0, 162, 5, 162, 5);
    }

    public ComfortScreen(Component title) {
        super(title);
    }


    @Override
    protected void init() {
        super.init();

        // Add widgets and precomputed values
        // this.addRenderableWidget(new ImageButton(x, y, width, height, BAD_BAR, onPress)
//        this.addRenderableWidget(new TabButton(tabManager, trading, 4, 4));
//        this.addRenderableWidget(new TabButton((tabManager, comfort, 4, 4));

    //(new TabNavigationBar.Builder(tabManager, 5))
        // (new TabNavigationBar.Builder(tabManager, 4).addTabs (trading, comfort));
    // (new TabButton(tabManager, trading,);
    }

    // In some Screen subclass
    @Override
    public void tick() {
        super.tick();

        // Execute some logic every frame
    }

    // mouseX and mouseY indicate the scaled coordinates of where the cursor is in on the screen
    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        // Background is typically rendered first
        this.renderBackground(graphics, mouseX, mouseY, partialTick);


        // Render things here before widgets (background textures)
            graphics.blit(BACKGROUND, x, y, 0, 0, 256, 256);
            drawComfortBar(graphics);
        // Then the widgets if this is a direct child of the Screen
        super.render(graphics, mouseX, mouseY, partialTick);

        // graphics.blitSprite();
            // graphics.drawString(font, Component.translatable(BEDROOM_SIZE_STRING))

        // Render things after widgets (tooltips)
    }
}
