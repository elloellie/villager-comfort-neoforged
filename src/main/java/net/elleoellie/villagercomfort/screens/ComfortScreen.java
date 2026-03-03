package net.elleoellie.villagercomfort.screens;

import net.elleoellie.villagercomfort.Config;
import net.elleoellie.villagercomfort.VillagerComfort;
import net.elleoellie.villagercomfort.dataattachment.ComfortInfoRecord;
import net.elleoellie.villagercomfort.screens.helpers.BasicWidget;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.util.*;

public class ComfortScreen extends Screen {
    private static final ResourceLocation BACKGROUND = ResourceLocation.fromNamespaceAndPath(VillagerComfort.MOD_ID, "textures/gui/villagercomfortbackgroundnewnoscrollthreebox.png");
    private static final ResourceLocation COMFORT_BAR = ResourceLocation.fromNamespaceAndPath(VillagerComfort.MOD_ID, "textures/gui/villagercomfortbar.png");
    private static final ResourceLocation COMFORT_MARKER = ResourceLocation.fromNamespaceAndPath(VillagerComfort.MOD_ID, "comfortmarker");
    private static final ResourceLocation EMPTY_VALUE_BAR = ResourceLocation.fromNamespaceAndPath(VillagerComfort.MOD_ID, "bars/emptybar");
    private static final ResourceLocation VALUE_PROGRESS_BAR = ResourceLocation.fromNamespaceAndPath(VillagerComfort.MOD_ID, "bars/barprogress");
    //private static final ResourceLocation SCROLLER_DISABLED_SPRITE = ResourceLocation.withDefaultNamespace("container/villager/scroller_disabled");
    private static final ResourceLocation NO_BED_ICON = ResourceLocation.fromNamespaceAndPath(VillagerComfort.MOD_ID, "icons/nobed");
    private static final ResourceLocation WORKSTATION_IN_BEDROOM_ICON = ResourceLocation.fromNamespaceAndPath(VillagerComfort.MOD_ID, "icons/bedandworkstationtogether");
    private static final ResourceLocation TRAPPED_INSIDE_ICON = ResourceLocation.fromNamespaceAndPath(VillagerComfort.MOD_ID, "icons/trappedinside");
    private static final ResourceLocation TOO_MANY_BEDS_ICON = ResourceLocation.fromNamespaceAndPath(VillagerComfort.MOD_ID, "icons/toomanybeds");
    private static final ResourceLocation TOO_MANY_WORKSTATIONS_ICON = ResourceLocation.fromNamespaceAndPath(VillagerComfort.MOD_ID, "icons/toomanyworkstations");
    private static final ResourceLocation INSOMNIA_ICON = ResourceLocation.fromNamespaceAndPath(VillagerComfort.MOD_ID, "icons/insomnia");
    private static final ResourceLocation PANIC_ICON = ResourceLocation.fromNamespaceAndPath(VillagerComfort.MOD_ID, "icons/panic");
    private static final ResourceLocation PROBLEM_BOX = ResourceLocation.fromNamespaceAndPath(VillagerComfort.MOD_ID, "textures/gui/problembox.png");

    private static final Component title = Component.translatable("gui.villagercomfort.title");
    private static final Component light = Component.translatable("gui.villagercomfort.light");
    private static final Component size = Component.translatable("gui.villagercomfort.size");
    private static final Component bedroom = Component.translatable("gui.villagercomfort.bedroom");
    private static final Component workplace = Component.translatable("gui.villagercomfort.workplace");
    private static final Component misc = Component.translatable("gui.villagercomfort.misc");
    private static final Component bedJobDistanceText = Component.translatable("gui.villagercomfort.bed_job_distance");
    private static final Component problemText = Component.translatable("gui.villagercomfort.problem");
    private static final Component noProblemsText = Component.translatable("gui.villagercomfort.no_problems");

