package me.tykki.toofancysk.elements.sections;

import ch.njol.skript.Skript;
import ch.njol.skript.config.SectionNode;
import ch.njol.skript.expressions.base.EventValueExpression;
import ch.njol.skript.lang.EffectSection;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.TriggerItem;
import ch.njol.util.Kleenean;
import de.oliver.fancyholograms.api.FancyHologramsPlugin;
import de.oliver.fancyholograms.api.HologramManager;
import de.oliver.fancyholograms.api.data.TextHologramData;
import de.oliver.fancyholograms.api.hologram.Hologram;
import org.bukkit.Location;
import org.bukkit.event.Event;
import org.jspecify.annotations.Nullable;

import java.util.List;

public class SecHologram extends EffectSection {

    static {
        Skript.registerSection(SecHologram.class, "(create|spawn) [a] [new] hologram (named|with name) %string% at %location%");
    }

    private Hologram holo;
    private Expression<String> name;
    private Expression<Location> location;

    @Override
    @SuppressWarnings("unchecked")
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult,
                        @Nullable SectionNode sectionNode, @Nullable List<TriggerItem> triggerItems) {
        if (sectionNode != null) {
            loadOptionalCode(sectionNode);
        }
        name = (Expression<String>) exprs[0];
        location = (Expression<Location>) exprs[1];

        return true;
    }

    @Override
    @Nullable
    protected TriggerItem walk(Event e) {
        TextHologramData hologramData = new TextHologramData(name.getSingle(e), location.getSingle(e));
        HologramManager manager = FancyHologramsPlugin.get().getHologramManager();
        holo = manager.create(hologramData);

        manager.addHologram(holo);
        return walk(e, true);
    }

    @Override
    public String toString(@Nullable Event event, boolean debug) {
        return "create hologram named " + name.toString(event, debug) + " at " + location.toString(event, debug);
    }

}
