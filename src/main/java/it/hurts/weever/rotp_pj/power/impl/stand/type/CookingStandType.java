package it.hurts.weever.rotp_pj.power.impl.stand.type;

import com.github.standobyte.jojo.action.stand.StandAction;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.github.standobyte.jojo.power.impl.stand.stats.StandStats;
import com.github.standobyte.jojo.power.impl.stand.type.EntityStandType;
import it.hurts.weever.rotp_pj.capability.PlayerUtilCap;
import it.hurts.weever.rotp_pj.capability.PlayerUtilCapProvider;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.text.ITextComponent;
import org.jetbrains.annotations.Nullable;

public class CookingStandType<T extends StandStats> extends EntityStandType<T> {

    public CookingStandType(int color, ITextComponent partName,
                              StandAction[] attacks, StandAction[] abilities,
                              Class<T> statsClass, T defaultStats, @Nullable StandTypeOptionals additions) {
        super(color, partName, attacks, abilities, statsClass, defaultStats, additions);
    }

    protected CookingStandType(EntityStandType.AbstractBuilder<?, T> builder) {
        super(builder);
    }

    public static boolean getSecretFoodToggle(IStandPower power) {
        LivingEntity user = power.getUser();
        return user.getCapability(PlayerUtilCapProvider.CAPABILITY).map(PlayerUtilCap::getSecretFoodToggle).orElse(false);
    }

    public static boolean getMainHandToggle(IStandPower power) {
        LivingEntity user = power.getUser();
        return user.getCapability(PlayerUtilCapProvider.CAPABILITY).map(PlayerUtilCap::getMainHandToggle).orElse(false);
    }

    public static class Builder<T extends StandStats> extends EntityStandType.AbstractBuilder<Builder<T>, T> {

        @Override
        protected Builder<T> getThis() {
            return this;
        }

        @Override
        public CookingStandType<T> build() {
            return new CookingStandType<>(this);
        }

    }
}
