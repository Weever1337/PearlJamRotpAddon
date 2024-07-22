package it.hurts.weever.rotp_pj.network.s2c;

import com.github.standobyte.jojo.client.ClientUtil;
import com.github.standobyte.jojo.network.packets.IModPacketHandler;
import it.hurts.weever.rotp_pj.capability.PlayerUtilCapProvider;
import it.hurts.weever.rotp_pj.util.GameplayUtil;
import net.minecraft.entity.Entity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.Optional;
import java.util.function.Supplier;

public class TrSetBuffValuePacket {
    private final int entityId;
    private final String set;

    public TrSetBuffValuePacket(int entityId, String set) {
        this.entityId = entityId;
        this.set = set;
    }

    public static class Handler implements IModPacketHandler<TrSetBuffValuePacket> {

        @Override
        public void encode(TrSetBuffValuePacket msg, PacketBuffer buf) {
            buf.writeInt(msg.entityId);
        }

        @Override
        public TrSetBuffValuePacket decode(PacketBuffer buf) {
            return new TrSetBuffValuePacket(buf.readInt(), buf.readComponent().getString());
        }

        @Override
        public void handle(TrSetBuffValuePacket msg, Supplier<NetworkEvent.Context> ctx) {
            Entity entity = ClientUtil.getEntityById(msg.entityId);
            if (entity != null) {
                entity.getCapability(PlayerUtilCapProvider.CAPABILITY).ifPresent(cap -> cap.setBuffValue(msg.set));
            }
        }

        @Override
        public Class<TrSetBuffValuePacket> getPacketClass() {
            return TrSetBuffValuePacket.class;
        }
    }
}
