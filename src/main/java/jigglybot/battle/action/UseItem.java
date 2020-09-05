package jigglybot.battle.action;

import jigglybot.item.Item;

public class UseItem implements IAction
{
    public Item item;

    public UseItem(Item i)
    {
        this.item = i;
    }
}
