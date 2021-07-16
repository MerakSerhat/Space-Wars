package com.serhatmerak.spacewars.helpers;

import com.badlogic.gdx.Gdx;
import com.serhatmerak.spacewars.database.GameManager;
import com.serhatmerak.spacewars.game_objects.GameObjectCollector;
import com.serhatmerak.spacewars.game_objects.Mission;
import com.serhatmerak.spacewars.maps.Map;
import com.serhatmerak.spacewars.ships.SpaceShipStyle;

/**
 * Created by Serhat Merak on 22.05.2018.
 */

public class MissionController {
    private Mission mission;
    private Class<? extends Object> map;
    private boolean save = false;
    private float goMissionSec = 0;

    public void setMission(Mission mission) {
        this.mission = mission;
    }

    public void setMap(Class<? extends Object> map) {
        this.map = map;
    }

    public void beatenEnemy(SpaceShipStyle shipStyle){
        if(mission!= null) {
            for (Mission.BeatMission beatMission : mission.getComplexMission().beatMissions) {
                if (beatMission.beatEnemy) {
                    if (beatMission.map == null) {
                        if (beatMission.spaceShipStyle == shipStyle && beatMission.done < beatMission.amount) {
                            beatMission.done++;
                            save = true;
                        }
                    } else if (beatMission.map == map) {
                        if (beatMission.spaceShipStyle == shipStyle && beatMission.done < beatMission.amount) {
                            beatMission.done++;
                            save = true;
                        }
                    }

                    if (beatMission.amount - beatMission.done == 0)
                        beatMission.complated = true;
                }
            }
        }
    }

    public void beatenCrystal(){
        if(mission != null) {
            for (Mission.BeatMission beatMission : mission.getComplexMission().beatMissions) {
                if (!beatMission.beatEnemy) {
                    if (beatMission.map == null) {
                        beatMission.done++;
                        save = true;
                    } else if (beatMission.map == map) {
                        beatMission.done++;
                        save = true;
                    }

                    if (beatMission.amount - beatMission.done == 0)
                        beatMission.complated = true;

                }
            }
        }
    }

    public void collected(GameObjectCollector gameObjectCollector,boolean fromCrystal,SpaceShipStyle shipStyle){
        if(mission!= null){
            for(Mission.FindMission findMission: mission.getComplexMission().findMissions) {
                if(findMission.fromCrystal){
                    if(fromCrystal){
                        if(gameObjectCollector.state == findMission.gameObjectCollector.state){
                            if(gameObjectCollector.state == GameObjectCollector.State.COIN){
                                findMission.done += Integer.valueOf(gameObjectCollector.object.toString());
                                save = true;
                            }else {
                                if(gameObjectCollector.object == findMission.gameObjectCollector.object){
                                    findMission.done++;
                                    save = true;
                                }
                            }
                        }
                    }
                }else {

                    if(findMission.enemy == null || findMission.enemy == shipStyle){
                        if(gameObjectCollector.state == findMission.gameObjectCollector.state){
                            if(gameObjectCollector.state == GameObjectCollector.State.COIN){
                                findMission.done += Integer.valueOf(gameObjectCollector.object.toString());
                                save = true;                            }else {
                                if (gameObjectCollector.object == findMission.gameObjectCollector.object){
                                    findMission.done++;
                                    save = true;                                }
                            }
                        }
                    }
                }

                if(findMission.amount - findMission.done <= 0)
                    findMission.complated = true;
            }

        }
    }

    public boolean update(){
        if(mission != null) {
            for (Mission.GoMission goMission : mission.getComplexMission().goMissions) {
                if (goMission.map == map) {
                    if (!goMission.complated) {
                        goMissionSec += Gdx.graphics.getDeltaTime();
                        if(goMissionSec >= 1) {
                            goMission.done++;
                            goMissionSec = 0;
                        }
                        if (goMission.amount - goMission.done == 0) {
                            goMission.complated = true;
                            save = true;
                        }
                    }
                }else if(!goMission.complated && goMission.done != 0){
                    goMission.done = 0;
                    goMissionSec = 0;
                }
            }

            if(save)
                GameManager.gameManager.saveMission();
            save = false;



            return mission.complated;
        }else
            return false;
    }


}
