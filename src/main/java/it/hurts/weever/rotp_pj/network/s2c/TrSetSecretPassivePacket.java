package it.hurts.weever.rotp_pj.network.s2c;

import com.github.standobyte.jojo.client.ClientUtil;
import com.github.standobyte.jojo.network.packets.IModPacketHandler;
import it.hurts.weever.rotp_pj.capability.PlayerUtilCapProvider;
import net.minecraft.entity.Entity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class TrSetSecretPassivePacket {
    private final int entityId;
    private final boolean toggle;

    public TrSetSecretPassivePacket(int entityId, boolean toggle) {
        this.entityId = entityId;
        this.toggle = toggle;
    }

    public static class Handler implements IModPacketHandler<TrSetSecretPassivePacket> {

        @Override
        public void encode(TrSetSecretPassivePacket msg, PacketBuffer buf) {
            buf.writeInt(msg.entityId);
            buf.writeBoolean(msg.toggle);
        }

        @Override
        public TrSetSecretPassivePacket decode(PacketBuffer buf) {
            return new TrSetSecretPassivePacket(buf.readInt(), buf.readBoolean());
        }

        @Override
        public void handle(TrSetSecretPassivePacket msg, Supplier<NetworkEvent.Context> ctx) {
            Entity entity = ClientUtil.getEntityById(msg.entityId);
            if (entity != null) {
                entity.getCapability(PlayerUtilCapProvider.CAPABILITY).ifPresent(cap -> cap.setSecretFoodToggle(msg.toggle));
            }
        }

        @Override
        public Class<TrSetSecretPassivePacket> getPacketClass() {
            return TrSetSecretPassivePacket.class;
        }
    }
}
