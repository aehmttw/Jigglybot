package jigglybot.battle.action;

import jigglybot.ChannelWrapper;
import jigglybot.battle.Battle;
import jigglybot.monster.Monster;

public class Run implements IAction
{
    public void execute(Battle b, Monster run, Monster other, ChannelWrapper cw, int attempts)
    {
        if ((int)(Math.random() * 256) < (run.speed * run.getStageMultiplier(Monster.stage_speed)) * 32 /
                (other.speed * other.getStageMultiplier(Monster.stage_speed) * other.speed) + 30 * attempts)
        {
            cw.queue("Got away safely!");
            cw.queue("Whose POKéMON will JOIN next?");

            b.player1.inBattle = false;
            b.p1Mon = null;
            b.p1Damage = 0;
            b.player1 = null;
            cw.advance();
        }
        else
            cw.queue("Can't escape!");
    }
}
