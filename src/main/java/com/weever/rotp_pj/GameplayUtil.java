package com.weever.rotp_pj;

import com.github.standobyte.jojo.init.ModParticles;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.weever.rotp_pj.entity.PJEntity;
import com.weever.rotp_pj.init.InitStands;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Hand;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Random;
import java.util.UUID;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

@Mod.EventBusSubscriber(modid = RotpPJAddon.MOD_ID)
public class GameplayUtil {
    private static int Duration = 500;
//    @SubscribeEvent
//    public static void onCraftEvent(PlayerEvent.ItemCraftedEvent event) {
//        LivingEntity entity = event.getEntityLiving();
//        ItemStack item = event.getCrafting();
//        Vector3d pos = entity.position();
//        ItemStack itemStack = item.copy();
//        entity.level.addParticle(ModParticles.HAMON_AURA_RED.get(), pos.x, pos.y + 1.8, pos.z, 0, 0, 0);
//        if (item.isEdible()) {
//            itemStack.getOrCreateTag().putBoolean("injected", true);
//            itemStack.getOrCreateTag().putBoolean("crafted", true);
//            entity.setItemInHand(Hand.MAIN_HAND, itemStack);
//        }
//    } // TODO: Fix this shit.

    @SubscribeEvent
    public static void onEatingEvent(LivingEntityUseItemEvent.Tick event) {
        LivingEntity entity = event.getEntityLiving();
        ItemStack item = event.getItem();
        if (item.isEdible() && item.hasTag()) {
            CompoundNBT tags = item.getTag();
            Vector3d pos = entity.position();
            if (tags.getBoolean("injected")) {
                entity.level.addParticle(ModParticles.HAMON_AURA_RED.get(), pos.x, pos.y + 1.8, pos.z, 0, 0, 0);
            } else if (tags.getBoolean("poisoned")) {
                entity.level.addParticle(ModParticles.HAMON_AURA_BLUE.get(), pos.x, pos.y + 1.8, pos.z, 0, 0, 0);
            }
        }
    }

    @SubscribeEvent
    public static void onEatEvent(LivingEntityUseItemEvent.Finish event) {
        ItemStack item = event.getItem();
        LivingEntity entity = event.getEntityLiving();
        Random random = new Random();
        boolean randomBoolean = random.nextBoolean();
        Logger logger = RotpPJAddon.getLogger();
        logger.info("[Pearl Jam] randomBoolean: " + randomBoolean);
        if (item.isEdible() && item.hasTag()) {
            CompoundNBT tags = item.getTag();
            if (tags.getBoolean("injected")) {
                AddBuffs(entity, randomBoolean);
            } else if (tags.getBoolean("poisoned")) {
                AddDeBuffs(entity, randomBoolean);
            }
        }
    }

    @SubscribeEvent
    public static void ItemTooltipEvent(ItemTooltipEvent event) {
        ItemStack item = event.getItemStack();
        LivingEntity entity = event.getEntityLiving();
        IStandPower.getStandPowerOptional(entity).ifPresent(power -> {
            if(power.getType() == InitStands.STAND_PJ.getStandType()) {
                if (item.isEdible() && item.hasTag()) {
                    CompoundNBT tags = item.getTag();
                    if (tags.getBoolean("injected")) {
                        event.getToolTip().add(new StringTextComponent("Pearl Jam's Injected Food").withStyle(TextFormatting.GOLD).withStyle(TextFormatting.ITALIC));
                    } else if (tags.getBoolean("poisoned")) {
                        event.getToolTip().add(new StringTextComponent("Pearl Jam's Poisoned Food").withStyle(TextFormatting.DARK_PURPLE).withStyle(TextFormatting.ITALIC));
                    } else {
                        event.getToolTip().removeIf(text -> text.getString().contains("Pearl Jam's Injected Food"));
                        event.getToolTip().removeIf(text -> text.getString().contains("Pearl Jam's Poisoned Food"));
                    }
                }
            }
        });
    }

