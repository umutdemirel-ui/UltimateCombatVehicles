# Ultimate Combat & Vehicles

Minecraft **1.21.1** için özgün bir **Forge** silah + araç + ekipman modu.

- **Mod adı:** Ultimate Combat & Vehicles
- **Mod ID:** `ucv`
- **Minecraft:** 1.21.1
- **Forge:** 52.1.16
- **Java:** 21
- **Loader:** Forge (ForgeGradle 6)

Bu depo MrCrayfish veya başka bir moddan kod, model, texture veya ses kopyalamaz. Tüm içerik özgün olacaktır.

## Durum (Phase 2)

Forge 1.21.1 iskeleti + merkezi registry mimarisi.

- `ModItems` / `ModBlocks` / `ModEntities` / `ModSounds` / `ModMenus` / `ModParticles` / `ModCreativeTabs`
- Creative tab: **Ultimate Combat & Vehicles** (`itemGroup.ucv`)
- Yeni içerik tek satırlık `register(...)` helper’ları ile eklenir
- Silah ve araç item’ları Phase 3+ içinde bu kayıtlara bağlanacak

## Gereksinimler

- JDK **21** (Temurin / Adoptium önerilir)
- İnternet: ilk `./gradlew` çalıştırması Gradle, Forge ve Minecraft bağımlılıklarını indirir

## Kurulum (oyuncu)

1. Minecraft 1.21.1 için [Forge 52.1.16](https://files.minecraftforge.net/net/minecraftforge/forge/index_1.21.1.html) kurun.
2. `build/libs/ultimatecombatvehicles-0.1.0.jar` dosyasını `mods/` klasörüne koyun.
3. Oyunu Forge profili ile açın.

## Geliştirme / build

Linux / macOS:

```bash
chmod +x ./gradlew
./gradlew build
```

Windows:

```bat
gradlew.bat build
```

Başarılı build çıktısı: `build/libs/ultimatecombatvehicles-0.1.0.jar`

### Development client / server

```bash
./gradlew runClient
./gradlew runServer
```

İlk `runServer` öncesi `run/eula.txt` içinde `eula=true` olmalıdır.

## Kontroller (Phase 2)

- Mods menüsünde **Ultimate Combat & Vehicles** görünür
- Creative inventory’de aynı isimli tab vardır (silahlar eklenince dolacak)
- Server log: `[UCV] Registry architecture ready` ve `[UCV] Registered 1 creative tabs`

## Config

Phase 1’de oyuncu config dosyası yoktur. Config sistemi sonraki phase’de eklenecektir.

## Multiplayer

Mimari client = input / görsel, server = doğrulama + gameplay olacak şekilde ayrılmıştır. Dedicated server, client sınıflarını yüklemez.

## Bilinen kısıtlar

- İlk sürüm yalnızca Minecraft **1.21.1 Forge** hedefler. 1.20.x adaptörü daha sonra eklenecek.
- Silah, mermi, araç, GUI ve asset’ler henüz yok (Phase 3+).

## Gelecek özellikler

Silah çerçevesi, mermi/reload, recoil, 4 araç, yakıt, envanter, hasar, tamir, HUD, persistence ve multiplayer senkronizasyonu.

Daha fazla teknik not: [DEVELOPMENT.md](DEVELOPMENT.md)
