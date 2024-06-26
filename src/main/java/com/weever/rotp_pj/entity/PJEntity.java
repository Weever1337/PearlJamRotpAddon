package com.weever.rotp_pj.entity;

import com.github.standobyte.jojo.entity.stand.StandEntity;
import com.github.standobyte.jojo.entity.stand.StandEntityType;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

public class PJEntity extends StandEntity {
    public PJEntity(StandEntityType<PJEntity> type, World world) {
        super(type, world);
    }
}
