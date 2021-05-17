package jigglybot.location;

import java.util.ArrayList;
import java.util.Arrays;

public class SpawnEntryGenerator
{
    public static void main(String[] args)
    {
        String loc = "pokemon_mansion";

        String data = "Rattata \tRattata\n" +
                "\tR \tB \tY \t\n" +
                "1F \t1F\n" +
                "\t34, 37 \t35%\n" +
                "Raticate \tRaticate\n" +
                "\tR \tB \tY \t\n" +
                "1F \t1F\n" +
                "\t34, 37 \t25%\n" +
                "Vulpix \tVulpix\n" +
                "\tR \tB \tY \t\n" +
                "1F \t1F\n" +
                "\t34 \t10%\n" +
                "Growlithe \tGrowlithe\n" +
                "\tR \tB \tY \t\n" +
                "1F \t1F\n" +
                "\t34 \t10%\n" +
                "Growlithe \tGrowlithe\n" +
                "\tR \tB \tY \t\n" +
                "1F \t1F\n" +
                "\t26, 30, 34, 38 \t20%\n" +
                "Ponyta \tPonyta\n" +
                "\tR \tB \tY \t\n" +
                "1F \t1F\n" +
                "\t28, 30, 32, 34 \t40%\n" +
                "Grimer \tGrimer\n" +
                "\tR \tB \tY \t\n" +
                "1F \t1F\n" +
                "\t30 \t5%\n" +
                "Grimer \tGrimer\n" +
                "\tR \tB \tY \t\n" +
                "1F \t1F\n" +
                "\t30, 32 \t40%\n" +
                "Grimer \tGrimer\n" +
                "\tR \tB \tY \t\n" +
                "1F \t1F\n" +
                "\t23, 26 \t20%\n" +
                "Muk \tMuk\n" +
                "\tR \tB \tY \t\n" +
                "1F \t1F\n" +
                "\t39 \t1%\n" +
                "Muk \tMuk\n" +
                "\tR \tB \tY \t\n" +
                "1F \t1F\n" +
                "\t37 \t4%\n" +
                "Koffing \tKoffing\n" +
                "\tR \tB \tY \t\n" +
                "1F \t1F\n" +
                "\t30, 32 \t40%\n" +
                "Koffing \tKoffing\n" +
                "\tR \tB \tY \t\n" +
                "1F \t1F\n" +
                "\t30 \t5%\n" +
                "Weezing \tWeezing\n" +
                "\tR \tB \tY \t\n" +
                "1F \t1F\n" +
                "\t37 \t4%\n" +
                "Weezing \tWeezing\n" +
                "\tR \tB \tY \t\n" +
                "1F \t1F\n" +
                "\t39 \t1%\n" +
                "Rattata \tRattata\n" +
                "\tR \tB \tY \t\n" +
                "2F \t2F\n" +
                "\t37, 40 \t35%\n" +
                "Raticate \tRaticate\n" +
                "\tR \tB \tY \t\n" +
                "2F \t2F\n" +
                "\t37, 40 \t25%\n" +
                "Vulpix \tVulpix\n" +
                "\tR \tB \tY \t\n" +
                "2F \t2F\n" +
                "\t32 \t25%\n" +
                "Growlithe \tGrowlithe\n" +
                "\tR \tB \tY \t\n" +
                "2F \t2F\n" +
                "\t32 \t25%\n" +
                "Ponyta \tPonyta\n" +
                "\tR \tB \tY \t\n" +
                "2F \t2F\n" +
                "\t28, 30, 32 \t25%\n" +
                "Grimer \tGrimer\n" +
                "\tR \tB \tY \t\n" +
                "2F \t2F\n" +
                "\t30 \t5%\n" +
                "Grimer \tGrimer\n" +
                "\tR \tB \tY \t\n" +
                "2F \t2F\n" +
                "\t30, 34 \t40%\n" +
                "Grimer \tGrimer\n" +
                "\tR \tB \tY \t\n" +
                "2F \t2F\n" +
                "\t26, 29, 32, 35 \t35%\n" +
                "Muk \tMuk\n" +
                "\tR \tB \tY \t\n" +
                "2F \t2F\n" +
                "\t37 \t1%\n" +
                "Muk \tMuk\n" +
                "\tR \tB \tY \t\n" +
                "2F \t2F\n" +
                "\t39 \t4%\n" +
                "Muk \tMuk\n" +
                "\tR \tB \tY \t\n" +
                "2F \t2F\n" +
                "\t35, 38 \t5%\n" +
                "Koffing \tKoffing\n" +
                "\tR \tB \tY \t\n" +
                "2F \t2F\n" +
                "\t30, 34 \t40%\n" +
                "Koffing \tKoffing\n" +
                "\tR \tB \tY \t\n" +
                "2F \t2F\n" +
                "\t30 \t5%\n" +
                "Weezing \tWeezing\n" +
                "\tR \tB \tY \t\n" +
                "2F \t2F\n" +
                "\t39 \t4%\n" +
                "Weezing \tWeezing\n" +
                "\tR \tB \tY \t\n" +
                "2F \t2F\n" +
                "\t37 \t1%\n" +
                "Rattata \tRattata\n" +
                "\tR \tB \tY \t\n" +
                "3F \t3F\n" +
                "\t40, 43 \t35%\n" +
                "Raticate \tRaticate\n" +
                "\tR \tB \tY \t\n" +
                "3F \t3F\n" +
                "\t40, 43 \t25%\n" +
                "Vulpix \tVulpix\n" +
                "\tR \tB \tY \t\n" +
                "3F \t3F\n" +
                "\t33 \t15%\n" +
                "Growlithe \tGrowlithe\n" +
                "\tR \tB \tY \t\n" +
                "3F \t3F\n" +
                "\t33 \t15%\n" +
                "Ponyta \tPonyta\n" +
                "\tR \tB \tY \t\n" +
                "3F \t3F\n" +
                "\t32, 34, 36 \t24%\n" +
                "Ponyta \tPonyta\n" +
                "\tR \tB \tY \t\n" +
                "3F \t3F\n" +
                "\t32, 36 \t14%\n" +
                "Grimer \tGrimer\n" +
                "\tR \tB \tY \t\n" +
                "3F \t3F\n" +
                "\t34 \t5%\n" +
                "Grimer \tGrimer\n" +
                "\tR \tB \tY \t\n" +
                "3F \t3F\n" +
                "\t31, 35 \t40%\n" +
                "Grimer \tGrimer\n" +
                "\tR \tB \tY \t\n" +
                "3F \t3F\n" +
                "\t32, 35, 38 \t35%\n" +
                "Muk \tMuk\n" +
                "\tR \tB \tY \t\n" +
                "3F \t3F\n" +
                "\t42 \t1%\n" +
                "Muk \tMuk\n" +
                "\tR \tB \tY \t\n" +
                "3F \t3F\n" +
                "\t38, 40 \t15%\n" +
                "Muk \tMuk\n" +
                "\tR \tB \tY \t\n" +
                "3F \t3F\n" +
                "\t38, 41 \t5%\n" +
                "Koffing \tKoffing\n" +
                "\tR \tB \tY \t\n" +
                "3F \t3F\n" +
                "\t31, 35 \t40%\n" +
                "Koffing \tKoffing\n" +
                "\tR \tB \tY \t\n" +
                "3F \t3F\n" +
                "\t34 \t5%\n" +
                "Weezing \tWeezing\n" +
                "\tR \tB \tY \t\n" +
                "3F \t3F\n" +
                "\t38, 40 \t15%\n" +
                "Weezing \tWeezing\n" +
                "\tR \tB \tY \t\n" +
                "3F \t3F\n" +
                "\t42 \t1%\n" +
                "Magmar \tMagmar\n" +
                "\tR \tB \tY \t\n" +
                "3F \t3F\n" +
                "\t34 \t10%\n" +
                "Raticate \tRaticate\n" +
                "\tR \tB \tY \t\n" +
                "B1F \tB1F\n" +
                "\t37, 40, 43, 46 \t40%\n" +
                "Vulpix \tVulpix\n" +
                "\tR \tB \tY \t\n" +
                "B1F \tB1F\n" +
                "\t35 \t15%\n" +
                "Growlithe \tGrowlithe\n" +
                "\tR \tB \tY \t\n" +
                "B1F \tB1F\n" +
                "\t35 \t15%\n" +
                "Ponyta \tPonyta\n" +
                "\tR \tB \tY \t\n" +
                "B1F \tB1F\n" +
                "\t32, 34 \t15%\n" +
                "Grimer \tGrimer\n" +
                "\tR \tB \tY \t\n" +
                "B1F \tB1F\n" +
                "\t35 \t5%\n" +
                "Grimer \tGrimer\n" +
                "\tR \tB \tY \t\n" +
                "B1F \tB1F\n" +
                "\t31, 33 \t50%\n" +
                "Grimer \tGrimer\n" +
                "\tR \tB \tY \t\n" +
                "B1F \tB1F\n" +
                "\t35, 38 \t40%\n" +
                "Muk \tMuk\n" +
                "\tR \tB \tY \t\n" +
                "B1F \tB1F\n" +
                "\t42 \t1%\n" +
                "Muk \tMuk\n" +
                "\tR \tB \tY \t\n" +
                "B1F \tB1F\n" +
                "\t40 \t10%\n" +
                "Muk \tMuk\n" +
                "\tR \tB \tY \t\n" +
                "B1F \tB1F\n" +
                "\t41 \t10%\n" +
                "Koffing \tKoffing\n" +
                "\tR \tB \tY \t\n" +
                "B1F \tB1F\n" +
                "\t31, 33 \t50%\n" +
                "Koffing \tKoffing\n" +
                "\tR \tB \tY \t\n" +
                "B1F \tB1F\n" +
                "\t35 \t5%\n" +
                "Weezing \tWeezing\n" +
                "\tR \tB \tY \t\n" +
                "B1F \tB1F\n" +
                "\t40, 42 \t14%\n" +
                "Weezing \tWeezing\n" +
                "\tR \tB \tY \t\n" +
                "B1F \tB1F\n" +
                "\t42 \t1%\n" +
                "Magmar \tMagmar\n" +
                "\tR \tB \tY \t\n" +
                "B1F \tB1F\n" +
                "\t38 \t4%\n" +
                "Ditto \tDitto\n" +
                "\tR \tB \tY \t\n" +
                "B1F \tB1F\n" +
                "\t12, 18, 24 \t10%";

        data = data.replaceAll("\t", "");
        data = data.replaceAll("\n", " ");
        data = data.replaceAll(" R B Y ", "");
        data = data.replaceAll(" Grass Grass", "");
        data = data.replaceAll(" Building Walking", "");
        data = data.replaceAll(" Cave Cave", "");
        data = data.replaceAll(" 1F 1F", "");
        data = data.replaceAll(" 2F 2F", "");
        data = data.replaceAll(" 3F 3F", "");
        data = data.replaceAll(" B1F B1F", "");
        data = data.replaceAll(", ", ",");

        String[] parts = data.split(" ");

        for (int n = 0; n < parts.length / 4; n++)
        {
            int i = n * 4;
            String name = parts[i].toLowerCase();
            String[] levelsStr = parts[i + 2].split(",");
            ArrayList<Integer> levels = new ArrayList<>();

            for (String s: levelsStr)
            {
                if (s.contains("-"))
                {
                    int l = Integer.parseInt(s.split("-")[0]);
                    int l2 = Integer.parseInt(s.split("-")[1]);

                    for (int j = l; j <= l2; j++)
                    {
                        levels.add(j);
                    }
                }
                else
                    levels.add(Integer.parseInt(s));
            }

            int chance = Integer.parseInt(parts[i + 3].replaceAll("%", ""));

            String levels2 = levels.toString();

            System.out.println(loc + ".spawnEntries.add(new SpawnEntry(\"" + name + "\", new int[]{" + levels2.substring(1, levels2.length() - 1)  + "}, " + chance + "));");
        }

        //System.out.println(data);
    }
}
