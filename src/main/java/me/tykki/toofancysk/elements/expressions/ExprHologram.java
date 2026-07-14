package me.tykki.toofancysk.elements.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import de.oliver.fancyholograms.api.hologram.Hologram;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ExprHologram extends SimpleExpression<Hologram> {

    static {
        Skript.registerExpression(ExprHologram.class, Hologram.class, ExpressionType.SIMPLE, "[the] hologram[s] (named|with name[s]) %strings%");
    }

    private Expression<String> names;

    @Override
    @SuppressWarnings("unchecked")
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        this.names = (Expression<String>) exprs[0];
        return true;
    }

    @Override
    protected Hologram[] get(Event event) {
        List<Hologram> holograms = new ArrayList<>();
        for (String name : names.getAll(event)) {
            Hologram hologram = de.oliver.fancyholograms.api.FancyHologramsPlugin.get().getHologramManager().getHologram(name).orElse(null);
            if (hologram != null) {
                holograms.add(hologram);
            }
        }
        return holograms.toArray(new Hologram[0]);
    }

    @Override
    public boolean isSingle() {
        return names.isSingle();
    }

    @Override
    public Class<? extends Hologram> getReturnType() {
        return Hologram.class;
    }

    @Override
    public @NotNull String toString(Event event, boolean debug) {
        return "the hologram(s) named " + names.toString(event, debug);
    }

}
