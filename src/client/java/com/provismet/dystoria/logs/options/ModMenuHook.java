package com.provismet.dystoria.logs.options;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import net.fabricmc.loader.api.FabricLoader;

public class ModMenuHook implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory () {
        if (FabricLoader.getInstance().isModLoaded("cloth-config")) {
            return ClothMenu::build;
        }
        else return parent -> null;
    }
}
