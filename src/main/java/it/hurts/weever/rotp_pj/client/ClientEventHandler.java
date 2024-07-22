package it.hurts.weever.rotp_pj.client;

import com.github.standobyte.jojo.client.ui.actionshud.ActionsOverlayGui;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.github.standobyte.jojo.util.mc.MCUtil;
import com.github.standobyte.jojo.util.mc.reflection.ClientReflection;
import com.google.common.base.MoreObjects;
import com.mojang.blaze3d.matrix.MatrixStack;

import it.hurts.weever.rotp_pj.init.InitStands;
import it.hurts.weever.rotp_pj.power.impl.stand.type.CookingStandType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.renderer.FirstPersonRenderer;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.HandSide;
import net.minecraft.util.Timer;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.event.RenderArmEvent;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ClientEventHandler {
    private static ClientEventHandler instance = null;

    private final Minecraft mc;

    private Timer clientTimer;
    private ClientEventHandler(Minecraft mc) {
        this.mc = mc;
        this.clientTimer = ClientReflection.getTimer(mc);
    }

    public static void init(Minecraft mc) {
        if (instance == null) {
            instance = new ClientEventHandler(mc);
            MinecraftForge.EVENT_BUS.register(instance);
        }
    }

    private boolean modPostedEvent = false;
    @SuppressWarnings("resource")
    @SubscribeEvent(priority = EventPriority.LOW)
    public void onRenderHand(RenderHandEvent event) {
        ClientPlayerEntity player = Minecraft.getInstance().player;
        if (!event.isCanceled() && !modPostedEvent && event.getHand() == Hand.MAIN_HAND && !player.isInvisible()) {
            IStandPower.getStandPowerOptional(player).ifPresent(power -> {
                ActionsOverlayGui hud = ActionsOverlayGui.getInstance();
                if (hud.isActionSelectedAndEnabled(
                        InitStands.INJECT_FOOD.get(),
                        InitStands.INJECT_POISON_FOOD.get(),
                        InitStands.UNJECT_FOOD.get(),
                        InitStands.HAND_TOGGLE.get()
                )) {
//                    event.setCanceled(true);
                    if (MCUtil.isHandFree(player, Hand.OFF_HAND) && !CookingStandType.getMainHandToggle(power))
                        renderHand(Hand.OFF_HAND, event.getMatrixStack(), event.getBuffers(), event.getLight(),
                            event.getPartialTicks(), event.getInterpolatedPitch(), player);
                }
            });
        }
    }

    private void renderHand(Hand hand, MatrixStack matrixStack, IRenderTypeBuffer buffer, int light,
                            float partialTick, float interpolatedPitch, LivingEntity entity) {
        FirstPersonRenderer renderer = mc.getItemInHandRenderer();
        ClientPlayerEntity player = mc.player;
        Hand swingingArm = MoreObjects.firstNonNull(player.swingingArm, Hand.MAIN_HAND);
        float swingProgress = swingingArm == hand ? player.getAttackAnim(partialTick) : 0.0F;
        float equipProgress = hand == Hand.MAIN_HAND ?
                1.0F - MathHelper.lerp(partialTick, ClientReflection.getMainHandHeightPrev(renderer), ClientReflection.getMainHandHeight(renderer))
                : 1.0F - MathHelper.lerp(partialTick, ClientReflection.getOffHandHeightPrev(renderer), ClientReflection.getOffHandHeight(renderer));

        modPostedEvent = true;
        if (!ForgeHooksClient.renderSpecificFirstPersonHand(hand,
                matrixStack, buffer, light,
                partialTick, interpolatedPitch,
                swingProgress, equipProgress, entity.getItemInHand(hand))) {
            HandSide handSide = MCUtil.getHandSide(player, hand);

            matrixStack.pushPose();
            ClientReflection.renderPlayerArm(matrixStack, buffer, light, equipProgress,
                    swingProgress, handSide, renderer);
            matrixStack.popPose();
            // Nuh, i win
        }
        modPostedEvent = false;
    }
}
