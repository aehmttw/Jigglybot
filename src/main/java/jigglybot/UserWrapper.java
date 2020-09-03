package jigglybot;

import discord4j.core.object.entity.Member;
import discord4j.core.object.entity.channel.MessageChannel;
import jigglybot.monster.Monster;
import jigglybot.monster.Species;

import java.util.ArrayList;
import java.util.HashMap;

public class UserWrapper implements ICanBattle
{
    public static HashMap<Long, UserWrapper> wrappers = new HashMap<>();

    public long id;
    public String name;

    public Monster[] squad = new Monster[6];
    public ArrayList<Monster> storage = new ArrayList<>();
    public boolean initialized = false;

    public UserWrapper(long id)
    {
        this.id = id;
    }

    public static UserWrapper get(Member m)
    {
        long l = m.getId().asLong();
        UserWrapper w = wrappers.get(l);

        if (w == null)
        {
            w = new UserWrapper(l);
            wrappers.put(l, w);
        }

        w.name = m.getDisplayName();

        return w;
    }

    public void pickStarter(MessageChannel c, Species s)
    {
        this.initialized = true;
        this.squad[0] = new Monster(s, 1);
        this.squad[0].setOwner(this);
        c.createMessage("Congratulations! You have received " + this.squad[0].name + "!").block();
    }

    @Override
    public Monster getNextMonster()
    {
        return null;
    }
}
