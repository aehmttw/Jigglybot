package jigglybot.battle.action;

import jigglybot.ChannelWrapper;
import jigglybot.QuadConsumer;
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

    public double recoil = 0;

    public boolean effectTargetsEnemy = true;
    public double effectChance = 1;

    public int statusEffect = -1;
    public int stage = -1;
    public int stageAmount = -1;

    public static final Move none = new Move("none", -1, 0, 0, 0);
    public static final Move struggle = new Move("STRUGGLE", Type.normal, 10, 50, 100);

    public TriConsumer<Monster, Monster, ChannelWrapper> damageBehavior;
    public QuadConsumer<Monster, Monster, Integer, ChannelWrapper> postDamageBehavior;
    public TriConsumer<Monster, Monster, ChannelWrapper> effectBehavior;

    public Move(String name, int type, int maxPP, int power, int accuracy)
    {
        MoveList.by_name.put(name, this);

        this.name = name.toUpperCase();
        this.type = type;
        this.power = power;
        this.accuracy = accuracy;
        this.maxPP = maxPP;

        this.isSpecial = (type == Type.water || type == Type.grass || type == Type.fire || type == Type.ice ||
                type == Type.electric || type == Type.psychic || type == Type.dragon);

        this.damageBehavior = (attacker, defender, cw) ->
        {
            if (this.power == 0)
                return;

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

        this.postDamageBehavior = (monster, enemy, damage, cw) ->
        {
            if (recoil > 0)
                cw.queue(monster.name + "'s hit with recoil!");
            else if (recoil < 0)
                cw.queue("Sucked health from ");

            monster.hp -= damage * this.recoil;

            monster.hp = Math.min(monster.maxHp, Math.max(0, monster.hp));

            if (recoil != 0)
                cw.queue(monster.name + "'s HP: " + monster.hp + "/" + monster.maxHp);
        };

        this.effectBehavior = (monster, enemy, cw) ->
        {
            if (this.effectChance > Math.random())
            {
                Monster m = enemy;

                if (!this.effectTargetsEnemy)
                    m = monster;

                if (this.statusEffect >= 0 && m.status == 0)
                {
                    if (!(this.statusEffect == Monster.burned && (enemy.species.type1 == Type.fire || enemy.species.type2 == Type.fire)) &&
                            !(this.statusEffect == Monster.frozen && (enemy.species.type1 == Type.ice || enemy.species.type2 == Type.ice)) &&
                            !(this.statusEffect == Monster.poisoned && (enemy.species.type1 == Type.poison || enemy.species.type2 == Type.poison)))
                    {
                        m.status = this.statusEffect;
                        cw.queue(m.name + Monster.getEffectMessage(this.statusEffect));
                    }
                }

                if (this.stage >= 0)
                    cw.queue(m.modifyStage(this.stage, this.stageAmount));
            }

            if (enemy.status == Monster.frozen && this.type == Type.fire)
            {
                enemy.status = 0;
                cw.queue("Fire defrosted " + enemy.name + "!");
            }
        };
    }

    public void execute(Monster attacker, Monster defender, ChannelWrapper cw, ArrayList<Monster> participants)
    {
        if (this == Move.none)
            return;

        cw.queue(attacker.name + " used " + this.name + "!");

        int a = (int) (accuracy * attacker.getStageMultiplier(Monster.stage_accuracy) * defender.getStageMultiplier(Monster.stage_evasion));
        int r = (int) (Math.random() * 100);

        if (r < a || this.accuracy < 0)
        {
            int hp = defender.hp;

            this.damageBehavior.accept(attacker, defender, cw);
            this.postDamageBehavior.accept(attacker, defender, defender.hp - hp, cw);
            this.effectBehavior.accept(attacker, defender, cw);

            return;
        }

        if (useDamage)
            cw.queue(attacker.name + "'s attack missed!");
        else if (isSpecial)
            cw.queue("It didn't affect " + defender.name + "!");
        else
            cw.queue("But, it failed!");
    }
}
