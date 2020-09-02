package jigglybot;

import discord4j.core.DiscordClient;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.event.domain.message.ReactionAddEvent;
import discord4j.core.object.entity.Member;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.channel.MessageChannel;
import discord4j.core.object.reaction.Reaction;
import jigglybot.location.Location;
import jigglybot.monster.Dex;
import jigglybot.monster.Monster;
import jigglybot.monster.Species;

public class Bot
{
    public static String prefix = "-";

    public static void main(final String[] args)
    {
        Species.setup();
        Location.setup();
        Dex.setup();

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
                    channel.createMessage("Wild L" + monster.level + " " + monster.name.toUpperCase() + " appeared!").block();
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

        s.append("Can MOVE to: \n");

        for (int i = 0; i < l.neighbors.length; i++)
            s.append(i + 1).append(". ").append(l.neighbors[i].name).append("\n");

        channel.createMessage(s.toString().substring(0, s.length() - 1)).block();
    }
}
