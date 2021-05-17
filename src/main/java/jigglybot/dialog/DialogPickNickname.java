package jigglybot.dialog;

import jigglybot.ChannelWrapper;
import jigglybot.Dialog;
import jigglybot.UserWrapper;
import jigglybot.battle.action.Move;
import jigglybot.monster.Monster;

public class DialogPickNickname extends Dialog
{
    public Monster monster;
    public int location = 0;

    public DialogPickNickname(ChannelWrapper cw, UserWrapper uw, Monster monster)
    {
        super(cw, uw);
        this.monster = monster;
    }

    @Override
    public void execute()
    {
        this.channelWrapper.currentDialog = this;

        this.location = 0;
        this.showDialog();
    }

    @Override
    public void input(String s)
    {
        if (this.location == 0)
        {
            if (s.toLowerCase().equals("yes"))
            {
                this.location = 1;
                this.showDialog();
            }
            else if (s.toLowerCase().equals("no"))
            {
                this.location = 2;
                this.showDialog();
            }
            else
            {
                this.channelWrapper.messageChannel.createMessage("Please input YES or NO!").block();
            }
        }
        else if (this.location == 1)
        {
            if (s.toLowerCase().equals("cancel"))
            {
                this.location = 2;
                this.showDialog();
            }
            else if (s.toLowerCase().startsWith("nick"))
            {
                if (s.contains(" "))
                {
                    String sec = s.substring(s.indexOf(" ") + 1);

                    if (sec.length() > 20)
                        this.channelWrapper.messageChannel.createMessage("That nickname is too long!").block();
                    else
                    {
                        for (int i = 0; i < s.length(); i++)
                        {
                            if (!("abcdefghijklmnopqrstuvwxyz():;[]-?!/., ".contains(s.toLowerCase().charAt(i) + "")))
                            {
                                this.channelWrapper.messageChannel.createMessage("Invalid nickname!").block();
                                return;
                            }
                        }

                        this.monster.name = sec.toUpperCase();
                        this.location = 2;
                        this.showDialog();
                    }
                }
                else
                    this.channelWrapper.messageChannel.createMessage("Please specify nickname!").block();
            }
            else
            {
                this.channelWrapper.messageChannel.createMessage("Please choose NICK or CANCEL!").block();
            }
        }
        else if (this.location == 2)
        {
            if (s.toLowerCase().equals("yes"))
            {
                this.location = 5;
                this.showDialog();
            }
            else if (s.toLowerCase().equals("no"))
            {
                this.location = 0;
                this.showDialog();
            }
            else
            {
                this.channelWrapper.messageChannel.createMessage("Please input YES or NO!").block();
            }
        }
    }

    public void showDialog()
    {
        if (this.location == 0)
        {
            this.channelWrapper.queue("Do you want to give a nickname to " + this.monster.getName() + "? (YES/NO)");
            this.channelWrapper.advance();
        }
        else if (this.location == 1)
        {
            this.channelWrapper.queue("Please choose NICK for " + this.monster.getName() + " or CANCEL");
            this.channelWrapper.advance();
        }
        else if (this.location == 2)
        {
            boolean found = false;

            for (int x = 0; x < this.user.squad.length; x++)
            {
                if ((this.user).squad[x] == null)
                {
                    found = true;
                    this.user.squad[x] = this.monster;
                    break;
                }
            }

            if (!found)
            {
                this.user.storage.add(this.monster);
                this.channelWrapper.queue(this.monster.getName() + " was transferred to STORAGE PC!");
            }
            else
                this.channelWrapper.queue(this.monster.getName() + " was added to your POKéMON!");

            this.channelWrapper.advance();
            this.channelWrapper.currentDialog = null;
            this.user.save();
        }
    }
}
