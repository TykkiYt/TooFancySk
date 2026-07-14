package me.tykki.toofancysk.elements.effects.npcs;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import de.oliver.fancynpcs.api.FancyNpcsPlugin;
import de.oliver.fancynpcs.api.Npc;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;

public class EffRemoveNPC extends Effect {

    static {
        Skript.registerEffect(EffRemoveNPC.class,
                "(remove|delete) [the] npc [named] %string%"
        );
    }

    private Expression<String> targetNpcExpression;

    @Override
    @SuppressWarnings("unchecked")
    public boolean init(Expression<?>[] exprs, int matchedPattern, @NotNull Kleenean isDelayed, @NotNull SkriptParser.ParseResult parseResult) {
        targetNpcExpression = (Expression<String>) exprs[0];
        return true;
    }

    @Override
    protected void execute(@NotNull Event event) {
        Npc npc = FancyNpcsPlugin.get().getNpcManager().getNpc(targetNpcExpression.getSingle(event));
        if (npc == null) return;
        npc.removeForAll();

        FancyNpcsPlugin.get().getNpcManager().removeNpc(npc);
    }

    @Override
    public @NotNull String toString(Event event, boolean b) {
        return "remove npc " + targetNpcExpression.toString(event, b);
    }

}
