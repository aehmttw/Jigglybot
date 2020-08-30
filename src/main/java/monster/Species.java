package monster;

import discord4j.core.object.entity.channel.MessageChannel;

import java.util.ArrayList;
import java.util.HashMap;
import static monster.Type.*;

public class Species
{
    public final String name;
    public final int baseHP;
    public final int baseAttack;
    public final int baseDefense;
    public final int baseSpeed;
    public final int baseSpecial;
    public final int id;

    public final int type1;
    public final int type2;

    public static int currentID = 1;

    public final HashMap<Integer, Move> moveUnlocks = new HashMap<>();
    public final ArrayList<Move> learnableMoves = new ArrayList<>();

    public static final HashMap<Integer, Species> speciesByNum = new HashMap<>();
    public static final HashMap<String, Species> speciesByName = new HashMap<>();

    public Species(String name, int type1, int type2, int baseHP, int baseAttack, int baseDefense, int baseSpeed, int baseSpecial)
    {
        this.id = currentID;
        currentID++;

        this.name = name;
        this.baseHP = baseHP;
        this.baseAttack = baseAttack;
        this.baseDefense = baseDefense;
        this.baseSpeed = baseSpeed;
        this.baseSpecial = baseSpecial;
        this.type1 = type1;
        this.type2 = type2;

        speciesByName.put(name.toLowerCase(), this);
        speciesByNum.put(this.id, this);
    }

    public Species(String name, int type1, int baseHP, int baseAttack, int baseDefense, int baseSpeed, int baseSpecial)
    {
        this(name, type1, type1, baseHP, baseAttack, baseDefense, baseSpeed, baseSpecial);
    }

    public void printStats(MessageChannel c)
    {
        String s = this.id + ". " + this.name.toUpperCase() + "\n";

        if (this.type1 != this.type2)
            s += Type.getTypeString(this.type1) + " ";

        s += Type.getTypeString(this.type2) + "\n";

        s += "HP " + this.baseHP + "\n";
        s += "ATTACK " + this.baseAttack + "\n";
        s += "DEFENSE " + this.baseDefense + "\n";
        s += "SPEED " + this.baseSpeed + "\n";
        s += "SPECIAL " + this.baseSpecial;

        c.createMessage(s).block();
    }

