package it.hurts.rotp_pj.action.stand;

import com.github.standobyte.jojo.action.ActionTarget;
import com.github.standobyte.jojo.action.stand.StandAction;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.github.standobyte.jojo.util.general.LazySupplier;
import it.hurts.rotp_pj.GameplayUtil;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
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
            boolean hand = GameplayUtil.getHandOfUser().containsKey((PlayerEntity) user);
            if (hand) {
                GameplayUtil.getHandOfUser().remove((PlayerEntity) user);
            } else {
                GameplayUtil.getHandOfUser().put((PlayerEntity) user, Hand.MAIN_HAND);
            }
        }
    }

    @Override
    public IFormattableTextComponent getTranslatedName(IStandPower power, String key) {
        LivingEntity user = power.getUser();
        boolean hand = GameplayUtil.getHandOfUser().containsKey((PlayerEntity) user);
        return new TranslationTextComponent(key + (hand ? ".true" : ".false"));
    }

    private final LazySupplier<ResourceLocation> mainTex =
            new LazySupplier<>(() -> makeIconVariant(this, "_main"));
    private final LazySupplier<ResourceLocation> offTex =
            new LazySupplier<>(() -> makeIconVariant(this, "_off"));
    @Override
    public ResourceLocation getIconTexture(@Nullable IStandPower power) {
        if (power != null) {
            boolean hand = GameplayUtil.getHandOfUser().containsKey((PlayerEntity) power.getUser());
            if (hand) {
                return mainTex.get();
            } else {
                return offTex.get();
            }
        } else {
            return super.getIconTexture(power);
        }
    }
}