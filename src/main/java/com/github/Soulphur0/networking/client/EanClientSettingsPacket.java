package com.github.Soulphur0.networking.client;

import com.github.Soulphur0.config.EanClientSettings;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

import static com.github.Soulphur0.ElytraAeronautics.MOD_ID;

/**
 * Elytra Aeronautics' custom packet class, it holds a EanClientSettings instance with a client config setting.<br><br>
 * Reading and writing calls are done by the EanClientSettingsPacketSerializer class.<br><br>
 * The logic of packing and unpacking the settings themselves is done within the EanClientSettings class.<br><br>
 *
 * @see EanClientSettingsPacket
 * @see EanClientSettings
 *
 */
public record EanClientSettingsPacket(EanClientSettings clientSettings) implements CustomPayload {
    public static final CustomPayload.Id<EanClientSettingsPacket> ID = new CustomPayload.Id<>(
            Identifier.of(MOD_ID, "client_settings"));
    public static final PacketCodec<PacketByteBuf, EanClientSettingsPacket> CODEC = PacketCodec.of(
            EanClientSettingsPacket::write,
            buf -> new EanClientSettingsPacket(EanClientSettings.createFromBuffer(buf)));

    public void write(PacketByteBuf buf) {
        clientSettings.writeToBuffer(buf);
    }

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
