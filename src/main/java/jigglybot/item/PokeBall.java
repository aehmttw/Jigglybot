package jigglybot.item;

import jigglybot.ChannelWrapper;
import jigglybot.ICanBattle;
import jigglybot.UserWrapper;
import jigglybot.battle.Battle;
import jigglybot.dialog.DialogAddDexEntry;

public class PokeBall extends BattleItemType
{
    public static final int normal_ball_num = 0;
    public static final int great_ball_num = 1;
    public static final int ultra_ball_num = 2;
    public static final int safari_ball_num = 3;
    public static final int master_ball_num = 4;

    public static final PokeBall pokeBall = new PokeBall("POKé BALL", normal_ball_num);
    public static final PokeBall greatBall = new PokeBall("GREAT BALL", great_ball_num);
    public static final PokeBall ultraBall = new PokeBall("ULTRA BALL", ultra_ball_num);
    public static final PokeBall safariBall = new PokeBall("SAFARI BALL", safari_ball_num);
    public static final PokeBall masterBall = new PokeBall("MASTER BALL", master_ball_num);

    public final int type;

    private PokeBall(String name, int type)
    {
        super(name);
        this.type = type;
    }

    @Override
    public void use(Battle b, ICanBattle u, ChannelWrapper cw)
    {
        int i = b.p2Mon.getCaptureResult(this.type);

        if (i == -1)
        {
            cw.queue("The trainer blocked the BALL!");
            cw.queue("Don't be a thief!");
        }
        else if (i == 0)
            cw.queue("You missed the POKéMON!");
        else if (i == 1)
        {
            cw.queue("The BALL shakes...");
            cw.queue("Poof! The BALL broke!");
            cw.queue("Darn! The POKéMON broke free!");
        }
        else if (i == 2)
        {
            cw.queue("The BALL shakes...");
            cw.queue("The BALL shakes again...");
            cw.queue("Poof! The BALL broke!");
            cw.queue("Aww! It appeared to be caught!");
        }
        else if (i == 3)
        {
            cw.queue("The BALL shakes...");
            cw.queue("The BALL shakes again...");
            cw.queue("The BALL shakes violently...");
            cw.queue("Poof! The BALL broke!");
            cw.queue("Shoot! It was so close too!");
        }
        else
        {
            cw.queue("The BALL shakes...");
            cw.queue("The BALL shakes again...");
            cw.queue("The BALL shakes violently...");
            cw.queue("All right! " + b.p2Mon.getName() + " was caught!");

            b.p2Mon.setOwner((UserWrapper) u);

            cw.currentDialog = new DialogAddDexEntry(cw, b.player1, b.p2Mon);
            cw.currentDialog.execute();

            b.p2Mon = null;
            b.ended = true;
        }
    }
}
