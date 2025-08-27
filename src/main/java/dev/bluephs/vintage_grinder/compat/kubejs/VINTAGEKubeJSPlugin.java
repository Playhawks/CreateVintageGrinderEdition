package dev.bluephs.vintage_grinder.compat.kubejs;

import dev.bluephs.vintage_grinder.compat.kubejs.recipes.*;
import dev.latvian.mods.kubejs.KubeJSPlugin;
import dev.latvian.mods.kubejs.recipe.schema.RegisterRecipeSchemasEvent;
import net.minecraft.resources.ResourceLocation;

public class VINTAGEKubeJSPlugin extends KubeJSPlugin {
    public void registerRecipeSchemas(RegisterRecipeSchemasEvent event) {
        event.register(new ResourceLocation("vintage_grinder:polishing"), PolishingRecipeSchema.POLISHING_PROCESSING);
    }
}
