package dev.bluephs.vintage_grinder.foundation.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import dev.bluephs.vintage_grinder.VintageGrinder;
import net.createmod.catnip.gui.UIRenderHelper;
import net.createmod.catnip.gui.element.ScreenElement;
import net.createmod.catnip.theme.Color;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public enum VintageGuiTextures implements ScreenElement {

    // JEI
    JEI_UP_ARROW("jei/widgets", 0, 0, 18, 14),
    JEI_UP_TO_RIGHT_ARROW("jei/widgets", 17, 0, 14, 18),
    JEI_FRAGILE("jei/widgets", 31, 0, 14, 23);

    public static final int FONT_COLOR = 0x575F7A;

    public final ResourceLocation location;
    public int width, height;
    public int startX, startY;

    private VintageGuiTextures(String location, int width, int height) {
        this(location, 0, 0, width, height);
    }

    private VintageGuiTextures(int startX, int startY) {
        this("icons", startX * 16, startY * 16, 16, 16);
    }

    private VintageGuiTextures(String location, int startX, int startY, int width, int height) {
        this(VintageGrinder.MOD_ID, location, startX, startY, width, height);
    }

    private VintageGuiTextures(String namespace, String location, int startX, int startY, int width, int height) {
        this.location = new ResourceLocation(namespace, "textures/gui/" + location + ".png");
        this.width = width;
        this.height = height;
        this.startX = startX;
        this.startY = startY;
    }

    @OnlyIn(Dist.CLIENT)
    public void bind() {
        RenderSystem.setShaderTexture(0, location);
    }

    @OnlyIn(Dist.CLIENT)
    public void render(GuiGraphics graphics, int x, int y) {
        graphics.blit(location, x, y, startX, startY, width, height);
    }

    @OnlyIn(Dist.CLIENT)
    public void render(GuiGraphics graphics, int x, int y, Color c) {
        bind();
        UIRenderHelper.drawColoredTexture(graphics, c, x, y, startX, startY, width, height);
    }
}
