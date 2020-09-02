package jigglybot.monster;

public class Type
{
    public static final int normal = 0;
    public static final int fighting = 1;
    public static final int flying = 2;
    public static final int poison = 3;
    public static final int ground = 4;
    public static final int rock = 5;
    public static final int bug = 6;
    public static final int ghost = 7;
    public static final int fire = 8;
    public static final int water = 9;
    public static final int grass = 10;
    public static final int electric = 11;
    public static final int psychic = 12;
    public static final int ice = 13;
    public static final int dragon = 14;

    public static final double[][] table = new double[][]
            {
                    {1.0, 1.0, 1.0, 1.0, 1.0, 0.5, 1.0, 0.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0},
                    {2.0, 1.0, 0.5, 0.5, 1.0, 2.0, 0.5, 0.0, 1.0, 1.0, 1.0, 1.0, 0.5, 2.0, 1.0},
                    {1.0, 2.0, 1.0, 1.0, 1.0, 0.5, 2.0, 1.0, 1.0, 1.0, 2.0, 0.5, 1.0, 1.0, 1.0},
                    {1.0, 1.0, 1.0, 0.5, 0.5, 0.5, 2.0, 0.5, 1.0, 1.0, 2.0, 1.0, 1.0, 1.0, 1.0},
                    {1.0, 1.0, 0.0, 2.0, 1.0, 2.0, 0.5, 1.0, 2.0, 1.0, 0.5, 2.0, 1.0, 1.0, 1.0},
                    {1.0, 0.5, 2.0, 1.0, 0.5, 1.0, 2.0, 1.0, 2.0, 1.0, 1.0, 1.0, 1.0, 2.0, 1.0},
                    {1.0, 0.5, 0.5, 2.0, 1.0, 1.0, 1.0, 0.5, 0.5, 1.0, 2.0, 1.0, 2.0, 1.0, 1.0},
                    {0.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 2.0, 1.0, 1.0, 1.0, 1.0, 0.0, 1.0, 1.0},
                    {1.0, 1.0, 1.0, 1.0, 1.0, 0.5, 2.0, 1.0, 0.5, 0.5, 2.0, 1.0, 1.0, 2.0, 0.5},
                    {1.0, 1.0, 1.0, 1.0, 2.0, 2.0, 1.0, 1.0, 2.0, 0.5, 0.5, 1.0, 1.0, 1.0, 0.5},
                    {1.0, 1.0, 0.5, 0.5, 2.0, 2.0, 0.5, 1.0, 0.5, 2.0, 0.5, 1.0, 1.0, 1.0, 0.5},
                    {1.0, 1.0, 2.0, 1.0, 0.0, 1.0, 1.0, 1.0, 1.0, 2.0, 0.5, 0.5, 1.0, 1.0, 0.5},
                    {1.0, 2.0, 1.0, 2.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 0.5, 1.0, 1.0},
                    {1.0, 1.0, 2.0, 1.0, 2.0, 1.0, 1.0, 1.0, 1.0, 0.5, 2.0, 1.0, 1.0, 0.5, 2.0},
                    {1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 2.0}
            };

    public static double getEffectiveness(int moveType, int t1, int t2)
    {
        if (t1 == t2)
            return table[moveType][t1];
        else
            return table[moveType][t1] * table[moveType][t2];
    }

    public static String getEffectiveMessage(int moveType, int t1, int t2, String enemy)
    {
        return getEffectiveMessage(getEffectiveness(moveType, t1, t2), enemy);
    }

    public static String getEffectiveMessage(double amount, String enemy)
    {
        if (amount <= 0)
            return "It's doesn't affect enemy " + enemy + "!";
        else if (amount < 1.0)
            return "It's not very effective...";
        else if (amount == 1.0)
            return null;
        else
            return "It's super effective!";
    }

    public static String getTypeString(int type)
    {
        if (type == 0)
            return "NORMAL";
        else if (type == 1)
            return "FIGHTING";
        else if (type == 2)
            return "FLYING";
        else if (type == 3)
            return "POISON";
        else if (type == 4)
            return "GROUND";
        else if (type == 5)
            return "ROCK";
        else if (type == 6)
            return "BUG";
        else if (type == 7)
            return "GHOST";
        else if (type == 8)
            return "FIRE";
        else if (type == 9)
            return "WATER";
        else if (type == 10)
            return "GRASS";
        else if (type == 11)
            return "ELECTRIC";
        else if (type == 12)
            return "PSYCHIC";
        else if (type == 13)
            return "ICE";
        else if (type == 14)
            return "DRAGON";
        else
            return "BIRD";
    }
}
