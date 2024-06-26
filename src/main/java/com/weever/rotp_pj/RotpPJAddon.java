package com.weever.rotp_pj;

import com.weever.rotp_pj.init.InitEntities;
import com.weever.rotp_pj.init.InitStands;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.weever.rotp_pj.init.InitSounds;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(RotpPJAddon.MOD_ID)
public class RotpPJAddon {
    public static final String MOD_ID = "rotp_pj";
    public static final Logger LOGGER = LogManager.getLogger();

    public RotpPJAddon() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        InitEntities.ENTITIES.register(modEventBus);
        InitSounds.SOUNDS.register(modEventBus);
        InitStands.ACTIONS.register(modEventBus);
        InitStands.STANDS.register(modEventBus);
    }

    public static Logger getLogger() {
        return LOGGER;
    }
}
