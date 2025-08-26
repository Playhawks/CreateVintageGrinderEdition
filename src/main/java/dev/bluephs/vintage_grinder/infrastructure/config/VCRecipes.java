package dev.bluephs.vintage_grinder.infrastructure.config;


import net.createmod.catnip.config.ConfigBase;

public class VCRecipes extends ConfigBase {
	public final ConfigGroup recipes = group(0, "recipes",
			Comments.recipes);
	public final ConfigGroup grinder = group(1, "grinder",
			Comments.grinder);
	public final ConfigBool destroyOnWrongGrinderSpeed =
			b(false, "destroyOnWrongGrinderSpeed", Comments.destroyOnWrongGrinderSpeed);
	public final ConfigInt lowSpeedValue =
			i(16, 1, 256, "lowSpeedValue", Comments.lowSpeedValue);
	public final ConfigInt mediumSpeedValue =
			i(64, 1, 256, "mediumSpeedValue", Comments.mediumSpeedValue);
	public final ConfigBool allowSandpaperPolishingOnGrinder =
			b(true, "allowSandpaperPolishingOnGrinder", Comments.allowSandpaperPolishingOnGrinder);
	public final ConfigInt speedLimitsForSandpaperPolishingRecipes =
			i(1, 0, 3, "speedLimitsForSandpaperPolishingRecipes", Comments.speedLimitsForSandpaperPolishingRecipes);

	@Override
	public String getName() {
		return "recipes";
	}

	private static class Comments {
		static String destroyOnWrongGrinderSpeed = "Destroy item, when it inserted in grinder with wrong recipe speed. Only for sandpaper recipes.";
		static String lowSpeedValue = "Low speed value for grinder crafts, speedLimits = 1.";
		static String mediumSpeedValue = "Medium speed value for grinder crafts, speedLimits = 2.";
		static String allowSandpaperPolishingOnGrinder = "Allows sandpaper crafts on belt grinder, when recipes collides belt grinder recipe have priority.";
		static String speedLimitsForSandpaperPolishingRecipes = "Works only when \"allowSandpaperPolishingOnGrinder\" is true. Defines speed limits for sandpaper recipes on belt grinder.";
		static String grinder = "Grinder settings";
		static String recipes = "Recipes configs";
	}
}