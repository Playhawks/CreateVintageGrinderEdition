package dev.bluephs.vintage_grinder.infrastructure.ponder;

import dev.bluephs.vintage_grinder.VintageBlocks;
import dev.bluephs.vintage_grinder.infrastructure.ponder.scenes.*;
import com.simibubi.create.infrastructure.ponder.AllCreatePonderTags;
import com.tterrag.registrate.util.entry.ItemProviderEntry;
import com.tterrag.registrate.util.entry.RegistryEntry;
import net.createmod.ponder.api.registration.PonderSceneRegistrationHelper;
import net.minecraft.resources.ResourceLocation;

public class VintagePonderScene {

    public static void register(PonderSceneRegistrationHelper<ResourceLocation> helper) {
        PonderSceneRegistrationHelper<ItemProviderEntry<?>> HELPER = helper.withKeyFunction(RegistryEntry::getId);
        HELPER.forComponents(VintageBlocks.BELT_GRINDER)
                .addStoryBoard("belt_grinder/processing", BeltGrinderScenes::processing, AllCreatePonderTags.KINETIC_APPLIANCES);
    }
}