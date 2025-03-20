package it.hurts.weever.rotp_pj.entity;

import com.github.standobyte.jojo.entity.stand.StandEntity;
import com.github.standobyte.jojo.entity.stand.StandEntityType;

import net.minecraft.world.World;

public class PJStandEntity extends StandEntity {
    public PJStandEntity(StandEntityType<PJStandEntity> type, World world) {
        super(type, world);
    }
}
