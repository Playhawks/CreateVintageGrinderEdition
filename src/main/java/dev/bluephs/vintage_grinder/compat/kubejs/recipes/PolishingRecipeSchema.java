package dev.bluephs.vintage_grinder.compat.kubejs.recipes;

import com.google.gson.JsonObject;
import com.simibubi.create.content.processing.recipe.ProcessingOutput;
import com.simibubi.create.foundation.recipe.BlockTagIngredient;
import dev.latvian.mods.kubejs.item.InputItem;
import dev.latvian.mods.kubejs.item.OutputItem;
import dev.latvian.mods.kubejs.item.ingredient.TagContext;
import dev.latvian.mods.kubejs.recipe.RecipeJS;
import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.component.*;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

public interface PolishingRecipeSchema {

	RecipeKey<OutputItem[]> RESULTS = ItemComponents.OUTPUT_ARRAY.key("results");
	RecipeKey<InputItem[]> INGREDIENT = ItemComponents.INPUT_ARRAY.key("ingredients");
	RecipeKey<Long> PROCESSING_TIME_REQUIRED = TimeComponent.TICKS.key("processingTime").optional(100L).alwaysWrite();
	RecipeKey<Boolean> FRAGILE = BooleanComponent.BOOLEAN.key("fragile").optional(false);
	RecipeKey<Integer> SPEED_LIMIT = NumberComponent.IntRange.INT.key("speedLimits").optional(0);

	class ProcessingRecipeJS extends RecipeJS {
		@Override
		public boolean inputItemHasPriority(Object from) {
			if (from instanceof InputItem || from instanceof Ingredient || from instanceof ItemStack) {
				return true;
			}

			var input = readInputItem(from);
			if (input.ingredient instanceof BlockTagIngredient blockTag) {
				return !TagContext.INSTANCE.getValue().isEmpty(blockTag.getTag());
			}

			return !input.isEmpty();
		}

		@Override
		public OutputItem readOutputItem(Object from) {
			if (from instanceof ProcessingOutput output) {
				return OutputItem.of(output.getStack(), output.getChance());
			} else {
				var outputItem = super.readOutputItem(from);
				if (from instanceof JsonObject j && j.has("chance")) {
					return outputItem.withChance(j.get("chance").getAsFloat());
				}
				return outputItem;
			}
		}

		public RecipeJS fragile() {
			return setValue(FRAGILE, true);
		}

		public RecipeJS speedLimits(int limits) {
			return setValue(SPEED_LIMIT, limits);
		}
	}

	RecipeSchema POLISHING_PROCESSING = new RecipeSchema(ProcessingRecipeJS.class, ProcessingRecipeJS::new, RESULTS, INGREDIENT, PROCESSING_TIME_REQUIRED, SPEED_LIMIT, FRAGILE);
}
