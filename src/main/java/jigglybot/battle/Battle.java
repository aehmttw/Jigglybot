package jigglybot.battle;

import jigglybot.ChannelWrapper;
import jigglybot.ICanBattle;
import jigglybot.UserWrapper;
import jigglybot.battle.action.*;
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

    public int p1Damage = 0;
    public int p2Damage = 0;

    public int p1Runs = 0;
    public int p2Runs = 0;

    public boolean started = false;
    public boolean joinable = true;

    public IAction p1Move;
    public IAction p2Move;

    public boolean p2sTurn = false;

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
                {
                    this.p2Mon = this.player2.getNextMonster();
                    this.p2Damage = 0;
                }

                this.p1Participants.clear();
                this.player1 = player;
                player.inBattle = true;
                this.p1Mon = player.getNextMonster();
                this.p1Damage = 0;

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
        else if (in == null)
            this.channel.messageChannel.createMessage("Please specify ATTACK!").block();
        else
        {
            Monster m = this.p1Mon;

            if (user == this.player2)
                m = this.p2Mon;

            int move = -1;

            for (int i = 0; i < m.moves.length; i++)
            {
                if (m.moves[i] != null && (in.equalsIgnoreCase(m.moves[i].name) || in.equals("" + (i + 1))))
                {
                    move = i;
                    break;
                }
            }

            if (move != -1)
            {
                if (m.movePP[move] > 0)
                {
                    m.movePP[move]--;
                    this.actionDecided(m.moves[move]);
                }
                else
                {
                    for (int i = 0; i < m.moves.length; i++)
                    {
                        if (m.moves[i] != null && m.movePP[i] > 0)
                        {
                            this.channel.messageChannel.createMessage("No PP left for this move!").block();
                            return;
                        }
                    }

                    this.channel.queue(m.name + " has no moves left!");
                    this.actionDecided(Move.struggle);
                }
            }
            else
                this.channel.messageChannel.createMessage("Invalid move!").block();
        }
    }

    public void inputRun(UserWrapper user)
    {
        if (user != this.player1 && user != this.player2)
            this.channel.messageChannel.createMessage("You must JOIN the battle!").block();
        else if (!this.channel.messages.isEmpty())
            this.channel.messageChannel.createMessage("Can't do this now!").block();
        else if (!this.joinable) //TODO add trainer
            this.channel.messageChannel.createMessage("No! There's no running from a trainer battle!").block();
        else
            this.actionDecided(new Run());
    }

    public void inputCapture(UserWrapper user)
    {
        if (user != this.player1)
            this.channel.messageChannel.createMessage("You must JOIN the battle!").block();
        else if (!this.channel.messages.isEmpty())
            this.channel.messageChannel.createMessage("Can't do this now!").block();
        else
            this.actionDecided(new UseItem(new Item(PokeBall.pokeBall)));
    }

    public void inputSwitch(UserWrapper user, String in)
    {
        if (user != this.player1 && user != this.player2)
            this.channel.messageChannel.createMessage("You must JOIN the battle!").block();
        else if (!this.channel.messages.isEmpty())
            this.channel.messageChannel.createMessage("Can't do this now!").block();
        else if ((user == this.player1 && this.p2sTurn) || (user == this.player2 && !this.p2sTurn))
            this.channel.messageChannel.createMessage("Can't do this now!").block();
        else if (in == null)
            this.channel.messageChannel.createMessage("Please specify POKéMON NO. or NAME!").block();
        else
        {
            int index = 0;

            try
            {
                index = Integer.parseInt(in) - 1;
            }
            catch (Exception e)
            {
                for (int i = 0; i < user.squad.length; i++)
                {
                    if (user.squad[i] != null && user.squad[i].name.equalsIgnoreCase(in))
                    {
                        index = i;
                        break;
                    }
                }
            }

            if (user.squad[index] == null)
                this.channel.messageChannel.createMessage("Please specify POKéMON NO. or NAME!").block();
            else if (user.squad[index].hp <= 0)
                this.channel.messageChannel.createMessage("There's no will to fight!").block();
            else if (user.squad[index] == this.p1Mon || user.squad[index] == this.p2Mon)
                this.channel.messageChannel.createMessage(user.squad[index].name + " is already out!").block();
            else
                this.actionDecided(new SwitchMonster(user.squad[index]));
        }
    }

    public void turnStart()
    {
        player1.queryMove(this.channel, p1Mon);
    }

    public void actionDecided(IAction a)
    {
        if (!this.p2sTurn)
        {
            if (a instanceof Move)
                this.p1Move = a;
            else if (a instanceof UseItem)
                ((UseItem) a).item.useForBattle(this, this.player1, this.channel);
            else if (a instanceof SwitchMonster)
                this.p1Mon = ((SwitchMonster) a).execute(this, this.p1Mon, this.p1Damage, this.p2Mon.maxHp, this.p2Mon.hp * 1.0 / this.p2Mon.maxHp);
            else if (a instanceof Run)
            {
                this.p1Runs++;
                ((Run) a).execute(this, this.p1Mon, this.p2Mon, this.channel, this.p1Runs);
            }

            if (!this.p1Participants.contains(this.p1Mon))
                this.p1Participants.add(this.p1Mon);

            if (this.ended)
            {
                this.channel.currentBattle = null;
                this.player1.inBattle = false;

                if (this.player2 instanceof UserWrapper)
                    ((UserWrapper) this.player2).inBattle = false;

                return;
            }

            this.p2sTurn = true;
            this.player2.queryMove(this.channel, this.p2Mon);
        }
        else
        {
            if (a instanceof Move)
                this.p2Move = a;
            else if (a instanceof UseItem)
                ((UseItem) a).item.useForBattle(this, this.player2, this.channel);
            else if (a instanceof SwitchMonster)
                this.p2Mon = ((SwitchMonster) a).execute(this, this.p2Mon, this.p2Damage, this.p1Mon.maxHp, this.p1Mon.hp * 1.0 / this.p1Mon.maxHp);
            else if (a instanceof Run)
            {
                this.p2Runs++;
                ((Run) a).execute(this, this.p2Mon, this.p1Mon, this.channel, this.p2Runs);
            }

            if (!this.p2Participants.contains(this.p2Mon))
                this.p2Participants.add(this.p2Mon);

            if (this.ended)
            {
                this.channel.currentBattle = null;
                this.player1.inBattle = false;

                if (this.player2 instanceof UserWrapper)
                    ((UserWrapper) this.player2).inBattle = false;

                return;
            }

            this.p2sTurn = false;

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
        {
            p1 = (Move) a1;
            this.p1Runs = 0;
        }

        if (a2 instanceof Move)
        {
            p2 = (Move) a2;
            this.p2Runs = 0;
        }

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

        int p1health = p1Mon.hp;
        int p2health = p2Mon.hp;

        if (p1first)
        {
            p1.execute(this.p1Mon, this.p2Mon, cw, this.p1Participants);

            if (!this.testFaint())
            {
                p2.execute(this.p2Mon, this.p1Mon, cw, this.p2Participants);
                this.testFaint();
            }
        }
        else
        {
            p2.execute(this.p2Mon, this.p1Mon, cw, this.p2Participants);

            if (!this.testFaint())
            {
                p1.execute(this.p1Mon, this.p2Mon, cw, this.p1Participants);
                this.testFaint();
            }
        }

        p2Damage += p1Mon.hp - p1health;
        p1Damage += p2Mon.hp - p2health;

        boolean next = true;

        if (this.p1Mon.hp <= 0)
        {
            this.p1Participants.remove(this.p1Mon);
            this.p1Mon = null;
            this.p1Damage = 0;
            this.p2Participants.clear();

            next = false;

            if (!this.joinable)
            {
                ((UserWrapper)this.player2).inBattle = false;

                if (this.player1.getNextMonster() != null)
                {
                    next = true;
                    this.p1Mon = this.player1.getNextMonster();
                    this.p1Damage = 0;
                }
                else
                    this.channel.queue(((UserWrapper)this.player2).name + " defeated " + this.player1.name + "!");
            }
        }

        if (this.p2Mon.hp <= 0)
        {
            this.p2Participants.remove(this.p2Mon);
            this.p2Mon = null;
            this.p2Damage = 0;
            this.p1Participants.clear();

            next = false;

            if (!this.joinable)
            {
                ((UserWrapper)this.player2).inBattle = false;

                if (this.player2.getNextMonster() != null)
                {
                    next = true;
                    this.p2Mon = this.player2.getNextMonster();
                    this.p2Damage = 0;
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
            this.p1Damage = 0;
            this.player1 = null;

            if (this.player2.getNextMonster() != null)
            {
                this.p2Mon = this.player2.getNextMonster();
                this.p2Damage = 0;
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

    public boolean testFaint()
    {
        boolean a = this.testFaint(this.p1Mon, this.p2Participants);
        boolean b = this.testFaint(this.p2Mon, this.p1Participants);

        return a || b;
    }

    public boolean testFaint(Monster defender, ArrayList<Monster> participants)
    {
        if (defender.hp <= 0)
        {
            defender.hp = 0;

            this.channel.queue(defender.name + " fainted!");

            for (Monster m: participants)
            {
                int xp = m.xp;
                boolean lvlup = m.defeatedEnemy(defender, participants.size());

                this.channel.queue(m.name + " gained " + (m.xp - xp) + " EXP. Points!");

                if (lvlup)
                    this.channel.queue(m.name + " grew to level " + m.level + "!");
            }

            return true;
        }

        return false;
    }
}
