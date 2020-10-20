package jigglybot.battle.action;

import jigglybot.battle.Battle;
import jigglybot.monster.Monster;

public class SwitchMonster implements IAction
{
    public final Monster monster;

    public SwitchMonster(Monster monster)
    {
        this.monster = monster;
    }
    
    public Monster execute(Battle b, Monster prev, int damage, int hp, double frac)
    {
        if (damage >= 0.7 * hp)
            b.channel.queue(prev.name + " good! Come back!");
        else if (damage >= 0.3 * hp)
            b.channel.queue(prev.name + " OK! Come back!");
        else if (damage >= 1)
            b.channel.queue(prev.name + " Come back!");
        else
            b.channel.queue(prev.name + " enough! Come back!");

        if (frac >= 0.7)
            b.channel.queue("Go! " + monster.name + "!");
        else if (frac >= 0.4)
            b.channel.queue("Do it! " + monster.name + "!");
        else if (frac >= 0.1)
            b.channel.queue("Get'm! " + monster.name + "!");
        else
            b.channel.queue("The enemy's weak! Get'm! " + monster.name + "!");

        return this.monster;
    }
}
