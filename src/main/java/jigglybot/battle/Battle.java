package jigglybot.battle;

import jigglybot.ChannelWrapper;
import jigglybot.ICanBattle;
import jigglybot.UserWrapper;
import jigglybot.battle.action.IAction;
import jigglybot.battle.action.Move;
import jigglybot.battle.action.UseItem;
import jigglybot.item.Item;
import jigglybot.item.PokeBall;
import jigglybot.monster.Monster;

import java.util.ArrayList;

public class Battle
{
    public ChannelWrapper channel;

    public UserWrapper player1;
    public ICanBattle player2;

    public Monster p1Mon;
    public Monster p2Mon;

    public boolean started = false;
    public boolean joinable = true;

    public IAction p1Move;
    public IAction p2Move;

    public boolean stage = false;

    public boolean ended = false;

    public ArrayList<Monster> p1Participants = new ArrayList<>();
    public ArrayList<Monster> p2Participants = new ArrayList<>();

    public Battle(ChannelWrapper channel, ICanBattle challenger)
    {
        this.channel = channel;
        this.player2 = challenger;
        this.p2Mon = challenger.getNextMonster();
        this.p2Participants.add(p2Mon);
    }

    public Battle(ChannelWrapper channel, UserWrapper p1, UserWrapper p2)
    {
        this.channel = channel;
        this.player1 = p1;
        this.player2 = p2;
        this.joinable = false;

        p1.inBattle = true;
        p2.inBattle = true;
    }

    public void join(UserWrapper player)
    {
        if (!player.initialized)
            channel.messageChannel.createMessage("You need to START first!").block();
        else if (this.p1Mon != null)
            channel.messageChannel.createMessage("You can JOIN when a POKéMON faints!").block();
        else if (player.inBattle)
            channel.messageChannel.createMessage("You're already in a battle!");
        else
        {
            boolean canJoin = false;

            for (int i = 0; i < player.squad.length; i++)
            {
                if (player.squad[i] != null && player.squad[i].hp > 0)
                {
                    canJoin = true;
                    break;
                }
            }

            if (!canJoin)
                channel.messageChannel.createMessage("All your POKéMON have fainted! You need to HEAL!").block();
            else
            {
                this.started = true;
                this.channel.messages.clear();
                this.channel.activeMessage = -1;

                if (this.p2Mon == null)
                    this.p2Mon = this.player2.getNextMonster();

                this.p1Participants.clear();
                this.player1 = player;
                player.inBattle = true;
                this.p1Mon = player.getNextMonster();
                this.p1Participants.add(p1Mon);

                channel.queue(player.name + " joined the battle!");

                if (p2Mon == null)
                    channel.queue("Go " + p1Mon.name + "!");
                else
                {
                    double frac = p2Mon.hp * 1.0 / p2Mon.maxHp;

                    if (frac >= 0.7)
                        channel.queue("Go! " + p1Mon.name + "!");
                    else if (frac >= 0.4)
                        channel.queue("Do it! " + p1Mon.name + "!");
                    else if (frac >= 0.1)
                        channel.queue("Get'm! " + p1Mon.name + "!");
                    else
                        channel.queue("The enemy's weak! Get'm! " + p1Mon.name + "!");
                }

                this.turnStart();
            }
        }
    }

    public void inputFight(UserWrapper user, String in)
    {
        if (user != this.player1 && user != this.player2)
            this.channel.messageChannel.createMessage("You must JOIN the battle!").block();
        else if (!this.channel.messages.isEmpty())
            this.channel.messageChannel.createMessage("Can't do this now!").block();
        else if (user == this.player1)
        {
            int move = -1;

            for (int i = 0; i < this.p1Mon.moves.length; i++)
            {
                if (this.p1Mon.moves[i].name != null && (in.equalsIgnoreCase(this.p1Mon.moves[i].name) || in.equals("" + (i + 1))))
                {
                    move = i;
                    break;
                }
            }

            if (move != -1)
                this.actionDecided(this.p1Mon.moves[move]);
            else
                this.channel.messageChannel.createMessage("Invalid move!").block();
        }
    }

    public void inputCapture(UserWrapper user)
    {
        if (user != this.player1)
            this.channel.messageChannel.createMessage("You must JOIN the battle!").block();
        else if (!this.channel.messages.isEmpty())
            this.channel.messageChannel.createMessage("Can't do this now!").block();
        else
        {
            this.actionDecided(new UseItem(new Item(PokeBall.pokeBall)));
        }
    }