    private static final Component noBedTooltip = Component.translatable("gui.villagercomfort.no_bed_tooltip");
    private static final Component workstationInBedroomTooltip = Component.translatable("gui.villagercomfort.workstation_in_bedroom_tooltip");
    private static final Component trappedInsideTooltip = Component.translatable("gui.villagercomfort.trapped_inside_tooltip");
    private static final Component tooManyBedsTooltip = Component.translatable("gui.villagercomfort.too_many_beds_tooltip");
    private static final Component tooManyWorkstationsTooltip = Component.translatable("gui.villagercomfort.too_many_workstations_tooltip");
    private static final Component insomniaTooltip = Component.translatable("gui.villagercomfort.insomnia_tooltip");
    private static final Component panicTooltip = Component.translatable("gui.villagercomfort.panic_tooltip");
    private static final Component comfortBarTooltip = Component.translatable("gui.villagercomfort.comfort_bar_tooltip");

    private final ComfortInfoRecord villager;

    // ideally, this would probably be handled dynamically, registering more boxes if more are detected.
    // but it's fine for now.
    private BasicWidget problemsBox1;
    private BasicWidget problemsBox2;
    private BasicWidget problemsBox3;
    private BasicWidget problemsBox4;
    private BasicWidget problemsBox5;
    private BasicWidget problemsBox6;
    
    private BasicWidget comfortBarWidget;

    Set<Component> renderedProblemsTooltips;
    Set<BasicWidget> renderedProblems;
    Map<BasicWidget, Component> tooltipsToBoxes;

    double bedroomSizePercent;
    double bedroomLightPercent;
    double workplaceSizePercent;
    double workplaceLightPercent;
    double bedWorkplaceDistancePercent;

    Font font;

    int i;
    int j;
    private int textureWidth;
    private int textureHeight;
    int xTextFirst;
    int yTextFirst;
    int imageWidth;
    int imageHeight;
    int startingProblemBoxLocationX;
    int startingProblemBoxLocationY;
    int startingIconLocationX;
    int startingIconLocationY;

    private static final int comfortBarX = 136;
    private static final int comfortBarY = 16;


    public ComfortScreen(ComfortInfoRecord comfortInfoRecord) {
        super(title);
        this.villager = comfortInfoRecord;
    }

    @Override
    protected void init() {
        super.init();

        // Add widgets and precomputed values
        this.textureWidth = 512;
        this.textureHeight = 256;

        this.imageWidth = 276;
        this.imageHeight = 166;

        this.i = (width - imageWidth) / 2;
        this.j = (height - imageHeight) / 2;

        this.xTextFirst = i + 103;
        this.yTextFirst = j + 64;

        this.font = Minecraft.getInstance().font;

        this.renderedProblemsTooltips = new HashSet<>();
        this.renderedProblems = new HashSet<>();
        this.tooltipsToBoxes = new HashMap<>();

        this.startingProblemBoxLocationX = i + 9;
        this.startingProblemBoxLocationY = j + 97;
        this.startingIconLocationX = i + 13;
        this.startingIconLocationY = j + 101;

        this.bedroomSizePercent = (double) villager.bedroomSize() / (double)villager.configMaxBedroom();
        this.bedroomLightPercent = (double) villager.bedroomLight() / (double) villager.configBedroomLight();
        this.workplaceSizePercent = (double) villager.workplaceSize() / (double) villager.configMaxWorkplace();
        this.workplaceLightPercent = (double) villager.workplaceLight() / (double) villager.configWorkplaceLight();
        this.bedWorkplaceDistancePercent = (double) villager.bedWorkstationDistance() / (double)villager.configBedWorkplaceDistance();

        this.problemsBox1 = addRenderableWidget(BasicWidget.texture(this, startingProblemBoxLocationX, startingProblemBoxLocationY, 26, 26, PROBLEM_BOX).active(false).visible(false));
        this.problemsBox2 = addRenderableWidget(BasicWidget.texture(this, startingProblemBoxLocationX, startingProblemBoxLocationY, 26, 26, PROBLEM_BOX).active(false).visible(false));
        this.problemsBox3 = addRenderableWidget(BasicWidget.texture(this, startingProblemBoxLocationX, startingProblemBoxLocationY, 26, 26, PROBLEM_BOX).active(false).visible(false));
        this.problemsBox4 = addRenderableWidget(BasicWidget.texture(this, startingProblemBoxLocationX, startingProblemBoxLocationY, 26, 26, PROBLEM_BOX).active(false).visible(false));
        this.problemsBox5 = addRenderableWidget(BasicWidget.texture(this, startingProblemBoxLocationX, startingProblemBoxLocationY, 26, 26, PROBLEM_BOX).active(false).visible(false));
        this.problemsBox6 = addRenderableWidget(BasicWidget.texture(this, startingProblemBoxLocationX, startingProblemBoxLocationY, 26, 26, PROBLEM_BOX).active(false).visible(false));
        this.comfortBarWidget = addRenderableWidget(BasicWidget.texture(this, i + comfortBarX, j + comfortBarY, 102, 5, COMFORT_BAR).active(true).visible(true));
    }

