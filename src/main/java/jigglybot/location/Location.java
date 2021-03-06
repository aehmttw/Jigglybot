package jigglybot.location;

import jigglybot.monster.Monster;
import jigglybot.monster.Species;

import java.util.ArrayList;

public class Location
{
    public Location[] neighbors;
    public ArrayList<SpawnEntry> spawnEntries = new ArrayList<>();

    public int id;
    public final String name;

    public boolean hasHeal = false;

    public static int currentID = 0;

    public static final Location pallet_town = new Location("PALLET TOWN");
    public static final Location route_1 = new Location("ROUTE 1");
    public static final Location viridian_city = new Location("VIRIDIAN CITY");
    public static final Location route_2 = new Location("ROUTE 2");
    public static final Location viridian_forest = new Location("VIRIDIAN FOREST");
    public static final Location upper_route_2 = new Location("UPPER ROUTE 2");
    public static final Location digglett_cave = new Location("DIGLETT's CAVE");
    public static final Location pewter_city = new Location("PEWTER CITY");
    public static final Location route_3 = new Location("ROUTE 3");
    public static final Location mt_moon = new Location("MT.MOON");
    public static final Location route_4 = new Location("ROUTE 4");
    public static final Location cerulean_city = new Location("CERULEAN CITY");
    public static final Location route_24 = new Location("ROUTE 24");
    public static final Location route_25 = new Location("ROUTE 25");
    public static final Location sea_cottage = new Location("SEA COTTAGE");
    public static final Location route_5 = new Location("ROUTE 5");
    public static final Location route_6 = new Location("ROUTE 6");
    public static final Location vermilion_city = new Location("VERMILION CITY");
    public static final Location ss_anne = new Location("S.S.ANNE");
    public static final Location route_9 = new Location("ROUTE 9");
    public static final Location rock_tunnel = new Location("ROCK TUNNEL");
    public static final Location route_10 = new Location("ROUTE 10");
    public static final Location lavender_town = new Location("LAVENDER TOWN");
    public static final Location pokemon_tower = new Location("POKéMON TOWER");
    public static final Location route_8 = new Location("ROUTE 8");
    public static final Location route_7 = new Location("ROUTE 7");
    public static final Location celadon_city = new Location("CELADON CITY");
    public static final Location saffron_city = new Location("SAFFRON CITY");
    public static final Location route_11 = new Location("ROUTE 11");
    public static final Location route_12 = new Location("ROUTE 12");
    public static final Location route_13 = new Location("ROUTE 13");
    public static final Location route_14 = new Location("ROUTE 14");
    public static final Location route_15 = new Location("ROUTE 15");
    public static final Location route_16 = new Location("ROUTE 16");
    public static final Location route_17 = new Location("ROUTE 17");
    public static final Location route_18 = new Location("ROUTE 18");
    public static final Location fuchsia_city = new Location("FUCHSIA CITY");
    public static final Location safari_zone = new Location("SAFARI ZONE");
    public static final Location sea_route_19 = new Location("SEA ROUTE 19");
    public static final Location seafoam_islands = new Location("SEAFOAM ISLANDS");
    public static final Location sea_route_20 = new Location("SEA ROUTE 20");
    public static final Location cinnabar_island = new Location("CINNABAR ISLAND");
    public static final Location sea_route_21 = new Location("SEA ROUTE 21");
    public static final Location route_22 = new Location("ROUTE 22");
    public static final Location route_23 = new Location("ROUTE 23");
    public static final Location victory_road = new Location("VICTORY ROAD");
    public static final Location indigo_plateau = new Location("INDIGO PLATEAU");
    public static final Location power_plant = new Location("POWER PLANT");

