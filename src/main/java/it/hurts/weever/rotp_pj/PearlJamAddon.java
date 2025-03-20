package it.hurts.weever.rotp_pj;

import it.hurts.weever.rotp_pj.init.*;
import it.hurts.weever.rotp_pj.network.PacketManager;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(PearlJamAddon.MOD_ID)
public class PearlJamAddon {
    public static final String MOD_ID = "rotp_pj";
    public static final Logger LOGGER = LogManager.getLogger();

    public PearlJamAddon() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        InitEntities.ENTITIES.register(modEventBus);
        InitSounds.SOUNDS.register(modEventBus);
        InitStands.ACTIONS.register(modEventBus);
        InitStands.STANDS.register(modEventBus);
        InitParticles.PARTICLES.register(modEventBus);
        PacketManager.init();
        modEventBus.addListener(this::onFMLCommonSetup);
    }

    public void onFMLCommonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(InitCapabilities::registerCapabilities);
    }

    public static Logger getLogger() {
        return LOGGER;
    }
}