    private void renderComfortBar(GuiGraphics graphics) {
        //graphics.blitSprite(COMFORT_BAR, i + comfortBarX, j + comfortBarY, 0, 102, 5);
        int comfort = villager.comfort();
        double comfortAsPercentage = (double) (comfort) / (double) 2.0F / (double) Config.MAX_COMFORT_RANGE.get(); // (double)100.0F;
        int comfortNumberOffset = (int) Math.floor((double) 102.0F * comfortAsPercentage);
        int numberStringOffsetX = i + comfortBarX + comfortNumberOffset + (102 / 2);
        int numberStringOffsetY = j + comfortBarY + 10;
        String comfortString = String.valueOf(comfort / 10);
        Objects.requireNonNull(this.font);
        graphics.drawCenteredString(this.font, Component.literal(comfortString), numberStringOffsetX, numberStringOffsetY, -13378253);// numberStringOffsetX - 9, 0);
        graphics.blitSprite(COMFORT_MARKER, numberStringOffsetX - 4, j + comfortBarY - 1, 0, 8, 8);
    }

    private void renderValueTexts(GuiGraphics graphics) {
        //bedroom
        graphics.drawString(font, bedroom, xTextFirst + 20, yTextFirst - 33, 4210752, false);
        
        graphics.drawString(font, size, xTextFirst + 25, yTextFirst - 18, 4210752, false);
        renderValueBars(graphics, bedroomSizePercent, xTextFirst + 25, yTextFirst - 18);

        graphics.drawString(font, light, xTextFirst + 90, yTextFirst - 18, 4210752, false);
        renderValueBars(graphics, bedroomLightPercent, xTextFirst + 90, yTextFirst - 18);

        //workplace
        graphics.drawString(font, workplace, xTextFirst + 20, yTextFirst + 10, 4210752, false);

        graphics.drawString(font, size, xTextFirst + 25, yTextFirst + 25, 4210752, false);
        renderValueBars(graphics, workplaceSizePercent, xTextFirst + 25, yTextFirst + 25);

        graphics.drawString(font, light, xTextFirst + 90, yTextFirst + 25, 4210752, false);
        renderValueBars(graphics, workplaceLightPercent, xTextFirst + 90, yTextFirst + 25);

        //misc
        graphics.drawString(font, misc, xTextFirst + 20, yTextFirst + 53, 4210752, false);

        graphics.drawString(font, bedJobDistanceText, xTextFirst + 25, yTextFirst + 68, 4210752, false);
        renderValueBars(graphics, bedWorkplaceDistancePercent, xTextFirst + 25, yTextFirst + 68);
    }

    private void renderValueBars(GuiGraphics graphics, double valuePercent, int x, int y) {
        graphics.blitSprite(EMPTY_VALUE_BAR, x, y + 10, 0, 41, 5);
        if (valuePercent >= 1.0D) {
            graphics.blitSprite(VALUE_PROGRESS_BAR, x, y + 10, 0, 41, 5);
        } else if (valuePercent == 0.0D) {
            return;
        } else {
            int valueAsPercentageSprite = (int) (41.0F * valuePercent);
            graphics.blitSprite(VALUE_PROGRESS_BAR, 41, 5, 0, 0, x, y + 10, 0, valueAsPercentageSprite, 5);
        }
    }



