package dev.bluephs.vintage_grinder.foundation.advancement;

import dev.bluephs.vintage_grinder.VintageGrinder;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public enum VintageAdvancements {

    USE_BELT_GRINDER("use_belt_grinder"),
    BELT_GRINDER_SKIN_CHANGE("belt_grinder_skin_change");

    private String id;
    private SimpleVintageTrigger trigger;

	VintageAdvancements(String id) {
        this.id = id;
        trigger = new SimpleVintageTrigger(id);
    };

    public void award(Level level, Player player) {
        if (level.isClientSide()) return;
        if (player instanceof ServerPlayer serverPlayer) {
            trigger.trigger(serverPlayer);
        } else {
            VintageGrinder.logThis("Could not award Vintage Advancement " + id + " to client-side Player.");
        };
    };

    public static void register() {
        for (VintageAdvancements e : values()) {
            CriteriaTriggers.register(e.trigger);
        };
    };
}
