package it.hurts.rotp_pj.action.stand;

import com.github.standobyte.jojo.action.ActionConditionResult;
import com.github.standobyte.jojo.action.ActionTarget;
import com.github.standobyte.jojo.action.stand.StandAction;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.github.standobyte.jojo.util.general.LazySupplier;
import it.hurts.rotp_pj.GameplayUtil;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class CraftPassive extends StandAction {
    public CraftPassive(Builder builder) {
        super(builder);
    }

    @Override
    public void onPerform(World world, LivingEntity user, IStandPower power, ActionTarget target) {
        if (!world.isClientSide()) {
            boolean passive = GameplayUtil.getPassiveUsers().containsKey((PlayerEntity) user);
            if (passive) {
                GameplayUtil.getPassiveUsers().remove((PlayerEntity) user);
            } else {
                GameplayUtil.getPassiveUsers().put((PlayerEntity) user, true);
            }
        }
    }

    @Override
    public IFormattableTextComponent getTranslatedName(IStandPower power, String key) {
        LivingEntity user = power.getUser();
        boolean passive = GameplayUtil.getPassiveUsers().containsKey((PlayerEntity) user);
        return new TranslationTextComponent(key + (passive ? ".true" : ".false"));
    }

    private final LazySupplier<ResourceLocation> onTex =
            new LazySupplier<>(() -> makeIconVariant(this, "_on"));
    private final LazySupplier<ResourceLocation> offTex =
            new LazySupplier<>(() -> makeIconVariant(this, "_off"));
    @Override
    public ResourceLocation getIconTexture(@Nullable IStandPower power) {
        if (power != null) {
            boolean passive = GameplayUtil.getPassiveUsers().containsKey((PlayerEntity) power.getUser());
            if (passive) {
                return onTex.get();
            } else {
                return offTex.get();
            }
        } else {
            return super.getIconTexture(power);
        }
    }
}
