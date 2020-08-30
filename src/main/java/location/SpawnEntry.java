package location;

import monster.Species;

public class SpawnEntry
{
    public final Species species;
    public final int[] levels;
    public final int weight;

    public SpawnEntry(String species, int[] levels, int weight)
    {
        this.species = Species.by_name.get(species);
        this.levels = levels;
        this.weight = weight;

        if (this.species == null)
            throw new RuntimeException("Invalid spawn species!");
    }
}
