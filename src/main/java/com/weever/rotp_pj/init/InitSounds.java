package com.weever.rotp_pj.init;

import com.weever.rotp_pj.RotpPJAddon;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class InitSounds {
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(
            ForgeRegistries.SOUND_EVENTS, RotpPJAddon.MOD_ID);

    public static final RegistryObject<SoundEvent> PJ_INJECT = SOUNDS.register("pj_inject",
            () -> new SoundEvent(new ResourceLocation(RotpPJAddon.MOD_ID, "pj_inject"))
    );

    public static final RegistryObject<SoundEvent> PJ_UNJECT = SOUNDS.register("pj_unject",
            () -> new SoundEvent(new ResourceLocation(RotpPJAddon.MOD_ID, "pj_unject"))
    );

    public static final RegistryObject<SoundEvent> PJ_SUMMON = SOUNDS.register("pj_summon",
            () -> new SoundEvent(new ResourceLocation(RotpPJAddon.MOD_ID, "pj_summon"))
    );

    public static final RegistryObject<SoundEvent> PJ_UNSUMMON = SOUNDS.register("pj_unsummon",
            () -> new SoundEvent(new ResourceLocation(RotpPJAddon.MOD_ID, "pj_unsummon"))
    );
}
