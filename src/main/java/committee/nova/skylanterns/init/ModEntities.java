package committee.nova.skylanterns.init;

import committee.nova.skylanterns.SkyLanterns;
import committee.nova.skylanterns.common.entities.SkyLanternEntity;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/2/12 8:45
 * Version: 1.0
 */
public class ModEntities {

    public static final DeferredRegister<EntityType<?>> ENTITIE_TYPE = DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, SkyLanterns.MOD_ID);

    public static final Supplier<EntityType<SkyLanternEntity>> SKY_LANTERN = ENTITIE_TYPE.register("skylantern",
            () -> EntityType.Builder.of(SkyLanternEntity::new, MobCategory.CREATURE)
                    .sized(1f, 1f)
                    .build(ResourceLocation.fromNamespaceAndPath(SkyLanterns.MOD_ID, "skylantern").toString()));

    public static void register(IEventBus modEventBus) {
        ENTITIE_TYPE.register(modEventBus);
    }
}