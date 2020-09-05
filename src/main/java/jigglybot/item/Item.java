package jigglybot.item;

import jigglybot.ChannelWrapper;
import jigglybot.ICanBattle;
import jigglybot.UserWrapper;
import jigglybot.battle.Battle;

public class Item
{
    public ItemType type;
    public int amount;
    public final boolean forBattle;

    public Item(ItemType type)
    {
        this.forBattle = (type instanceof BattleItemType);
        this.type = type;
    }

    public void useForBattle(Battle b, ICanBattle u, ChannelWrapper cw)
    {
        if (u instanceof UserWrapper) //TODO add trainer
            cw.queue(((UserWrapper) u).name + " used " + this.type.name + "!");

        ((BattleItemType)this.type).use(b, u, cw);
        this.amount--;
    }
}
