package dev.bluephs.vintage_grinder.compat.kubejs;

import com.simibubi.create.content.processing.recipe.ProcessingRecipeSerializer;
import dev.bluephs.vintage_grinder.VintageRecipes;
import dev.bluephs.vintage_grinder.compat.kubejs.recipes.*;
import dev.latvian.mods.kubejs.KubeJSPlugin;
import dev.latvian.mods.kubejs.create.ProcessingRecipeSchema;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;
import dev.latvian.mods.kubejs.recipe.schema.RegisterRecipeSchemasEvent;
import dev.latvian.mods.kubejs.registry.RegistryInfo;

import java.util.Map;

public class VINTAGEKubeJSPlugin extends KubeJSPlugin {
    private static final Map<VintageRecipes, RecipeSchema> recipeSchema = Map.of(
            VintageRecipes.POLISHING, PolishingRecipeSchema.POLISHING_PROCESSING
    );

    public void registerRecipeSchemas(RegisterRecipeSchemasEvent event) {
        for (var vintageRecipeType : VintageRecipes.values()) {
            if (vintageRecipeType.getSerializer() instanceof ProcessingRecipeSerializer<?>) {
                var schema = recipeSchema.getOrDefault(vintageRecipeType, ProcessingRecipeSchema.PROCESSING_DEFAULT);
                event.register(vintageRecipeType.getId(), schema);
            }
        }
    }
}
