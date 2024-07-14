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

public class HandToggle extends StandAction {
    public HandToggle(Builder builder) {
        super(builder);
    }

    @Override
    public void onPerform(World world, LivingEntity user, IStandPower power, ActionTarget target) {
        if (!world.isClientSide()) {
            boolean hand = GameplayUtil.getMainHandOrNot().contains((PlayerEntity) user);
            if (hand) {
                GameplayUtil.getMainHandOrNot().remove((PlayerEntity) user);
            } else {
                GameplayUtil.getMainHandOrNot().add((PlayerEntity) user);
            }
        }
    }

    @Override
    public IFormattableTextComponent getTranslatedName(IStandPower power, String key) {
        LivingEntity user = power.getUser();
        boolean hand = GameplayUtil.getMainHandOrNot().contains((PlayerEntity) user);
        return new TranslationTextComponent(key + (hand ? ".true" : ".false"));
    }

    private final LazySupplier<ResourceLocation> rightTex =
            new LazySupplier<>(() -> makeIconVariant(this, "_main"));
    private final LazySupplier<ResourceLocation> leftTex =
            new LazySupplier<>(() -> makeIconVariant(this, "_off"));

    @Override
    public ResourceLocation getIconTexture(@Nullable IStandPower power) {
        if (power != null) {
            boolean hand = GameplayUtil.getMainHandOrNot().contains((PlayerEntity) power.getUser());
            HandSide handSide = MCUtil.getHandSide(power.getUser(), Hand.MAIN_HAND);

            if (hand) { // what a hell 💀
                if (handSide == HandSide.LEFT) {
                    return leftTex.get();
                } else {
                    return rightTex.get();
                }
            } else {
                if (handSide == HandSide.LEFT) {
                    return rightTex.get();
                } else {
                return leftTex.get();
                }
            }
        } else {
            return super.getIconTexture(power);
        }
    }
}