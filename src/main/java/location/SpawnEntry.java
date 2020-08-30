package location;

import discord4j.core.spec.Spec;
import monster.Species;

public class SpawnEntry
{
    public final Species species;
    public final int[] levels;
    public final int weight;

    public SpawnEntry(String species, int[] levels, int weight)
    {
        this.species = Species.speciesByName.get(species);
        this.levels = levels;
        this.weight = weight;

        if (this.species == null)
            throw new RuntimeException("Invalid spawn species!");
    }
}
