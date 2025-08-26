package dev.bluephs.vintage_grinder.foundation.advancement;

import dev.bluephs.vintage_grinder.VintageGrinder;
import com.simibubi.create.foundation.advancement.SimpleCreateTrigger;

import net.minecraft.resources.ResourceLocation;

public class SimpleVintageTrigger extends SimpleCreateTrigger {

    private ResourceLocation trueID;

    public SimpleVintageTrigger(String id) {
        super(id);
        trueID = VintageGrinder.asResource(id);
    };

    @Override
    public ResourceLocation getId() {
        return trueID;
    };
    
};
