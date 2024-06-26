package com.weever.rotp_pj.action.stand;

import com.github.standobyte.jojo.action.ActionConditionResult;
import com.github.standobyte.jojo.action.ActionTarget;
import com.github.standobyte.jojo.action.stand.StandEntityAction;
import com.github.standobyte.jojo.entity.stand.StandEntity;
import com.github.standobyte.jojo.entity.stand.StandEntityTask;
import com.github.standobyte.jojo.init.ModParticles;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.weever.rotp_pj.GameplayUtil;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Hand;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

public class InjectPoisonFood extends StandEntityAction {
    public InjectPoisonFood(Builder builder) {
        super(builder);
    }

    @Override
    protected ActionConditionResult checkStandConditions(StandEntity stand, IStandPower power, ActionTarget target) {
        LivingEntity user = power.getUser();
        ItemStack itemStack = GameplayUtil.GetFoodItem(user);
        if (itemStack == ItemStack.EMPTY) {
            return ActionConditionResult.NEGATIVE;
        }
        if (itemStack.hasTag()) {
            CompoundNBT tags = itemStack.getTag();
            if (tags.getBoolean("injected")) {
                return ActionConditionResult.NEGATIVE;
            } else if (tags.getBoolean("poisoned")) {
                return ActionConditionResult.NEGATIVE;
            }
        }
        return ActionConditionResult.POSITIVE;
    }

    @Override
    public void standPerform(World world, StandEntity standEntity, IStandPower userPower, StandEntityTask task) {
        if (!world.isClientSide()) {
            LivingEntity user = userPower.getUser();
            ItemStack itemStack = GameplayUtil.GetFoodItem(user);
            double x = user.getX();
            double y = user.getY();
            double z = user.getZ();
            if (itemStack != ItemStack.EMPTY) {
                ItemStack stack = itemStack.copy();
                world.addParticle(ModParticles.HAMON_SPARK_YELLOW.get(), x, y, z, .1, 5, 30);
                stack.getOrCreateTag().putBoolean("poisoned", true);
                user.setItemInHand(Hand.OFF_HAND, stack);
            }
        }
    }

    @Override
    public IFormattableTextComponent getTranslatedName(IStandPower power, String key) {
        LivingEntity user = power.getUser();
        ItemStack itemStack = GameplayUtil.GetFoodItem(user);
        if (itemStack != ItemStack.EMPTY) {
            return new TranslationTextComponent(key + ".param", itemStack.getItem().getName(itemStack));
        } else {
            return super.getTranslatedName(power, key);
        }
    }
}