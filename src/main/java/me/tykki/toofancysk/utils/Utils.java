package me.tykki.toofancysk.utils;

import de.oliver.fancyholograms.api.FancyHologramsPlugin;
import de.oliver.fancyholograms.api.HologramManager;
import de.oliver.fancyholograms.api.hologram.Hologram;

public class Utils {

    public static void deleteHologram(Hologram[] hologram) {
        HologramManager manager = FancyHologramsPlugin.get().getHologramManager();
        for (Hologram h : hologram) {
            if (h != null) {
                manager.removeHologram(h);
            }
        }
    }

}