    private void hasProblems(GuiGraphics graphics) {
        graphics.drawString(font, problemText, i + 6, j + 80, 4210752, false);
        if (villager.hasBed() &&
                !villager.isWorkstationInBedroom() &&
                ((villager.currentDay() - villager.lastDayOutside()) <= 0)
                && (villager.bedsCount() <= villager.configBedsThreshold())
                && (villager.workstationsCount() <= villager.configWorkstationsThreshold())
                && ((villager.currentDay() - villager.lastSleepDay() - 1) <= 0)
                && ((villager.currentDay() - villager.lastZombieDay()) > 0))
        {
            graphics.drawString(font, noProblemsText, i + 20, j + 100, 4210752, false);
            //graphics.blitSprite(SCROLLER_DISABLED_SPRITE, i + 94, j + 18, 0, 6, 27);
        }
        else {
            renderProblems(graphics);
            if (renderedProblemsTooltips.size() > 6) {
                // make the scrollbar scrollable
                /* this currently is unimplemented and unecessary, since
                 only 6 problems at msot can be visible right now. but
                 once i add more possible problems it'll be needed!*/
            }
            else {
                // don't make the scrollbar scrollable
            }
        }
    }

    private void renderProblems(GuiGraphics graphics){
        renderedProblemsTooltips.clear();
        if (!villager.hasBed()) {
            renderedProblemsTooltips.add(noBedTooltip);
            this.gridManager(graphics, NO_BED_ICON, noBedTooltip);
        }
        if (villager.hasBed() && villager.isWorkstationInBedroom()){
            renderedProblemsTooltips.add(workstationInBedroomTooltip);
            this.gridManager(graphics, WORKSTATION_IN_BEDROOM_ICON, workstationInBedroomTooltip);
        }
        if ((villager.currentDay() - villager.lastDayOutside() - 1) > 0){
            renderedProblemsTooltips.add(trappedInsideTooltip);
            this.gridManager(graphics, TRAPPED_INSIDE_ICON, trappedInsideTooltip);
        }
        if (villager.bedsCount() > villager.configBedsThreshold()){
            renderedProblemsTooltips.add(tooManyBedsTooltip);
            this.gridManager(graphics, TOO_MANY_BEDS_ICON, tooManyBedsTooltip);
        }
        if (villager.workstationsCount() > villager.configWorkstationsThreshold()){
            renderedProblemsTooltips.add(tooManyWorkstationsTooltip);
            this.gridManager(graphics, TOO_MANY_WORKSTATIONS_ICON, tooManyWorkstationsTooltip);
        }
        if ((villager.currentDay() - villager.lastSleepDay() - 1) > 0){
            renderedProblemsTooltips.add(insomniaTooltip);
            this.gridManager(graphics, INSOMNIA_ICON, insomniaTooltip);
        }
        if ((villager.currentDay() - villager.lastZombieDay() -1) < 0){
            renderedProblemsTooltips.add(panicTooltip);
            this.gridManager(graphics, PANIC_ICON, panicTooltip);
        }
    }

