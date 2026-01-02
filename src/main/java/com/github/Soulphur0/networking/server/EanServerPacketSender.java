package com.github.Soulphur0.networking.server;

import com.github.Soulphur0.config.EanClientSettings;
import com.github.Soulphur0.config.EanServerSettings;
import com.github.Soulphur0.networking.client.EanClientSettingsPacket;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;

/**
 * Class that holds utility method for networking.<br><br>
 * Its main purpose is to hold static methods with all necessary sends to the client.<br><br>
 *
 */
public class EanServerPacketSender {

    // ? Sync all clients' config with the server.
    // ¿ Used when the server config is changed by an operator.
    public static void syncAllClientsConfigWithServer(PlayerEntity user) {
        if (user.getWorld().isClient()) return;

        // + Create server settings packet.
        EanServerSettingsPacket packet = new EanServerSettingsPacket(new EanServerSettings());

        // + Send sync order to all connected clients.
        ServerPlayNetworking.send((ServerPlayerEntity) user, packet);
        if (user.getServer() != null) {
            for (ServerPlayerEntity serverPlayer : PlayerLookup.all(user.getServer())) {
                ServerPlayNetworking.send(serverPlayer, packet);
            }
        }
    }

    // ? Sync a single client's config with the server.
    // ¿ Used when a player joins the server.
    public static void syncClientConfigWithServer(PlayerEntity user) {
        if (user.getWorld().isClient()) return;

        // + Create server settings packet.
        EanServerSettingsPacket packet = new EanServerSettingsPacket(new EanServerSettings());

        // + Send sync order to the joined player.
        ServerPlayNetworking.send((ServerPlayerEntity) user, packet);
    }

    // ? Send config written in command to the client.
    // ¿ Since the config command is server side, it is needed to send a packet to the client in order to update client-related configuration.
    public static void sendClientConfig(ServerPlayerEntity user, EanClientSettings setting) {
        if (user.getWorld().isClient()) return;

        // + Create client settings packet.
        EanClientSettingsPacket packet = new EanClientSettingsPacket(setting);

        // + Send packet to the client.
        ServerPlayNetworking.send(user, packet);
    }
}