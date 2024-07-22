package it.hurts.weever.rotp_pj.network.s2c;
import java.util.function.Supplier;

import com.github.standobyte.jojo.client.ClientUtil;
import com.github.standobyte.jojo.network.packets.IModPacketHandler;

import it.hurts.weever.rotp_pj.capability.PlayerUtilCapProvider;
import net.minecraft.entity.Entity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

public class TrSetMainToggleHandPacket {
    private final int entityId;
    private final boolean toggle;

    public TrSetMainToggleHandPacket(int entityId, boolean toggle) {
        this.entityId = entityId;
        this.toggle = toggle;
    }

    public static class Handler implements IModPacketHandler<TrSetMainToggleHandPacket> {

        @Override
        public void encode(TrSetMainToggleHandPacket msg, PacketBuffer buf) {
            buf.writeInt(msg.entityId);
            buf.writeBoolean(msg.toggle);
        }

        @Override
        public TrSetMainToggleHandPacket decode(PacketBuffer buf) {
            return new TrSetMainToggleHandPacket(buf.readInt(), buf.readBoolean());
        }

        @Override
        public void handle(TrSetMainToggleHandPacket msg, Supplier<NetworkEvent.Context> ctx) {
            Entity entity = ClientUtil.getEntityById(msg.entityId);
            if (entity != null) {
                entity.getCapability(PlayerUtilCapProvider.CAPABILITY).ifPresent(cap -> cap.setMainHandToggle(msg.toggle));
            }
        }

        @Override
        public Class<TrSetMainToggleHandPacket> getPacketClass() {
            return TrSetMainToggleHandPacket.class;
        }
    }
}
