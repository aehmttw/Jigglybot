package jigglybot;

import jigglybot.monster.Monster;

public interface ICanBattle
{
    //anyone implementing this can participate in a battle

    Monster getNextMonster();

    void queryMove(ChannelWrapper cw, Monster m);
}
