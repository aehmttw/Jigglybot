package jigglybot;

import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.channel.MessageChannel;
import discord4j.core.object.reaction.ReactionEmoji;
import jigglybot.location.Location;

import java.util.ArrayList;
import java.util.HashMap;

public class ChannelWrapper
{
    public static HashMap<Long, ChannelWrapper> wrappers = new HashMap<>();

    public Location location = Location.pallet_town;
    public long id;
    public ArrayList<String> messages = new ArrayList<>();
    public MessageChannel messageChannel;

    public long activeMessage = -1;

    public ChannelWrapper(MessageChannel m, long id)
    {
        this.messageChannel = m;
        this.id = id;
    }

    public static ChannelWrapper get(MessageChannel m)
    {
        long l = m.getId().asLong();
        ChannelWrapper w = wrappers.get(l);

        if (w == null)
        {
            w = new ChannelWrapper(m, l);
            wrappers.put(l, w);
        }

        return w;
    }

    public void queue(String message)
    {
        if (message != null)
            this.messages.add(message);
    }

    public void advance()
    {
        Message m = messageChannel.createMessage(this.messages.remove(0)).block();

        if (this.messages.size() > 0)
        {
            this.activeMessage = m.getId().asLong();
            m.addReaction(ReactionEmoji.unicode("\uD83D\uDD3D")).block();
        }
        else
            this.activeMessage = -1;
    }

    public boolean isClear()
    {
        return this.messages.isEmpty();
    }
}
