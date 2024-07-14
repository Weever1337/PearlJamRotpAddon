package it.hurts.weever.rotp_pj;

import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import it.hurts.weever.rotp_pj.init.InitStands;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = RotpPJAddon.MOD_ID, value = Dist.CLIENT)
public class ClientEvents {
    @SubscribeEvent
    public static void ItemTooltipEvent(ItemTooltipEvent event) {
        ItemStack item = event.getItemStack();
        LivingEntity entity = event.getEntityLiving();
        if (entity != null && entity.level.isClientSide()) {
            IStandPower.getStandPowerOptional(entity).ifPresent(power -> {
                if (power.getType() == InitStands.STAND_PJ.getStandType()) {
                    if (item.isEdible() && item.hasTag()) {
                        CompoundNBT tags = item.getTag();
                        String desc = "";
                        if (!tags.getString("author").isEmpty()) {
                            desc = " by " + tags.getString("author");
                        }
                        if (tags.getBoolean("injected")) {
                            event.getToolTip().add(new StringTextComponent("Pearl Jam's Injected Food" + desc).withStyle(TextFormatting.GOLD).withStyle(TextFormatting.ITALIC));
                        } else if (tags.getBoolean("poisoned")) {
                            event.getToolTip().add(new StringTextComponent("Pearl Jam's Poisoned Food" + desc).withStyle(TextFormatting.DARK_PURPLE).withStyle(TextFormatting.ITALIC));
                        } else {
                            event.getToolTip().removeIf(text -> text.getString().contains("Pearl Jam's"));
                        }
                    }
                }
            });
        }
    }
}
