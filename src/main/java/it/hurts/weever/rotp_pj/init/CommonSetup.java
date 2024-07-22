package it.hurts.weever.rotp_pj.init;

import it.hurts.weever.rotp_pj.RotpPJAddon;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod.EventBusSubscriber(modid = RotpPJAddon.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CommonSetup {
    @SubscribeEvent
    public static void onFMLCommonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(InitCapabilities::registerCapabilities);
    }
}