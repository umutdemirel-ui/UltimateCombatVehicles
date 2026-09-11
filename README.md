# Ultimate Combat & Vehicles

Minecraft **1.21.1** için özgün bir **Forge** silah + araç + ekipman modu.

- **Mod adı:** Ultimate Combat & Vehicles
- **Mod ID:** `ucv`
- **Minecraft:** 1.21.1
- **Forge:** 52.1.16
- **Java:** 21
- **Loader:** Forge (ForgeGradle 6)

Bu depo MrCrayfish veya başka bir moddan kod, model, texture veya ses kopyalamaz. Tüm içerik özgün olacaktır.

## Durum (Phase 3)

Weapon framework hazır; **5 silah henüz kayıtlı değil**.

- `WeaponProperties` + builder (damage, fire rate, magazine, reload, range, recoil, spread, …)
- `FireMode` (`SEMI_AUTO` / `BURST` / `FULL_AUTO`), `AmmoType`, `WeaponType`, `WeaponState`
- `BaseWeapon` (ortak item), `ModItems.registerWeapon(properties)`
- Datapack şeması: `data/ucv/weapons/*.json`
- Attachment slot + ADS alanları ileride doldurulmak üzere mevcut

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

## Kontroller (Phase 3)

- `./gradlew test` — weapon properties / state / JSON parser
- Server log: `[UCV] Weapon framework ready`
- Creative tab boş (silah item’ları sonraki phase)

## Config

Phase 1’de oyuncu config dosyası yoktur. Config sistemi sonraki phase’de eklenecektir.

## Multiplayer

Mimari client = input / görsel, server = doğrulama + gameplay olacak şekilde ayrılmıştır. Dedicated server, client sınıflarını yüklemez.

## Bilinen kısıtlar

- İlk sürüm yalnızca Minecraft **1.21.1 Forge** hedefler. 1.20.x adaptörü daha sonra eklenecek.
- Beş silah, mermi item’ları, ateş etme ve araçlar henüz yok (Phase 4+).

## Gelecek özellikler

Silah çerçevesi, mermi/reload, recoil, 4 araç, yakıt, envanter, hasar, tamir, HUD, persistence ve multiplayer senkronizasyonu.

Daha fazla teknik not: [DEVELOPMENT.md](DEVELOPMENT.md)
