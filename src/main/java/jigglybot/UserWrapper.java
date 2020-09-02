package jigglybot;

import discord4j.core.object.entity.Member;

import java.util.HashMap;

public class UserWrapper
{
    public static HashMap<Long, UserWrapper> wrappers = new HashMap<>();

    public long id;

    public UserWrapper(long id)
    {
        this.id = id;
    }

    public static UserWrapper get(Member m)
    {
        long l = m.getId().asLong();
        UserWrapper w = wrappers.get(l);

        if (w == null)
        {
            w = new UserWrapper(l);
            wrappers.put(l, w);
        }

        return w;
    }
}
