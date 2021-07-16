package com.serhatmerak.spacewars.helpers;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.I18NBundle;
import com.serhatmerak.spacewars.game_objects.GameObjectCollector;
import com.serhatmerak.spacewars.game_objects.Gem;
import com.serhatmerak.spacewars.game_objects.Mission;
import com.serhatmerak.spacewars.game_objects.Weapon;
import com.serhatmerak.spacewars.maps.Base;
import com.serhatmerak.spacewars.maps.Map;
import com.serhatmerak.spacewars.maps.Map1;
import com.serhatmerak.spacewars.maps.Map2;
import com.serhatmerak.spacewars.maps.Map3;
import com.serhatmerak.spacewars.maps.Map4;
import com.serhatmerak.spacewars.ships.SpaceShipStyle;

/**
 * Created by Serhat Merak on 7.07.2018.
 */

public class MissionDatas {
    public static final MissionDatas missionDatas = new MissionDatas();
    private I18NBundle texts = Assets.getInstance().assetManager.get(Assets.texts);
    private MissionDatas() {}

    public Array<Mission> missions;

    public void create(){
        missions = new Array<Mission>();
        createMission1();
        createMission2();
        createMission3();
        createMission4();
        createMission5();
        createMission6();
    }

    private void createMission1() {
        Mission mission = new Mission();
        mission.id = 0;
        mission.setTxtTitle(texts.get("m2title"));
        mission.addPrize(new GameObjectCollector().addCoin(500));
        mission.addPrize(new GameObjectCollector().addGem(Gem.GemStyle.RED1,100));
        mission.addPrize(new GameObjectCollector().addGem(Gem.GemStyle.GREEN1,100));
        mission.addPrize(new GameObjectCollector().addGem(Gem.GemStyle.YELLOW1,100));
        mission.addPrize(new GameObjectCollector().addGem(Gem.GemStyle.BLUE1,100));

        Mission.ComplexMission complexMission = new Mission.ComplexMission();

        Mission.BeatMission beatMission1 = new Mission.BeatMission();
        beatMission1.amount = 3;
        beatMission1.spaceShipStyle = SpaceShipStyle.RedShip1;
        beatMission1.map = Base.class;
        beatMission1.mapName = Base.name;

        Mission.BeatMission beatMission2 = new Mission.BeatMission();
        beatMission2.amount = 3;
        beatMission2.spaceShipStyle = SpaceShipStyle.GreenShip1;
        beatMission2.map = Base.class;
        beatMission2.mapName = Base.name;

        Mission.BeatMission beatMission3 = new Mission.BeatMission();
        beatMission3.amount = 3;
        beatMission3.spaceShipStyle = SpaceShipStyle.OrangeShip1;
        beatMission3.map = Base.class;
        beatMission3.mapName = Base.name;

        Mission.BeatMission beatMission4 = new Mission.BeatMission();
        beatMission4.amount = 3;
        beatMission4.spaceShipStyle = SpaceShipStyle.BlueShip1;
        beatMission4.map = Base.class;
        beatMission4.mapName = Base.name;


        complexMission.addBeatMission(beatMission1);
        complexMission.addBeatMission(beatMission2);
        complexMission.addBeatMission(beatMission3);
        complexMission.addBeatMission(beatMission4);

        mission.setTxtMission(getExplanation(complexMission));
        mission.setComplexMission(complexMission);
        missions.add(mission);
    }
    private void createMission2() {
        Mission mission = new Mission();
        mission.id = 1;
        mission.setTxtTitle(texts.get("m3title"));
        mission.addPrize(new GameObjectCollector().addCoin(1000));

        Mission.ComplexMission complexMission = new Mission.ComplexMission();

        Mission.BeatMission beatMission = new Mission.BeatMission();
        beatMission.amount = 1;
        beatMission.beatEnemy = false;
        beatMission.map = Base.class;
        beatMission.mapName = Base.name;

        complexMission.addBeatMission(beatMission);
        mission.setTxtMission(getExplanation(complexMission));
        mission.setComplexMission(complexMission);
        missions.add(mission);
    }
    private void createMission3() {
        Mission mission = new Mission();
        mission.setTxtTitle(texts.get("m3title"));
        mission.id = 2;
        mission.addPrize(new GameObjectCollector().addCoin(300));
        mission.addPrize(new GameObjectCollector().addGem(Gem.GemStyle.RED2,100));

        Mission.ComplexMission complexMission = new Mission.ComplexMission();

        Mission.BeatMission beatMission = new Mission.BeatMission();
        beatMission.amount = 10;
        beatMission.spaceShipStyle = SpaceShipStyle.RedShip3;
        beatMission.map = Map1.class;
        beatMission.mapName = Map1.name;

        Mission.GoMission goMission = new Mission.GoMission();
        goMission.amount = 300;
        goMission.map = Map1.class;
        goMission.mapName = Map1.name;

        Mission.FindMission findMission = new Mission.FindMission();
        findMission.gameObjectCollector = new GameObjectCollector().addGem(Gem.GemStyle.RED2,100);
        findMission.amount = 1;
        findMission.enemy = SpaceShipStyle.RedShip3;


        complexMission.addBeatMission(beatMission);
        complexMission.addGoMission(goMission);
        complexMission.addFindMission(findMission);

        mission.setTxtMission(getExplanation(complexMission));
        mission.setComplexMission(complexMission);
        missions.add(mission);
    }
    private void createMission4() {
        Mission mission = new Mission();
        mission.setTxtTitle(texts.get("m4title"));
        mission.id = 3;
        mission.addPrize(new GameObjectCollector().addCoin(300));
        mission.addPrize(new GameObjectCollector().addGem(Gem.GemStyle.YELLOW2,100));

        Mission.ComplexMission complexMission = new Mission.ComplexMission();

        Mission.BeatMission beatMission = new Mission.BeatMission();
        beatMission.amount = 10;
        beatMission.spaceShipStyle = SpaceShipStyle.OrangeShip3;
        beatMission.map = Map2.class;
        beatMission.mapName = Map2.name;

        Mission.GoMission goMission = new Mission.GoMission();
        goMission.amount = 300;
        goMission.map = Map2.class;
        goMission.mapName = Map1.name;

        Mission.FindMission findMission = new Mission.FindMission();
        findMission.gameObjectCollector = new GameObjectCollector().addGem(Gem.GemStyle.YELLOW2,100);
        findMission.amount = 1;
        findMission.enemy = SpaceShipStyle.OrangeShip3;


        complexMission.addBeatMission(beatMission);
        complexMission.addGoMission(goMission);
        complexMission.addFindMission(findMission);

        mission.setTxtMission(getExplanation(complexMission));
        mission.setComplexMission(complexMission);
        missions.add(mission);
    }
    private void createMission5() {
        Mission mission = new Mission();
        mission.setTxtTitle(texts.get("m5title"));
        mission.id = 4;
        mission.addPrize(new GameObjectCollector().addCoin(300));
        mission.addPrize(new GameObjectCollector().addGem(Gem.GemStyle.BLUE2,100));

        Mission.ComplexMission complexMission = new Mission.ComplexMission();

        Mission.BeatMission beatMission = new Mission.BeatMission();
        beatMission.amount = 10;
        beatMission.spaceShipStyle = SpaceShipStyle.BlueShip3;
        beatMission.map = Map3.class;
        beatMission.mapName = Map3.name;

        Mission.GoMission goMission = new Mission.GoMission();
        goMission.amount = 300;
        goMission.map = Map3.class;
        goMission.mapName = Map3.name;

        Mission.FindMission findMission = new Mission.FindMission();
        findMission.gameObjectCollector = new GameObjectCollector().addGem(Gem.GemStyle.YELLOW2,100);
        findMission.amount = 1;
        findMission.enemy = SpaceShipStyle.OrangeShip3;


        complexMission.addBeatMission(beatMission);
        complexMission.addGoMission(goMission);
        complexMission.addFindMission(findMission);

        mission.setTxtMission(getExplanation(complexMission));
        mission.setComplexMission(complexMission);
        missions.add(mission);
    }
    private void createMission6() {
        Mission mission = new Mission();
        mission.setTxtTitle(texts.get("m6title"));
        mission.id = 5;
        mission.addPrize(new GameObjectCollector().addCoin(300));
        mission.addPrize(new GameObjectCollector().addGem(Gem.GemStyle.GREEN2,100));

        Mission.ComplexMission complexMission = new Mission.ComplexMission();

        Mission.BeatMission beatMission = new Mission.BeatMission();
        beatMission.amount = 10;
        beatMission.spaceShipStyle = SpaceShipStyle.GreenShip3;
        beatMission.map = Map4.class;
        beatMission.mapName = Map4.name;

        Mission.GoMission goMission = new Mission.GoMission();
        goMission.amount = 300;
        goMission.map = Map4.class;
        goMission.mapName = Map4.name;

        Mission.FindMission findMission = new Mission.FindMission();
        findMission.gameObjectCollector = new GameObjectCollector().addGem(Gem.GemStyle.GREEN2,100);
        findMission.amount = 1;
        findMission.enemy = SpaceShipStyle.GreenShip3;


        complexMission.addBeatMission(beatMission);
        complexMission.addGoMission(goMission);
        complexMission.addFindMission(findMission);

        mission.setTxtMission(getExplanation(complexMission));
        mission.setComplexMission(complexMission);
        missions.add(mission);
    }



