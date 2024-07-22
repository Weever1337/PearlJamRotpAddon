package it.hurts.weever.rotp_pj.network.s2c;

import com.github.standobyte.jojo.client.ClientUtil;
import com.github.standobyte.jojo.network.packets.IModPacketHandler;
import it.hurts.weever.rotp_pj.capability.PlayerUtilCapProvider;
import net.minecraft.entity.Entity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class TrSetDeBuffValuePacket {
    private final int entityId;
    private final String set;

    public TrSetDeBuffValuePacket(int entityId, String set) {
        this.entityId = entityId;
        this.set = set;
    }

    public static class Handler implements IModPacketHandler<TrSetDeBuffValuePacket> {

        @Override
        public void encode(TrSetDeBuffValuePacket msg, PacketBuffer buf) {
            buf.writeInt(msg.entityId);
        }

        @Override
        public TrSetDeBuffValuePacket decode(PacketBuffer buf) {
            return new TrSetDeBuffValuePacket(buf.readInt(), buf.readComponent().getString());
        }

        @Override
        public void handle(TrSetDeBuffValuePacket msg, Supplier<NetworkEvent.Context> ctx) {
            Entity entity = ClientUtil.getEntityById(msg.entityId);
            if (entity != null) {
                entity.getCapability(PlayerUtilCapProvider.CAPABILITY).ifPresent(cap -> cap.setDeBuffValue(msg.set));
            }
        }

        @Override
        public Class<TrSetDeBuffValuePacket> getPacketClass() {
            return TrSetDeBuffValuePacket.class;
        }
    }
}
