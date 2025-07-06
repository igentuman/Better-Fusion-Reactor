# Better Fusion Reactor Information

## Summary
Better Fusion Reactor is a Minecraft mod that serves as an addon for Mekanism Generators. It enhances the fusion reactor mechanics by making them more complex and realistic, requiring players to control various parameters with redstone or computers. The mod aims to simulate the challenges of maintaining a stable fusion reaction, where efficiency depends on matching current reactivity with target reactivity.

## Structure
- **src/main/java/igentuman/bfr**: Core mod code
  - **client**: Client-side rendering and UI
  - **common**: Core functionality, multiblock structures, and networking
  - **mixin**: Mixin classes for modifying Mekanism behavior
  - **datagen**: Data generation for recipes and assets
- **src/main/resources**: Assets, configs, and data files
- **annotation-processor**: Custom annotation processor for code generation
- **src/test**: Test framework (currently empty)
- **src/generated**: Generated resources

## Language & Runtime
**Language**: Java
**Version**: Java 17
**Build System**: Gradle 8.2.1
**Package Manager**: Gradle/Maven

## Dependencies
**Main Dependencies**:
- Minecraft Forge (1.20.1-47.1.76)
- Mekanism (1.20.1-10.4.0.14)
- Mekanism Generators (1.20.1-10.4.0.14)
- Architectury (9.2.14)
- JEI (15.2.0.23)
- CC: Tweaked (Forge version)
- Sedna
- Markdown Manual
- OC2R (Open Computers 2)

**Development Dependencies**:
- JavaPoet (1.13.0)
- GSON (2.10)
- Parchment Mappings (2023.07.09-1.20.1)

## Build & Installation
```bash
# Build the mod
./gradlew build

# Run client for testing
./gradlew runClient

# Run server for testing
./gradlew runServer

# Generate resources
./gradlew runData
```

## Main Files
**Main Class**: `src/main/java/igentuman/bfr/common/BetterFusionReactor.java`
**Config**: `src/main/java/igentuman/bfr/common/config/BetterFusionReactorConfig.java`
**Multiblock Manager**: Fusion reactor is implemented as a multiblock structure using Mekanism's multiblock system

## Integration Points
- **Mekanism**: Extends Mekanism Generators' fusion reactor functionality
- **ComputerCraft**: Integration for computer control of reactors
- **Open Computers 2**: Support for OC2 integration
- **JEI**: Custom recipe categories for fusion reactor

## Minecraft Specifics
**Minecraft Version**: 1.20.1
**Forge Version**: 47.1.76
**Mod ID**: bfr
**Resource Pack Format**: 9
**Data Pack Format**: 10

## Project Configuration
**Group ID**: igentuman.bfr
**Artifact ID**: BetterFusionReactor
**Version Format**: ${minecraft_version}-${mod_version}
**Current Version**: 1.20.1-1.4.7