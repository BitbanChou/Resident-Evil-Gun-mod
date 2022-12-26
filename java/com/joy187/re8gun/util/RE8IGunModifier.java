package com.joy187.re8gun.util;

public interface RE8IGunModifier {

    default int modifyMaxAmmo(int ammo) {
        return ammo;
    }

}
