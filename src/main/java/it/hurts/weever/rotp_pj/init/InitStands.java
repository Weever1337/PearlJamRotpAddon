package it.hurts.weever.rotp_pj.init;

import com.github.standobyte.jojo.action.Action;
import com.github.standobyte.jojo.action.stand.StandAction;
import com.github.standobyte.jojo.entity.stand.StandEntityType;
import com.github.standobyte.jojo.init.power.stand.EntityStandRegistryObject;
import com.github.standobyte.jojo.init.power.stand.ModStandsInit;
import com.github.standobyte.jojo.power.impl.stand.stats.StandStats;
import com.github.standobyte.jojo.power.impl.stand.type.EntityStandType;
import com.github.standobyte.jojo.power.impl.stand.type.StandType;
import com.github.standobyte.jojo.util.mod.StoryPart;
import it.hurts.weever.rotp_pj.RotpPJAddon;
import it.hurts.weever.rotp_pj.action.stand.*;
import it.hurts.weever.rotp_pj.entity.PJEntity;

import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;

public class InitStands {
    @SuppressWarnings("unchecked")
    public static final DeferredRegister<Action<?>> ACTIONS = DeferredRegister.create(
            (Class<Action<?>>) ((Class<?>) Action.class), RotpPJAddon.MOD_ID);
    @SuppressWarnings("unchecked")
    public static final DeferredRegister<StandType<?>> STANDS = DeferredRegister.create(
            (Class<StandType<?>>) ((Class<?>) StandType.class), RotpPJAddon.MOD_ID);
    
 // ======================================== Pearl Jam ========================================
    public static final RegistryObject<StandAction> INJECT_FOOD = ACTIONS.register("pj_inject",
         () -> new InjectFood(new StandAction.Builder()
                 .cooldown(3)
                 .holdToFire(10, false)
                 .shout(InitSounds.PJ_INJECT)
         ));

    public static final RegistryObject<StandAction> INJECT_POISON_FOOD = ACTIONS.register("pj_poison_inject",
            () -> new InjectPoisonFood(new StandAction.Builder()
                    .cooldown(6)
                    .holdToFire(20, false)
                    .shout(InitSounds.PJ_INJECT)
            ));

    public static final RegistryObject<StandAction> UNJECT_FOOD = ACTIONS.register("pj_unject",
            () -> new UnjectFood(new StandAction.Builder()
                    .cooldown(3)
                    .holdToFire(10, false)
                    .shout(InitSounds.PJ_UNJECT)
            ));

    public static final RegistryObject<StandAction> CRAFT_PASSIVE_TOGGLE = ACTIONS.register("pj_craft_passive",
            () -> new CraftPassive(new StandAction.Builder()
                    .cooldown(3)
                    .holdToFire(5, false)
            ));

    public static final RegistryObject<StandAction> HAND_TOGGLE = ACTIONS.register("pj_hand_toggle",
            () -> new HandToggle(new StandAction.Builder()
                    .cooldown(3)
                    .holdToFire(5, false)
            )
    );

    public static final RegistryObject<StandAction> SECRET_BY_CHEF = ACTIONS.register("pj_secret_by_chef",
            () -> new SecretFood(new StandAction.Builder()
                    .cooldown(3)
                    .holdToFire(5, false)
            ));

    public static final EntityStandRegistryObject<EntityStandType<StandStats>, StandEntityType<PJEntity>> STAND_PJ =
            new EntityStandRegistryObject<>("pearl_jam",
                    STANDS, 
                    () -> new EntityStandType.Builder<>()
                    .color(0xe7b863)
                    .storyPartName(StoryPart.DIAMOND_IS_UNBREAKABLE.getName())
                    .leftClickHotbar(
                            INJECT_FOOD.get(),
                            INJECT_POISON_FOOD.get()
                    )
                    .rightClickHotbar(
                            UNJECT_FOOD.get(),
                            // CRAFT_PASSIVE_TOGGLE.get(), Need to fix it.
                            HAND_TOGGLE.get(),
                            SECRET_BY_CHEF.get()
                    )
                    .defaultStats(StandStats.class, new StandStats.Builder()
                            .power(2)
                            .speed(9)
                            .range(11, 13)
                            .durability(14)
                            .precision(2).randomWeight(3)
                            .build()
                    )
                    .disableManualControl().disableStandLeap()
                    .build(),
                    InitEntities.ENTITIES,
                    () -> new StandEntityType<>(PJEntity::new, 0.65F, 1.8F)
                            .summonSound(InitSounds.PJ_SUMMON)
                            .unsummonSound(InitSounds.PJ_UNSUMMON))
            .withDefaultStandAttributes();
}
