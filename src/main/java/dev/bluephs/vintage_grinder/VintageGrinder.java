package dev.bluephs.vintage_grinder;

import dev.bluephs.vintage_grinder.foundation.advancement.VintageAdvancements;
import dev.bluephs.vintage_grinder.infrastructure.config.VintageConfig;
import dev.bluephs.vintage_grinder.infrastructure.ponder.VintagePonderPlugin;
import org.slf4j.Logger;
import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

@Mod(VintageGrinder.MOD_ID)
public class VintageGrinder {
    public static final String MOD_ID = "vintage_grinder";
    private static final Logger LOGGER = LogUtils.getLogger();

    public static void logThis(String str) {
        LOGGER.info(str);
    }

    public static final CreateRegistrate MY_REGISTRATE = CreateRegistrate.create(MOD_ID);

    static {
        MY_REGISTRATE.setTooltipModifierFactory(item -> {
            return new ItemDescription.Modifier(item, FontHelper.Palette.STANDARD_CREATE)
                    .andThen(TooltipModifier.mapNull(KineticStats.create(item)));
        });
    }

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MOD_ID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MOD_ID);
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MOD_ID);
    public static final RegistryObject<Item> GRINDER_BELT = ITEMS.register("grinder_belt", () -> new Item(new Item.Properties()));

    public static boolean useEnergy = false;

    public static final RegistryObject<CreativeModeTab> VINTAGE_TAB = CREATIVE_MODE_TABS.register("vintage_tab", () -> CreativeModeTab.builder()
            .withTabsBefore(CreativeModeTabs.COMBAT)
            .title(Component.translatable("itemGroup." + MOD_ID))
            .icon(() -> VintageBlocks.BELT_GRINDER.get().asItem().getDefaultInstance())
            .displayItems((parameters, output) -> {
                boolean forceItems = VintageConfig.client().forceCompatItemsIntoCreativeTab.get();

                output.accept(VintageBlocks.BELT_GRINDER.get());
                output.accept(GRINDER_BELT.get());

                if (VintageConfig.client().legacyMaterialsIntoCreativeTab.get()) {
                    output.accept(AllItems.REFINED_RADIANCE);
                    output.accept(AllItems.SHADOW_STEEL);
                }

            }).build());

    public VintageGrinder() {
        onCtor();
        MinecraftForge.EVENT_BUS.register(this);
    }

    public static void onCtor() {
        ModLoadingContext modLoadingContext = ModLoadingContext.get();
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        IEventBus forgeEventBus = MinecraftForge.EVENT_BUS;
        MY_REGISTRATE.registerEventListeners(modEventBus);
        BLOCKS.register(modEventBus);
        ITEMS.register(modEventBus);
        CREATIVE_MODE_TABS.register(modEventBus);
        VintageBlocks.register();
        VintageBlockEntity.register();
        VintageRecipes.register(modEventBus);
        VintagePartialModels.init();
        modEventBus.addListener(VintageGrinder::commonSetup);
        VintageConfig.register(modLoadingContext);
    }

    private static void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(VintageAdvancements::register);
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        VintageRecipesList.init(event.getServer());
    }

    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            PonderIndex.addPlugin(new VintagePonderPlugin());
        }
    }

    public static ResourceLocation asResource(String path) {
        return new ResourceLocation(MOD_ID, path);
    }
}
