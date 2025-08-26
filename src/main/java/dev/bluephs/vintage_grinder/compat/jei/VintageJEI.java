package dev.bluephs.vintage_grinder.compat.jei;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import javax.annotation.Nonnull;
import dev.bluephs.vintage_grinder.VintageBlocks;
import dev.bluephs.vintage_grinder.VintageGrinder;
import dev.bluephs.vintage_grinder.VintageRecipes;
import dev.bluephs.vintage_grinder.VintageRecipesList;
import dev.bluephs.vintage_grinder.compat.jei.category.*;
import dev.bluephs.vintage_grinder.content.kinetics.grinder.PolishingRecipe;
import dev.bluephs.vintage_grinder.infrastructure.config.VCRecipes;
import dev.bluephs.vintage_grinder.infrastructure.config.VintageConfig;
import com.simibubi.create.AllItems;
import com.simibubi.create.compat.jei.CreateJEI;
import com.simibubi.create.compat.jei.DoubleItemIcon;
import com.simibubi.create.compat.jei.ItemIcon;
import com.simibubi.create.compat.jei.EmptyBackground;
import com.simibubi.create.compat.jei.category.CreateRecipeCategory;
import com.simibubi.create.content.equipment.sandPaper.SandPaperPolishingRecipe;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.runtime.IIngredientManager;
import net.createmod.catnip.config.ConfigBase;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.NotNull;

import static com.simibubi.create.compat.jei.CreateJEI.consumeAllRecipes;

@JeiPlugin
public class VintageJEI implements IModPlugin {

	private static final ResourceLocation ID = new ResourceLocation(VintageGrinder.MOD_ID, "jei_plugin");

	@Override
	@Nonnull
	public ResourceLocation getPluginUid() {
		return ID;
	}

	public IIngredientManager ingredientManager;
	final List<CreateRecipeCategory<?>> ALL = new ArrayList<>();

	@Override
	public void registerCategories(IRecipeCategoryRegistration registration) {
		ALL.clear();

		ALL.add(builder(PolishingRecipe.class)
				.addTypedRecipes(VintageRecipes.POLISHING::getType)
				.catalyst(VintageBlocks.BELT_GRINDER::get)
				.itemIcon(VintageBlocks.BELT_GRINDER.get())
				.emptyBackground(177, 85)
				.build("polishing", GrinderPolishingCategory::new));

		ALL.add(builder(SandPaperPolishingRecipe.class)
				.enableWhen(c -> c.allowSandpaperPolishingOnGrinder)
				.addAllRecipesIf(r -> r instanceof SandPaperPolishingRecipe
						&& VintageRecipesList.isPolishing(r))
				.catalyst(VintageBlocks.BELT_GRINDER::get)
				.doubleItemIcon(VintageBlocks.BELT_GRINDER.get(), AllItems.SAND_PAPER)
				.emptyBackground(177, 85)
				.build("grinder_sandpaper_polishing", GrinderSandpaperPolishingCategory::new));

		ALL.forEach(registration::addRecipeCategories);
	}

	@Override
	public void registerRecipes(IRecipeRegistration registration) {
		ingredientManager = registration.getIngredientManager();
		ALL.forEach(c -> c.registerRecipes(registration));
	}

	@Override
	public void registerRecipeCatalysts(@NotNull IRecipeCatalystRegistration registration) {
		ALL.forEach(c -> c.registerCatalysts(registration));
	}

	private <T extends Recipe<?>> CategoryBuilder<T> builder(Class<? extends T> recipeClass) {
		return new CategoryBuilder<>(recipeClass);
	}

	private static class CategoryBuilder<T extends Recipe<?>> {
		private final Class<? extends T> recipeClass;
		private Predicate<VCRecipes> predicate = cRecipes -> true;

		private IDrawable background;
		private IDrawable icon;

		private final List<Consumer<List<T>>> recipeListConsumers = new ArrayList<>();
		private final List<Supplier<? extends ItemStack>> catalysts = new ArrayList<>();

		public CategoryBuilder(Class<? extends T> recipeClass) {
			this.recipeClass = recipeClass;
		}

		public CategoryBuilder<T> addRecipeListConsumer(Consumer<List<T>> consumer) {
			recipeListConsumers.add(consumer);
			return this;
		}

		public CategoryBuilder<T> enableWhen(Function<VCRecipes, ConfigBase.ConfigBool> configValue) {
			predicate = c -> configValue.apply(c).get();
			return this;
		}

		public CategoryBuilder<T> addTypedRecipes(Supplier<RecipeType<? extends T>> recipeType) {
			return addRecipeListConsumer(recipes -> CreateJEI.<T>consumeTypedRecipes(recipes::add, recipeType.get()));
		}

		public CategoryBuilder<T> catalystStack(Supplier<ItemStack> supplier) {
			catalysts.add(supplier);
			return this;
		}

		public CategoryBuilder<T> catalyst(Supplier<ItemLike> supplier) {
			return catalystStack(() -> new ItemStack(supplier.get()
					.asItem()));
		}

		public void icon(IDrawable icon) {
			this.icon = icon;
		}

		public CategoryBuilder<T> itemIcon(ItemLike item) {
			icon(new ItemIcon(() -> new ItemStack(item)));
			return this;
		}

		public CategoryBuilder<T> doubleItemIcon(ItemLike item1, ItemLike item2) {
			icon(new DoubleItemIcon(() -> new ItemStack(item1), () -> new ItemStack(item2)));
			return this;
		}

		public void background(IDrawable background) {
			this.background = background;
		}

		public CategoryBuilder<T> emptyBackground(int width, int height) {
			background(new EmptyBackground(width, height));
			return this;
		}

		public CategoryBuilder<T> addAllRecipesIf(Predicate<Recipe<?>> pred) {
			return addRecipeListConsumer(recipes -> consumeAllRecipes(recipe -> {
				if (pred.test(recipe)) {
					recipes.add((T) recipe);
				}
			}));
		}

		public CategoryBuilder<T> addAllRecipesIf(Predicate<Recipe<?>> pred, Function<Recipe<?>, T> converter) {
			return addRecipeListConsumer(recipes -> consumeAllRecipes(recipe -> {
				if (pred.test(recipe)) {
					recipes.add(converter.apply(recipe));
				}
			}));
		}

		public CreateRecipeCategory<T> build(String name, CreateRecipeCategory.Factory<T> factory) {
			Supplier<List<T>> recipesSupplier;
			if (predicate.test(VintageConfig.server().recipes)) {
				recipesSupplier = () -> {
					List<T> recipes = new ArrayList<>();
					for (Consumer<List<T>> consumer : recipeListConsumers)
						consumer.accept(recipes);
					return recipes;
				};
			} else {
				recipesSupplier = Collections::emptyList;
			}

			CreateRecipeCategory.Info<T> info = new CreateRecipeCategory.Info<>(
					new mezz.jei.api.recipe.RecipeType<>(VintageGrinder.asResource(name), recipeClass),
					Component.translatable(VintageGrinder.MOD_ID + ".recipe." + name), background, icon, recipesSupplier, catalysts);
            return factory.create(info);
		}
	}
}