    public static void setup()
    {
        new Species("Bulbasaur", grass, poison, 45, 49, 49, 45, 65);
        new Species("Ivysaur", grass, poison,60, 62, 63, 60, 80);
        new Species("Venusaur", grass, poison,80, 82, 83, 80, 100);
        new Species("Charmander", fire,39, 52, 43, 65, 50);
        new Species("Charmeleon", fire, 58, 64, 58, 80, 65);
        new Species("Charizard", fire, flying, 78, 84, 78, 100, 85);
        new Species("Squirtle", water, 44, 48, 65, 43, 50);
        new Species("Wartortle", water, 59, 63, 80, 58, 65);
        new Species("Blastoise", water, 79, 83, 100, 78, 85);
        new Species("Caterpie", bug, 45, 30, 35, 45, 20);
        new Species("Metapod", bug, 50, 20, 55, 30, 25);
        new Species("Butterfree", bug, flying, 45, 50, 70, 80);
        new Species("Weedle", bug, poison, 40, 35, 30, 50, 20);
        new Species("Kakuna", bug, poison, 45, 25, 50, 35, 25);
        new Species("Beedrill", bug, poison, 65, 80, 40, 75, 45);
        new Species("Pidgey", normal, flying, 40, 45, 40, 56, 35);
        new Species("Pidgeotto", normal, flying, 63, 60, 55, 71, 50);
        new Species("Pidgeot", normal, flying, 83, 80, 75, 91, 70);
        new Species("Rattata", normal, 30, 56, 35, 72, 25);
        new Species("Raticate", normal, 55, 81, 60, 97, 50);
        new Species("Spearow", normal, flying, 40, 60, 30, 70, 31);
        new Species("Fearow", normal, flying, 65, 90, 65, 100, 61);
        new Species("Ekans", poison, 35, 60, 44, 55, 40);
        new Species("Arbok", poison, 60, 85, 69, 80, 65);
        new Species("Pikachu", electric, 35, 55, 30, 90, 50);
        new Species("Raichu", electric, 60, 90, 55, 100, 90);
        new Species("Sandshrew", ground, 50, 75, 85, 40, 30);
        new Species("Sandslash", ground, 75, 100, 110, 65, 55);
        new Species("Nidoran♀", poison, 55, 47, 52, 41, 40);
        new Species("Nidorina", poison, 70, 62, 67, 56, 55);
        new Species("Nidoqueen", poison, ground, 90, 82, 87, 76, 75);
        new Species("Nidoran♂", poison, 46, 57, 40, 50, 40);
        new Species("Nidorino", poison, 61, 72, 57, 65, 55);
        new Species("Nidoking", poison, ground, 81, 92, 77, 85, 75);
        new Species("Clefairy", normal, 70, 45, 48, 35, 60);
        new Species("Clefable", normal, 95, 70, 73, 60, 85);
        new Species("Vulpix", fire, 38, 41, 40, 65, 65);
        new Species("Ninetales", fire, 73, 76, 75, 100, 100);
        new Species("Jigglypuff", normal, 115, 45, 20, 20, 25);
        new Species("Wigglytuff", normal, 140, 70, 45, 45, 50);
        new Species("Zubat", poison, flying, 40, 45, 35, 55, 40);
        new Species("Golbat", poison, flying, 75, 80, 70, 90, 75);
        new Species("Oddish", grass, poison, 45, 50, 55, 30, 75);
        new Species("Gloom", grass, poison, 60, 65, 70, 40, 85);
        new Species("Vileplume", grass, poison, 75, 80, 85, 50, 100);
        new Species("Paras", bug, grass, 35, 70, 55, 25, 55);
        new Species("Parasect", bug, grass, 60, 95, 80, 30, 80);
        new Species("Venonat", bug, poison, 60, 55, 50, 45, 40);
        new Species("Venomoth", bug, poison, 70, 65, 60, 90, 90);
        new Species("Diglett", ground, 10, 55, 25, 95, 45);
        new Species("Dugtrio", ground, 35, 80, 50, 120, 70);
        new Species("Meowth", normal, 40, 45, 35, 90, 40);
        new Species("Persian", normal, 65, 70, 60, 115, 65);
        new Species("Psyduck", water, 50, 52, 48, 55, 50);
        new Species("Golduck", water, 80, 82, 78, 85, 80);
        new Species("Mankey", fighting, 40, 80, 35, 70, 35);
        new Species("Primeape", fighting, 65, 105, 60, 95, 60);
        new Species("Growlithe", fire, 55, 70, 45, 60, 50);
        new Species("Arcanine", fire, 90, 110, 80, 95, 80);
        new Species("Poliwag", water, 40, 50, 40, 90, 40);
        new Species("Poliwhirl", water, 65, 65, 65, 90, 50);
        new Species("Poliwrath", water, fighting, 90, 85, 95, 70, 70);
        new Species("Abra", psychic, 25, 20, 15, 90, 105);
        new Species("Kadabra", psychic, 40, 35, 30, 105, 120);
        new Species("Alakazam", psychic, 55, 50, 45, 120, 135);
        new Species("Machop", fighting, 70, 80, 50, 35, 35);
        new Species("Machoke", fighting, 80, 100, 70, 45, 50);
        new Species("Machamp", fighting, 90, 130, 80, 55, 65);
        new Species("Bellsprout", grass, poison, 50, 75, 35, 40, 70);
        new Species("Weepinbell", grass, poison, 65, 90, 50, 55, 85);
        new Species("Victreebel", grass, poison, 80, 105, 65, 70, 100);
        new Species("Tentacool", water, poison, 40, 40, 35, 70, 100);
        new Species("Tentacruel", water, poison, 80, 70, 65, 100, 120);
        new Species("Geodude", rock, ground, 40, 80, 100, 20, 30);
        new Species("Graveler", rock, ground, 55, 95, 115, 35, 45);
        new Species("Golem", rock, ground, 80, 110, 130, 45, 55);
        new Species("Ponyta", fire, 50, 85, 55, 90, 65);
        new Species("Rapidash", fire, 65, 100, 70, 105, 80);
        new Species("Slowpoke", water, psychic, 90, 65, 65, 15, 40);
        new Species("Slowbro", water, psychic, 75, 110, 30, 80);
        new Species("Magnemite", electric, 25, 35, 70, 45, 95);
        new Species("Magneton", electric, 50, 60, 95, 70, 120);
        new Species("Farfetch'd", normal, flying, 52, 65, 55, 60, 58);
        new Species("Doduo", normal, flying, 35, 85, 45, 75, 35);
        new Species("Dodrio", normal, flying, 60, 110, 70, 100, 60);
        new Species("Seel", water, 65, 45, 55, 45, 70);
        new Species("Dewgong", water, ice, 90, 70, 80, 70, 95);
        new Species("Grimer", poison, 80, 80, 50, 25, 40);
        new Species("Muk", poison, 105, 105, 75, 50, 65);
        new Species("Shellder", water, 30, 65, 100, 40, 45);
        new Species("Cloyster", water, ice, 50, 95, 180, 70, 85);
        new Species("Gastly", ghost, poison, 30, 35, 30, 80, 100);
        new Species("Haunter", ghost, poison, 45, 50, 45, 95, 115);
        new Species("Gengar", ghost, poison, 60, 65, 60, 110, 130);
        new Species("Onix", rock, ground, 35, 45, 160, 70, 30);
        new Species("Drowzee", psychic, 60, 48, 45, 42, 90);
        new Species("Hypno", psychic, 85, 73, 70, 67, 115);
        new Species("Krabby", water, 30, 105, 90, 50, 25);
        new Species("Kingler", water, 55, 130, 115, 75, 50);
        new Species("Voltorb", electric, 40, 30, 50, 100, 55);
        new Species("Electrode", electric, 60, 50, 70, 140, 80);
        new Species("Exeggcute", grass, psychic, 60, 40, 80, 40, 60);
        new Species("Exeggutor", grass, psychic, 95, 95, 85, 55, 125);
        new Species("Cubone", ground, 50, 50, 95, 35, 40);
        new Species("Marowak", ground, 60, 80, 110, 45, 50);
        new Species("Hitmonlee", fighting, 50, 120, 53, 87, 35);
        new Species("Hitmonchan", fighting, 50, 105, 79, 76, 35);
        new Species("Lickitung", normal, 90, 55, 75, 30, 60);
        new Species("Koffing", poison, 40, 65, 95, 35, 60);
        new Species("Weezing", poison, 65, 90, 120, 60, 85);
        new Species("Rhyhorn", ground, rock, 80, 85, 95, 25, 30);
        new Species("Rhydon", ground, rock, 105, 130, 120, 40, 45);
        new Species("Chansey", normal, 250, 5, 5, 50, 105);
        new Species("Tangela", grass, 65, 55, 115, 60, 100);
        new Species("Kangaskhan", normal, 105, 95, 80, 90, 40);
        new Species("Horsea", water, 30, 40, 70, 60, 70);
        new Species("Seadra", water, 55, 65, 95, 85, 95);
        new Species("Goldeen", water, 45, 67, 60, 63, 50);
        new Species("Seaking", water, 80, 92, 65, 68, 80);
        new Species("Staryu", water, 30, 45, 55, 85, 70);
        new Species("Starmie", water, psychic, 60, 75, 85, 115, 100);
        new Species("Mr.Mime", psychic, 40, 45, 65, 90, 100);
        new Species("Scyther", bug, flying, 70, 110, 80, 105, 55);
        new Species("Jynx", ice, psychic, 65, 50, 35, 95, 95);
        new Species("Electabuzz", electric, 65, 83, 57, 105, 85);
        new Species("Magmar", fire, 65, 95, 57, 93, 85);
        new Species("Pinsir", bug, 65, 125, 100, 85, 55);
        new Species("Tauros", normal, 75, 100, 95, 110, 70);
        new Species("Magikarp", water, 20, 10, 55, 80, 20);
        new Species("Gyarados", water, flying, 95, 125, 79, 81, 100);
        new Species("Lapras", water, ice, 130, 85, 80, 60, 95);
        new Species("Ditto", normal, 48, 48, 48, 48, 48);
        new Species("Eevee", normal, 55, 55, 50, 55, 65);
        new Species("Vaporeon", water, 130, 65, 60, 65, 110);
        new Species("Jolteon", electric, 65, 65, 60, 130, 110);
        new Species("Flareon", fire, 65, 130, 60, 65, 110);
        new Species("Porygon", normal, 65, 60, 70, 40, 75);
        new Species("Omanyte", rock, water, 35, 40, 100, 35, 90);
        new Species("Omastar", rock, water, 70, 60, 125, 55, 115);
        new Species("Kabuto", rock, water, 30, 80, 90, 55, 45);
        new Species("Kabutops", rock, water, 60, 115, 105, 80, 70);
        new Species("Aerodactyl", rock, flying, 80, 105, 65, 130, 60);
        new Species("Snorlax", normal, 160, 110, 65, 30, 65);
        new Species("Articuno", ice, flying, 90, 85, 100, 85, 125);
        new Species("Zapdos", electric, flying, 90, 90, 85, 100, 125);
        new Species("Moltres", fire, flying, 90, 100, 90, 90, 125);
        new Species("Dratini", dragon, 41, 64, 45, 50, 50);
        new Species("Dragonair", dragon, 61, 84, 65, 70, 70);
        new Species("Dragonite", dragon, flying, 91, 134, 95, 80, 100);
        new Species("Mewtwo", psychic, 106, 110, 90, 130, 154);
        new Species("Mew", psychic, 100, 100, 100, 100, 100);
    }
}
