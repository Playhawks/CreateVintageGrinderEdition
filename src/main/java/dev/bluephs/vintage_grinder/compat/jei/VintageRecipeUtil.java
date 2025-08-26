package dev.bluephs.vintage_grinder.compat.jei;

import dev.bluephs.vintage_grinder.VintageLang;
import mezz.jei.api.gui.ingredient.IRecipeSlotRichTooltipCallback;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;

public class VintageRecipeUtil {

    public static IRecipeSlotRichTooltipCallback addTooltip(String lang) {
        return (view, tooltip) -> {
            Component text = VintageLang.translateDirect(lang).withStyle(ChatFormatting.LIGHT_PURPLE);
            tooltip.add(text);
        };
    }
}
