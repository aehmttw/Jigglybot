package jigglybot;

public abstract class Dialog
{
    public ChannelWrapper channelWrapper;
    public UserWrapper user;

    public Dialog(ChannelWrapper cw, UserWrapper uw)
    {
        this.channelWrapper = cw;
        this.user = uw;
    }

    public abstract void execute();

    public abstract void input(String s);
}
