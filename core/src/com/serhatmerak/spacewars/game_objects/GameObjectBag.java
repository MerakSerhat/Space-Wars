package com.serhatmerak.spacewars.game_objects;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Serhat Merak on 1.05.2018.
 */

public class GameObjectBag{

    public Array<GameObjectCollector> gameObjectCollectors;

    public GameObjectBag(GameObjectCollector[] collectors){
        gameObjectCollectors = new Array<GameObjectCollector>();
        for (int i = 0;i< collectors.length; i++ ){
            if(MathUtils.random(1,100) <= collectors[i].percent)
                gameObjectCollectors.add(collectors[i]);
        }
    }

}
