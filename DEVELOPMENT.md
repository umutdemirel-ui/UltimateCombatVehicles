# Development notes

## Architecture

```
src/main/java/com/ucv/
  UltimateCombatVehicles.java   Forge @Mod entry
  UCV.java                      MOD_ID / constants (no MC types)
  client/                       Dist.CLIENT only
  server/                       server lifecycle / future validation
  common/                       version-agnostic gameplay contracts
    weapons/
    vehicles/
    network/
  registry/                     Forge 1.21.x DeferredRegister bindings
  util/
```

Rule: `common/` must not import `net.minecraft` or `net.minecraftforge`.
Version-specific code stays in `registry/`, `client/`, `server/`.

Later multi-version layout (not built yet):

```
common/          shared Java
forge/1.21.1/    this adapter (current tree)
forge/1.20.1/    future
```

## Versions

| Piece        | Value                          |
|--------------|--------------------------------|
| Minecraft    | 1.21.1                         |
| Forge        | 52.1.16                        |
| ForgeGradle  | 6.x (`[6.0.24,6.2)`)           |
| Gradle       | 8.8 (wrapper)                  |
| Java         | 21                             |
| Mappings     | official 1.21.1                |
| Mod ID       | ucv                            |

Change versions in `gradle.properties` only. `mods.toml` and `pack.mcmeta` are expanded at build time.

## Commands

```bash
./gradlew build          # produce the mod JAR
./gradlew runClient      # development Minecraft client
./gradlew runServer      # development dedicated server (--nogui)
./gradlew --refresh-dependencies
```

Low-RAM machines: `org.gradle.jvmargs` is already `-Xmx2G`. Drop to `-Xmx1536M` if the daemon is killed.

## Network / first setup

ForgeGradle downloads Minecraft, Forge userdev, and mappings from:

- `https://maven.minecraftforge.net/`
- `https://libraries.minecraft.net/`
- `https://services.gradle.org/` (Gradle distribution)
- Maven Central

A locked-down network that blocks those hosts cannot compile or launch the game. The Gradle Wrapper JAR is committed; the Gradle distribution is not.

## Adding a system

1. Put rules and data types in `com.ucv.common.*`.
2. Bind items/entities in `com.ucv.registry`.
3. Send packets as input only; compute damage/fuel/inventory on the server.
4. Do not copy assets or code from other mods.

## Logging

Use the `[UCV]` prefix. Do not spam every tick in production.
