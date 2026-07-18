package me.tykki.toofancysk.elements.effects.holograms;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import de.oliver.fancyholograms.api.data.TextHologramData;
import de.oliver.fancyholograms.api.hologram.Hologram;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class EffSetHologramLine extends Effect {

    static {
        Skript.registerEffect(EffSetHologramLine.class,
                "set line %number% of [hologram] %hologram% to %string%"
        );
    }

    private Expression<Number> lineNumberExpression;
    private Expression<Hologram> hologramExpression;
    private Expression<String> textExpression;

    @Override
    @SuppressWarnings("unchecked")
    public boolean init(Expression<?>[] exprs, int matchedPattern, @NotNull Kleenean isDelayed, @NotNull SkriptParser.ParseResult parseResult) {
        lineNumberExpression = (Expression<Number>) exprs[0];
        hologramExpression   = (Expression<Hologram>) exprs[1];
        textExpression       = (Expression<String>) exprs[2];
        return true;
    }

    @Override
    protected void execute(@NotNull Event event) {
        Number lineNumber = lineNumberExpression.getSingle(event);
        Hologram hologram = hologramExpression.getSingle(event);
        String text       = textExpression.getSingle(event);

        if (lineNumber == null || hologram == null || text == null) return;

        if (!(hologram.getData() instanceof TextHologramData hologramData)) return;

        double requestedLine = lineNumber.doubleValue();
        if (requestedLine < 1 || requestedLine != Math.rint(requestedLine)) return;

        // Skript käyttää 1-pohjaista indeksointia, Java 0-pohjaista
        int index = (int) requestedLine - 1;
        List<String> lines = new ArrayList<>(hologramData.getText());
        if (index < 0 || index >= lines.size()) return;

        lines.set(index, text);
        hologramData.setText(lines);
        hologram.forceUpdate();
    }

    @Override
    public @NotNull String toString(Event event, boolean debug) {
        return "set line " + lineNumberExpression.toString(event, debug)
                + " of hologram " + hologramExpression.toString(event, debug)
                + " to " + textExpression.toString(event, debug);
    }

}
