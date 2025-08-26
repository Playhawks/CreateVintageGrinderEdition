package dev.bluephs.vintage_grinder;

import com.simibubi.create.foundation.utility.CreateLang;
import net.createmod.catnip.lang.LangBuilder;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

import static net.createmod.catnip.lang.LangBuilder.resolveBuilders;

public class VintageLang extends CreateLang {

    public static MutableComponent translateDirect(String key, Object... args) {
        return Component.translatable(VintageGrinder.MOD_ID + "." + key, resolveBuilders(args));
    }

    public static LangBuilder builder() {
        return new LangBuilder(VintageGrinder.MOD_ID);
    }

    public static LangBuilder translate(String langKey, Object... args) {
        return builder().translate(langKey, args);
    }
}
