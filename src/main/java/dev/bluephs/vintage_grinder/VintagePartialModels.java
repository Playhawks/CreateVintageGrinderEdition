package dev.bluephs.vintage_grinder;

import dev.engine_room.flywheel.lib.model.baked.PartialModel;

public class VintagePartialModels {

	public static final PartialModel
		GRINDER_BELT_ACTIVE = block("belt_grinder/belt_active"),
		GRINDER_BELT_INACTIVE = block("belt_grinder/belt_inactive"),
		GRINDER_BELT_REVERSED = block("belt_grinder/belt_reversed"),
		GRINDER_BELT_ACTIVE_RED = block("belt_grinder/belt_active_red"),
		GRINDER_BELT_INACTIVE_RED = block("belt_grinder/belt_inactive_red"),
		GRINDER_BELT_REVERSED_RED = block("belt_grinder/belt_reversed_red"),
		GRINDER_BELT_ACTIVE_DIAMOND = block("belt_grinder/belt_active_diamond"),
		GRINDER_BELT_INACTIVE_DIAMOND = block("belt_grinder/belt_inactive_diamond"),
		GRINDER_BELT_REVERSED_DIAMOND = block("belt_grinder/belt_reversed_diamond"),
		GRINDER_BELT_ACTIVE_IRON = block("belt_grinder/belt_active_iron"),
		GRINDER_BELT_INACTIVE_IRON = block("belt_grinder/belt_inactive_iron"),
		GRINDER_BELT_REVERSED_IRON = block("belt_grinder/belt_reversed_iron"),
		GRINDER_BELT_ACTIVE_OBSIDIAN = block("belt_grinder/belt_active_obsidian"),
		GRINDER_BELT_INACTIVE_OBSIDIAN = block("belt_grinder/belt_inactive_obsidian"),
		GRINDER_BELT_REVERSED_OBSIDIAN = block("belt_grinder/belt_reversed_obsidian");
	private static PartialModel block(String path) {
		return PartialModel.of(VintageGrinder.asResource("block/" + path));
	}

	public static void init() {
		// init static fields
	}

}