    private void gridManager(GuiGraphics graphics, ResourceLocation resourceLocation, Component tooltip){
        // this is kind of a stupid way to do this. a for loop would be better.
        // but i don't feel like re-writing the for loop i deleted. so it'll do for now...
        int secondProblemIconXColumn = this.startingIconLocationX + 30;
        int thirdProblemIconXRowColumn = this.startingIconLocationX + 60;
        int twoRowsProblemIconY =  this.startingIconLocationY + 30;

        if (renderedProblemsTooltips.size() == 1)
        {
            this.problemsBox1.active(true).visible(true);
            graphics.blitSprite(resourceLocation, startingIconLocationX, startingIconLocationY, 18, 18);
            renderedProblems.add(problemsBox1);
            tooltipsToBoxes.put(problemsBox1, tooltip);
        }
        if (renderedProblemsTooltips.size() == 2) {
            this.problemsBox2.active(true).visible(true);
            problemsBox2.setX(startingProblemBoxLocationX + 30);
            graphics.blitSprite(resourceLocation, secondProblemIconXColumn, startingIconLocationY, 18, 18);
            renderedProblems.add(problemsBox2);
            tooltipsToBoxes.put(problemsBox2, tooltip);
        }
        if (renderedProblemsTooltips.size() == 3){
            this.problemsBox3.active(true).visible(true);
            problemsBox3.setX(startingProblemBoxLocationX + 60);
            graphics.blitSprite(resourceLocation, thirdProblemIconXRowColumn, startingIconLocationY, 18, 18);
            renderedProblems.add(problemsBox3);
            tooltipsToBoxes.put(problemsBox3, tooltip);
        }
        if (renderedProblemsTooltips.size() == 4){
            this.problemsBox4.active(true).visible(true);
            problemsBox4.setY(startingProblemBoxLocationX + 30);
            graphics.blitSprite(resourceLocation, startingIconLocationX, twoRowsProblemIconY, 18, 18);
            renderedProblems.add(problemsBox4);
            tooltipsToBoxes.put(problemsBox4, tooltip);
        }
        if (renderedProblemsTooltips.size() == 5){
            this.problemsBox5.active(true).visible(true);
            problemsBox5.setX(startingProblemBoxLocationX + 30);
            problemsBox5.setY(startingProblemBoxLocationX + 30);
            graphics.blitSprite(resourceLocation, secondProblemIconXColumn, twoRowsProblemIconY, 18, 18);
            renderedProblems.add(problemsBox5);
            tooltipsToBoxes.put(problemsBox5, tooltip);
        }
        if (renderedProblemsTooltips.size() == 6){
            this.problemsBox6.active(true).visible(true);
            problemsBox6.setX(startingProblemBoxLocationX + 60);
            problemsBox6.setY(startingProblemBoxLocationX + 30);
            graphics.blitSprite(resourceLocation, thirdProblemIconXRowColumn, twoRowsProblemIconY, 18, 18);
            renderedProblems.add(problemsBox6);
            tooltipsToBoxes.put(problemsBox6, tooltip);
        }
    }

    // In some Screen subclass
    @Override
    public void tick() {
        super.tick();

        // Execute some logic every frame
    }


    @Override
    public void renderBackground(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        this.renderMenuBackground(graphics);
        graphics.blit(BACKGROUND, i, j, 0, 0.0F, 0.0F, imageWidth, imageHeight, textureWidth, textureHeight);
        // this is a part of the original renderBackground we're overriding, but is
        // depreciated. i'm commenting it out for now since i don't need it and the error it throws bugs me.
        // this is mostly a reminder that i overrid it for this screen if i do in the future lol.
        //NeoForge.EVENT_BUS.post(new ScreenEvent.BackgroundRendered(this, graphics));
    }

    // mouseX and mouseY indicate the scaled coordinates of where the cursor is in on the screen
    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        // Background is typically rendered first
        this.renderBackground(graphics, mouseX, mouseY, partialTick);

        // Render things here before widgets (background textures)
        // Then the widgets if this is a direct child of the Screen
        super.render(graphics, mouseX, mouseY, partialTick);
        this.renderComfortBar(graphics);
        this.renderValueTexts(graphics);
        this.hasProblems(graphics);

        // Render things after widgets (tooltips)
        for (BasicWidget genericProblemsBox : renderedProblems) {
            if (genericProblemsBox.visible && genericProblemsBox.isHovered()) {
                graphics.renderTooltip(font, tooltipsToBoxes.get(genericProblemsBox), mouseX, mouseY);
            }
        }
        if (comfortBarWidget.visible && comfortBarWidget.isHovered()) {
            graphics.renderTooltip(font, comfortBarTooltip, mouseX, mouseY);
        }
    }


    @Override
    public void onClose() {
        // Stop any handlers here

        // Call last in case it interferes with the override
        super.onClose();
    }

    @Override
    public void removed() {
        // Reset initial states here
        renderedProblems.clear();
        renderedProblemsTooltips.clear();
        tooltipsToBoxes.clear();
        // Call last in case it interferes with the override
        super.removed()
        ;}

}
