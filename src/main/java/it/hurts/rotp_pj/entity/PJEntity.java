package it.hurts.rotp_pj.entity;

import com.github.standobyte.jojo.entity.stand.StandEntity;
import com.github.standobyte.jojo.entity.stand.StandEntityType;

import net.minecraft.world.World;

public class PJEntity extends StandEntity {
    public PJEntity(StandEntityType<PJEntity> type, World world) {
        super(type, world);
    }
}
