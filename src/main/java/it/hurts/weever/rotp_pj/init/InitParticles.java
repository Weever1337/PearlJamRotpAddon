package it.hurts.weever.rotp_pj.init;

import it.hurts.weever.rotp_pj.RotpPJAddon;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.ParticleType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class InitParticles {
    public static final DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, RotpPJAddon.MOD_ID);

    public static final RegistryObject<BasicParticleType> SPARK_YELLOW = PARTICLES.register("spark_yellow", () -> new BasicParticleType(false));
    public static final RegistryObject<BasicParticleType> SPARK_ORANGE = PARTICLES.register("spark_orange", () -> new BasicParticleType(false));
}
