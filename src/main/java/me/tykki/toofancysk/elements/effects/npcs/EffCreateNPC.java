package me.tykki.toofancysk.elements.effects.npcs;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import de.oliver.fancynpcs.api.FancyNpcsPlugin;
import de.oliver.fancynpcs.api.Npc;
import de.oliver.fancynpcs.api.NpcData;
import org.bukkit.Location;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;

public class EffCreateNPC extends Effect {

    static {
        Skript.registerEffect(EffCreateNPC.class,
                "create [an] npc [named] %string% at %location%"
        );
    }

    private Expression<String> npcNameExpression;
    private Expression<Location> locationExpression;

    @Override
    @SuppressWarnings("unchecked")
    public boolean init(Expression<?>[] exprs, int matchedPattern, @NotNull Kleenean isDelayed, @NotNull SkriptParser.ParseResult parseResult) {
        npcNameExpression = (Expression<String>) exprs[0];
        locationExpression = (Expression<Location>) exprs[1];
        return true;
    }

    @Override
    protected void execute(@NotNull Event event) { // NpcData(name, creatorUUID, location)
        NpcData data = new NpcData(npcNameExpression.getSingle(event), null, locationExpression.getSingle(event));

        data.setSkin("Steve"); // default skin
        data.setDisplayName(npcNameExpression.getSingle(event));

        Npc npc = FancyNpcsPlugin.get().getNpcAdapter().apply(data);
        FancyNpcsPlugin.get().getNpcManager().registerNpc(npc);
        npc.create();
        npc.spawnForAll();
    }

    @Override
    public @NotNull String toString(Event event, boolean b) {
        return "create npc " + npcNameExpression.toString(event, b) + " at " + locationExpression.toString(event, b);
    }

}
