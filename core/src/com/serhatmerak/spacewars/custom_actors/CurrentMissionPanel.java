package com.serhatmerak.spacewars.custom_actors;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.I18NBundle;
import com.serhatmerak.spacewars.User;
import com.serhatmerak.spacewars.database.GameManager;
import com.serhatmerak.spacewars.game_objects.GameObjectCollector;
import com.serhatmerak.spacewars.game_objects.Gem;
import com.serhatmerak.spacewars.game_objects.Mission;
import com.serhatmerak.spacewars.game_objects.Weapon;
import com.serhatmerak.spacewars.helpers.Assets;
import com.serhatmerak.spacewars.huds.GamePlayHuds;

/**
 * Created by Serhat Merak on 12.05.2018.
 */

public class CurrentMissionPanel extends MissionTable{


    private GamePlayHuds huds;


    public CurrentMissionPanel(GamePlayHuds huds) {
        super(User.user.mission);
        this.huds = huds;
        selectButton.remove();
        setColor(1,1,1,0.5f);
    }

    public void setAmountLabelTexts(){
            int i = 0;
            for (Mission.BeatMission beatMission : mission.getComplexMission().beatMissions) {
                if (!beatMission.complated) {
                    amountLabels.get(i).setText((beatMission.amount - beatMission.done) + "");
                } else if(!amountLabels.get(i).getText().toString().equals("")){
                    amountLabels.get(i).setText("");
                    labels.get(i).setColor(Color.GREEN);
                }
                i++;

            }

            for (Mission.FindMission findMission : mission.getComplexMission().findMissions) {
                if (!findMission.complated) {
                    amountLabels.get(i).setText((findMission.amount - findMission.done) + "");
                } else if(!amountLabels.get(i).getText().toString().equals("")){
                    amountLabels.get(i).setText("");
                    labels.get(i).setColor(Color.GREEN);
                }
                i++;
            }

            for (Mission.GoMission goMission : mission.getComplexMission().goMissions) {
                if (!goMission.complated) {
                    if (goMission.amount - (int) goMission.done != 0) {
                        amountLabels.get(i).setText((int) (goMission.amount - goMission.done) + "");
                    }
                } else if(!amountLabels.get(i).getText().toString().equals("")){
                    amountLabels.get(i).setText("");
                    labels.get(i).setColor(Color.GREEN);
                }

                i++;
            }

            boolean complated = true;
            for (Mission.MiniMission miniMission : mission.getComplexMission().allMiniMissions) {
                if (!miniMission.complated)
                    complated = false;
            }

            mission.complated = complated;
            if (complated) {
                remove();
                showPrize();
                mission = null;
                User.user.mission = null;
                GameManager.gameManager.saveMission();
            }
    }

    private void showPrize() {
        String alert = texts.get("missionCompleted") + "\n";
        for(GameObjectCollector gameObjectCollector:mission.prizes){
            if(gameObjectCollector.state == GameObjectCollector.State.GEM ){
                Gem.GemStyle gem = (Gem.GemStyle) gameObjectCollector.object;
                alert += "\n"+gem.color+gem.name+"[]";

                User.user.gemBag.add(new Gem(gem));
                GameManager.gameManager.addGem(User.user.gemBag.get(User.user.gemBag.size - 1));
            }else if(gameObjectCollector.state == GameObjectCollector.State.WEAPON ){
                Weapon.WeaponStyle weapon = (Weapon.WeaponStyle) gameObjectCollector.object;
                alert += "\n"+weapon.color + weapon.name+"[]";

                User.user.weaponBag.add(new Weapon(weapon));
                GameManager.gameManager.addWep(User.user.weaponBag.get(User.user.weaponBag.size - 1));
            }else {
                alert += "[YELLOW]" + gameObjectCollector.object + " " + texts.get("coin") + "[]";
                User.user.coin += Integer.valueOf(gameObjectCollector.object.toString());
            }

            GameManager.gameManager.saveCoin();

        }

        huds.showToast(alert);
    }
}
