package jigglybot;

import discord4j.core.object.entity.Member;
import discord4j.core.object.entity.channel.MessageChannel;
import jigglybot.item.Item;
import jigglybot.monster.Monster;
import jigglybot.monster.Species;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class UserWrapper implements ICanBattle
{
    public static HashMap<Long, UserWrapper> wrappers = new HashMap<>();
    public static final String save_dir = "userdata";

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

        this.load();
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
        StringBuilder s = new StringBuilder("What will " + this.name + " do?\n" +
                "```\nFIGHT\n");

        for (int i = 0; i < m.moves.length; i++)
        {
            if (m.moves[i] != null)
                s.append(i + 1).append(". ").append(m.moves[i].name).append(" (PP ").append(m.movePP[i]).append("/").append(m.moves[i].maxPP).append(")\n");
        }

        s.append("Psst... Looking for more moves? Use -LEARN <move name> to learn that move!``````\nITEM\nNot yet available! Use -CATCH for now!``````\nSWITCH POKéMON\n");

        for (int i = 0; i < this.squad.length; i++)
        {
            if (this.squad[i] != null)
            {
                if (this.squad[i].hp <= 0)
                    s.append(i + 1).append(". ").append(this.squad[i].name).append(" FNT\n");
                else if (this.squad[i].status != 0)
                    s.append(i + 1).append(". ").append(this.squad[i].name).append(" ").append(this.squad[i].getStatusText()).append("\n");
                else
                    s.append(i + 1).append(". ").append(this.squad[i].name).append(" L").append(this.squad[i].level).append(" (HP ").append(this.squad[i].hp).append("/").append(this.squad[i].maxHp).append(")\n");
            }
        }

        s.append("``````\nRUN\nAttempts to escape the battle!```");

        cw.queue(s.toString());
        cw.advance();
    }

    public boolean save()
    {
        try
        {
            File f = this.getFile();

            if (!f.exists())
                f.createNewFile();

            PrintWriter pw = new PrintWriter(f);

            pw.println("squad");

            for (Monster m: this.squad)
            {
                if (m != null)
                    pw.println(m.toString());
            }

            pw.println("storage");

            for (Monster m: this.storage)
            {
                pw.println(m.toString());
            }

            pw.close();

            return true;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }

    public void load()
    {
        if (!this.getFile().exists())
            return;

        try
        {
            BufferedReader br = new BufferedReader(new FileReader(this.getFile()));

            int monsterCount = 0;
            int mode = 0;
            String line = br.readLine();

            while (line != null)
            {
                if (line.equals("squad"))
                    mode = 1;
                else if (line.equals("storage"))
                    mode = 2;
                else
                {
                    if (mode == 1)
                    {
                        this.squad[monsterCount] = new Monster(line);
                        monsterCount++;
                        this.initialized = true;
                    }
                    else if (mode == 2)
                    {
                        this.storage.add(new Monster(line));
                    }
                }

                line = br.readLine();
            }

            br.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void endBattle()
    {
        this.save();

        for (Monster m: this.squad)
        {
            if (m == null)
                continue;

            for (int i = 0; i < m.stages.length; i++)
            {
                m.stages[i] = 0;
            }

            m.confuseTurns = 0;

            if (m.status == Monster.asleep)
                m.sleepTurns = (int) (Math.random() * 7 + 1);

            m.flinched = false;
        }
    }

    public File getFile()
    {
        return new File(save_dir + "/" + this.id);
    }
}
