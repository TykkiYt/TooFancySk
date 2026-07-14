package me.tykki.toofancysk.elements.effects.npcs;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import de.oliver.fancynpcs.api.FancyNpcsPlugin;
import de.oliver.fancynpcs.api.Npc;
import de.oliver.fancynpcs.api.NpcAttribute;
import org.bukkit.entity.EntityType;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;

public class EffSetNPCAttribute extends Effect {

    static {
        Skript.registerEffect(EffSetNPCAttribute.class,
                "set [the] attribute %string% of npc [named] %string% to %string%"
        );
    }

    private Expression<String> attributeExpression;
    private Expression<String> targetNpcExpression;
    private Expression<String> valueExpression;

    @Override
    @SuppressWarnings("unchecked")
    public boolean init(Expression<?>[] exprs, int matchedPattern, @NotNull Kleenean isDelayed, @NotNull SkriptParser.ParseResult parseResult) {
        attributeExpression = (Expression<String>) exprs[0];
        targetNpcExpression = (Expression<String>) exprs[1];
        valueExpression = (Expression<String>) exprs[2];
        return true;
    }

    @Override
    protected void execute(@NotNull Event event) {
        Npc npc = FancyNpcsPlugin.get().getNpcManager().getNpc(targetNpcExpression.getSingle(event));
        if (npc == null) return;

        if (npc.getData().getType() != EntityType.PLAYER) {
            Skript.error("The npc must be a player npc to set attributes");
            return;
        }

        NpcAttribute attribute = FancyNpcsPlugin.get().getAttributeManager().getAttributeByName(EntityType.PLAYER, attributeExpression.getSingle(event));
        npc.getData().addAttribute(attribute, valueExpression.getSingle(event));

        npc.updateForAll();
    }

    @Override
    public @NotNull String toString(@NotNull Event event, boolean b) {
        return "set " + attributeExpression.toString(event, b) + " of " + targetNpcExpression.toString(event, b) + " to " + valueExpression.toString(event, b);
    }

}
