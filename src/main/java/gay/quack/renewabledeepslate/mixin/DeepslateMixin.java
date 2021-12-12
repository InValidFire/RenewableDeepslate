package gay.quack.renewabledeepslate.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.LavaFluid;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.WorldAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(LavaFluid.class)
public abstract class DeepslateMixin {
    @Shadow protected abstract void playExtinguishEvent(WorldAccess world, BlockPos pos);

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/WorldAccess;setBlockState(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;I)Z"), method = "flow", cancellable = true, locals = LocalCapture.CAPTURE_FAILEXCEPTION)
    private void renewableDeepslate(WorldAccess world, BlockPos pos, BlockState state, Direction direction, FluidState arg4, CallbackInfo ci, FluidState fluidState2) {
        if (pos.getY() < 0) {
            world.setBlockState(pos, Blocks.DEEPSLATE.getDefaultState(), 3);
            playExtinguishEvent(world, pos);
            ci.cancel();
        }
    }
}
