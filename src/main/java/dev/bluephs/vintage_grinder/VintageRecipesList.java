package dev.bluephs.vintage_grinder;

import dev.bluephs.vintage_grinder.content.kinetics.grinder.PolishingRecipe;
import net.minecraft.core.NonNullList;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;

import java.util.ArrayList;
import java.util.List;

public class VintageRecipesList {
    static List<PolishingRecipe> polishing;

    static public void init(MinecraftServer level) {
        polishing = level.getRecipeManager().getAllRecipesFor(VintageRecipes.POLISHING.getType());
    }

    static public boolean isPolishing(Recipe<?> r) {
        if (polishing == null) return true;
        if (polishing.isEmpty()) return true;

        for (PolishingRecipe recipe : polishing)
            for (ItemStack stack : r.getIngredients().get(0).getItems())
                if (recipe.getIngredients().get(0).test(stack)) return false;

        return true;
    }
}
