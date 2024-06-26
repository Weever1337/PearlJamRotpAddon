package com.weever.rotp_pj.client.render;

import com.github.standobyte.jojo.client.render.entity.model.stand.StandEntityModel;
import com.github.standobyte.jojo.client.render.entity.model.stand.StandModelRegistry;
import com.github.standobyte.jojo.client.render.entity.renderer.stand.StandEntityRenderer;
import com.weever.rotp_pj.RotpPJAddon;
import com.weever.rotp_pj.entity.PJEntity;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;

public class PJRenderer extends StandEntityRenderer<PJEntity, StandEntityModel<PJEntity>> {
    
    public PJRenderer(EntityRendererManager renderManager) {
        super(renderManager, 
                StandModelRegistry.registerModel(new ResourceLocation(RotpPJAddon.MOD_ID, "pearl_jam"), PJModel::new),
                new ResourceLocation(RotpPJAddon.MOD_ID, "textures/entity/stand/pearl_jam.png"), 0);
    }
}
