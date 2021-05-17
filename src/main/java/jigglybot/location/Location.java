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

    public boolean hasCenter = false;

    public static ArrayList<Location> allLocations = new ArrayList<>();
    public static int currentID = 0;

    public static final Location pallet_town = new Location("PALLET TOWN");
    public static final Location route_1 = new Location("ROUTE 1");
    public static final Location viridian_city = new Location("VIRIDIAN CITY");
    public static final Location route_2 = new Location("ROUTE 2");
    public static final Location viridian_forest = new Location("VIRIDIAN FOREST");
    public static final Location upper_route_2 = new Location("UPPER ROUTE 2");
    public static final Location diglett_cave = new Location("DIGLETT's CAVE");
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
    public static final Location pokemon_mansion = new Location("POKéMON MANSION");
    public static final Location sea_route_21 = new Location("SEA ROUTE 21");
    public static final Location route_22 = new Location("ROUTE 22");
    public static final Location route_23 = new Location("ROUTE 23");
    public static final Location victory_road = new Location("VICTORY ROAD");
    public static final Location indigo_plateau = new Location("INDIGO PLATEAU");
    public static final Location power_plant = new Location("POWER PLANT");
    public static final Location cerulean_cave = new Location("CERULEAN CAVE");

    public static void setup()
    {
        pallet_town.neighbors = new Location[]{route_1, sea_route_21};
        pallet_town.hasCenter = true;

        route_1.neighbors = new Location[]{viridian_city, pallet_town};
        route_1.spawnEntries.add(new SpawnEntry("pidgey", new int[]{2, 3, 4, 5}, 100));
        route_1.spawnEntries.add(new SpawnEntry("pidgey", new int[]{2, 3, 4, 5, 6, 7}, 70));
        route_1.spawnEntries.add(new SpawnEntry("rattata", new int[]{2, 3, 4}, 130));

        viridian_city.neighbors = new Location[]{route_22, route_2, route_1};
        viridian_city.hasCenter = true;

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
        viridian_forest.spawnEntries.add(new SpawnEntry("caterpie", new int[]{3}, 5));
        viridian_forest.spawnEntries.add(new SpawnEntry("caterpie", new int[]{3, 4, 5}, 45));
        viridian_forest.spawnEntries.add(new SpawnEntry("caterpie", new int[]{3, 4, 5, 6}, 55));
        viridian_forest.spawnEntries.add(new SpawnEntry("metapod", new int[]{4}, 5));
        viridian_forest.spawnEntries.add(new SpawnEntry("metapod", new int[]{4, 5, 6}, 40));
        viridian_forest.spawnEntries.add(new SpawnEntry("metapod", new int[]{4, 6}, 20));
        viridian_forest.spawnEntries.add(new SpawnEntry("weedle", new int[]{3, 4, 5}, 45));
        viridian_forest.spawnEntries.add(new SpawnEntry("weedle", new int[]{3}, 5));
        viridian_forest.spawnEntries.add(new SpawnEntry("kakuna", new int[]{4, 5, 6}, 40));
        viridian_forest.spawnEntries.add(new SpawnEntry("kakuna", new int[]{4}, 5));
        viridian_forest.spawnEntries.add(new SpawnEntry("pidgey", new int[]{4, 6, 8}, 24));
        viridian_forest.spawnEntries.add(new SpawnEntry("pidgeotto", new int[]{9}, 1));
        viridian_forest.spawnEntries.add(new SpawnEntry("pikachu", new int[]{3, 5}, 10));

        upper_route_2.neighbors = new Location[]{diglett_cave, route_2};
        upper_route_2.spawnEntries = route_2.spawnEntries;

        diglett_cave.neighbors = new Location[]{upper_route_2, route_11};
        diglett_cave.spawnEntries.add(new SpawnEntry("diglett", new int[]{15, 16, 17, 18, 19, 20, 21, 22}, 95));
        diglett_cave.spawnEntries.add(new SpawnEntry("dugtrio", new int[]{29, 31}, 5));

        pewter_city.neighbors = new Location[]{viridian_forest, route_3};
        pewter_city.hasCenter = true;

        route_3.neighbors = new Location[]{pewter_city, mt_moon};
        route_3.hasCenter = true;
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
        route_24.spawnEntries.add(new SpawnEntry("caterpie", new int[]{7}, 20));
        route_24.spawnEntries.add(new SpawnEntry("metapod", new int[]{8}, 20));
        route_24.spawnEntries.add(new SpawnEntry("weedle", new int[]{7}, 20));
        route_24.spawnEntries.add(new SpawnEntry("kakuna", new int[]{8}, 20));
        route_24.spawnEntries.add(new SpawnEntry("pidgey", new int[]{12, 13}, 40));
        route_24.spawnEntries.add(new SpawnEntry("pidgey", new int[]{13, 15, 17}, 29));
        route_24.spawnEntries.add(new SpawnEntry("pidgeotto", new int[]{17}, 1));
        route_24.spawnEntries.add(new SpawnEntry("oddish", new int[]{12, 13, 14}, 25));
        route_24.spawnEntries.add(new SpawnEntry("oddish", new int[]{12, 14}, 35));
        route_24.spawnEntries.add(new SpawnEntry("venonat", new int[]{13, 16}, 10));
        route_24.spawnEntries.add(new SpawnEntry("abra", new int[]{8, 10, 12}, 30));
        route_24.spawnEntries.add(new SpawnEntry("bellsprout", new int[]{12, 13, 14}, 25));
        route_24.spawnEntries.add(new SpawnEntry("bellsprout", new int[]{12, 14}, 25));

        cerulean_city.neighbors = new Location[]{route_4, route_24, route_9, route_5, cerulean_cave};
        cerulean_city.hasCenter = true;

        route_25.neighbors = new Location[]{route_24, sea_cottage};
        route_25.spawnEntries.add(new SpawnEntry("caterpie", new int[]{8}, 1));
        route_25.spawnEntries.add(new SpawnEntry("caterpie", new int[]{8}, 20));
        route_25.spawnEntries.add(new SpawnEntry("metapod", new int[]{7}, 4));
        route_25.spawnEntries.add(new SpawnEntry("metapod", new int[]{9}, 20));
        route_25.spawnEntries.add(new SpawnEntry("weedle", new int[]{8}, 20));
        route_25.spawnEntries.add(new SpawnEntry("weedle", new int[]{8}, 1));
        route_25.spawnEntries.add(new SpawnEntry("kakuna", new int[]{9}, 20));
        route_25.spawnEntries.add(new SpawnEntry("kakuna", new int[]{7}, 4));
        route_25.spawnEntries.add(new SpawnEntry("pidgey", new int[]{13}, 30));
        route_25.spawnEntries.add(new SpawnEntry("pidgey", new int[]{13, 15, 17}, 29));
        route_25.spawnEntries.add(new SpawnEntry("pidgeotto", new int[]{17}, 1));
        route_25.spawnEntries.add(new SpawnEntry("oddish", new int[]{12, 13, 14}, 25));
        route_25.spawnEntries.add(new SpawnEntry("oddish", new int[]{12, 14}, 35));
        route_25.spawnEntries.add(new SpawnEntry("venonat", new int[]{13, 16}, 10));
        route_25.spawnEntries.add(new SpawnEntry("abra", new int[]{10, 12}, 30));
        route_25.spawnEntries.add(new SpawnEntry("bellsprout", new int[]{12, 13, 14}, 25));
        route_25.spawnEntries.add(new SpawnEntry("bellsprout", new int[]{12, 14}, 25));

        sea_cottage.neighbors = new Location[]{route_25};

        route_5.neighbors = new Location[]{cerulean_city, saffron_city};
        route_5.spawnEntries.add(new SpawnEntry("pidgey", new int[]{13, 15, 16}, 80));
        route_5.spawnEntries.add(new SpawnEntry("pidgey", new int[]{15, 16, 17}, 45));
        route_5.spawnEntries.add(new SpawnEntry("pidgeotto", new int[]{17}, 5));
        route_5.spawnEntries.add(new SpawnEntry("rattata", new int[]{14, 16}, 25));
        route_5.spawnEntries.add(new SpawnEntry("jigglypuff", new int[]{3, 5, 7}, 10));
        route_5.spawnEntries.add(new SpawnEntry("oddish", new int[]{13, 15, 16}, 35));
        route_5.spawnEntries.add(new SpawnEntry("meowth", new int[]{10, 12, 14, 16}, 25));
        route_5.spawnEntries.add(new SpawnEntry("mankey", new int[]{10, 12, 14, 16}, 25));
        route_5.spawnEntries.add(new SpawnEntry("abra", new int[]{7}, 15));
        route_5.spawnEntries.add(new SpawnEntry("bellsprout", new int[]{13, 15, 16}, 35));

        route_6.neighbors = new Location[]{saffron_city, vermilion_city};
        route_6.spawnEntries.add(new SpawnEntry("pidgey", new int[]{13, 15, 16}, 80));
        route_6.spawnEntries.add(new SpawnEntry("pidgey", new int[]{15, 16, 17}, 40));
        route_6.spawnEntries.add(new SpawnEntry("pidgeotto", new int[]{17}, 5));
        route_6.spawnEntries.add(new SpawnEntry("rattata", new int[]{14, 16}, 30));
        route_6.spawnEntries.add(new SpawnEntry("jigglypuff", new int[]{3, 5, 7}, 10));
        route_6.spawnEntries.add(new SpawnEntry("oddish", new int[]{13, 15, 16}, 35));
        route_6.spawnEntries.add(new SpawnEntry("meowth", new int[]{10, 12, 14, 16}, 25));
        route_6.spawnEntries.add(new SpawnEntry("mankey", new int[]{10, 12, 14, 16}, 25));
        route_6.spawnEntries.add(new SpawnEntry("abra", new int[]{7}, 15));
        route_6.spawnEntries.add(new SpawnEntry("bellsprout", new int[]{13, 15, 16}, 35));

        vermilion_city.neighbors = new Location[]{route_6, route_11, ss_anne};
        vermilion_city.hasCenter = true;

        ss_anne.neighbors = new Location[]{vermilion_city};

        route_9.neighbors = new Location[]{cerulean_city, rock_tunnel};
        route_9.hasCenter = true;
        route_9.spawnEntries.add(new SpawnEntry("rattata", new int[]{14, 16, 17}, 80));
        route_9.spawnEntries.add(new SpawnEntry("rattata", new int[]{18}, 15));
        route_9.spawnEntries.add(new SpawnEntry("raticate", new int[]{20}, 4));
        route_9.spawnEntries.add(new SpawnEntry("spearow", new int[]{13, 16, 17}, 70));
        route_9.spawnEntries.add(new SpawnEntry("spearow", new int[]{17}, 10));
        route_9.spawnEntries.add(new SpawnEntry("fearow", new int[]{19}, 1));
        route_9.spawnEntries.add(new SpawnEntry("ekans", new int[]{11, 13, 15, 17}, 25));
        route_9.spawnEntries.add(new SpawnEntry("sandshrew", new int[]{11, 13, 15, 17}, 25));
        route_9.spawnEntries.add(new SpawnEntry("nidoran♀", new int[]{16, 18}, 35));
        route_9.spawnEntries.add(new SpawnEntry("nidorina", new int[]{18}, 5));
        route_9.spawnEntries.add(new SpawnEntry("nidoran♂", new int[]{16, 18}, 25));
        route_9.spawnEntries.add(new SpawnEntry("nidorino", new int[]{18}, 5));

        rock_tunnel.neighbors = new Location[]{route_9, route_10};
        rock_tunnel.spawnEntries.add(new SpawnEntry("zubat", new int[]{15, 16, 17, 18}, 110));
        rock_tunnel.spawnEntries.add(new SpawnEntry("zubat", new int[]{15, 17, 19, 21}, 55));
        rock_tunnel.spawnEntries.add(new SpawnEntry("geodude", new int[]{16, 17}, 50));
        rock_tunnel.spawnEntries.add(new SpawnEntry("geodude", new int[]{16, 18, 20}, 35));
        rock_tunnel.spawnEntries.add(new SpawnEntry("machop", new int[]{15, 17}, 30));
        rock_tunnel.spawnEntries.add(new SpawnEntry("machop", new int[]{17, 19, 21}, 10));
        rock_tunnel.spawnEntries.add(new SpawnEntry("onix", new int[]{13, 15}, 10));

        route_10.neighbors = new Location[]{rock_tunnel, power_plant, lavender_town};
        route_10.spawnEntries.add(new SpawnEntry("rattata", new int[]{18}, 15));
        route_10.spawnEntries.add(new SpawnEntry("raticate", new int[]{20}, 5));
        route_10.spawnEntries.add(new SpawnEntry("spearow", new int[]{13, 16, 17}, 60));
        route_10.spawnEntries.add(new SpawnEntry("ekans", new int[]{11, 13, 15, 17}, 25));
        route_10.spawnEntries.add(new SpawnEntry("sandshrew", new int[]{11, 13, 15, 17}, 25));
        route_10.spawnEntries.add(new SpawnEntry("nidoran♀", new int[]{17}, 10));
        route_10.spawnEntries.add(new SpawnEntry("nidoran♂", new int[]{17}, 10));
        route_10.spawnEntries.add(new SpawnEntry("machop", new int[]{16, 18}, 5));
        route_10.spawnEntries.add(new SpawnEntry("magnemite", new int[]{16, 18, 20, 22}, 55));
        route_10.spawnEntries.add(new SpawnEntry("voltorb", new int[]{14, 16, 17}, 90));

        power_plant.neighbors = new Location[]{route_10};
        power_plant.spawnEntries.add(new SpawnEntry("pikachu", new int[]{20, 24}, 50));
        power_plant.spawnEntries.add(new SpawnEntry("raichu", new int[]{33, 36}, 5));
        power_plant.spawnEntries.add(new SpawnEntry("magnemite", new int[]{21, 23}, 50));
        power_plant.spawnEntries.add(new SpawnEntry("magnemite", new int[]{30, 35}, 40));
        power_plant.spawnEntries.add(new SpawnEntry("magneton", new int[]{32, 35}, 20));
        power_plant.spawnEntries.add(new SpawnEntry("magneton", new int[]{33, 38}, 20));
        power_plant.spawnEntries.add(new SpawnEntry("grimer", new int[]{33, 37}, 15));
        power_plant.spawnEntries.add(new SpawnEntry("muk", new int[]{33, 37}, 5));
        power_plant.spawnEntries.add(new SpawnEntry("voltorb", new int[]{21, 23}, 70));
        power_plant.spawnEntries.add(new SpawnEntry("voltorb", new int[]{33, 37}, 20));
        power_plant.spawnEntries.add(new SpawnEntry("electabuzz", new int[]{33, 36}, 5));
        power_plant.spawnEntries.add(new SpawnEntry("voltorb", new int[]{40}, 18));
        power_plant.spawnEntries.add(new SpawnEntry("electrode", new int[]{43}, 6));
        power_plant.spawnEntries.add(new SpawnEntry("zapdos", new int[]{50}, 3));

        lavender_town.neighbors = new Location[]{route_8, route_10, route_12, pokemon_tower};
        lavender_town.hasCenter = true;

        pokemon_tower.neighbors = new Location[]{lavender_town};
        //todo ghosts

        route_8.neighbors = new Location[]{saffron_city, lavender_town};
        route_8.spawnEntries.add(new SpawnEntry("pidgey", new int[]{18, 20}, 70));
        route_8.spawnEntries.add(new SpawnEntry("pidgey", new int[]{20, 22}, 40));
        route_8.spawnEntries.add(new SpawnEntry("pidgeotto", new int[]{24}, 10));
        route_8.spawnEntries.add(new SpawnEntry("rattata", new int[]{20}, 15));
        route_8.spawnEntries.add(new SpawnEntry("ekans", new int[]{17, 19}, 20));
        route_8.spawnEntries.add(new SpawnEntry("sandshrew", new int[]{17, 19}, 20));
        route_8.spawnEntries.add(new SpawnEntry("vulpix", new int[]{15, 16, 17, 18}, 20));
        route_8.spawnEntries.add(new SpawnEntry("jigglypuff", new int[]{19, 24}, 10));
        route_8.spawnEntries.add(new SpawnEntry("meowth", new int[]{18, 20}, 25));
        route_8.spawnEntries.add(new SpawnEntry("mankey", new int[]{18, 20}, 25));
        route_8.spawnEntries.add(new SpawnEntry("growlithe", new int[]{15, 16, 17, 18}, 20));
        route_8.spawnEntries.add(new SpawnEntry("abra", new int[]{15, 19}, 20));
        route_8.spawnEntries.add(new SpawnEntry("kadabra", new int[]{20, 27}, 5));

        route_7.neighbors = new Location[]{celadon_city, saffron_city};
        route_7.spawnEntries.add(new SpawnEntry("pidgey", new int[]{19, 22}, 60));
        route_7.spawnEntries.add(new SpawnEntry("pidgey", new int[]{20, 22}, 40));
        route_7.spawnEntries.add(new SpawnEntry("pidgeotto", new int[]{24}, 10));
        route_7.spawnEntries.add(new SpawnEntry("rattata", new int[]{20}, 15));
        route_7.spawnEntries.add(new SpawnEntry("vulpix", new int[]{18, 20}, 10));
        route_7.spawnEntries.add(new SpawnEntry("jigglypuff", new int[]{19, 24}, 10));
        route_7.spawnEntries.add(new SpawnEntry("oddish", new int[]{19, 22}, 30));
        route_7.spawnEntries.add(new SpawnEntry("meowth", new int[]{17, 18, 19, 20}, 30));
        route_7.spawnEntries.add(new SpawnEntry("mankey", new int[]{17, 18, 19, 20}, 30));
        route_7.spawnEntries.add(new SpawnEntry("growlithe", new int[]{18, 20}, 10));
        route_7.spawnEntries.add(new SpawnEntry("abra", new int[]{15, 19, 26}, 25));
        route_7.spawnEntries.add(new SpawnEntry("bellsprout", new int[]{19, 22}, 30));

        celadon_city.neighbors = new Location[]{route_16, route_7};
        celadon_city.hasCenter = true;

        saffron_city.neighbors = new Location[]{route_7, route_5, route_8, route_6};
        saffron_city.hasCenter = true;

        route_11.neighbors = new Location[]{vermilion_city, diglett_cave, route_12};
        route_11.spawnEntries.add(new SpawnEntry("pidgey", new int[]{16, 18}, 40));
        route_11.spawnEntries.add(new SpawnEntry("pidgeotto", new int[]{18, 20}, 10));
        route_11.spawnEntries.add(new SpawnEntry("rattata", new int[]{15, 17}, 25));
        route_11.spawnEntries.add(new SpawnEntry("raticate", new int[]{17}, 1));
        route_11.spawnEntries.add(new SpawnEntry("spearow", new int[]{13, 15, 17}, 70));
        route_11.spawnEntries.add(new SpawnEntry("ekans", new int[]{12, 14, 15}, 40));
        route_11.spawnEntries.add(new SpawnEntry("sandshrew", new int[]{12, 14, 15}, 40));
        route_11.spawnEntries.add(new SpawnEntry("drowzee", new int[]{9, 11, 13, 15}, 50));
        route_11.spawnEntries.add(new SpawnEntry("drowzee", new int[]{15, 17, 19}, 24));

        route_12.neighbors = new Location[]{route_11, lavender_town, route_13};
        route_12.spawnEntries.add(new SpawnEntry("pidgey", new int[]{23, 25, 27}, 70));
        route_12.spawnEntries.add(new SpawnEntry("pidgey", new int[]{28}, 15));
        route_12.spawnEntries.add(new SpawnEntry("pidgeotto", new int[]{28}, 10));
        route_12.spawnEntries.add(new SpawnEntry("oddish", new int[]{22, 24, 26}, 40));
        route_12.spawnEntries.add(new SpawnEntry("oddish", new int[]{25, 27}, 35));
        route_12.spawnEntries.add(new SpawnEntry("gloom", new int[]{28, 30}, 5));
        route_12.spawnEntries.add(new SpawnEntry("gloom", new int[]{29}, 5));
        route_12.spawnEntries.add(new SpawnEntry("venonat", new int[]{24, 26}, 40));
        route_12.spawnEntries.add(new SpawnEntry("bellsprout", new int[]{22, 24, 26}, 40));
        route_12.spawnEntries.add(new SpawnEntry("bellsprout", new int[]{25, 27}, 25));
        route_12.spawnEntries.add(new SpawnEntry("weepinbell", new int[]{28, 30}, 5));
        route_12.spawnEntries.add(new SpawnEntry("weepinbell", new int[]{29}, 5));
        route_12.spawnEntries.add(new SpawnEntry("farfetch'd", new int[]{26, 31}, 5));

        route_13.neighbors = new Location[]{route_12, route_14};
        route_13.spawnEntries.add(new SpawnEntry("pidgey", new int[]{25, 27}, 60));
        route_13.spawnEntries.add(new SpawnEntry("pidgey", new int[]{28}, 10));
        route_13.spawnEntries.add(new SpawnEntry("pidgeotto", new int[]{28}, 15));
        route_13.spawnEntries.add(new SpawnEntry("oddish", new int[]{22, 24, 26}, 40));
        route_13.spawnEntries.add(new SpawnEntry("oddish", new int[]{25, 27}, 35));
        route_13.spawnEntries.add(new SpawnEntry("gloom", new int[]{28, 30}, 5));
        route_13.spawnEntries.add(new SpawnEntry("gloom", new int[]{29}, 5));
        route_13.spawnEntries.add(new SpawnEntry("venonat", new int[]{24, 26}, 20));
        route_13.spawnEntries.add(new SpawnEntry("bellsprout", new int[]{22, 24, 26}, 40));
        route_13.spawnEntries.add(new SpawnEntry("bellsprout", new int[]{25, 27}, 25));
        route_13.spawnEntries.add(new SpawnEntry("weepinbell", new int[]{28, 30}, 5));
        route_13.spawnEntries.add(new SpawnEntry("weepinbell", new int[]{29}, 5));
        route_13.spawnEntries.add(new SpawnEntry("farfetch'd", new int[]{26, 31}, 5));
        route_13.spawnEntries.add(new SpawnEntry("ditto", new int[]{25}, 10));

        route_14.neighbors = new Location[]{route_15, route_13};
        route_14.spawnEntries.add(new SpawnEntry("pidgey", new int[]{26}, 30));
        route_14.spawnEntries.add(new SpawnEntry("pidgeotto", new int[]{28, 30}, 10));
        route_14.spawnEntries.add(new SpawnEntry("pidgeotto", new int[]{30}, 10));
        route_14.spawnEntries.add(new SpawnEntry("oddish", new int[]{22, 24, 26}, 40));
        route_14.spawnEntries.add(new SpawnEntry("oddish", new int[]{26, 28}, 35));
        route_14.spawnEntries.add(new SpawnEntry("gloom", new int[]{30}, 5));
        route_14.spawnEntries.add(new SpawnEntry("venonat", new int[]{24, 26}, 40));
        route_14.spawnEntries.add(new SpawnEntry("venonat", new int[]{24, 27}, 19));
        route_14.spawnEntries.add(new SpawnEntry("venomoth", new int[]{30}, 1));
        route_14.spawnEntries.add(new SpawnEntry("bellsprout", new int[]{22, 24, 26}, 40));
        route_14.spawnEntries.add(new SpawnEntry("bellsprout", new int[]{26, 28}, 25));
        route_14.spawnEntries.add(new SpawnEntry("weepinbell", new int[]{30}, 5));
        route_14.spawnEntries.add(new SpawnEntry("ditto", new int[]{23}, 30));

        route_15.neighbors = new Location[]{fuchsia_city, route_14};
        route_15.spawnEntries.add(new SpawnEntry("pidgey", new int[]{23}, 30));
        route_15.spawnEntries.add(new SpawnEntry("pidgeotto", new int[]{28, 30}, 10));
        route_15.spawnEntries.add(new SpawnEntry("pidgeotto", new int[]{32}, 10));
        route_15.spawnEntries.add(new SpawnEntry("oddish", new int[]{22, 24, 26}, 40));
        route_15.spawnEntries.add(new SpawnEntry("oddish", new int[]{26, 28}, 35));
        route_15.spawnEntries.add(new SpawnEntry("gloom", new int[]{30}, 5));
        route_15.spawnEntries.add(new SpawnEntry("venonat", new int[]{26, 28}, 40));
        route_15.spawnEntries.add(new SpawnEntry("venonat", new int[]{24, 27}, 19));
        route_15.spawnEntries.add(new SpawnEntry("venomoth", new int[]{30}, 1));
        route_15.spawnEntries.add(new SpawnEntry("bellsprout", new int[]{22, 24, 26}, 40));
        route_15.spawnEntries.add(new SpawnEntry("bellsprout", new int[]{26, 28}, 25));
        route_15.spawnEntries.add(new SpawnEntry("weepinbell", new int[]{30}, 5));
        route_15.spawnEntries.add(new SpawnEntry("ditto", new int[]{26}, 30));

        route_16.neighbors = new Location[]{celadon_city, route_17};
        route_16.spawnEntries.add(new SpawnEntry("rattata", new int[]{18, 20, 22}, 60));
        route_16.spawnEntries.add(new SpawnEntry("rattata", new int[]{23, 24}, 25));
        route_16.spawnEntries.add(new SpawnEntry("raticate", new int[]{23, 25}, 10));
        route_16.spawnEntries.add(new SpawnEntry("raticate", new int[]{25, 26}, 5));
        route_16.spawnEntries.add(new SpawnEntry("spearow", new int[]{20, 22}, 80));
        route_16.spawnEntries.add(new SpawnEntry("spearow", new int[]{22, 23}, 30));
        route_16.spawnEntries.add(new SpawnEntry("fearow", new int[]{24}, 5));
        route_16.spawnEntries.add(new SpawnEntry("doduo", new int[]{18, 20, 22}, 50));
        route_16.spawnEntries.add(new SpawnEntry("doduo", new int[]{22, 24, 26}, 35));
        route_16.spawnEntries.add(new SpawnEntry("snorlax", new int[]{24}, 5));
        //todo bike

        route_17.neighbors = new Location[]{route_16, route_18};
        route_17.spawnEntries.add(new SpawnEntry("raticate", new int[]{25, 27, 29}, 60));
        route_17.spawnEntries.add(new SpawnEntry("spearow", new int[]{20, 22}, 80));
        route_17.spawnEntries.add(new SpawnEntry("fearow", new int[]{25, 27}, 10));
        route_17.spawnEntries.add(new SpawnEntry("fearow", new int[]{27, 29}, 20));
        route_17.spawnEntries.add(new SpawnEntry("ponyta", new int[]{28, 30, 32}, 24));
        route_17.spawnEntries.add(new SpawnEntry("doduo", new int[]{24, 26, 28}, 50));
        route_17.spawnEntries.add(new SpawnEntry("doduo", new int[]{26, 27, 28}, 55));
        route_17.spawnEntries.add(new SpawnEntry("dodrio", new int[]{29}, 1));
        //todo bike

        route_18.neighbors = new Location[]{route_17, fuchsia_city};

        //todo bike
        route_18.spawnEntries.add(new SpawnEntry("rattata", new int[]{23, 24}, 25));
        route_18.spawnEntries.add(new SpawnEntry("raticate", new int[]{25, 29}, 40));
        route_18.spawnEntries.add(new SpawnEntry("raticate", new int[]{25, 26}, 5));
        route_18.spawnEntries.add(new SpawnEntry("spearow", new int[]{20, 22}, 80));
        route_18.spawnEntries.add(new SpawnEntry("spearow", new int[]{22, 23}, 30));
        route_18.spawnEntries.add(new SpawnEntry("fearow", new int[]{25, 27, 29}, 30));
        route_18.spawnEntries.add(new SpawnEntry("fearow", new int[]{24}, 5));
        route_18.spawnEntries.add(new SpawnEntry("doduo", new int[]{24, 26, 28}, 50));
        route_18.spawnEntries.add(new SpawnEntry("doduo", new int[]{22, 24, 26}, 35));

        fuchsia_city.neighbors = new Location[]{route_18, safari_zone, route_15, sea_route_19};
        fuchsia_city.hasCenter = true;

        safari_zone.neighbors = new Location[]{fuchsia_city};
        //todo different catch mechanics

        sea_route_19.neighbors = new Location[]{fuchsia_city, seafoam_islands};

        seafoam_islands.neighbors = new Location[]{sea_route_19, sea_route_20};
        seafoam_islands.spawnEntries.add(new SpawnEntry("zubat", new int[]{21}, 20));
        seafoam_islands.spawnEntries.add(new SpawnEntry("zubat", new int[]{9, 18, 27, 36}, 50));
        seafoam_islands.spawnEntries.add(new SpawnEntry("golbat", new int[]{29}, 10));
        seafoam_islands.spawnEntries.add(new SpawnEntry("golbat", new int[]{27, 36}, 5));
        seafoam_islands.spawnEntries.add(new SpawnEntry("psyduck", new int[]{28}, 5));
        seafoam_islands.spawnEntries.add(new SpawnEntry("psyduck", new int[]{30}, 20));
        seafoam_islands.spawnEntries.add(new SpawnEntry("golduck", new int[]{38}, 1));
        seafoam_islands.spawnEntries.add(new SpawnEntry("slowpoke", new int[]{30}, 20));
        seafoam_islands.spawnEntries.add(new SpawnEntry("slowpoke", new int[]{28}, 5));
        seafoam_islands.spawnEntries.add(new SpawnEntry("slowpoke", new int[]{28, 30}, 15));
        seafoam_islands.spawnEntries.add(new SpawnEntry("slowbro", new int[]{38}, 1));
        seafoam_islands.spawnEntries.add(new SpawnEntry("seel", new int[]{30}, 40));
        seafoam_islands.spawnEntries.add(new SpawnEntry("shellder", new int[]{28, 30}, 19));
        seafoam_islands.spawnEntries.add(new SpawnEntry("krabby", new int[]{28, 30}, 20));
        seafoam_islands.spawnEntries.add(new SpawnEntry("krabby", new int[]{25, 27}, 30));
        seafoam_islands.spawnEntries.add(new SpawnEntry("horsea", new int[]{28, 30}, 20));
        seafoam_islands.spawnEntries.add(new SpawnEntry("staryu", new int[]{28, 30}, 19));

        seafoam_islands.spawnEntries.add(new SpawnEntry("zubat", new int[]{18, 27, 36}, 45));
        seafoam_islands.spawnEntries.add(new SpawnEntry("golbat", new int[]{27}, 10));
        seafoam_islands.spawnEntries.add(new SpawnEntry("psyduck", new int[]{28, 30}, 15));
        seafoam_islands.spawnEntries.add(new SpawnEntry("slowpoke", new int[]{28, 30}, 15));
        seafoam_islands.spawnEntries.add(new SpawnEntry("slowpoke", new int[]{29}, 10));
        seafoam_islands.spawnEntries.add(new SpawnEntry("seel", new int[]{28, 30}, 30));
        seafoam_islands.spawnEntries.add(new SpawnEntry("seel", new int[]{22, 26}, 5));
        seafoam_islands.spawnEntries.add(new SpawnEntry("dewgong", new int[]{38}, 8));
        seafoam_islands.spawnEntries.add(new SpawnEntry("shellder", new int[]{32}, 15));
        seafoam_islands.spawnEntries.add(new SpawnEntry("shellder", new int[]{30}, 20));
        seafoam_islands.spawnEntries.add(new SpawnEntry("krabby", new int[]{30, 32}, 30));
        seafoam_islands.spawnEntries.add(new SpawnEntry("krabby", new int[]{26, 28}, 25));
        seafoam_islands.spawnEntries.add(new SpawnEntry("kingler", new int[]{37}, 1));
        seafoam_islands.spawnEntries.add(new SpawnEntry("kingler", new int[]{28}, 5));
        seafoam_islands.spawnEntries.add(new SpawnEntry("horsea", new int[]{30, 32}, 30));
        seafoam_islands.spawnEntries.add(new SpawnEntry("seadra", new int[]{37}, 1));
        seafoam_islands.spawnEntries.add(new SpawnEntry("staryu", new int[]{30}, 20));
        seafoam_islands.spawnEntries.add(new SpawnEntry("staryu", new int[]{32}, 15));

        seafoam_islands.spawnEntries.add(new SpawnEntry("zubat", new int[]{27, 36}, 40));
        seafoam_islands.spawnEntries.add(new SpawnEntry("golbat", new int[]{30}, 8));
        seafoam_islands.spawnEntries.add(new SpawnEntry("golbat", new int[]{27, 36}, 15));
        seafoam_islands.spawnEntries.add(new SpawnEntry("psyduck", new int[]{30, 32}, 30));
        seafoam_islands.spawnEntries.add(new SpawnEntry("golduck", new int[]{37}, 1));
        seafoam_islands.spawnEntries.add(new SpawnEntry("slowpoke", new int[]{30, 32}, 30));
        seafoam_islands.spawnEntries.add(new SpawnEntry("slowpoke", new int[]{31}, 4));
        seafoam_islands.spawnEntries.add(new SpawnEntry("slowbro", new int[]{37}, 1));
        seafoam_islands.spawnEntries.add(new SpawnEntry("slowbro", new int[]{31}, 1));
        seafoam_islands.spawnEntries.add(new SpawnEntry("seel", new int[]{30, 32}, 70));
        seafoam_islands.spawnEntries.add(new SpawnEntry("seel", new int[]{24}, 10));
        seafoam_islands.spawnEntries.add(new SpawnEntry("shellder", new int[]{28}, 5));
        seafoam_islands.spawnEntries.add(new SpawnEntry("shellder", new int[]{30}, 10));
        seafoam_islands.spawnEntries.add(new SpawnEntry("krabby", new int[]{28, 30}, 15));
        seafoam_islands.spawnEntries.add(new SpawnEntry("krabby", new int[]{27, 29}, 20));
        seafoam_islands.spawnEntries.add(new SpawnEntry("kingler", new int[]{28}, 10));
        seafoam_islands.spawnEntries.add(new SpawnEntry("horsea", new int[]{28, 30}, 15));
        seafoam_islands.spawnEntries.add(new SpawnEntry("staryu", new int[]{30}, 10));
        seafoam_islands.spawnEntries.add(new SpawnEntry("staryu", new int[]{28}, 5));

        seafoam_islands.spawnEntries.add(new SpawnEntry("zubat", new int[]{27, 36}, 25));
        seafoam_islands.spawnEntries.add(new SpawnEntry("golbat", new int[]{27}, 25));
        seafoam_islands.spawnEntries.add(new SpawnEntry("psyduck", new int[]{31, 33}, 35));
        seafoam_islands.spawnEntries.add(new SpawnEntry("slowpoke", new int[]{31, 33}, 35));
        seafoam_islands.spawnEntries.add(new SpawnEntry("seel", new int[]{31, 33}, 60));
        seafoam_islands.spawnEntries.add(new SpawnEntry("seel", new int[]{26, 30}, 15));
        seafoam_islands.spawnEntries.add(new SpawnEntry("dewgong", new int[]{37}, 2));
        seafoam_islands.spawnEntries.add(new SpawnEntry("dewgong", new int[]{28, 32}, 5));
        seafoam_islands.spawnEntries.add(new SpawnEntry("shellder", new int[]{29, 31}, 15));
        seafoam_islands.spawnEntries.add(new SpawnEntry("krabby", new int[]{29, 31}, 15));
        seafoam_islands.spawnEntries.add(new SpawnEntry("krabby", new int[]{29, 31}, 20));
        seafoam_islands.spawnEntries.add(new SpawnEntry("kingler", new int[]{39}, 4));
        seafoam_islands.spawnEntries.add(new SpawnEntry("kingler", new int[]{30}, 10));
        seafoam_islands.spawnEntries.add(new SpawnEntry("horsea", new int[]{29, 31}, 15));
        seafoam_islands.spawnEntries.add(new SpawnEntry("seadra", new int[]{39}, 4));
        seafoam_islands.spawnEntries.add(new SpawnEntry("staryu", new int[]{29, 31}, 15));

        seafoam_islands.spawnEntries.add(new SpawnEntry("zubat", new int[]{36, 45}, 20));
        seafoam_islands.spawnEntries.add(new SpawnEntry("golbat", new int[]{32}, 2));
        seafoam_islands.spawnEntries.add(new SpawnEntry("golbat", new int[]{27, 36}, 30));
        seafoam_islands.spawnEntries.add(new SpawnEntry("psyduck", new int[]{29, 31}, 15));
        seafoam_islands.spawnEntries.add(new SpawnEntry("golduck", new int[]{39}, 4));
        seafoam_islands.spawnEntries.add(new SpawnEntry("slowpoke", new int[]{29, 31}, 15));
        seafoam_islands.spawnEntries.add(new SpawnEntry("slowbro", new int[]{39}, 4));
        seafoam_islands.spawnEntries.add(new SpawnEntry("seel", new int[]{29, 31}, 30));
        seafoam_islands.spawnEntries.add(new SpawnEntry("seel", new int[]{28, 32}, 20));
        seafoam_islands.spawnEntries.add(new SpawnEntry("dewgong", new int[]{30, 34}, 5));
        seafoam_islands.spawnEntries.add(new SpawnEntry("shellder", new int[]{31, 33}, 30));
        seafoam_islands.spawnEntries.add(new SpawnEntry("krabby", new int[]{31, 33}, 35));
        seafoam_islands.spawnEntries.add(new SpawnEntry("krabby", new int[]{30}, 15));
        seafoam_islands.spawnEntries.add(new SpawnEntry("kingler", new int[]{32}, 10));
        seafoam_islands.spawnEntries.add(new SpawnEntry("horsea", new int[]{31, 33}, 35));
        seafoam_islands.spawnEntries.add(new SpawnEntry("staryu", new int[]{31, 33}, 30));

        seafoam_islands.spawnEntries.add(new SpawnEntry("articuno", new int[]{50}, 15));

        sea_route_20.neighbors = new Location[]{cinnabar_island, seafoam_islands};

        cinnabar_island.neighbors = new Location[]{sea_route_20, sea_route_21, pokemon_mansion};
        cinnabar_island.hasCenter = true;

        pokemon_mansion.neighbors = new Location[]{cinnabar_island};
        pokemon_mansion.spawnEntries.add(new SpawnEntry("rattata", new int[]{34, 37}, 35));
        pokemon_mansion.spawnEntries.add(new SpawnEntry("raticate", new int[]{34, 37}, 25));
        pokemon_mansion.spawnEntries.add(new SpawnEntry("vulpix", new int[]{34}, 10));
        pokemon_mansion.spawnEntries.add(new SpawnEntry("growlithe", new int[]{34}, 10));
        pokemon_mansion.spawnEntries.add(new SpawnEntry("growlithe", new int[]{26, 30, 34, 38}, 20));
        pokemon_mansion.spawnEntries.add(new SpawnEntry("ponyta", new int[]{28, 30, 32, 34}, 40));
        pokemon_mansion.spawnEntries.add(new SpawnEntry("grimer", new int[]{30}, 5));
        pokemon_mansion.spawnEntries.add(new SpawnEntry("grimer", new int[]{30, 32}, 40));
        pokemon_mansion.spawnEntries.add(new SpawnEntry("grimer", new int[]{23, 26}, 20));
        pokemon_mansion.spawnEntries.add(new SpawnEntry("muk", new int[]{39}, 1));
        pokemon_mansion.spawnEntries.add(new SpawnEntry("muk", new int[]{37}, 4));
        pokemon_mansion.spawnEntries.add(new SpawnEntry("koffing", new int[]{30, 32}, 40));
        pokemon_mansion.spawnEntries.add(new SpawnEntry("koffing", new int[]{30}, 5));
        pokemon_mansion.spawnEntries.add(new SpawnEntry("weezing", new int[]{37}, 4));
        pokemon_mansion.spawnEntries.add(new SpawnEntry("weezing", new int[]{39}, 1));
        pokemon_mansion.spawnEntries.add(new SpawnEntry("rattata", new int[]{37, 40}, 35));
        pokemon_mansion.spawnEntries.add(new SpawnEntry("raticate", new int[]{37, 40}, 25));
        pokemon_mansion.spawnEntries.add(new SpawnEntry("vulpix", new int[]{32}, 25));
        pokemon_mansion.spawnEntries.add(new SpawnEntry("growlithe", new int[]{32}, 25));
        pokemon_mansion.spawnEntries.add(new SpawnEntry("ponyta", new int[]{28, 30, 32}, 25));
        pokemon_mansion.spawnEntries.add(new SpawnEntry("grimer", new int[]{30}, 5));
        pokemon_mansion.spawnEntries.add(new SpawnEntry("grimer", new int[]{30, 34}, 40));
        pokemon_mansion.spawnEntries.add(new SpawnEntry("grimer", new int[]{26, 29, 32, 35}, 35));
        pokemon_mansion.spawnEntries.add(new SpawnEntry("muk", new int[]{37}, 1));
        pokemon_mansion.spawnEntries.add(new SpawnEntry("muk", new int[]{39}, 4));
        pokemon_mansion.spawnEntries.add(new SpawnEntry("muk", new int[]{35, 38}, 5));
        pokemon_mansion.spawnEntries.add(new SpawnEntry("koffing", new int[]{30, 34}, 40));
        pokemon_mansion.spawnEntries.add(new SpawnEntry("koffing", new int[]{30}, 5));
        pokemon_mansion.spawnEntries.add(new SpawnEntry("weezing", new int[]{39}, 4));
        pokemon_mansion.spawnEntries.add(new SpawnEntry("weezing", new int[]{37}, 1));
        pokemon_mansion.spawnEntries.add(new SpawnEntry("rattata", new int[]{40, 43}, 35));
        pokemon_mansion.spawnEntries.add(new SpawnEntry("raticate", new int[]{40, 43}, 25));
        pokemon_mansion.spawnEntries.add(new SpawnEntry("vulpix", new int[]{33}, 15));
        pokemon_mansion.spawnEntries.add(new SpawnEntry("growlithe", new int[]{33}, 15));
        pokemon_mansion.spawnEntries.add(new SpawnEntry("ponyta", new int[]{32, 34, 36}, 24));
        pokemon_mansion.spawnEntries.add(new SpawnEntry("ponyta", new int[]{32, 36}, 14));
        pokemon_mansion.spawnEntries.add(new SpawnEntry("grimer", new int[]{34}, 5));
        pokemon_mansion.spawnEntries.add(new SpawnEntry("grimer", new int[]{31, 35}, 40));
        pokemon_mansion.spawnEntries.add(new SpawnEntry("grimer", new int[]{32, 35, 38}, 35));
        pokemon_mansion.spawnEntries.add(new SpawnEntry("muk", new int[]{42}, 1));
        pokemon_mansion.spawnEntries.add(new SpawnEntry("muk", new int[]{38, 40}, 15));
        pokemon_mansion.spawnEntries.add(new SpawnEntry("muk", new int[]{38, 41}, 5));
        pokemon_mansion.spawnEntries.add(new SpawnEntry("koffing", new int[]{31, 35}, 40));
        pokemon_mansion.spawnEntries.add(new SpawnEntry("koffing", new int[]{34}, 5));
        pokemon_mansion.spawnEntries.add(new SpawnEntry("weezing", new int[]{38, 40}, 15));
        pokemon_mansion.spawnEntries.add(new SpawnEntry("weezing", new int[]{42}, 1));
        pokemon_mansion.spawnEntries.add(new SpawnEntry("magmar", new int[]{34}, 10));
        pokemon_mansion.spawnEntries.add(new SpawnEntry("raticate", new int[]{37, 40, 43, 46}, 40));
        pokemon_mansion.spawnEntries.add(new SpawnEntry("vulpix", new int[]{35}, 15));
        pokemon_mansion.spawnEntries.add(new SpawnEntry("growlithe", new int[]{35}, 15));
        pokemon_mansion.spawnEntries.add(new SpawnEntry("ponyta", new int[]{32, 34}, 15));
        pokemon_mansion.spawnEntries.add(new SpawnEntry("grimer", new int[]{35}, 5));
        pokemon_mansion.spawnEntries.add(new SpawnEntry("grimer", new int[]{31, 33}, 50));
        pokemon_mansion.spawnEntries.add(new SpawnEntry("grimer", new int[]{35, 38}, 40));
        pokemon_mansion.spawnEntries.add(new SpawnEntry("muk", new int[]{42}, 1));
        pokemon_mansion.spawnEntries.add(new SpawnEntry("muk", new int[]{40}, 10));
        pokemon_mansion.spawnEntries.add(new SpawnEntry("muk", new int[]{41}, 10));
        pokemon_mansion.spawnEntries.add(new SpawnEntry("koffing", new int[]{31, 33}, 50));
        pokemon_mansion.spawnEntries.add(new SpawnEntry("koffing", new int[]{35}, 5));
        pokemon_mansion.spawnEntries.add(new SpawnEntry("weezing", new int[]{40, 42}, 14));
        pokemon_mansion.spawnEntries.add(new SpawnEntry("weezing", new int[]{42}, 1));
        pokemon_mansion.spawnEntries.add(new SpawnEntry("magmar", new int[]{38}, 4));
        pokemon_mansion.spawnEntries.add(new SpawnEntry("ditto", new int[]{12, 18, 24}, 10));

        sea_route_21.neighbors = new Location[]{pallet_town, cinnabar_island};

        route_22.neighbors = new Location[]{route_23, viridian_city};
        route_22.spawnEntries.add(new SpawnEntry("rattata", new int[]{2, 3, 4}, 90));
        route_22.spawnEntries.add(new SpawnEntry("rattata", new int[]{3}, 10));
        route_22.spawnEntries.add(new SpawnEntry("spearow", new int[]{3, 5}, 20));
        route_22.spawnEntries.add(new SpawnEntry("spearow", new int[]{2, 4, 6}, 10));
        route_22.spawnEntries.add(new SpawnEntry("nidoran♀", new int[]{3, 4}, 5));
        route_22.spawnEntries.add(new SpawnEntry("nidoran♀", new int[]{2, 3, 4}, 40));
        route_22.spawnEntries.add(new SpawnEntry("nidoran♀", new int[]{2, 4}, 25));
        route_22.spawnEntries.add(new SpawnEntry("nidoran♂", new int[]{2, 3, 4}, 40));
        route_22.spawnEntries.add(new SpawnEntry("nidoran♂", new int[]{3, 4}, 5));
        route_22.spawnEntries.add(new SpawnEntry("nidoran♂", new int[]{2, 4}, 35));
        route_22.spawnEntries.add(new SpawnEntry("mankey", new int[]{3, 5}, 20));

        route_23.neighbors = new Location[]{victory_road, route_22};
        route_23.spawnEntries.add(new SpawnEntry("spearow", new int[]{26}, 30));
        route_23.spawnEntries.add(new SpawnEntry("fearow", new int[]{38, 41, 43}, 50));
        route_23.spawnEntries.add(new SpawnEntry("fearow", new int[]{40, 45}, 15));
        route_23.spawnEntries.add(new SpawnEntry("ekans", new int[]{26}, 25));
        route_23.spawnEntries.add(new SpawnEntry("arbok", new int[]{41}, 5));
        route_23.spawnEntries.add(new SpawnEntry("sandshrew", new int[]{26}, 25));
        route_23.spawnEntries.add(new SpawnEntry("sandslash", new int[]{41}, 5));
        route_23.spawnEntries.add(new SpawnEntry("nidorina", new int[]{41, 44}, 25));
        route_23.spawnEntries.add(new SpawnEntry("nidorino", new int[]{41, 44}, 35));
        route_23.spawnEntries.add(new SpawnEntry("mankey", new int[]{36, 41}, 20));
        route_23.spawnEntries.add(new SpawnEntry("primeape", new int[]{41, 46}, 5));
        route_23.spawnEntries.add(new SpawnEntry("ditto", new int[]{33, 38, 43}, 60));

        victory_road.neighbors = new Location[]{indigo_plateau, route_23};
        victory_road.spawnEntries.add(new SpawnEntry("zubat", new int[]{22}, 30));
        victory_road.spawnEntries.add(new SpawnEntry("zubat", new int[]{39, 44}, 20));
        victory_road.spawnEntries.add(new SpawnEntry("golbat", new int[]{41}, 10));
        victory_road.spawnEntries.add(new SpawnEntry("machop", new int[]{24}, 50));
        victory_road.spawnEntries.add(new SpawnEntry("machoke", new int[]{42}, 8));
        victory_road.spawnEntries.add(new SpawnEntry("geodude", new int[]{26}, 30));
        victory_road.spawnEntries.add(new SpawnEntry("geodude", new int[]{26, 31, 36, 41}, 65));
        victory_road.spawnEntries.add(new SpawnEntry("graveler", new int[]{41}, 10));
        victory_road.spawnEntries.add(new SpawnEntry("graveler", new int[]{41, 47}, 5));
        victory_road.spawnEntries.add(new SpawnEntry("onix", new int[]{36, 39, 42}, 60));
        victory_road.spawnEntries.add(new SpawnEntry("onix", new int[]{43, 45}, 10));
        victory_road.spawnEntries.add(new SpawnEntry("marowak", new int[]{43}, 2));

        victory_road.spawnEntries.add(new SpawnEntry("zubat", new int[]{26}, 30));
        victory_road.spawnEntries.add(new SpawnEntry("zubat", new int[]{44}, 10));
        victory_road.spawnEntries.add(new SpawnEntry("golbat", new int[]{40}, 10));
        victory_road.spawnEntries.add(new SpawnEntry("golbat", new int[]{39}, 10));
        victory_road.spawnEntries.add(new SpawnEntry("machop", new int[]{22}, 50));
        victory_road.spawnEntries.add(new SpawnEntry("machoke", new int[]{41}, 10));
        victory_road.spawnEntries.add(new SpawnEntry("machoke", new int[]{39, 42}, 5));
        victory_road.spawnEntries.add(new SpawnEntry("geodude", new int[]{24}, 30));
        victory_road.spawnEntries.add(new SpawnEntry("geodude", new int[]{31, 36, 41}, 55));
        victory_road.spawnEntries.add(new SpawnEntry("graveler", new int[]{43}, 2));
        victory_road.spawnEntries.add(new SpawnEntry("graveler", new int[]{44}, 10));
        victory_road.spawnEntries.add(new SpawnEntry("onix", new int[]{36, 39, 42}, 60));
        victory_road.spawnEntries.add(new SpawnEntry("onix", new int[]{45, 47}, 10));
        victory_road.spawnEntries.add(new SpawnEntry("marowak", new int[]{40}, 8));

        victory_road.spawnEntries.add(new SpawnEntry("zubat", new int[]{22}, 30));
        victory_road.spawnEntries.add(new SpawnEntry("golbat", new int[]{41}, 10));
        victory_road.spawnEntries.add(new SpawnEntry("golbat", new int[]{44}, 15));
        victory_road.spawnEntries.add(new SpawnEntry("venomoth", new int[]{40}, 20));
        victory_road.spawnEntries.add(new SpawnEntry("machop", new int[]{24}, 50));
        victory_road.spawnEntries.add(new SpawnEntry("machoke", new int[]{42, 45}, 10));
        victory_road.spawnEntries.add(new SpawnEntry("machoke", new int[]{42, 45}, 10));
        victory_road.spawnEntries.add(new SpawnEntry("geodude", new int[]{26}, 30));
        victory_road.spawnEntries.add(new SpawnEntry("geodude", new int[]{36, 41, 46}, 50));
        victory_road.spawnEntries.add(new SpawnEntry("graveler", new int[]{43}, 10));
        victory_road.spawnEntries.add(new SpawnEntry("graveler", new int[]{41, 47}, 15));
        victory_road.spawnEntries.add(new SpawnEntry("onix", new int[]{42, 45}, 40));
        victory_road.spawnEntries.add(new SpawnEntry("onix", new int[]{49}, 10));

        victory_road.spawnEntries.add(new SpawnEntry("moltres", new int[]{50}, 9));

        indigo_plateau.neighbors = new Location[]{victory_road};
        indigo_plateau.hasCenter = true;

        cerulean_cave.neighbors = new Location[]{cerulean_city};
        cerulean_cave.spawnEntries.add(new SpawnEntry("arbok", new int[]{52}, 10));
        cerulean_cave.spawnEntries.add(new SpawnEntry("raichu", new int[]{53}, 8));
        cerulean_cave.spawnEntries.add(new SpawnEntry("sandslash", new int[]{52}, 20));
        cerulean_cave.spawnEntries.add(new SpawnEntry("golbat", new int[]{46}, 50));
        cerulean_cave.spawnEntries.add(new SpawnEntry("golbat", new int[]{50, 55}, 40));
        cerulean_cave.spawnEntries.add(new SpawnEntry("gloom", new int[]{55}, 10));
        cerulean_cave.spawnEntries.add(new SpawnEntry("parasect", new int[]{52}, 10));
        cerulean_cave.spawnEntries.add(new SpawnEntry("parasect", new int[]{54}, 5));
        cerulean_cave.spawnEntries.add(new SpawnEntry("venomoth", new int[]{49}, 20));
        cerulean_cave.spawnEntries.add(new SpawnEntry("venomoth", new int[]{54}, 5));
        cerulean_cave.spawnEntries.add(new SpawnEntry("kadabra", new int[]{49}, 10));
        cerulean_cave.spawnEntries.add(new SpawnEntry("weepinbell", new int[]{55}, 10));
        cerulean_cave.spawnEntries.add(new SpawnEntry("graveler", new int[]{45}, 15));
        cerulean_cave.spawnEntries.add(new SpawnEntry("magneton", new int[]{46}, 30));
        cerulean_cave.spawnEntries.add(new SpawnEntry("dodrio", new int[]{49}, 20));
        cerulean_cave.spawnEntries.add(new SpawnEntry("hypno", new int[]{46}, 30));
        cerulean_cave.spawnEntries.add(new SpawnEntry("ditto", new int[]{53}, 2));
        cerulean_cave.spawnEntries.add(new SpawnEntry("ditto", new int[]{55, 60}, 5));

        cerulean_cave.spawnEntries.add(new SpawnEntry("sandslash", new int[]{56}, 10));
        cerulean_cave.spawnEntries.add(new SpawnEntry("wigglytuff", new int[]{54}, 10));
        cerulean_cave.spawnEntries.add(new SpawnEntry("golbat", new int[]{52, 57}, 40));
        cerulean_cave.spawnEntries.add(new SpawnEntry("gloom", new int[]{58}, 5));
        cerulean_cave.spawnEntries.add(new SpawnEntry("venomoth", new int[]{51}, 30));
        cerulean_cave.spawnEntries.add(new SpawnEntry("kadabra", new int[]{51}, 30));
        cerulean_cave.spawnEntries.add(new SpawnEntry("weepinbell", new int[]{58}, 5));
        cerulean_cave.spawnEntries.add(new SpawnEntry("graveler", new int[]{50}, 15));
        cerulean_cave.spawnEntries.add(new SpawnEntry("dodrio", new int[]{51}, 50));
        cerulean_cave.spawnEntries.add(new SpawnEntry("electrode", new int[]{52}, 20));
        cerulean_cave.spawnEntries.add(new SpawnEntry("marowak", new int[]{52}, 20));
        cerulean_cave.spawnEntries.add(new SpawnEntry("rhyhorn", new int[]{50}, 10));
        cerulean_cave.spawnEntries.add(new SpawnEntry("rhydon", new int[]{52}, 20));
        cerulean_cave.spawnEntries.add(new SpawnEntry("rhydon", new int[]{58, 60}, 5));
        cerulean_cave.spawnEntries.add(new SpawnEntry("chansey", new int[]{56}, 10));
        cerulean_cave.spawnEntries.add(new SpawnEntry("ditto", new int[]{55, 60}, 10));
        cerulean_cave.spawnEntries.add(new SpawnEntry("ditto", new int[]{60}, 10));

        cerulean_cave.spawnEntries.add(new SpawnEntry("arbok", new int[]{57}, 5));
        cerulean_cave.spawnEntries.add(new SpawnEntry("raichu", new int[]{64}, 20));
        cerulean_cave.spawnEntries.add(new SpawnEntry("sandslash", new int[]{57}, 5));
        cerulean_cave.spawnEntries.add(new SpawnEntry("golbat", new int[]{54, 59}, 40));
        cerulean_cave.spawnEntries.add(new SpawnEntry("parasect", new int[]{64}, 20));
        cerulean_cave.spawnEntries.add(new SpawnEntry("graveler", new int[]{55}, 15));
        cerulean_cave.spawnEntries.add(new SpawnEntry("electrode", new int[]{55}, 30));
        cerulean_cave.spawnEntries.add(new SpawnEntry("marowak", new int[]{55}, 30));
        cerulean_cave.spawnEntries.add(new SpawnEntry("lickitung", new int[]{50, 55}, 5));
        cerulean_cave.spawnEntries.add(new SpawnEntry("rhyhorn", new int[]{52}, 10));
        cerulean_cave.spawnEntries.add(new SpawnEntry("rhydon", new int[]{55}, 50));
        cerulean_cave.spawnEntries.add(new SpawnEntry("rhydon", new int[]{62}, 10));
        cerulean_cave.spawnEntries.add(new SpawnEntry("chansey", new int[]{64}, 20));
        cerulean_cave.spawnEntries.add(new SpawnEntry("chansey", new int[]{56}, 5));
        cerulean_cave.spawnEntries.add(new SpawnEntry("ditto", new int[]{63, 65, 67}, 20));
        cerulean_cave.spawnEntries.add(new SpawnEntry("ditto", new int[]{60, 65}, 15));

        cerulean_cave.spawnEntries.add(new SpawnEntry("mewtwo", new int[]{70}, 9));
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

        allLocations.add(this);

        this.name = name;
    }
}
