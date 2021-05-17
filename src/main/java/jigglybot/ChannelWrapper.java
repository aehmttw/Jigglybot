package jigglybot;

import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.channel.MessageChannel;
import discord4j.core.object.reaction.ReactionEmoji;
import discord4j.core.spec.MessageCreateSpec;
import jigglybot.battle.Battle;
import jigglybot.location.Location;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Consumer;

public class ChannelWrapper
{
    public static HashMap<Long, ChannelWrapper> wrappers = new HashMap<>();

    public Location location = Location.pallet_town;
    public long id;
    public ArrayList<String> messages = new ArrayList<>();
    public MessageChannel messageChannel;

    public Dialog currentDialog = null;
    public Battle currentBattle = null;

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
        if (this.messages.isEmpty())
            return;

        String s = this.messages.remove(0);

        if (s.startsWith("*"))
        {
            String img = s.substring(1, s.lastIndexOf("*"));
            s = s.substring(s.lastIndexOf("*") + 1);

            messageChannel.createMessage(new Consumer<MessageCreateSpec>()
            {
                @Override
                public void accept(MessageCreateSpec messageCreateSpec)
                {
                    messageCreateSpec.addFile("/icon.png", getClass().getResourceAsStream(img));
                }
            }).block();
        }

        Message m = messageChannel.createMessage(s).block();

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
