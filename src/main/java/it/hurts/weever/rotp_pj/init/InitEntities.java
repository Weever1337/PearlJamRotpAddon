package it.hurts.weever.rotp_pj.init;

import it.hurts.weever.rotp_pj.RotpPJAddon;

import net.minecraft.entity.EntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class InitEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(
            ForgeRegistries.ENTITIES, RotpPJAddon.MOD_ID);
};
