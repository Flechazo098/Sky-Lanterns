package committee.nova.skylanterns.common.events;

import committee.nova.skylanterns.common.entities.SkyLanternEntity;
import committee.nova.skylanterns.init.ModItems;
import committee.nova.skylanterns.utils.EnumColor;
import net.minecraft.core.BlockPos;
import net.minecraft.core.dispenser.BlockSource;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;

@EventBusSubscriber
public class DispenserRegistry {
    @SubscribeEvent
    public static void onDispenserRegister(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            DispenserBlock.registerBehavior(ModItems.SKY_LANTERN_ORANGE.get(), new DefaultDispenseItemBehavior() {
                @Override
                protected @NotNull ItemStack execute(BlockSource blockSource, ItemStack item) {
                    return super.execute(blockSource, item);
                }
            });
            DispenserBlock.registerBehavior(ModItems.SKY_LANTERN_PINK.get(), new DefaultDispenseItemBehavior() {
                @Override
                @Nonnull
                public ItemStack execute(@Nonnull BlockSource source, @Nonnull ItemStack stack) {
                    return spawnSkyLantern(source, stack);
                }
            });
        });
    }

    public static ItemStack spawnSkyLantern(@Nonnull BlockSource source, @Nonnull ItemStack stack) {
        Level level = source.level();
        BlockPos blockpos = source.pos().relative(source.state().getValue(DispenserBlock.FACING));

        final SkyLanternEntity lanternEntity = SkyLanternEntity.create(level, blockpos, EnumColor.ORANGE);
        if (lanternEntity == null) {
            return stack;
        }
        level.addFreshEntity(lanternEntity);

        final int count = stack.getCount() - 1;
        if (count > 0) {
            stack.setCount(count);
            return stack;
        } else {
            return ItemStack.EMPTY;
        }
    }
}