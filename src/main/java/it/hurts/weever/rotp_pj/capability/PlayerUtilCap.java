package it.hurts.weever.rotp_pj.capability;

import it.hurts.weever.rotp_pj.network.PacketManager;
import it.hurts.weever.rotp_pj.network.s2c.TrSetBuffValuePacket;
import it.hurts.weever.rotp_pj.network.s2c.TrSetDeBuffValuePacket;
import it.hurts.weever.rotp_pj.network.s2c.TrSetMainToggleHandPacket;
import it.hurts.weever.rotp_pj.network.s2c.TrSetSecretPassivePacket;
import it.hurts.weever.rotp_pj.util.GameplayUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;

import java.util.Map;

public class PlayerUtilCap {
    private PlayerEntity player;
    private boolean mainHandToggle = false;
    private boolean secretFoodToggle = false;
    private String buffValue = "none";
    private String debuffValue = "none";

    public PlayerUtilCap(PlayerEntity player) {
        this.player = player;
    }

    public void setBuffValue(String set) {
        this.buffValue = set;
        if (!player.level.isClientSide()) {
            PacketManager.sendToClientsTrackingAndSelf(new TrSetBuffValuePacket(player.getId(), buffValue), player);
        }
    }

    public String getBuffValue() {
        return buffValue;
    }

    public void setDeBuffValue(String set) {
        this.buffValue = set;
        if (!player.level.isClientSide()) {
            PacketManager.sendToClientsTrackingAndSelf(new TrSetDeBuffValuePacket(player.getId(), debuffValue), player);
        }
    }

    public String getDeBuffValue() {
        return buffValue;
    }

    public void setMainHandToggle(boolean set) {
        this.mainHandToggle = set;
        if (!player.level.isClientSide()) {
            PacketManager.sendToClientsTrackingAndSelf(new TrSetMainToggleHandPacket(player.getId(), mainHandToggle), player);
        }
    }

    public boolean getMainHandToggle() {
        return mainHandToggle;
    }

    public void setSecretFoodToggle(boolean set) {
        this.secretFoodToggle = set;
        if (!player.level.isClientSide()) {
            PacketManager.sendToClientsTrackingAndSelf(new TrSetSecretPassivePacket(player.getId(), secretFoodToggle), player);
        }
    }

    public boolean getSecretFoodToggle() {
        return secretFoodToggle;
    }

    public void onTracking(ServerPlayerEntity tracking) {
        PacketManager.sendToClient(new TrSetMainToggleHandPacket(player.getId(), mainHandToggle), tracking);
        PacketManager.sendToClient(new TrSetSecretPassivePacket(player.getId(), secretFoodToggle), tracking);
        PacketManager.sendToClientsTrackingAndSelf(new TrSetBuffValuePacket(player.getId(), buffValue), player);
        PacketManager.sendToClientsTrackingAndSelf(new TrSetDeBuffValuePacket(player.getId(), debuffValue), player);
    }

    public CompoundNBT toNBT() {
        CompoundNBT nbt = new CompoundNBT();
        nbt.putBoolean("mainHandToggle", mainHandToggle);
        nbt.putBoolean("secretFoodToggle", secretFoodToggle);
        nbt.putString("buffValue", buffValue);
        nbt.putString("debuffValue", debuffValue);
        return nbt;
    }

    public void fromNBT(CompoundNBT nbt) {
        this.mainHandToggle = nbt.getBoolean("mainHandToggle");
        this.secretFoodToggle = nbt.getBoolean("secretFoodToggle");
    }
}
