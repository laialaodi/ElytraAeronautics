package com.github.Soulphur0.networking.server;

import com.github.Soulphur0.config.EanServerSettings;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

import static com.github.Soulphur0.ElytraAeronautics.MOD_ID;

/**
 * Elytra Aeronautics' custom packet class, it holds a EanServerSettings instance with all server-dependant config settings.<br><br>
 * Reading and writing calls are done by the EanServerSettingsPacketSerializer class.<br><br>
 * The logic of packing and unpacking the settings themselves is done within the EanServerSettings class.<br><br>
 *
 * @see EanServerSettingsPacket
 * @see EanServerSettings
 *
 */
public record EanServerSettingsPacket(EanServerSettings serverSettings) implements CustomPayload {
    public static final CustomPayload.Id<EanServerSettingsPacket> ID = new CustomPayload.Id<>(
            Identifier.of(MOD_ID, "server_settings"));
    public static final PacketCodec<PacketByteBuf, EanServerSettingsPacket> CODEC = PacketCodec.of(
            EanServerSettingsPacket::write,
            buf -> new EanServerSettingsPacket(EanServerSettings.createFromBuffer(buf)));

    public void write(PacketByteBuf buf) {
        serverSettings.writeToBuffer(buf);
    }

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
