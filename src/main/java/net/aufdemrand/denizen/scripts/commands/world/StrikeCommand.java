package net.aufdemrand.denizen.scripts.commands.world;

import net.aufdemrand.denizencore.exceptions.CommandExecutionException;
import net.aufdemrand.denizencore.exceptions.InvalidArgumentsException;
import net.aufdemrand.denizen.scripts.ScriptEntry;
import net.aufdemrand.denizen.scripts.commands.AbstractCommand;
import net.aufdemrand.denizen.objects.Element;
import net.aufdemrand.denizen.objects.dLocation;
import net.aufdemrand.denizen.objects.aH;
import net.aufdemrand.denizen.utilities.debugging.dB;


public class StrikeCommand extends AbstractCommand {

    @Override
    public void parseArgs(ScriptEntry scriptEntry) throws InvalidArgumentsException {

        // Iterate through arguments
        for (aH.Argument arg : aH.interpret(scriptEntry.getArguments())) {

            if (!scriptEntry.hasObject("location")
                    && arg.matchesArgumentType(dLocation.class))
                scriptEntry.addObject("location", arg.asType(dLocation.class));

            else if (arg.matches("no_damage") || arg.matches("nodamage"))
                scriptEntry.addObject("damage", Element.FALSE);

            else
                arg.reportUnhandled();

        }

        // Check required args
        if (!scriptEntry.hasObject("location"))
            throw new InvalidArgumentsException("Missing location argument!");

        scriptEntry.defaultObject("damage", Element.TRUE);
    }

    @Override
    public void execute(ScriptEntry scriptEntry) throws CommandExecutionException {

        // Extract objects from ScriptEntry
        dLocation location = (dLocation) scriptEntry.getObject("location");
        Boolean damage = scriptEntry.getElement("damage").asBoolean();

        // Debugger
        dB.report(scriptEntry, getName(),
                location.debug()
                + aH.debugObj("Damageable", String.valueOf(damage)));

        // Play the sound
        if (damage)
            location.getWorld().strikeLightning(location);
        else
            location.getWorld().strikeLightningEffect(location);
    }
}
