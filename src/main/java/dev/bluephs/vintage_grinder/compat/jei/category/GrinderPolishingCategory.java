package dev.bluephs.vintage_grinder.compat.jei.category;

import java.util.List;

import javax.annotation.ParametersAreNonnullByDefault;

import dev.bluephs.vintage_grinder.VintageGrinder;
import dev.bluephs.vintage_grinder.compat.jei.category.animations.AnimatedGrinder;
import dev.bluephs.vintage_grinder.content.kinetics.grinder.PolishingRecipe;
import dev.bluephs.vintage_grinder.foundation.gui.VintageGuiTextures;
import com.simibubi.create.compat.jei.category.CreateRecipeCategory;
import com.simibubi.create.content.processing.recipe.ProcessingOutput;
import com.simibubi.create.foundation.gui.AllGuiTextures;

import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;

@ParametersAreNonnullByDefault
public class GrinderPolishingCategory extends CreateRecipeCategory<PolishingRecipe> {

	private final AnimatedGrinder grinder = new AnimatedGrinder();

	public GrinderPolishingCategory(Info<PolishingRecipe> info) {
		super(info);
	}

	@Override
	public void setRecipe(IRecipeLayoutBuilder builder, PolishingRecipe recipe, IFocusGroup focuses) {
		builder
				.addSlot(RecipeIngredientRole.INPUT, 44, 5)
				.setBackground(getRenderedSlot(), -1, -1)
				.addIngredients(recipe.getIngredients().get(0));

		List<ProcessingOutput> results = recipe.getRollableResults();
		int i = 0;
		for (ProcessingOutput output : results) {
			int xOffset = i % 2 == 0 ? 0 : 19;
			int yOffset = (i / 2) * -19;
			builder
					.addSlot(RecipeIngredientRole.OUTPUT, 118 + xOffset, 48 + yOffset)
					.setBackground(getRenderedSlot(output), -1, -1)
					.addItemStack(output.getStack())
					.addRichTooltipCallback(addStochasticTooltip(output));
			i++;
		}
	}

	@Override
	public void draw(PolishingRecipe recipe, IRecipeSlotsView iRecipeSlotsView, GuiGraphics graphics, double mouseX, double mouseY) {
		AllGuiTextures.JEI_DOWN_ARROW.render(graphics, 70, 6);
		AllGuiTextures.JEI_SHADOW.render(graphics, 72 - 17, 42 + 13);

		grinder.draw(graphics, 72, 42);

		int speedLimits = recipe.getSpeedLimits();
		switch (speedLimits) {
			case 1 ->
					graphics.drawCenteredString(Minecraft.getInstance().font, Component.translatable(VintageGrinder.MOD_ID + ".jei.text.required_speed").append(" ").append(Component.translatable(VintageGrinder.MOD_ID + ".jei.text.low")), 88, 75, 0x00FF00);
			case 2 ->
					graphics.drawCenteredString(Minecraft.getInstance().font, Component.translatable(VintageGrinder.MOD_ID + ".jei.text.required_speed").append(" ").append(Component.translatable(VintageGrinder.MOD_ID + ".jei.text.medium")), 88, 75, 0xFFFF00);
			case 3 ->
					graphics.drawCenteredString(Minecraft.getInstance().font, Component.translatable(VintageGrinder.MOD_ID + ".jei.text.required_speed").append(" ").append(Component.translatable(VintageGrinder.MOD_ID + ".jei.text.high")), 88, 75, 0xFF0000);
			default ->
					graphics.drawCenteredString(Minecraft.getInstance().font, Component.translatable(VintageGrinder.MOD_ID + ".jei.text.required_speed").append(" ").append(Component.translatable(VintageGrinder.MOD_ID + ".jei.text.any")), 88, 75, 0xFFFFFF);
		}

		if (recipe.isFragile()) {
			VintageGuiTextures.JEI_FRAGILE.render(graphics, 2, 62);
			if (mouseX >= 2 && mouseX <= 15 && mouseY >= 62 && mouseY <= 85)
				graphics.renderTooltip(Minecraft.getInstance().font,
						Component.translatable(VintageGrinder.MOD_ID + ".jei.text.fragile"), (int)mouseX,  (int)mouseY);
		}
	}

}
