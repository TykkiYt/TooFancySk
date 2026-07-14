package me.tykki.toofancysk.elements.effects.npcs;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import de.oliver.fancynpcs.api.FancyNpcsPlugin;
import de.oliver.fancynpcs.api.Npc;
import org.bukkit.Location;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;

public class EffTeleportNPC extends Effect {

    static {
        Skript.registerEffect(EffTeleportNPC.class,
                "teleport [the] npc [named] %string% to %location%"
        );
    }

    private Expression<String> targetNpcExpression;
    private Expression<Location> locationExpression;

    @Override
    @SuppressWarnings("unchecked")
    public boolean init(Expression<?>[] exprs, int matchedPattern, @NotNull Kleenean isDelayed, @NotNull SkriptParser.ParseResult parseResult) {
        targetNpcExpression = (Expression<String>) exprs[0];
        locationExpression = (Expression<Location>) exprs[1];
        return true;
    }

    @Override
    protected void execute(@NotNull Event event) {
        Npc npc = FancyNpcsPlugin.get().getNpcManager().getNpc(targetNpcExpression.getSingle(event));
        if (npc == null) return;
        npc.getData().setLocation(locationExpression.getSingle(event));

        npc.updateForAll();
    }

    @Override
    public @NotNull String toString(Event event, boolean b) {
        return "teleport " + targetNpcExpression.toString(event, b) + " to " + locationExpression.toString(event, b);
    }

}
