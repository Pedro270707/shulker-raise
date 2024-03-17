package net.pedroricardo;

import io.wispforest.owo.config.annotation.Config;
import io.wispforest.owo.config.annotation.Hook;
import io.wispforest.owo.config.annotation.Modmenu;

@Modmenu(modId = "shulkerraise")
@Config(name = "shulkerraise", wrapperName = "ShulkerRaiseConfig")
public class ShulkerRaiseConfigModel {
    @Hook
    public boolean alwaysRenderWithBlockEntity = false;
}
