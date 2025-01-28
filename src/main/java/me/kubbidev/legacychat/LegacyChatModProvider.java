package me.kubbidev.legacychat;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.jetbrains.annotations.ApiStatus;

@Environment(EnvType.CLIENT)
public final class LegacyChatModProvider {
    private static LegacyChatMod instance = null;

    public static LegacyChatMod getInstance() {
        LegacyChatMod instance = LegacyChatModProvider.instance;
        if (instance == null) {
            throw new NotLoadedException();
        }
        return instance;
    }

    @ApiStatus.Internal
    static void register(LegacyChatMod instance) {
        LegacyChatModProvider.instance = instance;
    }

    @ApiStatus.Internal
    static void unregister() {
        LegacyChatModProvider.instance = null;
    }

    @ApiStatus.Internal
    private LegacyChatModProvider() {
        throw new UnsupportedOperationException("This class cannot be instantiated.");
    }

    private static final class NotLoadedException extends IllegalStateException {
        private static final String MESSAGE = """
                The LegacyChat API isn't loaded yet!
                This could be because:
                  a) the LegacyChat mod is not installed or it failed to enable
                  b) the mod in the stacktrace does not declare a dependency on LegacyChat
                  c) the mod in the stacktrace is retrieving the API before the mod 'initialize' phase
                     (call the #get method in onInitialize, not the constructor!)
                """;

        NotLoadedException() {
            super(MESSAGE);
        }
    }

}
