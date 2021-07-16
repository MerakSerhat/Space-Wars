package com.serhatmerak.spacewars.game_objects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.Array;
import com.serhatmerak.spacewars.gameplay_objects.Crystal;
import com.serhatmerak.spacewars.maps.Map;
import com.serhatmerak.spacewars.ships.SpaceShipStyle;


/**
 * Created by Serhat Merak on 12.05.2018.
 */

public class Mission {

    private String txtMission;
    private String txtPrize = "PRIZE:\n";
    private String txtTitle;
    private ComplexMission complexMission;

    public Array<GameObjectCollector> prizes;
    public boolean complated = false;

    public int id;

    public Mission(){
        prizes = new Array<GameObjectCollector>();
    }

    public void addPrize(GameObjectCollector gameObjectCollector){
        prizes.add(gameObjectCollector);
        if(gameObjectCollector.state == GameObjectCollector.State.COIN)
            txtPrize += "[YELLOW]" +gameObjectCollector.object + " COIN[]\n";
        else if (gameObjectCollector.state == GameObjectCollector.State.GEM){
            Gem.GemStyle gemStyle= (Gem.GemStyle) gameObjectCollector.object;
            String color = gemStyle.color;
            System.out.println(color);
            if(color.equals("[BLUE]"))
                color = "[#0099ff]";
            txtPrize += color+gemStyle.name +"[]\n";
        }else if (gameObjectCollector.state == GameObjectCollector.State.WEAPON) {
            Weapon.WeaponStyle weaponStyle = (Weapon.WeaponStyle) gameObjectCollector.object;
            String color = weaponStyle.color;
            if(color.equals("[BLUE]"))
                color = "[#0099ff]";
            txtPrize += color + weaponStyle.name +"[]\n";
        }
    }

    public String getTxtMission() {
        return txtMission;
    }

    public String getTxtPrize() {
        return txtPrize;
    }

    public String getTxtTitle() {
        return txtTitle;
    }

    public ComplexMission getComplexMission() {
        return complexMission;
    }

    public void setTxtMission(String txtMission) {
        this.txtMission = txtMission;
    }

    public void setTxtTitle(String txtTitle) {
        this.txtTitle = txtTitle;
    }

    public void setComplexMission(ComplexMission complexMission) {
        this.complexMission = complexMission;
    }

    static public class MiniMission{
        public boolean complated = false;
        public int done = 0;
        public int amount;
    }

    static public class BeatMission extends MiniMission{
        public boolean beatEnemy = true;
        public SpaceShipStyle spaceShipStyle;
        public Class< ? extends Map> map;
        public String mapName;
    }

    static public class GoMission extends MiniMission{
        public Class< ? extends Map> map;
        public String mapName;
    }

    static public class FindMission extends MiniMission{
        public GameObjectCollector gameObjectCollector;
        public SpaceShipStyle enemy;
        public boolean fromCrystal = false;
    }

    static public class ComplexMission{
        public Array<MiniMission> allMiniMissions;
        public Array<BeatMission> beatMissions;
        public Array<GoMission> goMissions;
        public Array<FindMission> findMissions;

        public ComplexMission(){
            allMiniMissions = new Array<MiniMission>();
            beatMissions = new Array<BeatMission>();
            goMissions = new Array<GoMission>();
            findMissions = new Array<FindMission>();


        }


        public void addBeatMission(BeatMission beatMission){
            beatMissions.add(beatMission);
            allMiniMissions.add(beatMission);
        }

        public void addGoMission(GoMission goMission){
            goMissions.add(goMission);
            allMiniMissions.add(goMission);
        }

        public void addFindMission(FindMission findMission){
            findMissions.add(findMission);
            allMiniMissions.add(findMission);
        }
    }
}
