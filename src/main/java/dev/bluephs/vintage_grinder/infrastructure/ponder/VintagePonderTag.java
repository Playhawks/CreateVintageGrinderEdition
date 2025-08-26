package dev.bluephs.vintage_grinder.infrastructure.ponder;

import dev.bluephs.vintage_grinder.VintageBlocks;
import com.simibubi.create.infrastructure.ponder.AllCreatePonderTags;
import com.tterrag.registrate.util.entry.RegistryEntry;
import net.createmod.ponder.api.registration.PonderTagRegistrationHelper;
import net.minecraft.resources.ResourceLocation;

public class VintagePonderTag {

    public static void register(PonderTagRegistrationHelper<ResourceLocation> helper) {
        PonderTagRegistrationHelper<RegistryEntry<?>> HELPER = helper.withKeyFunction(RegistryEntry::getId);
        HELPER.addToTag(AllCreatePonderTags.KINETIC_APPLIANCES)
                .add(VintageBlocks.BELT_GRINDER);
    }
}
