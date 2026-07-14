package me.tykki.toofancysk.elements;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.classes.ClassInfo;
import ch.njol.skript.classes.Parser;
import ch.njol.skript.expressions.base.EventValueExpression;
import ch.njol.skript.lang.ParseContext;
import ch.njol.skript.registrations.Classes;
import ch.njol.util.coll.CollectionUtils;
import de.oliver.fancyholograms.api.FancyHologramsPlugin;
import de.oliver.fancyholograms.api.data.TextHologramData;
import de.oliver.fancyholograms.api.hologram.Hologram;
import me.tykki.toofancysk.utils.Utils;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.skriptlang.skript.lang.converter.Converter;
import org.skriptlang.skript.lang.converter.Converters;

public class Types {

    static {
        Classes.registerClass(new ClassInfo<Hologram>(Hologram.class, "hologram")
                .user("hologram")
                .name("Hologram")
                .description("Represents a hologram in the FancyHolograms plugin.")
                .defaultExpression(new EventValueExpression<>(Hologram.class))
                .changer(new Changer<Hologram>() {
                    @Override
                    public Class<?> @Nullable [] acceptChange(ChangeMode changeMode) {
                        if (changeMode == ChangeMode.DELETE || changeMode == ChangeMode.RESET) {
                            return CollectionUtils.array();
                        }
                        return null;
                    }

                    @Override
                    public void change(Hologram[] holograms, Object @Nullable [] objects, ChangeMode changeMode) {
                        if (changeMode == ChangeMode.DELETE) {
                            Utils.deleteHologram(holograms);
                        } else {
                            for (Hologram hologram : holograms) {
                                TextHologramData data = (TextHologramData) hologram.getData();
                                data.setText(null);
                            }
                        }
                    }
                })
                .parser(new Parser<Hologram>() {
                    @Override
                    public boolean canParse(ParseContext context) {
                        return false;
                    }

                    @Override
                    public @NotNull String toString(Hologram hologram, int flags) {
                        return hologram.getName();
                    }

                    @Override
                    public @NotNull String toVariableNameString(Hologram hologram) {
                        return String.format("hologram named %s", hologram.getName());
                    }
                }));

        Converters.registerConverter(Hologram.class, Location.class, (Converter<Hologram, Location>) hologram -> {
            if (hologram == null) return null;
            TextHologramData data = (TextHologramData) hologram.getData();
            return data.getLocation();
        });

        Converters.registerConverter(String.class, Hologram.class, (Converter<String, Hologram>) name -> {
            return FancyHologramsPlugin.get().getHologramManager().getHologram(name).orElse(null);
        });
    }

}
