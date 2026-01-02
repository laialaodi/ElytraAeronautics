package com.github.Soulphur0;

import com.github.Soulphur0.config.commands.EanCommands;
import com.github.Soulphur0.config.singletons.FlightConfig;
import com.github.Soulphur0.networking.client.EanClientSettingsPacket;
import com.github.Soulphur0.networking.server.EanServerPacketSender;
import com.github.Soulphur0.networking.server.EanServerSettingsPacket;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.server.network.ServerPlayerEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ElytraAeronautics implements ModInitializer {
    public static final String MOD_ID = "elytra_aeronautics";
    public static final Logger LOGGER = LogManager.getLogger("ElytraAeronautics");

    @Override
    public void onInitialize() {
        // 注册网络数据包类型 - 服务器到客户端
        PayloadTypeRegistry.playS2C().register(EanServerSettingsPacket.ID, EanServerSettingsPacket.CODEC);
        // 注册网络数据包类型 - 客户端到服务器
        PayloadTypeRegistry.playC2S().register(EanClientSettingsPacket.ID, EanClientSettingsPacket.CODEC);

        // ? Read the config data saved in disk directly to config instance upon initialization.
        FlightConfig.readFromDisk();

        // ? Register config command.
        EanCommands.register();

        // ? Register event handler.
        // ¿ On world/server join, sync the config, on dedicated servers reading from disk is not needed.
        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            ServerPlayerEntity player = handler.player;

            if (server.isDedicated()) EanServerPacketSender.syncClientConfigWithServer(player);
            else {
                FlightConfig.readFromDisk();
                EanServerPacketSender.syncClientConfigWithServer(player);
            }
        });

        LOGGER.info("Elytra Aeronautics initialized! Have a good flight!");
    }
}