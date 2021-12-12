package gay.quack.renewabledeepslate.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FluidBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(FluidBlock.class)
public abstract class CobbledDeepslateMixin {
	@Shadow protected abstract void playExtinguishSound(WorldAccess world, BlockPos pos);

	@Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;setBlockState(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;)Z", ordinal = 0), method = "receiveNeighborFluids", locals = LocalCapture.CAPTURE_FAILEXCEPTION, cancellable = true)
	private void init(World world, BlockPos pos, BlockState arg2, CallbackInfoReturnable<Boolean> cir, Block block) {
		if (!world.getFluidState(pos).isStill() && pos.getY() < 0) {
			block = Blocks.COBBLED_DEEPSLATE;
			world.setBlockState(pos, block.getDefaultState());
			playExtinguishSound(world, pos);
			cir.setReturnValue(false);
			return;
		}
	}
}
