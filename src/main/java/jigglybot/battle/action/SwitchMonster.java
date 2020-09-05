package jigglybot.battle.action;

public class SwitchMonster implements IAction
{
    public final int monster;

    public SwitchMonster(int monster)
    {
        this.monster = monster;
    }
}
