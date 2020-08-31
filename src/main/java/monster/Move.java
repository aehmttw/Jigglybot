package monster;

import java.util.function.BiConsumer;

public class Move
{
    public final int type;
    public final int power;
    public final int accuracy;
    public final int maxPP;

    public Move(int type, int power, int accuracy, int maxPP)
    {
        this.type = type;
        this.power = power;
        this.accuracy = accuracy;
        this.maxPP = maxPP;
    }

    public BiConsumer<Monster, Monster> behavior = (attacker, defender) ->
    {

    };
}
