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
            b.channel.queue(prev.getName() + " good! Come back!");
        else if (damage >= 0.3 * hp)
            b.channel.queue(prev.getName() + " OK! Come back!");
        else if (damage >= 1)
            b.channel.queue(prev.getName() + " Come back!");
        else
            b.channel.queue(prev.getName() + " enough! Come back!");

        String img = ("*" + "/back/" + monster.species.name.toUpperCase())
                .replace("♂", "m").replace("♀", "f").replace("'", "").toLowerCase() + "b.png*";

        if (frac >= 0.7)
            b.channel.queue(img + "Go! " + monster.getName() + "!");
        else if (frac >= 0.4)
            b.channel.queue(img + "Do it! " + monster.getName() + "!");
        else if (frac >= 0.1)
            b.channel.queue(img + "Get'm! " + monster.getName() + "!");
        else
            b.channel.queue(img + "The enemy's weak! Get'm! " + monster.getName() + "!");

        return this.monster;
    }
}
