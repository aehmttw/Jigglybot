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

    public int[] stages = new int[6];

    public static final int stage_attack = 0;
    public static final int stage_defense = 1;
    public static final int stage_speed = 2;
    public static final int stage_special = 3;
    public static final int stage_evasion = 4;
    public static final int stage_accuracy = 5;

    public int status;

    public int hpEv = 0;
    public int attackEv = 0;
    public int defenseEv = 0;
    public int speedEv = 0;
    public int specialEv = 0;

    public static final int normal_ball = 0;
    public static final int great_ball = 1;
    public static final int ultra_ball = 2;
    public static final int safari_ball = 3;
    public static final int master_ball = 4;

    public static final double[] stage_effectiveness = {0.25, 0.28, 0.33, 0.40, 0.50, 0.66, 1.00, 1.50, 2.00, 2.50, 3.00, 3.50, 4.00};

    public static final int asleep = 1;
    public static final int paralyzed = 2;
    public static final int poisoned = 3;
    public static final int burned = 4;
    public static final int frozen = 5;

    public boolean isWild = true;

    public Monster(Species species, int level)
    {
        this.species = species;
        this.setLevel(level);
        this.recalculateStats();
        this.catchRate = species.catchRate;
        this.name = species.name;
    }

    public static int getDamage(Monster attacker, Monster enemy, double power, boolean special, boolean crit, double modifier)
    {
        double critBonus = 1;
        double effectiveMul = attacker.attack * 1.0 / enemy.defense;

        if (crit)
            critBonus = 2;

        if (special)
            effectiveMul = attacker.special * 1.0 / enemy.special;

        return (int)(((2.0 * attacker.level * critBonus / 5 + 2) * power * effectiveMul) / 50 * modifier);
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

    public int getCaptureResult(int ball)
    {
        if (!this.isWild)
            return -1;

        if (ball == master_ball)
            return 4;

        int maxN = 151;

        if (ball == normal_ball)
            maxN = 256;
        else if (ball == great_ball)
            maxN = 201;

        int n = (int) (Math.random() * maxN);

        if (this.status == asleep || this.status == frozen)
            n -= 25;
        else if (this.status == paralyzed || this.status == burned || this.status == poisoned)
            n -= 12;

        int b = ball == great_ball ? 8 : 12;
        int f = Math.min(Math.max((this.maxHp * 255 * 4) / (this.hp * b), 1), 255);

        if (n < 0)
            return 4;
        if (n <= this.catchRate)
        {
            int m = (int) (Math.random() * 256);

            if (f >= m)
                return 4;
        }

        int d = this.catchRate * 100 / (maxN - 1);

        if (d >= 256)
            return 3;

        int s = 0;

        if (this.status == asleep || this.status == frozen)
            s += 10;
        else if (this.status == paralyzed || this.status == burned || this.status == poisoned)
            s += 5;

        int x = d * f / 255 + s;

        if (x < 10)
            return 0;
        else if (x < 30)
            return 1;
        else if (x < 70)
            return 2;

        return 3;
    }

    public String modifyStage(int stage, int amount)
    {
        int prev = this.stages[stage];
        this.stages[stage] += amount;
        this.stages[stage] = Math.min(Math.max(this.stages[stage], -6), 6);

        if (prev - this.stages[stage] == 1)
            return this.name + "'s " + getStageName(stage) + " fell!";
        else if (prev - this.stages[stage] >= 2)
            return this.name + "'s " + getStageName(stage) + " greatly fell!";
        else if (prev - this.stages[stage] == -1)
            return this.name + "'s " + getStageName(stage) + " rose!";
        else if (prev - this.stages[stage] <= -2)
            return this.name + "'s " + getStageName(stage) + " greatly rose!";
        else
            return "Nothing happened!";
    }

    public static String getStageName(int stat)
    {
        if (stat == stage_attack)
            return "ATTACK";
        else if (stat == stage_defense)
            return "DEFENSE";
        else if (stat == stage_speed)
            return "SPEED";
        else if (stat == stage_evasion)
            return "EVASION";
        else if (stat == stage_accuracy)
            return "ACCURACY";
        else
            return "INVALID STAT";
    }

    public double getStageMultiplier(int stat)
    {
        if (stat != stage_evasion)
            return stage_effectiveness[stages[stat] + 6];
        else
            return stage_effectiveness[6 - stages[stat]];
    }
}