    public static void setup()
    {
        pallet_town.neighbors = new Location[]{route_1, sea_route_21};
        pallet_town.hasHeal = true;

        route_1.neighbors = new Location[]{viridian_city, pallet_town};
        route_1.spawnEntries.add(new SpawnEntry("pidgey", new int[]{2, 3, 4, 5}, 100));
        route_1.spawnEntries.add(new SpawnEntry("pidgey", new int[]{2, 3, 4, 5, 6, 7}, 70));
        route_1.spawnEntries.add(new SpawnEntry("rattata", new int[]{2, 3, 4}, 130));

        viridian_city.neighbors = new Location[]{route_22, route_2, route_1};
        viridian_city.hasHeal = true;

        route_2.neighbors = new Location[]{viridian_city, viridian_forest, upper_route_2};
        route_2.spawnEntries.add(new SpawnEntry("caterpie", new int[]{3, 4, 5}, 15));
        route_2.spawnEntries.add(new SpawnEntry("weedle", new int[]{3, 4, 5}, 15));
        route_2.spawnEntries.add(new SpawnEntry("pidgey", new int[]{3, 4, 5}, 90));
        route_2.spawnEntries.add(new SpawnEntry("pidgey", new int[]{3, 5, 7}, 30));
        route_2.spawnEntries.add(new SpawnEntry("rattata", new int[]{2, 3, 4, 5}, 80));
        route_2.spawnEntries.add(new SpawnEntry("rattata", new int[]{3, 4}, 40));
        route_2.spawnEntries.add(new SpawnEntry("nidoran♀", new int[]{4, 6}, 15));
        route_2.spawnEntries.add(new SpawnEntry("nidoran♂", new int[]{4, 6}, 15));

        viridian_forest.neighbors = new Location[]{pewter_city, route_2};

        upper_route_2.neighbors = new Location[]{digglett_cave, route_2};
        upper_route_2.spawnEntries = route_2.spawnEntries;

        digglett_cave.neighbors = new Location[]{upper_route_2, route_11};

        pewter_city.neighbors = new Location[]{viridian_forest, route_3};
        pewter_city.hasHeal = true;

        route_3.neighbors = new Location[]{pewter_city, mt_moon};
        route_3.hasHeal = true;
        route_3.spawnEntries.add(new SpawnEntry("pidgey", new int[]{6, 7, 8}, 90));
        route_3.spawnEntries.add(new SpawnEntry("rattata", new int[]{10, 12}, 15));
        route_3.spawnEntries.add(new SpawnEntry("spearow", new int[]{5, 6, 7, 8}, 90));
        route_3.spawnEntries.add(new SpawnEntry("spearow", new int[]{8, 9, 10, 11, 12}, 55));
        route_3.spawnEntries.add(new SpawnEntry("sandshrew", new int[]{8, 10}, 15));
        route_3.spawnEntries.add(new SpawnEntry("jigglypuff", new int[]{3, 5, 7}, 20));
        route_3.spawnEntries.add(new SpawnEntry("mankey", new int[]{9}, 15));

        mt_moon.neighbors = new Location[]{route_3, route_4};
        mt_moon.spawnEntries.add(new SpawnEntry("sandshrew", new int[]{12}, 4));
        mt_moon.spawnEntries.add(new SpawnEntry("clefairy", new int[]{8}, 2));
        mt_moon.spawnEntries.add(new SpawnEntry("clefairy", new int[]{11}, 1));
        mt_moon.spawnEntries.add(new SpawnEntry("zubat", new int[]{6, 7, 8, 9, 10, 11}, 158));
        mt_moon.spawnEntries.add(new SpawnEntry("zubat", new int[]{6, 7, 8, 9, 10, 11}, 75));
        mt_moon.spawnEntries.add(new SpawnEntry("paras", new int[]{8}, 10));
        mt_moon.spawnEntries.add(new SpawnEntry("geodude", new int[]{8, 10}, 30));
        mt_moon.spawnEntries.add(new SpawnEntry("geodude", new int[]{10}, 20));
        mt_moon.spawnEntries.add(new SpawnEntry("clefairy", new int[]{9}, 8));
        mt_moon.spawnEntries.add(new SpawnEntry("clefairy", new int[]{10, 12}, 5));
        mt_moon.spawnEntries.add(new SpawnEntry("zubat", new int[]{7, 8, 9, 10, 11}, 120));
        mt_moon.spawnEntries.add(new SpawnEntry("zubat", new int[]{8, 9, 10, 11}, 65));
        mt_moon.spawnEntries.add(new SpawnEntry("paras", new int[]{10}, 20));
        mt_moon.spawnEntries.add(new SpawnEntry("paras", new int[]{9, 11}, 10));
        mt_moon.spawnEntries.add(new SpawnEntry("geodude", new int[]{7, 8, 9}, 52));
        mt_moon.spawnEntries.add(new SpawnEntry("geodude", new int[]{10, 11}, 20));
        mt_moon.spawnEntries.add(new SpawnEntry("clefairy", new int[]{10, 12}, 12));
        mt_moon.spawnEntries.add(new SpawnEntry("clefairy", new int[]{9, 11, 13}, 10));
        mt_moon.spawnEntries.add(new SpawnEntry("zubat", new int[]{9, 10, 11, 12}, 98));
        mt_moon.spawnEntries.add(new SpawnEntry("zubat", new int[]{10, 11, 12, 13}, 60));
        mt_moon.spawnEntries.add(new SpawnEntry("paras", new int[]{10, 12}, 30));
        mt_moon.spawnEntries.add(new SpawnEntry("paras", new int[]{13}, 15));
        mt_moon.spawnEntries.add(new SpawnEntry("geodude", new int[]{9, 10}, 60));
        mt_moon.spawnEntries.add(new SpawnEntry("geodude", new int[]{11}, 15));

        route_4.neighbors = new Location[]{mt_moon, cerulean_city};
        route_4.spawnEntries.add(new SpawnEntry("rattata", new int[]{8, 10, 12}, 80));
        route_4.spawnEntries.add(new SpawnEntry("rattata", new int[]{10, 12}, 15));
        route_4.spawnEntries.add(new SpawnEntry("spearow", new int[]{8, 10, 12}, 70));
        route_4.spawnEntries.add(new SpawnEntry("spearow", new int[]{8, 9, 10, 11, 12}, 55));
        route_4.spawnEntries.add(new SpawnEntry("ekans", new int[]{6, 8, 10, 12}, 25));
        route_4.spawnEntries.add(new SpawnEntry("sandshrew", new int[]{6, 8, 10, 12}, 25));
        route_4.spawnEntries.add(new SpawnEntry("sandshrew", new int[]{8, 10}, 15));
        route_4.spawnEntries.add(new SpawnEntry("mankey", new int[]{9}, 15));

        route_24.neighbors = new Location[]{cerulean_city, route_25};

        cerulean_city.neighbors = new Location[]{route_4, route_24, route_9, route_5};
        cerulean_city.hasHeal = true;

        route_25.neighbors = new Location[]{route_24, sea_cottage};
        sea_cottage.neighbors = new Location[]{route_25};
        route_5.neighbors = new Location[]{cerulean_city, saffron_city};
        route_6.neighbors = new Location[]{saffron_city, vermilion_city};

        vermilion_city.neighbors = new Location[]{route_6, route_11, ss_anne};
        vermilion_city.hasHeal = true;

        ss_anne.neighbors = new Location[]{vermilion_city};

        route_9.neighbors = new Location[]{cerulean_city, rock_tunnel};
        route_9.hasHeal = true;

        rock_tunnel.neighbors = new Location[]{route_9, route_10};
        route_10.neighbors = new Location[]{rock_tunnel, power_plant, lavender_town};
        power_plant.neighbors = new Location[]{route_10};

        lavender_town.neighbors = new Location[]{route_8, route_10, route_12, pokemon_tower};
        lavender_town.hasHeal = true;

        pokemon_tower.neighbors = new Location[]{lavender_town};
        route_8.neighbors = new Location[]{saffron_city, lavender_town};
        route_7.neighbors = new Location[]{celadon_city, saffron_city};

        celadon_city.neighbors = new Location[]{route_16, route_7};
        celadon_city.hasHeal = true;

        saffron_city.neighbors = new Location[]{route_7, route_5, route_8, route_6};
        saffron_city.hasHeal = true;

        route_11.neighbors = new Location[]{vermilion_city, digglett_cave, route_12};
        route_12.neighbors = new Location[]{route_11, lavender_town, route_13};
        route_13.neighbors = new Location[]{route_12, route_14};
        route_14.neighbors = new Location[]{route_15, route_13};
        route_15.neighbors = new Location[]{fuchsia_city, route_14};
        route_16.neighbors = new Location[]{celadon_city, route_17};
        route_17.neighbors = new Location[]{route_16, route_18};
        route_18.neighbors = new Location[]{route_17, fuchsia_city};

        fuchsia_city.neighbors = new Location[]{route_18, safari_zone, route_15, sea_route_19};
        fuchsia_city.hasHeal = true;

        safari_zone.neighbors = new Location[]{fuchsia_city};
        sea_route_19.neighbors = new Location[]{fuchsia_city, seafoam_islands};
        seafoam_islands.neighbors = new Location[]{sea_route_19, sea_route_20};
        sea_route_20.neighbors = new Location[]{cinnabar_island, seafoam_islands};

        cinnabar_island.neighbors = new Location[]{sea_route_20, sea_route_21};
        cinnabar_island.hasHeal = true;

        sea_route_21.neighbors = new Location[]{pallet_town, cinnabar_island};
        route_22.neighbors = new Location[]{route_23, viridian_city};
        route_23.neighbors = new Location[]{victory_road, route_22};
        victory_road.neighbors = new Location[]{indigo_plateau, route_23};

        indigo_plateau.neighbors = new Location[]{victory_road};
        indigo_plateau.hasHeal = true;
    }

    public Monster spawn()
    {
        int total = 0;
        int current = 0;

        for (SpawnEntry s: this.spawnEntries)
            total += s.weight;

        int num = (int) (Math.random() * total);

        SpawnEntry spawnEntry = null;
        for (SpawnEntry s: this.spawnEntries)
        {
            current += s.weight;
            if (current > num)
            {
                spawnEntry = s;
                break;
            }
        }

        Monster m;

        if (Math.random() < 0.0001)
            m = new Monster(Species.by_name.get("mew"), (int) (Math.random() * 100 + 1));
        else
        {
            int level = spawnEntry.levels[(int) (Math.random() * spawnEntry.levels.length)];
            m = new Monster(spawnEntry.species, level);
        }

        return m;
    }

    public Location(String name)
    {
        this.id = currentID;
        currentID++;

        this.name = name;
    }
}
