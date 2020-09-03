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

    public double critMultiplier = 1;
    public int priority = 0;

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
            double mod = (int)(Math.random() * 39 + 217) / 255.0;

            if (this.type == attacker.species.type1 || this.type == attacker.species.type2)
                mod *= 1.5;

            boolean crit = false;

            int t = (int) (attacker.species.baseSpeed / 2 * critMultiplier);
            if (t > (int)(Math.random() * 256))
                crit = true;

            mod *= Type.getEffectiveness(this.type, defender.species.type1, defender.species.type2);

            if (crit)
                cw.queue("Critical hit!");

            cw.queue(Type.getEffectiveMessage(this.type, defender.species.type1, defender.species.type2, defender.name));

            defender.hp -= Monster.getDamage(attacker, defender, this.power, this.isSpecial, crit, mod);
        };
    }

    public boolean execute(Monster attacker, Monster defender, ChannelWrapper cw, int participants)
    {
        cw.queue(attacker.name + " used " + this.name + "!");

        if (this.accuracy >= 0)
        {
            int a = (int) (accuracy * attacker.getStageMultiplier(Monster.stage_accuracy) * defender.getStageMultiplier(Monster.stage_evasion));
            int r = (int) (Math.random() * 256);

            if (r < a)
            {
                this.damageBehavior.accept(attacker, defender, cw);

                if (defender.hp < 0)
                {
                    defender.hp = 0;
                    attacker.defeatedEnemy(defender, participants);
                    return true;
                }
            }
        }

        if (useDamage)
            cw.queue("But, it missed!");
        else if (isSpecial)
            cw.queue("It didn't affect " + defender.name + "!");
        else
            cw.queue("But, it failed!");

        return false;
    }
}
