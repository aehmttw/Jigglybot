package jigglybot;

public class DialogEraseAllData extends Dialog
{
    public DialogEraseAllData(ChannelWrapper cw, UserWrapper uw)
    {
        super(cw, uw);
    }

    @Override
    public void execute()
    {
        channelWrapper.messageChannel.createMessage("Are you sure you want to erase all your data and start fresh? This cannot be undone! (ERASE/CANCEL)").block();
    }

    @Override
    public void input(String s)
    {
        if (s.toLowerCase().equals("erase"))
        {
            UserWrapper.wrappers.remove(user.id);
            user.getFile().delete();
            channelWrapper.messageChannel.createMessage("Successfully erased all your data!").block();
            channelWrapper.currentDialog = null;
        }
        else
        {
            channelWrapper.messageChannel.createMessage("Your data was not erased!").block();
            channelWrapper.currentDialog = null;
        }
    }
}
