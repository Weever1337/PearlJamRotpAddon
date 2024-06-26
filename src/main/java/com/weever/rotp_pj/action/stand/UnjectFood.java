package com.weever.rotp_pj.action.stand;

import com.github.standobyte.jojo.action.ActionConditionResult;
import com.github.standobyte.jojo.action.ActionTarget;
import com.github.standobyte.jojo.action.stand.StandEntityAction;
import com.github.standobyte.jojo.entity.stand.StandEntity;
import com.github.standobyte.jojo.entity.stand.StandEntityTask;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.weever.rotp_pj.GameplayUtil;
import com.weever.rotp_pj.entity.PJEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class UnjectFood extends StandEntityAction {
    public UnjectFood(Builder builder) {
        super(builder);
    }

    @Override
    protected ActionConditionResult checkStandConditions(StandEntity stand, IStandPower power, ActionTarget target) {
        LivingEntity user = power.getUser();
        ItemStack itemStack = GameplayUtil.GetFoodItem(user);
        if (itemStack != ItemStack.EMPTY && itemStack.hasTag()) {
            CompoundNBT tags = itemStack.getTag();
            if (tags.getBoolean("injected")) {
                return ActionConditionResult.POSITIVE;
            } else if (tags.getBoolean("poisoned")) {
                return ActionConditionResult.POSITIVE;
            }
        }
        return ActionConditionResult.NEGATIVE;
    }

    @Override
    public void standPerform(World world, StandEntity standEntity, IStandPower userPower, StandEntityTask task) {
        if (!world.isClientSide()) {
            LivingEntity user = userPower.getUser();
            ItemStack itemStack = GameplayUtil.GetFoodItem(user);
            if (itemStack != ItemStack.EMPTY) {
                ItemStack stack = itemStack.getItem().getDefaultInstance().copy();
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