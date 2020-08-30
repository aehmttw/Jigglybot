import discord4j.core.DiscordClient;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Member;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.channel.MessageChannel;
import location.Location;
import monster.Species;

public class Bot
{
    public static String prefix = "-";

    public static void main(final String[] args)
    {
        Species.setup();
        Location.setup();

        final String token = args[0];
        final DiscordClient client = DiscordClient.create(token);
        final GatewayDiscordClient gateway = client.login().block();

        gateway.on(MessageCreateEvent.class).subscribe(event ->
        {
            Message message = event.getMessage();
            MessageChannel channel = message.getChannel().block();
            String content = message.getContent().toLowerCase();
            Member m = message.getAuthorAsMember().block();
            UserWrapper user = UserWrapper.get(m);

            if (content.startsWith(prefix + "loc"))
            {
                printLocation(channel, m, true);
            }

            if (content.startsWith(prefix + "love"))
            {
                channel.createMessage("Jigglybot loves all of you <3 !").block();
            }

            if (content.startsWith(prefix + "dex"))
            {
                if (!content.contains(" "))
                {
                    channel.createMessage("Please specify POKéDEX No. or NAME!").block();
                }
                else
                {
                    String arg = content.substring(content.indexOf(" ") + 1);

                    boolean fail = false;
                    try
                    {
                        int num = Integer.parseInt(arg);
                        Species s = Species.speciesByNum.get(num);

                        if (s == null)
                            fail = true;
                        else
                            s.printStats(channel);
                    }
                    catch (Exception e)
                    {
                        Species s = Species.speciesByName.get(arg.toLowerCase());

                        if (s == null)
                            fail = true;
                        else
                            s.printStats(channel);
                    }

                    if (fail)
                        channel.createMessage("No POKéMON found!").block();
                }
            }

            if (content.startsWith(prefix + "move"))
            {
                try
                {
                    int loc = Integer.parseInt(content.split(" ")[1]) - 1;
                    Location l = user.location.neighbors[loc];
                    user.location = l;

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

        gateway.onDisconnect().block();
    }

    public static void printLocation(MessageChannel channel, Member m, boolean showCurrent)
    {
        Location l = UserWrapper.get(m).location;

        StringBuilder s = new StringBuilder();

        if (showCurrent)
            s.append("Currently in ").append(l.name).append("\n\n");

        s.append("Can MOVE to: \n");

        for (int i = 0; i < l.neighbors.length; i++)
            s.append(i + 1).append(". ").append(l.neighbors[i].name).append("\n");

        channel.createMessage(s.toString().substring(0, s.length() - 1)).block();
    }
}
