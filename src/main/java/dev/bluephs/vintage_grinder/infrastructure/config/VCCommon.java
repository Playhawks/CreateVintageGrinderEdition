package dev.bluephs.vintage_grinder.infrastructure.config;

import net.createmod.catnip.config.ConfigBase;

public class VCCommon extends ConfigBase {

	public final ConfigBase.ConfigGroup common = group(0, "common", Comments.common);

	public final ConfigInt defaultBeltGrinderSkin = i(0, 0, 4, "defaultBeltGrinderSkin", Comments.defaultBeltGrinderSkin);

	@Override
	public String getName() {
		return "common";
	}

	private static class Comments {
		static String common = "Client/server settings";
		static String defaultBeltGrinderSkin = "Defines default Belt Grinder appearance";
	}
}
