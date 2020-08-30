import discord4j.core.object.entity.Member;
import location.Location;

import java.util.HashMap;

public class UserWrapper
{
    public static HashMap<Long, UserWrapper> wrappers = new HashMap<>();

    public Location location = Location.pallet_town;
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
