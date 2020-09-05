package jigglybot.item;

import jigglybot.ChannelWrapper;
import jigglybot.ICanBattle;
import jigglybot.UserWrapper;
import jigglybot.battle.Battle;

public abstract class BattleItemType extends ItemType
{
    public BattleItemType(String name)
    {
        super(name);
    }

    public abstract void use(Battle b, ICanBattle u, ChannelWrapper cw);
}
