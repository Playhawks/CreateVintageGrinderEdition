package dev.bluephs.vintage_grinder;

import dev.bluephs.vintage_grinder.infrastructure.config.VCStress;
import com.simibubi.create.AllTags;
import dev.bluephs.vintage_grinder.content.kinetics.grinder.*;
import com.simibubi.create.foundation.data.SharedProperties;
import static com.simibubi.create.foundation.data.ModelGen.customItemModel;
import com.tterrag.registrate.util.entry.BlockEntry;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.level.material.MapColor;

import static dev.bluephs.vintage_grinder.VintageGrinder.MY_REGISTRATE;
import static com.simibubi.create.foundation.data.TagGen.*;

public class VintageBlocks {

    static {
        MY_REGISTRATE.setCreativeTab(VintageGrinder.VINTAGE_TAB);
    }

    //Machines
    public static final BlockEntry<GrinderBlock> BELT_GRINDER = MY_REGISTRATE.block("belt_grinder", GrinderBlock::new)
            .initialProperties(SharedProperties::stone)
            .addLayer(() -> RenderType::cutoutMipped)
            .properties(p -> p.mapColor(MapColor.SAND))
            .transform(axeOrPickaxe())
            .blockstate(new GrinderGenerator()::generate)
            .transform(VCStress.setImpact(4.0))
            .item()
            .tag(AllTags.AllItemTags.CONTRAPTION_CONTROLLED.tag)
            .transform(customItemModel())
            .register();

    public static void register() {}
}
