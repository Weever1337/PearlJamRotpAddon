package it.hurts.weever.rotp_pj.client;

import com.github.standobyte.jojo.client.particle.HamonSparkParticle;
import it.hurts.weever.rotp_pj.RotpPJAddon;
import it.hurts.weever.rotp_pj.client.render.PJRenderer;
import it.hurts.weever.rotp_pj.init.InitParticles;
import it.hurts.weever.rotp_pj.init.InitStands;

import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@EventBusSubscriber(modid = RotpPJAddon.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientInit {
    
    @SubscribeEvent
    public static void onFMLClientSetup(FMLClientSetupEvent event) {
        RenderingRegistry.registerEntityRenderingHandler(
                InitStands.STAND_PJ.getEntityType(), PJRenderer::new);
        event.enqueueWork(() -> {
            Minecraft mc = event.getMinecraftSupplier().get();

            ClientEventHandler.init(mc);
        });
    }

    @SubscribeEvent
    public static void onMcConstructor(ParticleFactoryRegisterEvent event) {
        Minecraft mc = Minecraft.getInstance();
        mc.particleEngine.register(InitParticles.SPARK_YELLOW.get(), HamonSparkParticle.HamonParticleFactory::new);
        mc.particleEngine.register(InitParticles.SPARK_ORANGE.get(), HamonSparkParticle.HamonParticleFactory::new);
    }
}
