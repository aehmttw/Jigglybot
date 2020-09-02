package jigglybot.monster;

import jigglybot.ChannelWrapper;
import jigglybot.TriConsumer;

public class Move
{
    public final String name;
    public final int type;
    public final int power;
    public final int accuracy;
    public final int maxPP;

    public boolean useDamage = true;
    public boolean isSpecial = false;

    public TriConsumer<Monster, Monster, ChannelWrapper> damageBehavior;

    public Move(String name, int type, int power, int accuracy, int maxPP)
    {
        this.name = name.toUpperCase();
        this.type = type;
        this.power = power;
        this.accuracy = accuracy;
        this.maxPP = maxPP;

        this.isSpecial = (type == Type.water || type == Type.grass || type == Type.fire || type == Type.ice ||
                type == Type.electric || type == Type.psychic || type == Type.dragon);

        this.damageBehavior = (attacker, defender, cw) ->
        {

        };
    }

    public void execute(Monster attacker, Monster defender, ChannelWrapper cw)
    {
        if (this.accuracy >= 0)
        {
            int a = (int) (accuracy * attacker.getStageMultiplier(Monster.stage_accuracy) * defender.getStageMultiplier(Monster.stage_evasion));
            int r = (int) (Math.random() * 256);

            cw.queue(attacker.name + " used " + this.name + "!");

            if (r < a)
            {
                this.damageBehavior.accept(attacker, defender, cw);
            }
            else
            {
                if (useDamage)
                    cw.queue("But, it missed!");
                else if (isSpecial)
                    cw.queue("It didn't affect " + defender.name + "!");
                else
                    cw.queue("But, it failed!");
            }
        }
    }
}
