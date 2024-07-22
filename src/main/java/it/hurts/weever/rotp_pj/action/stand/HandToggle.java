package it.hurts.weever.rotp_pj.action.stand;

import com.github.standobyte.jojo.action.ActionTarget;
import com.github.standobyte.jojo.action.stand.StandAction;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.github.standobyte.jojo.util.general.LazySupplier;
import com.github.standobyte.jojo.util.mc.MCUtil;
import it.hurts.weever.rotp_pj.capability.PlayerUtilCapProvider;
import it.hurts.weever.rotp_pj.power.impl.stand.type.CookingStandType;
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
            user.getCapability(PlayerUtilCapProvider.CAPABILITY).ifPresent(entityPJUserCapability -> {
                System.out.println(entityPJUserCapability.getMainHandToggle());
                entityPJUserCapability.setMainHandToggle(!entityPJUserCapability.getMainHandToggle());
                System.out.println(entityPJUserCapability.getMainHandToggle());
            });
        }
    }

    @Override
    public IFormattableTextComponent getTranslatedName(IStandPower power, String key) {
        boolean hand = ((CookingStandType<?>) power.getType()).getMainHandToggle(power);
        return new TranslationTextComponent(key + (hand ? ".true" : ".false"));
    }

    private final LazySupplier<ResourceLocation> rightTex =
            new LazySupplier<>(() -> makeIconVariant(this, "_main"));
    private final LazySupplier<ResourceLocation> leftTex =
            new LazySupplier<>(() -> makeIconVariant(this, "_off"));

    @Override
    public ResourceLocation getIconTexture(@Nullable IStandPower power) {
        if (power != null) {
            LivingEntity user = power.getUser();
            boolean hand = user instanceof PlayerEntity ? ((CookingStandType<?>) power.getType()).getMainHandToggle(power) : false;
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