package jigglybot;

import jigglybot.monster.Monster;

import java.util.ArrayList;

public class DialogReleaseMonsters extends Dialog
{
    public ArrayList<Monster> monsters;

    public DialogReleaseMonsters(ChannelWrapper cw, UserWrapper uw, ArrayList<Monster> monsters)
    {
        super(cw, uw);
        this.monsters = monsters;
    }

    @Override
    public void execute()
    {
        StringBuilder mons = new StringBuilder();

        if (monsters.size() == 1)
            mons = new StringBuilder(monsters.get(0).name + " is");
        else if (monsters.size() == 2)
            mons = new StringBuilder(monsters.get(0).name + " and " + monsters.get(1).name + " are");
        else
        {
            for (int i = 0; i < monsters.size(); i++)
            {
                mons.append(monsters.get(i).name);

                if (i < monsters.size() - 1)
                    mons.append(", ");

                if (i == monsters.size() - 2)
                    mons.append("and ");
            }

            mons.append(" are");
        }

        channelWrapper.messageChannel.createMessage("Once released, " + mons + " gone forever! OK? (RELEASE/CANCEL)").block();
    }

    @Override
    public void input(String s)
    {
        if (s.toLowerCase().equals("release"))
        {
            for (Monster m: this.monsters)
            {
                user.storage.remove(m);
            }

            StringBuilder mons = new StringBuilder();

            if (monsters.size() == 1)
                mons = new StringBuilder(monsters.get(0).name + " was");
            else if (monsters.size() == 2)
                mons = new StringBuilder(monsters.get(0).name + " and " + monsters.get(1).name + " were");
            else
            {
                for (int i = 0; i < monsters.size(); i++)
                {
                    mons.append(monsters.get(i).name);

                    if (i < monsters.size() - 1)
                        mons.append(", ");

                    if (i == monsters.size() - 2)
                        mons.append("and ");
                }

                mons.append(" were");
            }

            channelWrapper.messageChannel.createMessage(mons + " released outside. Bye!").block();
            channelWrapper.currentDialog = null;
        }
        else
        {
            channelWrapper.messageChannel.createMessage("No POKéMON were released!").block();
            channelWrapper.currentDialog = null;
        }
    }
}