    private static void AddBuffs(LivingEntity entity, boolean power) {
        Random random = new Random();
        int randomInt = random.nextInt(11);
        int amplifier = 0;
        if (power) amplifier += 3;
        Logger logger = RotpPJAddon.getLogger();
        logger.info("[Pearl Jam] randomInt: " + randomInt);
        entity.addEffect(new EffectInstance(Effects.SATURATION, Duration/3, 3+amplifier, false, false, true));
        switch (randomInt) {
            case 0:
                entity.addEffect(new EffectInstance(Effects.MOVEMENT_SPEED, Duration, amplifier, false, false, true));
                break;
            case 1:
                entity.addEffect(new EffectInstance(Effects.DAMAGE_BOOST, Duration, amplifier, false, false, true));
                break;
            case 2:
                entity.addEffect(new EffectInstance(Effects.FIRE_RESISTANCE, Duration, amplifier, false, false, true));
                break;
            case 3:
                entity.addEffect(new EffectInstance(Effects.JUMP, Duration, amplifier, false, false, true));
                break;
            case 4:
                entity.addEffect(new EffectInstance(Effects.NIGHT_VISION, Duration, amplifier, false, false, true));
                break;
            case 5:
                entity.addEffect(new EffectInstance(Effects.HEALTH_BOOST, Duration, amplifier-2, false, false, true));
                break;
            case 6:
                entity.addEffect(new EffectInstance(Effects.REGENERATION, Duration, 7+amplifier, false, false, true));
                break;
            case 7:
                entity.addEffect(new EffectInstance(Effects.SLOW_FALLING, Duration, amplifier, false, false, true));
                break;
            case 8:
                entity.addEffect(new EffectInstance(Effects.DIG_SPEED, Duration, amplifier, false, false, true));
                break;
            case 9:
                entity.addEffect(new EffectInstance(Effects.DOLPHINS_GRACE, Duration, amplifier, false, false, true));
                break;
            case 10:
                entity.addEffect(new EffectInstance(Effects.WATER_BREATHING, Duration, amplifier, false, false, true));
                break;
        }
    }

    private static void AddDeBuffs(LivingEntity entity, boolean power) {
        Random random = new Random();
        int randomInt = random.nextInt(11);
        int amplifier = 0;
        if (power) amplifier += 3;
        Logger logger = RotpPJAddon.getLogger();
        logger.info("[Pearl Jam] randomInt: " + randomInt);
        switch (randomInt) {
            case 0:
                entity.addEffect(new EffectInstance(Effects.MOVEMENT_SLOWDOWN, Duration, amplifier, false, false, true));
                break;
            case 1:
                entity.addEffect(new EffectInstance(Effects.BLINDNESS, Duration, amplifier, false, false, true));
                break;
            case 2:
                entity.addEffect(new EffectInstance(Effects.WEAKNESS, Duration, amplifier, false, false, true));
                break;
            case 3:
                entity.addEffect(new EffectInstance(Effects.HUNGER, Duration, amplifier, false, false, true));
                break;
            case 4:
                entity.addEffect(new EffectInstance(Effects.DIG_SLOWDOWN, Duration, amplifier, false, false, true));
                break;
            case 5:
                entity.addEffect(new EffectInstance(Effects.DIG_SPEED, Duration, amplifier, false, false, true));
                break;
            case 6:
                entity.addEffect(new EffectInstance(Effects.POISON, Duration, amplifier, false, false, true));
                break;
            case 7:
                entity.addEffect(new EffectInstance(Effects.UNLUCK, Duration, amplifier, false, false, true));
                break;
            case 8:
                entity.addEffect(new EffectInstance(Effects.WITHER, Duration, amplifier, false, false, true));
                break;
        }
    }

    public static ItemStack GetFoodItem(@NotNull LivingEntity user) {
        ItemStack itemStack = ItemStack.EMPTY;
        ItemStack OffHand = user.getOffhandItem();
        Item off_item = OffHand.getItem();
        if (off_item.isEdible() || off_item.getUseAnimation(OffHand) == UseAction.DRINK) {
            itemStack = OffHand;
        }
        if (itemStack.getCount() != 1) {
            itemStack = ItemStack.EMPTY;
        }
        return itemStack;
    }
}
