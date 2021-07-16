package com.serhatmerak.spacewars.ships;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Serhat Merak on 31.03.2018.
 */

public class EnemyController {
    private World world;
    public Array<EnemyShip> enemyShips;

    public EnemyController(World world){
        enemyShips = new Array<EnemyShip>();
        this.world = world;
    }

    public void createRandomEnemies(SpaceShipStyle[] spaceShipStyles, int[] amounts, Vector2 maxPos){
        for (int i = 0; i< spaceShipStyles.length;i++){
            for(int a = 0;a < amounts[i];a++){
                enemyShips.add(new EnemyShip(spaceShipStyles[i],world,new Vector2(
                MathUtils.random(-2000,maxPos.x),MathUtils.random(-2000,maxPos.y))));
            }
        }
    }
}
