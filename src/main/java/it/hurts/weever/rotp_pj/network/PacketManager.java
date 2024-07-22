package it.hurts.weever.rotp_pj.network;

import com.github.standobyte.jojo.network.packets.IModPacketHandler;
import it.hurts.weever.rotp_pj.RotpPJAddon;
import it.hurts.weever.rotp_pj.network.s2c.TrSetBuffValuePacket;
import it.hurts.weever.rotp_pj.network.s2c.TrSetDeBuffValuePacket;
import it.hurts.weever.rotp_pj.network.s2c.TrSetMainToggleHandPacket;
import it.hurts.weever.rotp_pj.network.s2c.TrSetSecretPassivePacket;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.LogicalSidedProvider;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.fml.network.simple.SimpleChannel;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.function.Predicate;

public class PacketManager {

    private static final String PROTOCOL_VERSION = "1";
    private static SimpleChannel channel;
    private static int packetIndex = 0;

    public static void init() {
        channel = NetworkRegistry.ChannelBuilder
                .named(new ResourceLocation(RotpPJAddon.MOD_ID, "main_channel"))
                .clientAcceptedVersions(PROTOCOL_VERSION::equals)
                .serverAcceptedVersions(PROTOCOL_VERSION::equals)
                .networkProtocolVersion(() -> PROTOCOL_VERSION)
                .simpleChannel();

        registerMessage(channel, new TrSetMainToggleHandPacket.Handler(),          Optional.of(NetworkDirection.PLAY_TO_CLIENT));
        registerMessage(channel, new TrSetSecretPassivePacket.Handler(),          Optional.of(NetworkDirection.PLAY_TO_CLIENT));
        registerMessage(channel, new TrSetDeBuffValuePacket.Handler(),          Optional.of(NetworkDirection.PLAY_TO_CLIENT));
        registerMessage(channel, new TrSetBuffValuePacket.Handler(),          Optional.of(NetworkDirection.PLAY_TO_CLIENT));
    }

    private static <MSG> void registerMessage(SimpleChannel channel, IModPacketHandler<MSG> handler, Optional<NetworkDirection> networkDirection) {
        if (packetIndex > 127) {
            throw new IllegalStateException("Too many packets (> 127) registered for a single channel!");
        }
        channel.registerMessage(packetIndex++, handler.getPacketClass(), handler::encode, handler::decode, handler::enqueueHandleSetHandled, networkDirection);
    }

    public static void sendToServer(Object msg) {
        channel.sendToServer(msg);
    }

    public static void sendToClient(Object msg, ServerPlayerEntity player) {
        if (!(player instanceof FakePlayer)) {
            channel.send(PacketDistributor.PLAYER.with(() -> player), msg);
        }
    }

    public static void sendToClientsTracking(Object msg, Entity entity) {
        channel.send(PacketDistributor.TRACKING_ENTITY.with(() -> entity), msg);
    }

    public static void sendToClientsTrackingAndSelf(Object msg, Entity entity) {
        channel.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> entity), msg);
    }

    public static void sendToNearby(Object msg, @Nullable ServerPlayerEntity excluded, double x, double y, double z, double radius, RegistryKey<World> dimension) {
        channel.send(PacketDistributor.NEAR.with(() -> new PacketDistributor.TargetPoint(excluded, x, y, z, radius, dimension)), msg);
    }

    public static void sendToTrackingChunk(Object msg, Chunk chunk) {
        if (chunk != null) {
            channel.send(PacketDistributor.TRACKING_CHUNK.with(() -> chunk), msg);
        }
    }

    public static void sendGlobally(Object msg, @Nullable RegistryKey<World> dimension) {
        if (dimension != null) {
            channel.send(PacketDistributor.DIMENSION.with(() -> dimension), msg);
        }
        else {
            channel.send(PacketDistributor.ALL.noArg(), msg);
        }
    }

    public static void sendGloballyWithCondition(Object msg, @Nullable RegistryKey<World> dimension, Predicate<ServerPlayerEntity> condition) {
        MinecraftServer server = LogicalSidedProvider.INSTANCE.get(LogicalSide.SERVER);
        for (ServerPlayerEntity player : server.getPlayerList().getPlayers()) {
            if ((dimension == null || player.level.dimension() == dimension) && condition.test(player)) {
                channel.send(PacketDistributor.PLAYER.with(() -> player), msg);
            }
        }
    }
}
