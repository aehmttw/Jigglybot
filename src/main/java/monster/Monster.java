package monster;

public class Monster
{
    public Species species;

    public int catchRate;

    public int hpIv;
    public int attackIv;
    public int defenseIv;
    public int speedIv;
    public int specialIv;

    public String name;
    public long originalTrainer;
    public long owner;

    public int maxHp;
    public int attack;
    public int defense;
    public int speed;
    public int special;

    public int hp;
    public int xp;
    public int level;

    public int[] moves = new int[4];
    public int[] movePP = new int[4];

    public int status;

    public int hpEv = 0;
    public int attackEv = 0;
    public int defenseEv = 0;
    public int speedEv = 0;
    public int specialEv = 0;

    public void damage(Monster enemy, double power, boolean special, boolean crit, double modifier)
    {
        double critBonus = 1;
        double effectiveMul = this.attack * 1.0 / enemy.defense;

        if (crit)
            critBonus = 2;

        if (special)
            effectiveMul = this.special * 1.0 / enemy.special;

        enemy.hp -= (int)(((2.0 * this.level * critBonus / 5 + 2) * power * effectiveMul) / 50 * modifier);

        if (enemy.hp <= 0)
            enemy.hp = 0;
    }
}