    public void turnStart()
    {
        player1.queryMove(this.channel, p1Mon);
    }

    public void actionDecided(IAction a)
    {
        if (!this.stage)
        {
            if (a instanceof Move)
                this.p1Move = a;
            else if (a instanceof UseItem)
                ((UseItem) a).item.useForBattle(this, this.player1, this.channel);

            if (this.ended)
            {
                channel.currentBattle = null;
                this.player1.inBattle = false;

                if (this.player2 instanceof UserWrapper)
                    ((UserWrapper) this.player2).inBattle = false;

                return;
            }

            this.stage = true;
            this.player2.queryMove(this.channel, this.p2Mon);
        }
        else
        {
            if (a instanceof Move)
                this.p2Move = a;
            else if (a instanceof UseItem)
                ((UseItem) a).item.useForBattle(this, this.player2, this.channel);

            if (this.ended)
            {
                channel.currentBattle = null;
                this.player1.inBattle = false;

                if (this.player2 instanceof UserWrapper)
                    ((UserWrapper) this.player2).inBattle = false;

                return;
            }

            this.stage = false;

            if (this.p1Mon != null && this.p2Mon != null)
                this.fight(this.p1Move, this.p2Move, this.channel);

            this.p1Move = Move.none;
            this.p2Move = Move.none;
        }
    }

    public void fight(IAction a1, IAction a2, ChannelWrapper cw)
    {
        Move p1 = Move.none;
        Move p2 = Move.none;

        if (a1 instanceof Move)
            p1 = (Move) a1;

        if (a2 instanceof Move)
            p2 = (Move) a2;

        boolean p1first = Math.random() < 0.5;

        if (p1.priority > p2.priority)
            p1first = true;
        else if (p1.priority < p2.priority)
            p1first = false;
        else
        {
            if (p1Mon.speed * p1Mon.getStageMultiplier(Monster.stage_speed) > p2Mon.speed * p2Mon.getStageMultiplier(Monster.stage_speed))
                p1first = true;
            else if (p1Mon.speed * p1Mon.getStageMultiplier(Monster.stage_speed) < p2Mon.speed * p2Mon.getStageMultiplier(Monster.stage_speed))
                p1first = false;
        }

        if (p1first)
        {
            p1.execute(this.p1Mon, this.p2Mon, cw, this.p1Participants);

            if (this.p2Mon.hp > 0)
                p2.execute(this.p2Mon, this.p1Mon, cw, this.p2Participants);
        }
        else
        {
            p2.execute(this.p2Mon, this.p1Mon, cw, this.p2Participants);

            if (this.p1Mon.hp > 0)
                p1.execute(this.p1Mon, this.p2Mon, cw, this.p1Participants);
        }

        boolean next = true;

        if (this.p1Mon.hp <= 0)
        {
            this.p1Participants.remove(this.p1Mon);
            this.p1Mon = null;
            this.p2Participants.clear();

            next = false;

            if (!this.joinable)
            {
                ((UserWrapper)this.player2).inBattle = false;

                if (this.player1.getNextMonster() != null)
                {
                    next = true;
                    this.p1Mon = this.player1.getNextMonster();
                }
                else
                    this.channel.queue(((UserWrapper)this.player2).name + " defeated " + this.player1.name + "!");
            }
        }

        if (this.p2Mon.hp <= 0)
        {
            this.p2Participants.remove(this.p2Mon);
            this.p2Mon = null;
            this.p1Participants.clear();

            next = false;

            if (!this.joinable)
            {
                ((UserWrapper)this.player2).inBattle = false;

                if (this.player2.getNextMonster() != null)
                {
                    next = true;
                    this.p2Mon = this.player2.getNextMonster();
                    this.p2Participants.add(p2Mon);
                }
                else
                    this.channel.queue(this.player1.name + " defeated " + ((UserWrapper)this.player2).name + "!");
            }
        }

        if (next)
            this.turnStart();
        else
        {
            this.player1.inBattle = false;
            this.p1Mon = null;
            this.player1 = null;

            if (this.player2.getNextMonster() != null)
            {
                this.p2Mon = this.player2.getNextMonster();
                this.p2Participants.add(p2Mon);
                this.channel.queue("Whose POKéMON will JOIN next?");
            }
            else
            {
                this.channel.queue("Battle ended!");
                this.channel.currentBattle = null;
            }

            this.channel.advance();
        }

    }
}
