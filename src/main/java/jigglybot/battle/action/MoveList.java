package jigglybot.battle.action;

import jigglybot.monster.Monster;
import jigglybot.monster.Type;

import java.util.HashMap;

public class MoveList
{
    public static final HashMap<String, Move> by_name = new HashMap<>();

    public static void setup()
    {
        Move.struggle.recoil = 0.5;

        Move absorb = new Move("absorb", Type.grass, 20, 20, 100);
        absorb.recoil = -0.5;

        Move acid = new Move("acid", Type.poison, 30, 40, 100);
        acid.stage = Monster.stage_defense;
        acid.stageAmount = -1;
        acid.effectChance = 0.33;

        Move acidArmor = new Move("acid armor", Type.poison, 40, 0, -1);
        acidArmor.stage = Monster.stage_defense;
        acidArmor.stageAmount = 2;
        acidArmor.effectTargetsEnemy = false;

        Move agility = new Move("agility", Type.psychic, 30, 0, -1);
        agility.stage = Monster.stage_speed;
        agility.stageAmount = 2;
        agility.effectTargetsEnemy = false;

        Move amnesia = new Move("amnesia", Type.psychic, 20, 0, -1);
        amnesia.stage = Monster.stage_special;
        amnesia.stageAmount = 2;
        amnesia.effectTargetsEnemy = false;

        Move auroraBeam = new Move("aurora beam", Type.ice, 20, 65, 100);
        auroraBeam.stage = Monster.stage_attack;
        auroraBeam.stageAmount = -1;
        auroraBeam.effectChance = 0.33;

        //barrage

        Move barrier = new Move("barrier", Type.psychic, 30, 0, -1);
        barrier.stage = Monster.stage_defense;
        barrier.stageAmount = 2;
        barrier.effectTargetsEnemy = false;

        //bide

        //bind

        //bite

        Move blizzard = new Move("blizzard", Type.ice, 5, 120, 90);
        blizzard.effectChance = 0.1;
        blizzard.statusEffect = Monster.frozen;

        Move bodySlam = new Move("body slam", Type.normal, 15, 85, 100);
        bodySlam.effectChance = 0.3;
        bodySlam.statusEffect = Monster.paralyzed;

        //bone club

        //boomerang

        Move bubble = new Move("bubble", Type.water, 30, 20, 100);
        bubble.effectChance = 0.33;
        bubble.stage = Monster.stage_speed;
        bubble.stageAmount = -1;

        Move bubblebeam = new Move("bubblebeam", Type.water, 20, 65, 100);
        bubblebeam.effectChance = 0.33;
        bubblebeam.stage = Monster.stage_speed;
        bubblebeam.stageAmount = -1;

        //clamp

        //comet punch

        //confuse ray

        //confusion

        Move constrict = new Move("constrict", Type.normal, 35, 10, 100);
        constrict.effectChance = 0.33;
        constrict.stage = Monster.stage_speed;
        constrict.stageAmount = -1;

        //conversion

        //counter

        Move crabhammer = new Move("crabhammer", Type.water, 10, 90, 85);
        crabhammer.critMultiplier = 8;

        Move cut = new Move("cut", Type.normal, 30, 50, 95);

        Move defenseCurl = new Move("defense curl", Type.normal, 40, 0, -1);
        defenseCurl.stage = Monster.stage_defense;
        defenseCurl.stageAmount = 1;
        defenseCurl.effectTargetsEnemy = false;

        //dig

        //disable

        Move dizzyPunch = new Move("dizzy punch", Type.normal, 10, 70, 100);

        //double kick

        Move doubleTeam = new Move("double team", Type.normal, 40, 0, -1);
        doubleTeam.stage = Monster.stage_evasion;
        doubleTeam.stageAmount = 1;
        doubleTeam.effectTargetsEnemy = false;

        Move doubleEdge = new Move("double-edge", Type.normal, 15, 100, 100);
        doubleEdge.recoil = 0.25;

        //doubleslap

        //dragon rage

        //dream eater

        Move drillPeck = new Move("drill peck", Type.flying, 20, 80, 100);

        Move earthquake = new Move("earthquake", Type.ground, 10, 100, 100);

        Move eggBomb = new Move("egg bomb", Type.ground, 10, 100, 75);

        Move ember = new Move("ember", Type.fire, 25, 40, 100);
        ember.effectChance = 0.1;
        ember.statusEffect = Monster.burned;

        //explosion

        Move fireBlast = new Move("fire blast", Type.fire, 5, 120, 85);
        fireBlast.effectChance = 0.3;
        fireBlast.statusEffect = Monster.burned;

        Move firePunch = new Move("fire punch", Type.fire, 15, 75, 100);
        firePunch.effectChance = 0.1;
        firePunch.statusEffect = Monster.burned;

        //fire spin

        //fissure

        Move flamethrower = new Move("flamethrower", Type.fire, 15, 95, 100);
        flamethrower.effectChance = 0.1;
        flamethrower.statusEffect = Monster.burned;

        Move flash = new Move("flash", Type.normal, 20, 0, 70);
        flash.stage = Monster.stage_accuracy;
        flash.stageAmount = -1;

        //fly

        //focus energy

        //fury attack

        //fury swipes

        Move glare = new Move("glare", Type.normal, 30, 0, 75);
        glare.statusEffect = Monster.paralyzed;

        Move growl = new Move("growl", Type.normal, 40, 0, 100);
        growl.stage = Monster.stage_attack;
        growl.stageAmount = -1;

        Move growth = new Move("growth", Type.normal, 40, 0, -1);
        growth.stage = Monster.stage_special;
        growth.stageAmount = 1;
        growth.effectTargetsEnemy = false;

        //guillotine

        Move gust = new Move("gust", Type.normal, 35, 40, 100);

        Move harden = new Move("harden", Type.normal, 30, 0, -1);
        harden.stage = Monster.stage_defense;
        harden.stageAmount = 1;
        harden.effectTargetsEnemy = false;

        //haze

        //headbutt

        //hi jump kick

        Move hornAttack = new Move("horn attack", Type.normal, 25, 65, 100);

        //horn drill

        Move hydroPump = new Move("hydro pump", Type.water, 5, 120, 80);

        //hyper beam

        //hyper fang

        Move hypnosis = new Move("hypnosis", Type.psychic, 20, 0, 60);
        hypnosis.statusEffect = Monster.asleep;

        Move iceBeam = new Move("ice beam", Type.ice, 10, 95, 100);
        iceBeam.effectChance = 0.1;
        iceBeam.statusEffect = Monster.frozen;

        Move icePunch = new Move("ice beam", Type.ice, 15, 75, 100);
        icePunch.effectChance = 0.1;
        icePunch.statusEffect = Monster.frozen;

        //jump kick

        Move karateChop = new Move("karate chop", Type.normal, 25, 55, 100);
        karateChop.critMultiplier = 8;

        Move kinesis = new Move("kinesis", Type.psychic, 15, 0, 80);
        kinesis.stage = Monster.stage_accuracy;
        kinesis.stageAmount = -1;

        Move leechLife = new Move("leech life", Type.bug, 15, 20, 100);
        leechLife.recoil = -0.5;

        //leech seed

        Move leer = new Move("leer", Type.normal, 30, 0, 100);
        leer.stage = Monster.stage_defense;
        leer.stageAmount = -1;

        //lick

        //light screen

        Move lovelyKiss = new Move("lovely kiss", Type.normal, 10, 0, 75);
        lovelyKiss.statusEffect = Monster.asleep;

        //low kick

        Move meditate = new Move("meditate", Type.psychic, 40, 0, -1);
        meditate.stage = Monster.stage_attack;
        meditate.stageAmount = 1;
        meditate.effectTargetsEnemy = false;

        Move megaDrain = new Move("mega drain", Type.grass, 10, 40, 100);
        megaDrain.recoil = -0.5;

        Move megaKick = new Move("mega kick", Type.normal, 5, 120, 75);

        Move megaPunch = new Move("mega punch", Type.normal, 20, 80, 85);

        //metronome

        //mimic

        Move minimize = new Move("minimize", Type.normal, 20, 0, -1);
        minimize.stage = Monster.stage_evasion;
        minimize.stageAmount = 1;
        minimize.effectTargetsEnemy = false;

        //mirror move

        //mist

        //night shade

        //pay day

        Move peck = new Move("peck", Type.flying, 35, 35, 100);

        //petal dance

        //pin missile

        Move poisonGas = new Move("poison gas", Type.poison, 40, 0, 55);
        poisonGas.statusEffect = Monster.poisoned;

        Move poisonSting = new Move("poison sting", Type.poison, 35, 15, 100);
        poisonSting.statusEffect = Monster.poisoned;
        poisonSting.effectChance = 0.2;

        Move poisonpowder = new Move("poisonpowder", Type.poison, 35, 0, 75);
        poisonpowder.statusEffect = Monster.poisoned;

        Move pound = new Move("pound", Type.normal, 35, 40, 100);

        //psybeam

        Move psychic = new Move("psychic", Type.normal, 10, 90, 100);
        psychic.stage = Monster.stage_special;
        psychic.stageAmount = -1;
        psychic.effectChance = 0.33;

        //psywave

        Move quickAttack = new Move("quick attack", Type.normal, 30, 40, 100);
        quickAttack.priority = 1;

        //rage

        Move razorLeaf = new Move("razor leaf", Type.grass, 25, 55, 95);
        razorLeaf.critMultiplier = 8;

        //razor wind

        //recover

        //reflect

        //rest

        //roar

        Move rockSlide = new Move("rock slide", Type.rock, 10, 75, 90);

        Move rockThrow = new Move("rock throw", Type.rock, 15, 50, 65);

        //rolling kick

        Move sandAttack = new Move("psychic", Type.ground, 15, 0, 100);
        sandAttack.stage = Monster.stage_accuracy;
        sandAttack.stageAmount = -1;

        Move scratch = new Move("scratch", Type.normal, 35, 40, 100);

        Move screech = new Move("screech", Type.ground, 10, 0, 85);
        screech.stage = Monster.stage_defense;
        screech.stageAmount = -2;

        //seismic toss

        //selfdestruct

        Move sharpen = new Move("sharpen", Type.ground, 30, 0, -1);
        sharpen.stage = Monster.stage_attack;
        sharpen.stageAmount = 1;
        sharpen.effectTargetsEnemy = false;

        Move sing = new Move("sing", Type.normal, 15, 0, 55);
        sing.statusEffect = Monster.asleep;

        //skull bash

        //sky attack

        Move slam = new Move("slam", Type.normal, 20, 80, 75);

        Move slash = new Move("slash", Type.normal, 20, 70, 100);
        slash.critMultiplier = 8;

        Move sleepPowder = new Move("sleep powder", Type.grass, 15, 0, 75);
        sleepPowder.statusEffect = Monster.asleep;

        Move sludge = new Move("sludge", Type.poison, 20, 65, 100);
        sludge.statusEffect = Monster.poisoned;
        sludge.effectChance = 0.4;

        Move smog = new Move("smog", Type.poison, 20, 20, 100);
        smog.statusEffect = Monster.poisoned;
        smog.effectChance = 0.4;

        Move smokescreen = new Move("smokescreen", Type.normal, 20, 0, 100);
        smokescreen.stage = Monster.stage_accuracy;
        smokescreen.stageAmount = -1;

        //softboiled

        //solarbeam
    }
}
