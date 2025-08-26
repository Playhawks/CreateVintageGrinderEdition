package dev.bluephs.vintage_grinder;

import dev.bluephs.vintage_grinder.content.kinetics.grinder.GrinderBlockEntity;
import dev.bluephs.vintage_grinder.content.kinetics.grinder.GrinderRenderer;
import dev.bluephs.vintage_grinder.content.kinetics.grinder.GrinderVisual;
import com.tterrag.registrate.util.entry.BlockEntityEntry;

import static dev.bluephs.vintage_grinder.VintageGrinder.MY_REGISTRATE;

public class VintageBlockEntity {
    public static final BlockEntityEntry<GrinderBlockEntity> GRINDER = MY_REGISTRATE
            .blockEntity("grinder", GrinderBlockEntity::new)
            .visual(() -> GrinderVisual::new)
            .validBlocks(VintageBlocks.BELT_GRINDER)
            .renderer(() -> GrinderRenderer::new)
            .register();

    public static void register() {}
}
