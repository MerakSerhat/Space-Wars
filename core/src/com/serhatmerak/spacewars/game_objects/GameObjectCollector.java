package com.serhatmerak.spacewars.game_objects;

/**
 * Created by Serhat Merak on 30.04.2018.
 */

public class GameObjectCollector {
    public enum State{
        GEM,WEAPON,COIN
    }
    
    public State state;
    public Object object;
    public float percent;

    public GameObjectCollector(){

    }

    public GameObjectCollector(State state,Object object){
        this.state = state;
        this.object = object;
        this.percent = 100;
    }

    public GameObjectCollector addGem(Gem.GemStyle gemStyle,float percent){
        this.state = State.GEM;
        this.percent = percent;
        this.object = gemStyle;

        return this;
    }

    public GameObjectCollector addWep(Weapon.WeaponStyle weaponStyle, float percent){
        this.state = State.WEAPON;
        this.percent = percent;
        this.object = weaponStyle;

        return this;
    }

    public GameObjectCollector addCoin(int coin){
        this.state = State.COIN;
        this.object = coin;
        this.percent = 100;

        return this;
    }


}
