package jigglybot;

import jigglybot.monster.Monster;

public interface ICanBattle
{
    //anyone implementing this can participate in a battle

    public Monster getNextMonster();
}
