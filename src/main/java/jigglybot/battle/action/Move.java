package jigglybot.battle.action;

import jigglybot.ChannelWrapper;
import jigglybot.TriConsumer;
import jigglybot.monster.Monster;
import jigglybot.monster.Type;

import java.util.ArrayList;

public class Move implements IAction
{
    public final String name;
    public final int type;
    public final int power;
    public final int accuracy;
    public final int maxPP;

    public boolean useDamage = true;
    public boolean isSpecial;

    public double critMultiplier = 1;
    public int priority = 0;

    public static final Move none = new Move("none", -1, 0, 0, 0);

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

            if (defender.hp < 0)
                defender.hp = 0;

            cw.queue(defender.name + "'s HP: " + defender.hp + "/" + defender.maxHp);
        };
    }

    public boolean execute(Monster attacker, Monster defender, ChannelWrapper cw, ArrayList<Monster> participants)
    {
        if (this == Move.none)
            return false;

        cw.queue(attacker.name + " used " + this.name + "!");

        if (this.accuracy >= 0)
        {
            int a = (int) (accuracy * attacker.getStageMultiplier(Monster.stage_accuracy) * defender.getStageMultiplier(Monster.stage_evasion));
            int r = (int) (Math.random() * 100);

            if (r < a)
            {
                this.damageBehavior.accept(attacker, defender, cw);

                if (defender.hp <= 0)
                {
                    defender.hp = 0;

                    cw.queue(defender.name + " fainted!");

                    for (Monster m: participants)
                    {
                        int xp = m.xp;
                        boolean lvlup = m.defeatedEnemy(defender, participants.size());

                        cw.queue(m.name + " gained " + (m.xp - xp) + " EXP. Points!");

                        if (lvlup)
                            cw.queue(m.name + " grew to level " + m.level + "!");
                    }
                }

                return true;
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
