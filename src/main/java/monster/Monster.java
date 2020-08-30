package monster;

public class Monster
{
    public Species species;

    public int catchRate;

    public int attackIv = (int) (Math.random() * 16);
    public int defenseIv = (int) (Math.random() * 16);
    public int speedIv = (int) (Math.random() * 16);
    public int specialIv = (int) (Math.random() * 16);
    public int hpIv = (attackIv % 2) * 8 + (defenseIv % 2) * 4 + (speedIv % 2) * 2 + (specialIv % 2);

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

    public boolean isWild = true;

    public Monster(Species species, int level)
    {
        this.species = species;
        this.setLevel(level);
        this.recalculateStats();
        this.catchRate = species.catchRate;
        this.name = species.name;
    }

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

    public void recalculateStats()
    {
        this.maxHp = (int) (((this.species.baseHP + this.hpIv) * 2 + Math.sqrt(this.hpEv) / 4) * this.level / 100 + this.level + 10);
        this.attack = calculateStat(this.species.baseAttack, this.attackIv, this.attackEv, this.level);
        this.defense = calculateStat(this.species.baseDefense, this.defenseIv, this.defenseEv, this.level);
        this.speed = calculateStat(this.species.baseSpeed, this.speedIv, this.speedEv, this.level);
        this.special = calculateStat(this.species.baseSpecial, this.specialIv, this.specialEv, this.level);
    }

    public boolean addXP(int xp)
    {
        this.xp += xp;
        int maxXP = getLevelXP(this.species.xpGain, 100);

        if (this.xp > maxXP)
            this.xp = maxXP;

        boolean lvlup = false;

        while (getLevelXP(this.species.xpGain, this.level + 1) <= this.xp)
        {
            lvlup = true;
            this.level++;
        }

        return lvlup;
    }

    public boolean defeatedEnemy(Monster enemy, int participants)
    {
        double a = enemy.isWild ? 1 : 1.5;
        double t = this.originalTrainer == this.owner ? 1 : 1.5;
        double b = enemy.species.xpAward;
        double l = enemy.level;

        return this.addXP((int) ((a * t * b * l) / (7 * participants)));
    }

    public void setLevel(int level)
    {
        this.level = level;
        this.xp = getLevelXP(this.species.xpGain, level);
    }

    public static int getLevelXP(int type, int level)
    {
        if (type == Species.xp_fast)
            return (int) (0.8 * Math.pow(level, 3));
        else if (type == Species.xp_medium_fast)
            return (int) (Math.pow(level, 3));
        else if (type == Species.xp_medium_slow)
            return (int) Math.max(0, 1.2 * Math.pow(level, 3) - 15 * Math.pow(level, 2) + 100 * level - 140);
        else if (type == Species.xp_slow)
            return (int) (1.25 * Math.pow(level, 3));
        else
            return 1;
    }

    public static int calculateStat(int base, int iv, int ev, int level)
    {
        return (int) (((base + iv) * 2 + Math.sqrt(ev) / 4) * level / 100 + 5);
    }
}
