package it.hurts.rotp_pj.action.stand;

import com.github.standobyte.jojo.action.ActionConditionResult;
import com.github.standobyte.jojo.action.ActionTarget;
import com.github.standobyte.jojo.action.stand.StandAction;
import com.github.standobyte.jojo.action.stand.StandEntityAction;
import com.github.standobyte.jojo.entity.stand.StandEntity;
import com.github.standobyte.jojo.entity.stand.StandEntityTask;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import it.hurts.rotp_pj.GameplayUtil;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Hand;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

public class UnjectFood extends StandAction {
    public UnjectFood(Builder builder) {
        super(builder);
    }

    @Override
    public ActionConditionResult checkConditions(LivingEntity user, IStandPower power, ActionTarget target) {
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
    protected void perform(World world, LivingEntity user, IStandPower power, ActionTarget target) {
        if (!world.isClientSide()) {
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