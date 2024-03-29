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
import jigglybot.battle.Trainer;
import jigglybot.battle.action.MoveList;
import jigglybot.dialog.DialogEraseAllData;
import jigglybot.dialog.DialogReleaseMonsters;
import jigglybot.location.Location;
import jigglybot.monster.Dex;
import jigglybot.monster.Monster;
import jigglybot.monster.Species;

import java.io.File;
import java.util.ArrayList;
import java.util.function.Consumer;

public class Bot
{
    public static String prefix = "-";

    public static String token;
    public static DiscordClient client;
    public static GatewayDiscordClient gateway;

    public static void main(final String[] args)
    {
        Species.setup();
        Location.setup();
        Dex.setup();
        MoveList.setup();

        new File(UserWrapper.save_dir).mkdirs();

        token = args[0];
        client = DiscordClient.create(token);
        gateway = client.login().block();

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

            if (content.startsWith("next") || content.equals("n") || content.equals(""))
            {
                setting.advance();
                return;
            }

            if (!setting.messages.isEmpty())
            {
                setting.messageChannel.createMessage("Can't do this now!").block();
                return;
            }

            if (setting.currentDialog != null)
            {
                if (setting.currentDialog.user != user)
                    setting.messageChannel.createMessage("Please wait for " + setting.currentDialog.user.name + " to respond!").block();
                else
                    setting.currentDialog.input(content);

                return;
            }

            if (content.startsWith("loc"))
            {
                printLocation(channel, m, true);
            }

            if (content.startsWith("help"))
            {
                String s = "It's a message from PROF. OAK!```Hi! I see you have discovered my newest creation, the JIGGLYBOT! " +
                        "JIGGLYBOT will guide you through the wonderful KANTO world of POKéMON! If you're lost, just type " + prefix + "loc and " +
                        "JIGGLYBOT will tell you where you are. JIGGLYBOT will usually capitalize things you can do, such as HEAL or MOVE. " +
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
                            user.pickStarter(setting, Species.by_name.get("bulbasaur"));
                            break;
                        case "2":
                        case "CHARMANDER":
                            user.pickStarter(setting, Species.by_name.get("charmander"));
                            break;
                        case "3":
                        case "SQUIRTLE":
                            user.pickStarter(setting, Species.by_name.get("squirtle"));
                            break;
                        case "4":
                        case "PIKACHU":
                            user.pickStarter(setting, Species.by_name.get("pikachu"));
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

            if (content.startsWith("save"))
            {
                if (user.save())
                    channel.createMessage(user.name + " saved the game!").block();
                else
                    channel.createMessage("Failed to save the game!").block();
            }

            if (content.startsWith("reset"))
            {
                setting.currentDialog = new DialogEraseAllData(setting, user);
                setting.currentDialog.execute();
            }

            if (content.startsWith("pokemon") || content.startsWith("pokémon"))
            {
                String[] s = content.split(" ");

                if (s.length > 1)
                {
                    try
                    {
                        user.squad[Integer.parseInt(s[1]) - 1].printStats(setting);
                    }
                    catch (Exception e)
                    {
                        channel.createMessage("Invalid POKéMON number specified!").block();
                    }
                }
                else
                    user.printMonsters(setting);
            }

            if (content.startsWith("stats"))
            {
                String[] s = content.split(" ");

                if (s.length > 1)
                {
                    try
                    {
                        user.squad[Integer.parseInt(s[1]) - 1].printStats(setting);
                    }
                    catch (Exception e)
                    {
                        channel.createMessage("Invalid POKéMON number specified!").block();
                    }
                }
                else
                    setting.messageChannel.createMessage("Please supply a POKéMON number to show stats of!").block();
            }

            if (content.startsWith("swap"))
            {
                if (user.inBattle)
                {
                    setting.messageChannel.createMessage("Cannot swap POKéMON in battle!").block();
                    return;
                }

                String[] s = content.split(" ");

                try
                {
                    int first = Integer.parseInt(s[1]) - 1;
                    int second = Integer.parseInt(s[2]) - 1;

                    if (first >= 0 && second >= 0 && first < user.squad.length && second < user.squad.length &&
                            user.squad[first] != null && user.squad[second] != null)
                    {
                        Monster temp = user.squad[first];
                        user.squad[first] = user.squad[second];
                        user.squad[second] = temp;
                        setting.messageChannel.createMessage("Swapped " + user.squad[first].getName() + " and " + user.squad[second].getName() + "!").block();
                        user.printMonsters(setting);
                    }
                    else
                        setting.messageChannel.createMessage("Invalid POKéMON numbers specified!").block();
                }
                catch (Exception e)
                {
                    setting.messageChannel.createMessage("Please supply 2 POKéMON numbers to swap!").block();
                }
            }

            if (content.startsWith("page"))
            {
                if (user.inBattle)
                    channel.createMessage("Finish the battle first!").block();
                else if (!setting.location.hasCenter)
                    channel.createMessage("There is no POKéMON CENTER in " + setting.location.name + "!").block();
                else
                {
                    String[] s = content.split(" ");

                    try
                    {
                        int num = Integer.parseInt(s[1]) - 1;

                        if (num >= 0 && num <= (user.storage.size() - 1) / UserWrapper.entries_per_page)
                        {
                            channel.createMessage("Set page to " + (num + 1) + "!").block();
                            user.page = num;
                            user.printMonsters(setting);
                        }
                        else
                            channel.createMessage("Maximum page is " + (1 + (user.storage.size() - 1) / UserWrapper.entries_per_page) + "!").block();
                    }
                    catch (Exception e)
                    {
                        setting.messageChannel.createMessage("Invalid page number specified!").block();
                    }
                }
            }

            if (content.startsWith("deposit"))
            {
                if (user.inBattle)
                    channel.createMessage("Finish the battle first!").block();
                else if (!setting.location.hasCenter)
                    channel.createMessage("There is no POKéMON CENTER in " + setting.location.name + "!").block();
                else
                {
                    String[] s = content.split(" ");

                    try
                    {
                        int index = Integer.parseInt(s[1]) - 1;

                        if (index >= 0 && index < user.squad.length && user.squad[index] != null)
                        {
                            if (index == 0 && user.squad[1] == null)
                                setting.messageChannel.createMessage("You can't deposit the last POKéMON!").block();

                            Monster mon = user.squad[index];
                            user.storage.add(mon);

                            for (int i = index + 1; i < user.squad.length; i++)
                            {
                                user.squad[i - 1] = user.squad[i];
                                user.squad[i] = null;
                            }

                            setting.messageChannel.createMessage("Sent " + mon.getName() + " to STORAGE PC!").block();
                            user.printMonsters(setting);
                        }
                        else
                            setting.messageChannel.createMessage("Invalid POKéMON number specified!").block();
                    }
                    catch (Exception e)
                    {
                        setting.messageChannel.createMessage("Please supply a POKéMON number to send to STORAGE PC!").block();
                    }
                }
            }

            if (content.startsWith("withdraw"))
            {
                if (user.inBattle)
                    channel.createMessage("Finish the battle first!").block();
                else if (!setting.location.hasCenter)
                    channel.createMessage("There is no POKéMON CENTER in " + setting.location.name + "!").block();
                else
                {
                    String[] s = content.split(" ");

                    try
                    {
                        int index = Integer.parseInt(s[1]) - 1;

                        if (index >= 0 && index < user.storage.size())
                        {
                            int free = -1;
                            for (int i = 0; i < user.squad.length; i++)
                            {
                                if (user.squad[i] == null)
                                {
                                    free = i;
                                    break;
                                }
                            }

                            if (free == -1)
                                setting.messageChannel.createMessage("You can't take any more POKéMON. Deposit POKéMON first.").block();
                            else
                            {
                                Monster mon = user.storage.remove(index);
                                setting.messageChannel.createMessage("Took " + mon.getName() + " from STORAGE PC!").block();
                                user.squad[free] = mon;

                                user.printMonsters(setting);
                            }
                        }
                        else
                            setting.messageChannel.createMessage("Invalid POKéMON number specified!").block();
                    }
                    catch (Exception e)
                    {
                        setting.messageChannel.createMessage("Please supply a POKéMON number to withdraw from STORAGE PC!").block();
                    }
                }
            }

            if (content.startsWith("release"))
            {
                if (user.inBattle)
                    channel.createMessage("Finish the battle first!").block();
                else if (!setting.location.hasCenter)
                    channel.createMessage("There is no POKéMON CENTER in " + setting.location.name + "!").block();
                else
                {
                    String[] s = content.split(" ");

                    ArrayList<Monster> mons = new ArrayList<>();

                    if (s.length <= 1)
                    {
                        setting.messageChannel.createMessage("Please supply POKéMON numbers to release from STORAGE PC!").block();
                        return;
                    }

                    try
                    {
                        for (int i = 1; i < s.length; i++)
                        {
                            int index = Integer.parseInt(s[i]) - 1;

                            if (index >= 0 && index < user.storage.size())
                            {
                                Monster mon = user.storage.get(index);
                                mons.add(mon);
                            }
                            else
                            {
                                setting.messageChannel.createMessage("Invalid POKéMON numbers specified!").block();
                                return;
                            }
                        }
                    }
                    catch (Exception e)
                    {
                        setting.messageChannel.createMessage("Please supply POKéMON numbers to release from STORAGE PC!").block();
                    }

                    setting.currentDialog = new DialogReleaseMonsters(setting, user, mons);
                    setting.currentDialog.execute();
                }
            }

            if (content.startsWith("spawn"))
            {
                if (setting.location.spawnEntries.isEmpty())
                    channel.createMessage("Nothing spawns in " + setting.location.name + "!").block();
                else if (setting.currentBattle != null)
                    channel.createMessage("Finish the battle first!").block();
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

                    channel.createMessage("Wild L" + monster.level + " " + monster.getName().toUpperCase() + " appeared!\nYou can JOIN the fight!").block();
                    setting.currentBattle = new Battle(setting, monster);
                }
            }

            if (content.startsWith("battle"))
            {
                if (setting.currentBattle != null)
                    channel.createMessage("Finish the battle first!").block();
                else
                {
                    Trainer trainer = new Trainer();

                    channel.createMessage(trainer.name + " wants to fight!\nYou can JOIN the fight!").block();
                    setting.currentBattle = new Battle(setting, trainer);
                }
            }

            if (content.startsWith("join") && setting.currentBattle != null)
                setting.currentBattle.join(user, null);

            if (content.startsWith("fight") && setting.currentBattle != null)
            {
                if (!content.contains(" "))
                    setting.currentBattle.inputFight(user, null);
                else
                    setting.currentBattle.inputFight(user, content.substring(content.indexOf(" ") + 1));
            }

            if (content.startsWith("learn") && setting.currentBattle != null)
            {
                if (!content.contains(" "))
                    setting.currentBattle.inputLearn(user, null);
                else
                    setting.currentBattle.inputLearn(user, content.substring(content.indexOf(" ") + 1));
            }

            if (content.startsWith("switch") && setting.currentBattle != null)
            {
                if (setting.currentBattle.player1 == null)
                {
                    if (setting.currentBattle.prevP1 != user)
                        setting.messageChannel.createMessage("You must JOIN the battle!").block();
                    else
                    {
                        if (!content.contains(" "))
                            setting.messageChannel.createMessage("Please specify POKéMON NO. or NAME!").block();
                        else
                            setting.currentBattle.join(user, content.substring(content.indexOf(" ") + 1));
                    }
                }
                else
                {
                    if (!content.contains(" "))
                        setting.currentBattle.inputSwitch(user, null);
                    else
                        setting.currentBattle.inputSwitch(user, content.substring(content.indexOf(" ") + 1));
                }
            }

            if (content.startsWith("catch") && setting.currentBattle != null)
                setting.currentBattle.inputCapture(user);

            if (content.startsWith("run") && setting.currentBattle != null)
                setting.currentBattle.inputRun(user);

            if (content.startsWith("heal"))
            {
                if (user.inBattle)
                    channel.createMessage("Finish the battle first!").block();
                else if (!setting.location.hasCenter)
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
                            user.squad[i].status = 0;

                            for (int j = 0; j < user.squad[i].moves.length; j++)
                            {
                                if (user.squad[i].moves[j] != null)
                                {
                                    user.squad[i].movePP[j] = user.squad[i].moves[j].maxPP;
                                }
                            }
                        }
                    }

