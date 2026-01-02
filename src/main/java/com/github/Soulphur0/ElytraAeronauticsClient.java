package com.github.Soulphur0;

import com.github.Soulphur0.config.singletons.CloudConfig;
import com.github.Soulphur0.networking.client.EanClientPacketHandler;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class ElytraAeronauticsClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        // 现在尝试注册网络包接收器
        EanClientPacketHandler.register();

        CloudConfig.readFromDisk();
    }
}
