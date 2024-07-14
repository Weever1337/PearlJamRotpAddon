package it.hurts.weever.rotp_pj.action.stand;

import com.github.standobyte.jojo.action.ActionTarget;
import com.github.standobyte.jojo.action.stand.StandAction;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.github.standobyte.jojo.util.general.LazySupplier;
import com.github.standobyte.jojo.util.mc.MCUtil;
import it.hurts.weever.rotp_pj.GameplayUtil;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.HandSide;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class SecretFood extends StandAction {
    public SecretFood(Builder builder) {
        super(builder);
    }

    @Override
    public void onPerform(World world, LivingEntity user, IStandPower power, ActionTarget target) {
        if (!world.isClientSide()) {
            boolean hand = GameplayUtil.getSecretFoodOrNot().contains((PlayerEntity) user);
            if (hand) {
                GameplayUtil.getSecretFoodOrNot().remove((PlayerEntity) user);
            } else {
                GameplayUtil.getSecretFoodOrNot().add((PlayerEntity) user);
            }
        }
    }

    @Override
    public IFormattableTextComponent getTranslatedName(IStandPower power, String key) {
        LivingEntity user = power.getUser();
        boolean hand = GameplayUtil.getSecretFoodOrNot().contains((PlayerEntity) user);
        return new TranslationTextComponent(key + (hand ? ".false" : ".true"));
    }
}