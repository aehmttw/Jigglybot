package jigglybot;

import discord4j.core.DiscordClient;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.event.domain.message.ReactionAddEvent;
import discord4j.core.object.entity.Member;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.channel.MessageChannel;
import discord4j.core.object.reaction.Reaction;
import discord4j.core.spec.MessageCreateSpec;
import jigglybot.battle.Battle;
import jigglybot.battle.action.Move;
import jigglybot.battle.action.MoveList;
import jigglybot.location.Location;
import jigglybot.monster.Dex;
import jigglybot.monster.Monster;
import jigglybot.monster.Species;

import java.util.function.Consumer;

public class Bot
{
    public static String prefix = "-";

    public static void main(final String[] args)
    {
        Species.setup();
        Location.setup();
        Dex.setup();
        MoveList.setup();

        final String token = args[0];
        final DiscordClient client = DiscordClient.create(token);
        final GatewayDiscordClient gateway = client.login().block();

        gateway.on(MessageCreateEvent.class).subscribe(event ->
        {
            Message message = event.getMessage();
            MessageChannel channel = message.getChannel().block();

            String content = message.getContent().toLowerCase();

            if (content.startsWith(prefix))
                content = content.substring(1);
            else
                return;

            Member m = message.getAuthorAsMember().block();

            if (m == null || channel == null)
                return;

            UserWrapper user = UserWrapper.get(m);
            ChannelWrapper setting = ChannelWrapper.get(channel);

            if (content.startsWith("loc"))
            {
                printLocation(channel, m, true);
            }

            if (content.startsWith("next"))
            {
                setting.advance();
            }

            if (content.startsWith("help"))
            {
                String s = "It's a message from PROF. OAK!```Hi! I see you have discovered my newest creation, the JIGGLYBOT! " +
                        "JIGGLYBOT will guide you through the wonderful KANTO world of POKéMON! If you're lost, just type " + prefix + "loc and " +
                        "JIGGLYBOT will tell you where you are. JIGGLYBOT will usually capitalize things you can do, such as HEAL or MOVE." +
                        "You can do these things by typing them with a '" + prefix + "' before them, like " + prefix + "heal or " + prefix + "move. When " +
                        "there's more JIGGLYBOT wants to tell you, it will add a down arrow reaction. Just type " + prefix + "next or click the arrow " +
                        "to see what else JIGGLYBOT wants to tell you. Please help me document every species of POKéMON with your POKéDEX! " +
                        "You can access the POKéDEX with " + prefix + "dex.";

                if (!user.initialized)
                {
                    s += "\n\nOh! It's dangerous to go out alone! I should better give you a POKéMON to start off with! Type -start to get started!";
                }

                channel.createMessage(s + "```").block();
            }

            if (content.startsWith("start"))
            {
                if (user.initialized)
                {
                    channel.createMessage("You have already started!").block();
                    return;
                }

                if (!content.contains(" "))
                {
                    channel.createMessage("Welcome to the world of POKéMON!\n" +
                            "Please START with your first POKéMON:\n" +
                            "1. BULBASAUR\n" +
                            "2. CHARMANDER\n" +
                            "3. SQUIRTLE\n" +
                            "4. PIKACHU").block();
                }
                else
                {
                    String arg = content.split(" ")[1].toUpperCase();

                    switch (arg)
                    {
                        case "1":
                        case "BULBASAUR":
                            user.pickStarter(channel, Species.by_name.get("bulbasaur"));
                            break;
                        case "2":
                        case "CHARMANDER":
                            user.pickStarter(channel, Species.by_name.get("charmander"));
                            break;
                        case "3":
                        case "SQUIRTLE":
                            user.pickStarter(channel, Species.by_name.get("squirtle"));
                            break;
                        case "4":
                        case "PIKACHU":
                            user.pickStarter(channel, Species.by_name.get("pikachu"));
                            break;
                        default:
                            channel.createMessage("Invalid POKéMON specified!").block();
                            break;
                    }
                }
            }

            if (content.startsWith("love"))
            {
                channel.createMessage("Jigglybot loves all of you <3 !").block();
            }

            if (content.startsWith("spawn"))
            {
                if (setting.location.spawnEntries.isEmpty())
                    channel.createMessage("Nothing spawns in " + setting.location.name + "!").block();
                else
                {
                    Monster monster = setting.location.spawn();
                    channel.createMessage(new Consumer<MessageCreateSpec>()
                    {
                        @Override
                        public void accept(MessageCreateSpec messageCreateSpec)
                        {
                            messageCreateSpec.addFile("/icon.png", getClass().getResourceAsStream(("/front/" + monster.name)
                                    .replace("♂", "m").replace("♀", "f").replace("'", "").toLowerCase() + ".png"));
                        }
                    }).block();

                    channel.createMessage("Wild L" + monster.level + " " + monster.name.toUpperCase() + " appeared!\nYou can JOIN the fight!").block();
                    setting.currentBattle = new Battle(setting, monster);
                }
            }

            if (content.startsWith("join") && setting.currentBattle != null)
                setting.currentBattle.join(user);

            if (content.startsWith("fight") && setting.currentBattle != null)
            {
                if (!content.contains(" "))
                    setting.currentBattle.inputFight(user, null);
                else
                    setting.currentBattle.inputFight(user, content.substring(content.indexOf(" ") + 1));
            }

            if (content.startsWith("switch") && setting.currentBattle != null)
            {
                if (!content.contains(" "))
                    setting.currentBattle.inputSwitch(user, null);
                else
                    setting.currentBattle.inputSwitch(user, content.substring(content.indexOf(" ") + 1));
            }

            if (content.startsWith("catch") && setting.currentBattle != null)
                setting.currentBattle.inputCapture(user);

            if (content.startsWith("run") && setting.currentBattle != null)
                setting.currentBattle.inputRun(user);

            if (content.startsWith("heal"))
            {
                if (user.inBattle)
                    channel.createMessage("Can't do this now!").block();
                else if (!setting.location.hasHeal)
                    channel.createMessage("There is no POKéMON CENTER in " + setting.location.name + "!").block();
                else
                {
                    if (setting.messages.isEmpty())
                    {
                        setting.queue("Welcome to our POKéMON CENTER!");
                        setting.queue("We heal your POKéMON back to perfect health!");
                        setting.queue("OK. We'll need your POKéMON.");
                        setting.queue("...");
                        setting.queue("Thank you! Your POKéMON are fighting fit!");
                        setting.queue("We hope to see you again!");
                        setting.advance();
                    }
                    else
                        channel.createMessage("We healed your POKéMON to perfect health!\nWe hope to see you again!").block();

                    for (int i = 0; i < user.squad.length; i++)
                    {
                        if (user.squad[i] != null)
                        {
                            user.squad[i].hp = user.squad[i].maxHp;

                            for (int j = 0; j < user.squad[i].moves.length; j++)
                            {
                                if (user.squad[i].moves[j] != null)
                                {
                                    user.squad[i].movePP[j] = user.squad[i].moves[j].maxPP;
                                }
                            }
                        }
                    }
                }
            }

            if (content.startsWith("dex"))
            {
                if (!content.contains(" "))
                {
                    channel.createMessage("Please specify ALL, NAME, or POKéDEX No.!").block();
                }
                else
                {
                    String arg = content.substring(content.indexOf(" ") + 1);

                    if (arg.equals("all"))
                    {
                        if (!setting.messages.isEmpty())
                        {
                            channel.createMessage("Can't do this now!").block();
                            return;
                        }

                        StringBuilder s = new StringBuilder("All POKéDEX entries:\n");

                        int in = 0;
                        StringBuilder s1 = new StringBuilder();
                        int rows = 0;

                        for (int i: Species.by_num.keySet())
                        {
                            in++;
                            s1.append(String.format("%3d", i)).append(". ").append(Species.by_num.get(i).name);

                            while (s1.length() < 17 * in)
                                s1.append(" ");

                            if (in > 4)
                            {
                                in = 0;
                                s.append(s1.toString()).append("\n");
                                s1 = new StringBuilder();
                                rows++;
                            }

                            if (rows >= 8)
                            {
                                rows = 0;
                                setting.queue("```" + s.toString() + "```");
                                s = new StringBuilder();
                            }
                        }

                        s.append(s1.toString()).append("\n");
                        s.append("Specify POKéDEX No. or NAME for more!");
                        setting.queue("```" + s.toString() + "```");
                        setting.advance();
                    }
                    else
                    {
                        boolean fail = false;
                        try
                        {
                            int num = Integer.parseInt(arg);
                            Species s = Species.by_num.get(num);

                            if (s == null)
                                fail = true;
                            else
                                s.printDexEntry(channel);
                        }
                        catch (Exception e)
                        {
                            Species s = Species.by_name.get(arg.toLowerCase());

                            if (s == null)
                                fail = true;
                            else
                                s.printDexEntry(channel);
                        }

                        if (fail)
                            channel.createMessage("No POKéMON found!").block();
                    }
                }
            }

            if (content.startsWith("move"))
            {
                if (setting.currentBattle != null)
                {
                    channel.createMessage("Finish the battle first!").block();
                }
                else
                {
                    try
                    {
                        int loc = Integer.parseInt(content.split(" ")[1]) - 1;
                        Location l = setting.location.neighbors[loc];
                        setting.location = l;

                        channel.createMessage("Moved to " + l.name + "!").block();

                        printLocation(channel, m, false);
                    }
                    catch (Exception e)
                    {
                        channel.createMessage("Please add LOCATION NUMBER after MOVE command!\n\n").block();

                        printLocation(channel, m, true);
                    }
                }
            }
        });

        gateway.on(ReactionAddEvent.class).subscribe(event ->
        {
            Message message = event.getMessage().block();
            MessageChannel channel = message.getChannel().block();
            ChannelWrapper cw = ChannelWrapper.get(channel);

            if (cw.activeMessage == message.getId().asLong())
            {
                for (Reaction r: message.getReactions())
                {
                    if (r.getEmoji().asUnicodeEmoji().get().getRaw().equals("\uD83D\uDD3D") && r.getCount() > 1)
                    {
                        cw.advance();
                        break;
                    }
                }
            }
        });

        gateway.onDisconnect().block();
    }

    public static void printLocation(MessageChannel channel, Member m, boolean showCurrent)
    {
        Location l = ChannelWrapper.get(channel).location;

        StringBuilder s = new StringBuilder();

        if (showCurrent)
            s.append("Currently in ").append(l.name).append("\n\n");

        s.append("Things to do here:\n");

        if (l.hasHeal)
            s.append("HEAL your POKéMON at the POKéMON CENTER!\n");

        if (!l.spawnEntries.isEmpty())
            s.append("Encounter wild POKéMON!\n");

        s.append("\nMOVE to: \n");

        for (int i = 0; i < l.neighbors.length; i++)
            s.append(i + 1).append(". ").append(l.neighbors[i].name).append("\n");

        channel.createMessage(s.toString().substring(0, s.length() - 1)).block();
    }
}
