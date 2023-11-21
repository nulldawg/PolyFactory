package eu.pb4.justpolydrawers.mixin.player;

import eu.pb4.justpolydrawers.util.ServerPlayNetExt;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ServerPlayNetworkHandler.class)
public abstract class ServerPlayNetworkHandlerMixin implements ServerPlayNetExt {
    @Shadow private int floatingTicks;

    @Shadow private int vehicleFloatingTicks;

    @Shadow public abstract ServerPlayerEntity getPlayer();

    @Override
    public void polyFactory$resetFloating() {
        this.floatingTicks = 0;
        this.vehicleFloatingTicks = 0;
    }
}
