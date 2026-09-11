package com.ucv.common.weapons;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

public enum FireMode {
    SEMI_AUTO,
    BURST,
    FULL_AUTO;

    public String id() {
        return name().toLowerCase(Locale.ROOT);
    }

    public String translationKey() {
        return "ucv.weapon.fire_mode." + id();
    }

    public FireMode next(Collection<FireMode> supported) {
        List<FireMode> list = new ArrayList<>(supported);
        if (list.isEmpty()) {
            return this;
        }
        int index = list.indexOf(this);
        if (index < 0) {
            return list.getFirst();
        }
        return list.get((index + 1) % list.size());
    }

    public static FireMode fromId(String id) {
        return valueOf(id.trim().toUpperCase(Locale.ROOT));
    }
}
