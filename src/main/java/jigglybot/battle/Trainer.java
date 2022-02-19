package jigglybot.battle;

import discord4j.core.spec.Spec;
import jigglybot.ChannelWrapper;
import jigglybot.ICanBattle;
import jigglybot.battle.action.Move;
import jigglybot.monster.Monster;
import jigglybot.monster.Species;

import java.util.ArrayList;

public class Trainer implements ICanBattle
{
    public Monster[] monsters = new Monster[6];
    public int currentMonster = 0;

    public String name = "TRAINER";

    public Trainer()
    {
        int numMons = (int) (Math.random() * 6 + 1);

        for (int i = 0; i < numMons; i++)
        {
            Monster m = new Monster(Species.by_num.get((int) (Math.random() * Species.by_num.keySet().size())), (int) (Math.random() * 15 + 5));
            m.isWild = false;
            this.monsters[i] = m;
        }
    }

    @Override
    public Monster getNextMonster()
    {
        if (this.monsters[currentMonster].hp <= 0)
            this.currentMonster++;

        if (this.currentMonster >= this.monsters.length)
            return null;

        return this.monsters[currentMonster];
    }

    @Override
    public void queryMove(ChannelWrapper cw, Monster m, Monster enemy)
    {
        ArrayList<Move> validMoves = new ArrayList<>();

        for (int i = 0; i < this.monsters[this.currentMonster].moves.length; i++)
        {
            Move move = this.monsters[this.currentMonster].moves[i];

            if (move != null && this.monsters[this.currentMonster].movePP[i] > 0)
                validMoves.add(move);
        }

        if (validMoves.size() > 0)
            cw.currentBattle.actionDecided(validMoves.get((int) (Math.random() * validMoves.size())));
        else
        {
            cw.queue(m.getName() + " has no moves left!");
            cw.currentBattle.actionDecided(Move.struggle);
        }
    }

    @Override
    public String getName()
    {
        return this.name;
    }
}
