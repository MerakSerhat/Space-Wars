package com.serhatmerak.spacewars.database;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.serhatmerak.spacewars.GameMain;
import com.serhatmerak.spacewars.User;
import com.serhatmerak.spacewars.game_objects.Gem;
import com.serhatmerak.spacewars.game_objects.Mission;
import com.serhatmerak.spacewars.game_objects.Weapon;
import com.serhatmerak.spacewars.helpers.MissionDatas;
import com.serhatmerak.spacewars.pets.PetData;
import com.serhatmerak.spacewars.pets.PetStyle;
import com.serhatmerak.spacewars.ships.Ship;
import com.serhatmerak.spacewars.ships.SpaceShipStyle;

/**
 * Created by Serhat Merak on 12.07.2018.
 */

public class GameManager {
    public static final GameManager gameManager = new GameManager();

    public GameMain game;
    private FileHandle gemBagFile = Gdx.files.local("bin/gemBag.data");
    private FileHandle wepBagFile = Gdx.files.local("bin/wepBag.data");
    private FileHandle slotFile = Gdx.files.local("bin/slotFile.data");
    private FileHandle missionFile = Gdx.files.local("bin/mission.data");

    private Preferences preferences = Gdx.app.getPreferences("SpaceWars");

    public void initalize(){
        if(!preferences.contains(SpaceShipStyle.Ship1.toString())) {
            preferences.putBoolean(SpaceShipStyle.Ship1.toString(), true);
            preferences.putBoolean(SpaceShipStyle.Ship2.toString(), false);
            preferences.putBoolean(SpaceShipStyle.Ship3.toString(), false);
            preferences.putBoolean(SpaceShipStyle.Ship4.toString(), false);
            preferences.putBoolean(SpaceShipStyle.Ship5.toString(), false);
            preferences.putBoolean(SpaceShipStyle.Ship6.toString(), false);
            preferences.putBoolean(SpaceShipStyle.Ship7.toString(), false);

            preferences.putBoolean(PetStyle.PET1.toString(),true);
            preferences.putBoolean(PetStyle.PET2.toString(),false);
            preferences.putBoolean(PetStyle.PET3.toString(),false);
            preferences.putBoolean(PetStyle.PET4.toString(),false);

        }
    }

    public void openShip(SpaceShipStyle shipStyle){
        preferences.putBoolean(shipStyle.toString(),true);
        preferences.flush();
    }

    public void openPet(PetStyle petStyle){
        preferences.putBoolean(petStyle.toString(),true);
        preferences.flush();
    }

    public void saveCoin() {
        preferences.putInteger("coin", User.user.coin);
        preferences.flush();

    }
    public int loadCoin(){
        return preferences.getInteger("coin");
    }

    public void saveShips(){
        preferences.putString("pet",PetData.petData.petStyle.toString());
        preferences.putString("ship",User.user.shipStyle.toString());

        preferences.flush();
    }

    public void getShips(){
        User.user.shipStyle = SpaceShipStyle.valueOf(preferences.getString("ship",SpaceShipStyle.Ship1.toString()));
        PetData.petData.petStyle = PetStyle.valueOf(preferences.getString("pet",PetStyle.PET1.toString()));
    }

    public boolean isAvailable(SpaceShipStyle shipStyle){
        System.out.println(shipStyle == null);
        return preferences.getBoolean(shipStyle.toString(),false);
    }

    public boolean isAvailable(PetStyle petStyle){
        return preferences.getBoolean(petStyle.toString(),false);
    }

    private GameManager() {
    }

    public Array<Gem> getGemBag() {
        if (gemBagFile.exists()) {
            String text = gemBagFile.readString();
            String wordsArray[] = text.split("\\r?\\n");
            Array<Gem> gemBag = new Array<Gem>();

            Gem gem = null;
            for (String line : wordsArray) {
                if (line.length() > 6 && line.substring(0, 6).equals("style:")) {
                    Gem.GemStyle gemStyle = Gem.GemStyle.valueOf(line.substring(6, line.length()));
                    gem = new Gem(gemStyle);
                } else if (line.equals("]")) {
                    gemBag.add(gem);
                }
            }

            return gemBag;
        } else return new Array<Gem>();
    }

    public void updateGemBag() {
        gemBagFile.writeString("", false);
        for (Gem gem : User.user.gemBag) {
            addGem(gem);
        }
    }

    public void addGem(Gem gem) {
        String txt = "\n[";
        txt += "\nstyle:" + gem.gemStyle;
        txt += "\n]";
        gemBagFile.writeString(txt, true);
    }

    public Array<Weapon> getWepBag() {
        if (wepBagFile.exists()) {
            String text = wepBagFile.readString();
            String wordsArray[] = text.split("\\r?\\n");
            Array<Weapon> wepBag = new Array<Weapon>();

            Weapon weapon = null;
            for (String line : wordsArray) {
                if (line.length() > 6 && line.substring(0, 6).equals("style:")) {
                    Weapon.WeaponStyle weaponStyle = Weapon.WeaponStyle.valueOf(line.substring(6, line.length()));
                    weapon = new Weapon(weaponStyle);
                } else if (line.length() > 6 && line.substring(0, 6).equals("level:")) {
                    weapon.level = Integer.valueOf(line.substring(6, line.length())) - 1;
                    weapon.levelUp();
                } else if (line.equals("]")) {
                    wepBag.add(weapon);
                }
            }

            return wepBag;
        } else return new Array<Weapon>();
    }

    public void updateWepBag() {
        wepBagFile.writeString("", false);
        for (Weapon wep : User.user.weaponBag) {
            addWep(wep);
        }
    }

