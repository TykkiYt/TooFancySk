package me.tykki.toofancysk.elements.effects.holograms;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import de.oliver.fancyholograms.api.FancyHologramsPlugin;
import de.oliver.fancyholograms.api.HologramManager;
import de.oliver.fancyholograms.api.data.TextHologramData;
import de.oliver.fancyholograms.api.hologram.Hologram;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;

public class EffAddLineToHologram extends Effect {

    static {
        Skript.registerEffect(EffAddLineToHologram.class,
                "add line %string% to %hologram%"
        );
    }

    private Expression<String> lineExpression;
    private Expression<Hologram> hologramExpression;

    @Override
    @SuppressWarnings("unchecked")
    public boolean init(Expression<?>[] exprs, int matchedPattern, @NotNull Kleenean isDelayed, @NotNull SkriptParser.ParseResult parseResult) {
        lineExpression = (Expression<String>) exprs[0];
        hologramExpression = (Expression<Hologram>) exprs[1];
        return true;
    }

    @Override
    protected void execute(@NotNull Event event) {
        Hologram hologram = hologramExpression.getSingle(event);
        if (hologram == null) return;

        TextHologramData hologramData = (TextHologramData) hologram.getData();
        hologramData.addLine(lineExpression.getSingle(event));
        hologram.forceUpdate();
    }

    @Override
    public @NotNull String toString(@NotNull Event event, boolean b) {
        return "add line " + lineExpression.toString(event, b) + " to hologram " + hologramExpression.toString(event, b);
    }

}
