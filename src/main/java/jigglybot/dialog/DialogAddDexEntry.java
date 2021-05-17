package jigglybot.dialog;

import jigglybot.ChannelWrapper;
import jigglybot.Dialog;
import jigglybot.UserWrapper;
import jigglybot.monster.Monster;

public class DialogAddDexEntry extends Dialog
{
    public Monster monster;

    public DialogAddDexEntry(ChannelWrapper cw, UserWrapper uw, Monster mon)
    {
        super(cw, uw);
        this.monster = mon;
    }

    @Override
    public void execute()
    {
        if (user.dex[monster.species.id] < 2)
        {
            this.user.dex[monster.species.id] = 2;
            this.channelWrapper.queue("New POKéDEX data will be added for " + monster.species.name.toUpperCase() + "!");
            this.monster.species.printDexEntry(this.channelWrapper, user, true);
        }

        this.channelWrapper.currentDialog = new DialogPickNickname(this.channelWrapper, this.user, this.monster);
        this.channelWrapper.currentDialog.execute();
    }

    @Override
    public void input(String s)
    {

    }
}
