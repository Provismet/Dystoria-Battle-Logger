package com.provismet.dystoria.logs.options;

import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

public final class ClothMenu {
    public static Screen build (Screen parent) {
        ConfigBuilder builder = ConfigBuilder.create();
        builder.setParentScreen(parent);
        builder.setTitle(Text.translatable("title.dystoria.logger"));

        ConfigEntryBuilder entryBuilder = builder.entryBuilder();

        ConfigCategory general = builder.getOrCreateCategory(Text.translatable("category.dystoria.logger.general"));
        general.addEntry(
            entryBuilder.startBooleanToggle(Text.translatable("option.dystoria.logger.log"), ClientSettings.keepLogs)
                .setDefaultValue(false)
                .setTooltip(Text.translatable("tooltip.dystoria.logger.log"))
                .setSaveConsumer(newValue -> ClientSettings.keepLogs = newValue)
                .build()
        );

        builder.setSavingRunnable(ClientSettings::save);

        return builder.build();
    }
}
