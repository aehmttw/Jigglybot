package jigglybot.battle;

import jigglybot.ChannelWrapper;
import jigglybot.ICanBattle;
import jigglybot.UserWrapper;
import jigglybot.monster.Monster;

public class Battle
{
    public ChannelWrapper channel;

    public UserWrapper player1;
    public ICanBattle player2;

    public Monster p1Mon;
    public Monster p2Mon;

    public boolean started = false;
    public boolean joinable = true;

    public Battle(ChannelWrapper channel, ICanBattle challenger)
    {
        this.player2 = challenger;
        this.p2Mon = challenger.getNextMonster();
    }

    public Battle(ChannelWrapper channel, UserWrapper p1, UserWrapper p2)
    {
        this.player1 = p1;
        this.player2 = p2;
        this.joinable = false;
    }

    public void join(UserWrapper player, Monster m)
    {
        if (!player.initialized)
            channel.messageChannel.createMessage("You need to START first!").block();
        else if (this.player1 != null)
            channel.messageChannel.createMessage("You can join when a POKéMON faints!").block();
        else
        {
            boolean canJoin = false;

            for (int i = 0; i < player.squad.length; i++)
            {
                if (player.squad[i] != null && player.squad[i].hp > 0)
                {
                    canJoin = true;
                    break;
                }
            }

            if (!canJoin)
                channel.messageChannel.createMessage("All your POKéMON have fainted! You need to HEAL!").block();
            else
            {
                channel.messages.clear();
                channel.activeMessage = -1;

                this.player1 = player;

                channel.queue(player.name + " joined the battle!");

                if (p2Mon == null)
                    channel.queue("Go " + p1Mon.name + "!");
                else
                {
                    double frac = p2Mon.hp * 1.0 / p2Mon.maxHp;

                    if (frac >= 0.7)
                        channel.queue("Go! " + p1Mon.name + "!");
                    else if (frac >= 0.4)
                        channel.queue("Do it! " + p1Mon.name + "!");
                    else if (frac >= 0.1)
                        channel.queue("Get'm! " + p1Mon.name + "!");
                    else
                        channel.queue("The enemy's weak! Get'm! " + p1Mon.name + "!");
                }
            }
        }
    }
}
