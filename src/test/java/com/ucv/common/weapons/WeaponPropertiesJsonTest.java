package com.ucv.common.weapons;

import com.google.gson.JsonParser;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class WeaponPropertiesJsonTest {
    @Test
    void readsDatapackSchema() {
        String json = """
                {
                  "id": "json_probe",
                  "type": "smg",
                  "damage": 6,
                  "magazineSize": 25,
                  "fireRateTicks": 2,
                  "reloadTimeTicks": 35,
                  "range": 40,
                  "ammoType": "pistol_ammo",
                  "fireModes": ["FULL_AUTO"],
                  "recoil": 0.9,
                  "attachmentSlots": ["GRIP", "MUZZLE"]
                }
                """;
        WeaponProperties properties = WeaponPropertiesJson.read(JsonParser.parseString(json).getAsJsonObject());
        assertEquals("json_probe", properties.id());
        assertEquals(WeaponType.SMG, properties.type());
        assertEquals(AmmoType.PISTOL_AMMO, properties.ammoType());
        assertEquals(2, properties.fireRateTicks());
        assertTrue(properties.attachmentSlots().contains(AttachmentSlot.GRIP));
        assertEquals(FireMode.FULL_AUTO, properties.defaultFireMode());
    }
}
