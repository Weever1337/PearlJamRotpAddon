package it.hurts.weever.rotp_pj.action.stand;

import com.github.standobyte.jojo.action.ActionTarget;
import com.github.standobyte.jojo.action.stand.StandAction;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import it.hurts.weever.rotp_pj.capability.PlayerUtilCapProvider;
import it.hurts.weever.rotp_pj.power.impl.stand.type.CookingStandType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

public class SecretFood extends StandAction {
    public SecretFood(Builder builder) {
        super(builder);
    }

    @Override
    public void onPerform(World world, LivingEntity user, IStandPower power, ActionTarget target) {
        if (!world.isClientSide()) {
            user.getCapability(PlayerUtilCapProvider.CAPABILITY).ifPresent(entityPJUserCapability -> {
                System.out.println(entityPJUserCapability.getSecretFoodToggle());
                entityPJUserCapability.setSecretFoodToggle(!entityPJUserCapability.getSecretFoodToggle());
                System.out.println(entityPJUserCapability.getSecretFoodToggle());
            });
        }
    }

    @Override
    public IFormattableTextComponent getTranslatedName(IStandPower power, String key) {
        boolean passive = ((CookingStandType<?>) power.getType()).getSecretFoodToggle(power);
        return new TranslationTextComponent(key + (passive ? ".true" : ".false"));
    }
}