                    user.save();
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
                        StringBuilder s = new StringBuilder("All POKéDEX entries:\n");

                        int in = 0;
                        StringBuilder s1 = new StringBuilder();
                        int rows = 0;

                        int lastEntry = 0;
                        for (int i: Species.by_num.keySet())
                        {
                            if (user.dex[i] > 0)
                                lastEntry = i;
                        }

                        if (lastEntry == 0)
                            s.append("You must START first!");

                        for (int i: Species.by_num.keySet())
                        {
                            if (i > lastEntry)
                                break;

                            in++;
                            s1.append(String.format("%3d", i)).append(". ");

                            if (user.dex[i] <= 0)
                                s1.append("\u25CE----------");
                            else
                            {
                                if (user.dex[i] >= 2)
                                    s1.append("\u25C9");
                                else
                                    s1.append("\u25CE");

                                s1.append(Species.by_num.get(i).name);
                            }

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
                                s.printDexEntry(setting, user, false);
                        }
                        catch (Exception e)
                        {
                            Species s = Species.by_name.get(arg.toLowerCase());

                            if (s == null)
                                fail = true;
                            else
                                s.printDexEntry(setting, user, false);
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

        StringBuilder s = new StringBuilder("```");

        if (showCurrent)
            s.append("Currently in ").append(l.name).append("\n\n");

        s.append("Things to do here:\n");

        if (l.hasCenter)
        {
            s.append("HEAL your POKéMON at the POKéMON CENTER!\n");
            s.append("DEPOSIT, WITHDRAW, or RELEASE POKéMON with the POKéMON CENTER's STORAGE PC!\n");
        }

        if (!l.spawnEntries.isEmpty())
            s.append("Encounter wild POKéMON!\n");

        s.append("\nMOVE to: \n");

        for (int i = 0; i < l.neighbors.length; i++)
            s.append(i + 1).append(". ").append(l.neighbors[i].name).append("\n");

        channel.createMessage(s.toString().substring(0, s.length() - 1) + "```").block();
    }
}
