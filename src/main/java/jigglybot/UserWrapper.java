package jigglybot;

import discord4j.core.object.entity.Member;
import discord4j.core.object.entity.channel.MessageChannel;
import jigglybot.item.Item;
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
    public boolean inBattle = false;

    public ArrayList<Item> items = new ArrayList<>();

    public UserWrapper(long id)
    {
        //items.add(new Item())
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
        this.squad[0] = new Monster(s, 5);
        this.squad[0].setOwner(this);
        c.createMessage("Congratulations! You have received " + this.squad[0].name + "!").block();
    }

    @Override
    public Monster getNextMonster()
    {
        for (int i = 0; i < this.squad.length; i++)
        {
            if (this.squad[i].hp > 0)
                return this.squad[i];
        }

        return null;
    }

    @Override
    public void queryMove(ChannelWrapper cw, Monster m)
    {
        StringBuilder s = new StringBuilder("```What will " + this.name + " do?\n\n" +
                "FIGHT\n");

        for (int i = 0; i < m.moves.length; i++)
        {
            if (m.moves[i] != null)
                s.append(i + 1).append(". ").append(m.moves[i].name).append(" (PP ").append(m.movePP[i]).append("/").append(m.moves[i].maxPP).append(")    ");
        }

        s.append("\n\nITEM\nNot yet available!\n\nSWITCH POKéMON\n");

        for (int i = 0; i < this.squad.length; i++)
        {
            if (this.squad[i] != null)
            {
                if (this.squad[i].hp <= 0)
                    s.append(i + 1).append(". ").append(this.squad[i].name).append(" FNT    ");
                else
                    s.append(i + 1).append(". ").append(this.squad[i].name).append(" L").append(this.squad[i].level).append(" (HP ").append(this.squad[i].hp).append("/").append(this.squad[i].maxHp).append(")    ");
            }
        }

        s.append("\n\nRUN\nAttempts to escape the battle!```");

        cw.queue(s.toString());
        cw.advance();
    }
}
