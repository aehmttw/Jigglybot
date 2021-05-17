package jigglybot.monster;

import discord4j.common.util.Snowflake;
import discord4j.core.spec.MessageCreateSpec;
import jigglybot.Bot;
import jigglybot.ChannelWrapper;
import jigglybot.ICanBattle;
import jigglybot.UserWrapper;
import jigglybot.battle.action.Move;
import jigglybot.battle.action.MoveList;
import jigglybot.item.PokeBall;

import java.util.ArrayList;
import java.util.function.Consumer;

public class Monster implements ICanBattle
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

    public Move[] moves = new Move[4];
    public int[] movePP = new int[4];

    public int[] stages = new int[6];

    public boolean isWild = true;

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

    public boolean flinched = false;
    public int confuseTurns = 0;
    public int sleepTurns = 0;

    public static final double[] stage_effectiveness = {0.25, 0.28, 0.33, 0.40, 0.50, 0.66, 1.00, 1.50, 2.00, 2.50, 3.00, 3.50, 4.00};

    public static final int asleep = 1;
    public static final int paralyzed = 2;
    public static final int poisoned = 3;
    public static final int burned = 4;
    public static final int frozen = 5;

    public static final int confused = 6;

    public Monster(Species species, int level)
    {
        this.species = species;
        this.setLevel(level);
        this.recalculateStats();
        this.catchRate = species.catchRate;
        this.name = species.name.toUpperCase();
        this.hp = this.maxHp;

        this.moves[0] = MoveList.allMoves.get((int) (MoveList.allMoves.size() * Math.random()));
        this.moves[1] = MoveList.allMoves.get((int) (MoveList.allMoves.size() * Math.random()));
        this.moves[2] = MoveList.allMoves.get((int) (MoveList.allMoves.size() * Math.random()));
        this.movePP[0] = this.moves[0].maxPP;
        this.movePP[1] = this.moves[1].maxPP;
        this.movePP[2] = this.moves[2].maxPP;

    }

    public Monster(String s, UserWrapper owner)
    {
        this.isWild = false;
        this.owner = owner.id;
        this.fromString(s);
    }

    public static int getDamage(Monster attacker, Monster enemy, double power, boolean special, boolean crit, double modifier)
    {
        double critBonus = 1;
        double effectiveMul = (attacker.getStageMultiplier(stage_attack) * attacker.attack) / (enemy.getStageMultiplier(stage_defense) * enemy.defense);

        if (crit)
            critBonus = 2;

        if (special)
            effectiveMul = (attacker.getStageMultiplier(stage_special) * attacker.special) / (enemy.getStageMultiplier(stage_special) * enemy.special);

        if (attacker.status == burned)
            modifier *= 0.5;

        return Math.max(1, (int)(((2.0 * attacker.level * critBonus / 5 + 2) * power * effectiveMul) / 50 * modifier));
    }

    public void recalculateStats()
    {
        int prevMaxHP = this.maxHp;

        this.maxHp = (int) (((this.species.baseHP + this.hpIv) * 2 + Math.sqrt(this.hpEv) / 4) * this.level / 100 + this.level + 10);
        this.attack = calculateStat(this.species.baseAttack, this.attackIv, this.attackEv, this.level);
        this.defense = calculateStat(this.species.baseDefense, this.defenseIv, this.defenseEv, this.level);
        this.speed = calculateStat(this.species.baseSpeed, this.speedIv, this.speedEv, this.level);
        this.special = calculateStat(this.species.baseSpecial, this.specialIv, this.specialEv, this.level);

        this.hp += this.maxHp - prevMaxHP;
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
            this.recalculateStats();
        }

        return lvlup;
    }

    public boolean defeatedEnemy(Monster enemy, int participants)
    {
        double a = enemy.isWild ? 1 : 1.5;
        double t = this.originalTrainer == this.owner ? 1 : 1.5;
        double b = enemy.species.xpAward;
        double l = enemy.level;

        this.hpEv += enemy.maxHp;
        this.attackEv += enemy.speedEv;
        this.defenseEv += enemy.defenseEv;
        this.speedEv += enemy.speedEv;
        this.specialEv += enemy.specialEv;

        return this.addXP((int) ((a * t * b * l) / (7 * Math.max(participants, 1))));
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

        if (ball == PokeBall.master_ball_num)
            return 4;

        int maxN = 151;

        if (ball == PokeBall.normal_ball_num)
            maxN = 256;
        else if (ball == PokeBall.great_ball_num)
            maxN = 201;

        int n = (int) (Math.random() * maxN);

        if (this.status == asleep || this.status == frozen)
            n -= 25;
        else if (this.status == paralyzed || this.status == burned || this.status == poisoned)
            n -= 12;

        int b = ball == PokeBall.great_ball_num ? 8 : 12;
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
            return this.getName() + "'s " + getStageName(stage) + " fell!";
        else if (prev - this.stages[stage] >= 2)
            return this.getName() + "'s " + getStageName(stage) + " greatly fell!";
        else if (prev - this.stages[stage] == -1)
            return this.getName() + "'s " + getStageName(stage) + " rose!";
        else if (prev - this.stages[stage] <= -2)
            return this.getName() + "'s " + getStageName(stage) + " greatly rose!";
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
        else if (stat == stage_special)
            return "SPECIAL";
        else if (stat == stage_evasion)
            return "EVASION";
        else if (stat == stage_accuracy)
            return "ACCURACY";
        else
            return "INVALID STAT";
    }

    public static String getEffectMessage(int stat)
    {
        if (stat == asleep)
            return " fell asleep!";
        else if (stat == paralyzed)
            return "'s paralyzed! It may not attack!";
        else if (stat == poisoned)
            return " was poisoned!";
        else if (stat == burned)
            return " was burned!";
        else if (stat == frozen)
            return " was frozen solid!";
        else if (stat == confused)
            return " became confused!";
        else
            return " invalid effect!";
    }

    public double getStageMultiplier(int stat)
    {
        if (stat != stage_evasion)
            return stage_effectiveness[stages[stat] + 6];
        else
            return stage_effectiveness[6 - stages[stat]];
    }

    public double getEffectSpeedMultiplier()
    {
        if (this.status == Monster.paralyzed)
            return 0.25;

        return 1;
    }

    public void setOwner(UserWrapper user)
    {
        this.owner = user.id;

        if (this.isWild)
        {
            this.originalTrainer = user.id;
            this.isWild = false;
        }
    }

    @Override
    public Monster getNextMonster()
    {
        if (this.hp > 0)
            return this;
        else
            return null;
    }

    @Override
    public void queryMove(ChannelWrapper cw, Monster m)
    {
        ArrayList<Move> validMoves = new ArrayList<>();

        for (int i = 0; i < this.moves.length; i++)
        {
            Move move = this.moves[i];

            if (move != null && this.movePP[i] > 0)
                validMoves.add(move);
        }

        if (validMoves.size() > 0)
            cw.currentBattle.actionDecided(validMoves.get((int) (Math.random() * validMoves.size())));
        else
        {
            cw.queue(m.getName() + " has no moves left!");
            cw.currentBattle.actionDecided(Move.struggle);
        }
    }

    public String getStatusText()
    {
        if (this.hp <= 0)
            return "FNT";
        else if (this.status == asleep)
            return "SLP";
        else if (this.status == paralyzed)
            return "PAR";
        else if (this.status == poisoned)
            return "PSN";
        else if (this.status == burned)
            return "BRN";
        else if (this.status == frozen)
            return "FRZ";
        else
            return null;
    }

    @Override
    public String toString()
    {
        StringBuilder s = new StringBuilder();
        s.append(this.species.id).append(",");
        s.append(this.name).append(",");
        s.append(this.hp).append(",");
        s.append(this.level).append(",");
        s.append(this.status).append(",");

        for (Move m: this.moves)
        {
            if (m == null)
                s.append(",");
            else
                s.append(m.name.toLowerCase()).append(",");
        }

        for (int m: this.movePP)
        {
            s.append(m).append(",");
        }

        s.append(this.originalTrainer).append(",");
        s.append(this.xp).append(",");
        s.append(this.hpEv).append(",");
        s.append(this.attackEv).append(",");
        s.append(this.defenseEv).append(",");
        s.append(this.speedEv).append(",");
        s.append(this.specialEv).append(",");
        s.append(this.hpIv).append(",");
        s.append(this.attackIv).append(",");
        s.append(this.defenseIv).append(",");
        s.append(this.speedIv).append(",");
        s.append(this.specialIv).append(",");
        s.append(this.maxHp).append(",");
        s.append(this.attack).append(",");
        s.append(this.defense).append(",");
        s.append(this.speed).append(",");
        s.append(this.special);

        return s.toString();
    }

    public void fromString(String str)
    {
        String[] s = str.split(",");
        this.species = Species.by_num.get(Integer.parseInt(s[0]));
        this.name = s[1];
        this.hp = Integer.parseInt(s[2]);
        this.level = Integer.parseInt(s[3]);
        this.status = Integer.parseInt(s[4]);

        for (int i = 5; i < 9; i++)
        {
            this.moves[i - 5] = MoveList.by_name.get(s[i]);
        }

        for (int i = 9; i < 13; i++)
        {
            this.movePP[i - 9] = Integer.parseInt(s[i]);
        }

        this.originalTrainer = Long.parseLong(s[13]);
        this.xp = Integer.parseInt(s[14]);

        this.hpEv = Integer.parseInt(s[15]);
        this.attackEv = Integer.parseInt(s[16]);
        this.defenseEv = Integer.parseInt(s[17]);
        this.speedEv = Integer.parseInt(s[18]);
        this.specialEv = Integer.parseInt(s[19]);

        this.hpIv = Integer.parseInt(s[20]);
        this.attackIv = Integer.parseInt(s[21]);
        this.defenseIv = Integer.parseInt(s[22]);
        this.speedIv = Integer.parseInt(s[23]);
        this.specialIv = Integer.parseInt(s[24]);

        this.maxHp = Integer.parseInt(s[25]);
        this.attack = Integer.parseInt(s[26]);
        this.defense = Integer.parseInt(s[27]);
        this.speed = Integer.parseInt(s[28]);
        this.special = Integer.parseInt(s[29]);
    }

    public String getDisplayString(int index)
    {
        StringBuilder s = new StringBuilder();

        if (this.hp <= 0)
            s.append(index + 1).append(". ").append(this.getName()).append(" FNT\n");
        else if (this.status != 0)
            s.append(index + 1).append(". ").append(this.getName()).append(" ").append(this.getStatusText()).append(" (HP ").append(this.hp).append("/").append(this.maxHp).append(")\n");
        else
            s.append(index + 1).append(". ").append(this.getName()).append(" L").append(this.level).append(" (HP ").append(this.hp).append("/").append(this.maxHp).append(")\n");

        return s.toString();
    }

    public void printStats(ChannelWrapper cw)
    {
        StringBuilder s = new StringBuilder();
        s.append("No. ").append(String.format("%03d\n", this.species.id));
        s.append(this.getName()).append(" (").append(this.species.name.toUpperCase()).append(")\n");
        s.append("```L").append(this.level).append("\nHP: ").append(this.hp).append("/").append(this.maxHp).append("\nSTATUS: ");

        if (this.status == 0)
            s.append("OK");
        else
            s.append(this.getStatusText());

        s.append("\n``````ATTACK: ").append(this.attack).append("\nDEFENSE: ").append(this.defense).append("\nSPEED: ").append(this.speed).append("\nSPECIAL: ").append(this.special);
        s.append("``````TYPE1: ").append(Type.getTypeString(this.species.type1)).append("\n");
        if (this.species.type2 != this.species.type1)
            s.append("TYPE2: ").append(Type.getTypeString(this.species.type2)).append("\n");
        s.append("OT: ").append(Bot.client.getUserById(Snowflake.of(this.originalTrainer)).getData().block().username()).append("\n");
        s.append("``````EXP POINTS: ").append(this.xp).append("\n");

        if (this.level < 100)
            s.append("LEVEL UP: ").append(Monster.getLevelXP(this.species.xpGain, this.level + 1) - this.xp).append(" to L").append(this.level + 1);

        s.append("``````MOVES:\n");

        for (int i = 0; i < this.moves.length; i++)
        {
            if (this.moves[i] != null)
                s.append(i + 1).append(". ").append(this.moves[i].name).append(" (PP ").append(this.movePP[i]).append("/").append(this.moves[i].maxPP).append(")\n");
            else
                s.append("-");
        }

        s.append("```");

        cw.messageChannel.createMessage(new Consumer<MessageCreateSpec>()
        {
            @Override
            public void accept(MessageCreateSpec messageCreateSpec)
            {
                messageCreateSpec.addFile("/icon.png", getClass().getResourceAsStream(("/front/" + species.name.toUpperCase())
                        .replace("♂", "m").replace("♀", "f").replace("'", "").toLowerCase() + ".png"));
            }
        }).block();

        cw.messageChannel.createMessage(s.toString()).block();
    }

    public boolean isShiny()
    {
        return this.speedIv == 10 && this.defenseIv == 10 && this.specialIv == 10 &&
                (this.attackIv == 2 || this.attackIv == 3 || this.attackIv == 6 || this.attackIv == 7 ||
                        this.attackIv == 10 || this.attackIv == 11 || this.attackIv == 14 || this.attackIv == 15);
    }

    public String getName()
    {
        if (this.isShiny())
            return this.name + "\u2728";
        else
            return this.name;
    }
}
