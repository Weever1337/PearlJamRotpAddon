package it.hurts.weever.rotp_pj.init;

import it.hurts.weever.rotp_pj.RotpPJAddon;
import it.hurts.weever.rotp_pj.capability.PlayerUtilCap;
import it.hurts.weever.rotp_pj.capability.PlayerUtilCapProvider;
import it.hurts.weever.rotp_pj.capability.PlayerUtilCapStorage;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = RotpPJAddon.MOD_ID)
public class InitCapabilities {
    private static final ResourceLocation PLAYER_UTIL = new ResourceLocation(RotpPJAddon.MOD_ID, "player_util");

    @SubscribeEvent
    public static void onAttachCapabilitiesEntity(AttachCapabilitiesEvent<Entity> event) {
        Entity entity = event.getObject();
        if (entity instanceof PlayerEntity) {
            event.addCapability(PLAYER_UTIL, new PlayerUtilCapProvider((PlayerEntity) entity));
        }
    }

    public static void registerCapabilities() {
        CapabilityManager.INSTANCE.register(PlayerUtilCap.class, new PlayerUtilCapStorage(), () -> new PlayerUtilCap(null));
    }
}