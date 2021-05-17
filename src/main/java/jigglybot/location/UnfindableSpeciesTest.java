package jigglybot.location;

import jigglybot.monster.Species;

import java.util.ArrayList;

public class UnfindableSpeciesTest
{
    public static void main(String[] args)
    {
        Species.setup();
        Location.setup();

        for (Species s: Species.by_num.values())
        {
            ArrayList<Location> spawns = new ArrayList<>();
            for (Location l: Location.allLocations)
            {
                for (SpawnEntry e: l.spawnEntries)
                {
                    if (e.species == s)
                    {
                        spawns.add(l);
                        break;
                    }
                }
            }

            if (spawns.isEmpty())
                System.out.println(s.name);
        }
    }
}
