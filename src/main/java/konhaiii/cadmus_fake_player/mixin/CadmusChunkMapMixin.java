package konhaiii.cadmus_fake_player.mixin;

import com.mojang.datafixers.util.Pair;
import earth.terrarium.cadmus.common.claims.ClaimHandler;
import earth.terrarium.cadmus.common.claims.ClaimType;
import net.minecraft.server.level.ChunkMap;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ChunkMap.class)
public class CadmusChunkMapMixin {
	@Final
	@Shadow
	ServerLevel level;

	@Inject(method = "anyPlayerCloseEnoughForSpawning(Lnet/minecraft/world/level/ChunkPos;)Z", at = @At("RETURN"), cancellable = true)
	private void injected(ChunkPos chunkPos, CallbackInfoReturnable<Boolean> cir) {
		Pair<String, ClaimType> claimedChunk = ClaimHandler.getClaim(this.level, chunkPos);
		if (claimedChunk != null && claimedChunk.getSecond().equals(ClaimType.CHUNK_LOADED)) {
			cir.setReturnValue(true);
		}
	}
}