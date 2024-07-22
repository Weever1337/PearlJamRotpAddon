package it.hurts.weever.rotp_pj.entity;

import com.github.standobyte.jojo.entity.stand.StandEntity;
import com.github.standobyte.jojo.entity.stand.StandEntityType;

import com.github.standobyte.jojo.init.ModParticles;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.EntityPredicates;
import net.minecraft.world.World;

import java.util.List;

public class PJEntity extends StandEntity {
    public PJEntity(StandEntityType<PJEntity> type, World world) {
        super(type, world);
    }
}
