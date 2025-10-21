package me.kubbidev.legacychat;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.minecraft.client.MinecraftClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;

@Environment(EnvType.CLIENT)
public final class LegacyChatMod implements ClientModInitializer {

    public static final String MOD_ID = "legacychat";

    /**
     * The universal mod logger
     */
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    /**
     * The time when the mod was enabled
     */
    private Instant startTime;

    /**
     * The Minecraft client instance
     */
    private MinecraftClient client;

    // provide adapters

    public Instant getStartTime() {
        return startTime;
    }

    // lifecycle

    @Override
    public void onInitializeClient() {
        // Register the Client startup/shutdown events now
        ClientLifecycleEvents.CLIENT_STARTED.register(this::onClientStarted);
        ClientLifecycleEvents.CLIENT_STOPPING.register(this::onClientStopping);
    }

    private void onClientStarted(MinecraftClient client) {
        this.client = client;
        startTime = Instant.now();

        // register the mumble link provider
        LegacyChatModProvider.register(this);

        // successfully print the time taken when loading the mod!
        Duration timeTaken = Duration.between(getStartTime(), Instant.now());
        LOGGER.info("Successfully enabled. (took {}ms)", timeTaken.toMillis());
    }

    private void onClientStopping(MinecraftClient client) {
        LOGGER.info("Starting shutdown process...");

        // unregister provider
        LegacyChatModProvider.unregister();

        this.client = null;
        LOGGER.info("Goodbye!");
    }

    // MinecraftClient singleton getter

    public Optional<MinecraftClient> getClient() {
        return Optional.ofNullable(client);
    }
}