    public String getExplanation(Mission.ComplexMission complexMission){
     String exp = "";
        for (Mission.BeatMission beatMission: complexMission.beatMissions) {
            if(beatMission.beatEnemy)
                exp += "Beat " + beatMission.amount + " " + beatMission.spaceShipStyle.name() + " in " + beatMission.mapName + ",";
            else
                exp += "Beat " + beatMission.amount + " crystals in " + beatMission.mapName + ",";

        }

        for (Mission.FindMission findMission: complexMission.findMissions) {
            if(findMission.gameObjectCollector.state == GameObjectCollector.State.COIN){
                exp += "Find " + findMission.gameObjectCollector.object + " coin";
            }else if (findMission.gameObjectCollector.state == GameObjectCollector.State.WEAPON){
                exp += "Find " + ((Weapon.WeaponStyle) findMission.gameObjectCollector.object).name;
            }else
                exp += "Find " + ((Gem.GemStyle) findMission.gameObjectCollector.object).name;

            if(findMission.fromCrystal)
                exp += "crystal,";
            else if(findMission.enemy != null)
                exp += " from " + findMission.enemy.name() + ",";
            else exp +=",";

            exp += ",";
        }

        for (Mission.GoMission goMission: complexMission.goMissions) {
            exp += "Go to " + goMission.mapName + " and stay there " + goMission.amount + "sec ,";
        }

        exp = exp.substring(0,exp.length() - 1);

        return exp;
    }
}
