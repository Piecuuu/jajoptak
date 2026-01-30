package su.plo.matter;

import com.google.common.collect.Iterables;
import net.minecraft.server.level.ServerLevel;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Optional;

public class Globals {

    public static final int WORLD_SEED_LONGS = 16;
    public static final int WORLD_SEED_BITS = WORLD_SEED_LONGS * 64;

    public static final long[] worldSeed = new long[WORLD_SEED_LONGS];
    public static final ThreadLocal<Integer> dimension = ThreadLocal.withInitial(() -> 0);

    public enum Salt {
        UNDEFINED,
        BASTION_FEATURE,
        WOODLAND_MANSION_FEATURE,
        MINESHAFT_FEATURE,
        BURIED_TREASURE_FEATURE,
        NETHER_FORTRESS_FEATURE,
        PILLAGER_OUTPOST_FEATURE,
        GEODE_FEATURE,
        NETHER_FOSSIL_FEATURE,
        OCEAN_MONUMENT_FEATURE,
        RUINED_PORTAL_FEATURE,
        POTENTIONAL_FEATURE,
        GENERATE_FEATURE,
        JIGSAW_PLACEMENT,
        STRONGHOLDS,
        POPULATION,
        DECORATION,
        SLIME_CHUNK
    }

    public static void setupGlobals(ServerLevel world) {
        if (!org.dreeam.leaf.config.modules.misc.SecureSeed.enabled) return;

        long[] seed = world.getServer().getWorldData().worldGenOptions().featureSeed();
        System.arraycopy(seed, 0, worldSeed, 0, WORLD_SEED_LONGS);
        int worldIndex = Iterables.indexOf(world.getServer().levelKeys(), it -> it == world.dimension());
        if (worldIndex == -1)
            worldIndex = world.getServer().levelKeys().size(); // if we are in world construction it may not have been added to the map yet
        dimension.set(worldIndex);
    }

    public static long[] createRandomWorldSeed() {
        long[] seed = new long[WORLD_SEED_LONGS];
        SecureRandom rand = new SecureRandom();
        for (int i = 0; i < WORLD_SEED_LONGS; i++) {
            seed[i] = rand.nextLong();
        }
        return seed;
    }

    // 1024-bit string -> 16 * 64 long[]
    public static Optional<long[]> parseSeed(String seedStr) {
        if (seedStr.isEmpty()) return Optional.empty();

        if (seedStr.length() != WORLD_SEED_BITS) {
            throw new IllegalArgumentException("Secure seed length must be " + WORLD_SEED_BITS + "-bit but found " + seedStr.length() + "-bit.");
        }

        long[] seed = new long[WORLD_SEED_LONGS];

        for (int i = 0; i < WORLD_SEED_LONGS; i++) {
            int start = i * 64;
            int end = start + 64;
            String seedSection = seedStr.substring(start, end);

            BigInteger seedInDecimal = new BigInteger(seedSection, 2);
            seed[i] = seedInDecimal.longValue();
        }

        return Optional.of(seed);
    }

    // 16 * 64 long[] -> 1024-bit string
    public static String seedToString(long[] seed) {
        StringBuilder sb = new StringBuilder();

        for (long longV : seed) {
            // piecuu start - convert to hex instead of binary
            // Convert to 64 bit hex-encoded string (16 chars) per long
            String hexStr = String.format("%016X", longV);

            sb.append(hexStr);
            // piecuu end
        }

        return sb.toString();
    }
}
