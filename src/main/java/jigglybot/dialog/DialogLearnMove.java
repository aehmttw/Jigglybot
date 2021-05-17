package jigglybot.dialog;

import jigglybot.ChannelWrapper;
import jigglybot.Dialog;
import jigglybot.UserWrapper;
import jigglybot.battle.action.Move;
import jigglybot.monster.Monster;

public class DialogLearnMove extends Dialog
{
    public Monster monster;
    public Move move;

    public int location = 0;
    public int forgetMove = -1;

    public DialogLearnMove(ChannelWrapper cw, UserWrapper uw, Monster monster, Move newMove)
    {
        super(cw, uw);
        this.monster = monster;
        this.move = newMove;
    }

    @Override
    public void execute()
    {
        this.channelWrapper.currentDialog = this;

        int firstFree = -1;

        for (int i = 0; i < this.monster.moves.length; i++)
        {
            if (this.monster.moves[i] == null)
            {
                firstFree = i;
                break;
            }
        }

        if (firstFree != -1)
        {
            this.location = 4;
            this.forgetMove = firstFree;
            this.showDialog();
        }
        else
        {
            this.location = 0;
            this.showDialog();
        }
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
            else if (s.toLowerCase().equals("forget"))
            {
                this.channelWrapper.messageChannel.createMessage("Please specify MOVE!").block();
            }
            else if (s.toLowerCase().startsWith("forget "))
            {
                String t = s.substring(s.indexOf(" ") + 1);

                try
                {
                    int i = Integer.parseInt(t);

                    if (i >= 1 && i <= 4)
                        this.forgetMove = i - 1;
                }
                catch (Exception ignored)
                {
                    for (int i = 0; i < this.monster.moves.length; i++)
                    {
                        if (t.toLowerCase().equals(this.monster.moves[i].name.toLowerCase()))
                        {
                            this.forgetMove = i;
                        }
                    }
                }

                if (this.forgetMove != -1)
                {
                    this.location = 3;
                    this.showDialog();
                }
                else
                {
                    this.channelWrapper.messageChannel.createMessage("Invalid move!").block();
                }
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
            this.channelWrapper.queue(this.monster.getName() + " is trying to learn " + this.move.name + "!");
            this.channelWrapper.queue("But, " + this.monster.getName() + " can't learn more than 4 moves!");
            this.channelWrapper.queue("Delete an older move to make room for " + this.move.name + "? (YES/NO)");
            this.channelWrapper.advance();
        }
        else if (this.location == 1)
        {
            StringBuilder s = new StringBuilder("Which move should be forgotten?\n```Select move to FORGET\n");

            for (int i = 0; i < this.monster.moves.length; i++)
            {
                if (this.monster.moves[i] != null)
                    s.append(i + 1).append(". ").append(this.monster.moves[i].name).append("\n");
            }

            s.append("CANCEL learning ").append(this.move.name);

            s.append("```");
            this.channelWrapper.queue(s.toString());
            this.channelWrapper.advance();
        }
        else if (this.location == 2)
        {
            this.channelWrapper.queue("Abandon learning " + this.move.name + "? (YES/NO)");
            this.channelWrapper.advance();
        }
        else if (this.location == 3)
        {
            this.channelWrapper.queue("1, 2, and... Poof!");
            this.channelWrapper.queue(this.monster.getName() + " forgot " + this.monster.moves[forgetMove].name + "!");
            this.channelWrapper.queue("And...");

            this.location = 4;
            this.showDialog();
        }
        else if (this.location == 4)
        {
            this.channelWrapper.queue(this.monster.getName() + " learned " + this.move.name + "!");
            this.monster.moves[this.forgetMove] = this.move;
            this.monster.movePP[this.forgetMove] = this.move.maxPP;
            this.channelWrapper.advance();

            this.channelWrapper.currentDialog = null;
        }
        else if (this.location == 5)
        {
            this.channelWrapper.queue(this.monster.getName() + " did not learn " + this.move.name + "!");
            this.channelWrapper.advance();

            this.channelWrapper.currentDialog = null;
        }
    }
}