    public void addWep(Weapon weapon) {
        String txt = "\n[";
        txt += "\nstyle:" + weapon.weaponStyle;
        txt += "\nlevel:" + weapon.level;
        txt += "\n]";
        wepBagFile.writeString(txt, true);
    }

    public void saveSlots() {
        slotFile.writeString("ship:[\n",false);
        slotFile.writeString("gems:[\n",true);
        for (int i = 0; i < 5; i++) {
            if(User.user.gems.get(i) == null){
                slotFile.writeString("null:" + i + "\n",true);
            }else {
                String txt = "[\n";
                txt += "style:" + User.user.gems.get(i).gemStyle + "\n";
                txt += "index:" + i + "\n";
                txt += "]\n";
                slotFile.writeString(txt,true);
            }
        }
        slotFile.writeString("]\n",true);
        slotFile.writeString("weapons:[\n",true);
        for (int i = 0; i < 3; i++) {
            if(User.user.weapons.get(i) == null){
                slotFile.writeString("null:" + i + "\n",true);
            }else {
                String txt = "[\n";
                txt += "style:" + User.user.weapons.get(i).weaponStyle + "\n";
                txt += "level:" + User.user.weapons.get(i).level + "\n";
                txt += "index:" + i + "\n";
                txt += "]\n";
                slotFile.writeString(txt,true);
            }
        }
        slotFile.writeString("]\n",true);
        slotFile.writeString("]\n",true);
        slotFile.writeString("pet:[\n",true);
        slotFile.writeString("gems:[\n",true);
        for (int i = 0; i < PetData.petData.petStyle.gemNest; i++) {
            if(PetData.petData.gems.get(i) == null){
                slotFile.writeString("null:" + i + "\n",true);
            }else {
                String txt = "[\n";
                txt += "style:" + PetData.petData.gems.get(i).gemStyle + "\n";
                txt += "index:" + i + "\n";
                txt += "]\n";
                slotFile.writeString(txt,true);
            }
        }
        slotFile.writeString("]\n",true);
        slotFile.writeString("weapons:[\n",true);
        for (int i = 0; i < PetData.petData.petStyle.weaponNest; i++) {
            if(PetData.petData.weapons.get(i) == null){
                slotFile.writeString("null:" + i + "\n",true);
            }else {
                String txt = "[\n";
                txt += "style:" + PetData.petData.weapons.get(i).weaponStyle + "\n";
                txt += "level:" + PetData.petData.weapons.get(i).level + "\n";
                txt += "index:" + i + "\n";
                txt += "]\n";
                slotFile.writeString(txt,true);
            }
        }
        slotFile.writeString("]\n",true);
        slotFile.writeString("]\n",true);
    }
    public void getSlots(){
        boolean isShip = false;
        boolean isGem = false;

        Gem gem = null;
        Weapon weapon = null;
        if (slotFile.exists()) {
            String text = slotFile.readString();
            String wordsArray[] = text.split("\\r?\\n");

            for (String line:wordsArray) {
                System.out.println(line);
                if(line.equals("ship:[")){
                    isShip = true;
                }else if (line.equals("gems:[")){
                    isGem = true;
                }else if(line.equals("pet:[")){
                    isShip = false;
                }else if (line.equals("weapons:[")){
                    isGem = false;
                }
                if(isGem){
                    if(line.length() > 6 && line.substring(0,6).equals("style:")){
                        gem = new Gem(Gem.GemStyle.valueOf(line.substring(6,line.length())));
                    }else if(line.length() > 6 && line.substring(0,6).equals("index:")){
                        if (isShip){
                            User.user.gems.set(Integer.valueOf(line.substring(6,line.length())),gem);
                        }else {
                            PetData.petData.gems.set(Integer.valueOf(line.substring(6,line.length())),gem);
                        }
                    }
                }else {
                    if(line.length() > 6 && line.substring(0,6).equals("style:")){
                        weapon = new Weapon(Weapon.WeaponStyle.valueOf(line.substring(6,line.length())));
                    }else if(line.length() > 6 && line.substring(0,6).equals("level:")){
                        weapon.level = Integer.valueOf(line.substring(6,line.length())) - 1;
                        weapon.levelUp();
                    }else if(line.length() > 6 && line.substring(0,6).equals("index:")){
                        if(isShip){
                            User.user.weapons.set(Integer.valueOf(line.substring(6,line.length())),weapon);
                        }else {
                            PetData.petData.weapons.set(Integer.valueOf(line.substring(6,line.length())),weapon);
                        }
                    }
                }
            }


        }

    }

    public void saveMission(){
        if(User.user.mission == null){
            missionFile.writeString("null",false);
        }else {
            String txt;
            Mission mission = User.user.mission;
            Mission.ComplexMission complexMission = mission.getComplexMission();
            txt = mission.id + "\n";
            for (Mission.MiniMission miniMission: complexMission.allMiniMissions) {
                txt += miniMission.done +"\n";
            }

            missionFile.writeString(txt,false);
        }

    }
    public Mission getMission(){
        if(missionFile.exists()){
            if(missionFile.readString().equals("null"))
                return null;
            else {
                String text = missionFile.readString();
                String wordsArray[] = text.split("\\r?\\n");

                Mission mission = MissionDatas.missionDatas.missions.get(Integer.valueOf(wordsArray[0]));
                int i = 0;
                for (String line:wordsArray) {
                    if(i != 0){
                        Mission.MiniMission miniMission = mission.getComplexMission().allMiniMissions.get(i - 1);

                        miniMission.done = Integer.valueOf(line);
                        if(miniMission.amount - miniMission.done <= 0) {
                            miniMission.complated = true;
                        }
                    }

                    i++;
                }

                return mission;
            }
        }else return null;
    }

}
