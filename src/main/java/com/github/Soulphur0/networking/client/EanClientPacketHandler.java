package com.github.Soulphur0.networking.client;

import com.github.Soulphur0.config.EanServerSettings;
import com.github.Soulphur0.config.singletons.FlightConfig;
import com.github.Soulphur0.networking.server.EanServerSettingsPacket;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

/**
 * Class that handles incoming packets from the server.<br><br>
 * Right now, its use mainly revolve around synchronizing settings, it may serve other purposes further on.<br><br>
 *
 */
public class EanClientPacketHandler {

    public static void register() {
        // 只注册服务器到客户端的数据包接收器
        ClientPlayNetworking.registerGlobalReceiver(EanServerSettingsPacket.ID,
                EanClientPacketHandler::receiveServerSettings);
        // 注意：EanClientSettingsPacket 是客户端发送到服务器的，所以不需要在这里注册接收器
    }

    private static void receiveServerSettings(EanServerSettingsPacket payload, ClientPlayNetworking.Context context) {
        EanServerSettings serverSettings = payload.serverSettings();
        context.client().execute(() -> {
            FlightConfig.updateClientSettings(serverSettings.getFlightConfigInstance());
        });
    }
}
