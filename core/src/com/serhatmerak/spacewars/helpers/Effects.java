package com.serhatmerak.spacewars.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Serhat Merak on 6.04.2018.
 */

public class Effects {

    private Animation<Sprite> engineEfc;
    public float engineEfcElapsedTime = 0;

    private ParticleEffect explosionEfcPrototype;
    private ParticleEffectPool explosionPool;
    private Array<ParticleEffectPool.PooledEffect> explosionEffects;

    private ParticleEffect big_explosionEfcPrototype;
    private ParticleEffectPool big_explosionPool;
    private Array<ParticleEffectPool.PooledEffect> big_explosionEffects;


    public Effects(){
        createEngineEffect();
        createExplosionEfc();
        createBigExplosionEfc();

        addBigExplosionEffect(new Vector2());
        addExplosionEffect(new Vector2());
    }

    private void createBigExplosionEfc() {
        big_explosionEfcPrototype = new ParticleEffect();
        big_explosionEfcPrototype.load(Gdx.files.internal("effects/big_explosion.p"),Gdx.files.internal("effects"));
        big_explosionEfcPrototype.start();

        big_explosionPool = new ParticleEffectPool(big_explosionEfcPrototype,0,100);
        big_explosionEffects = new Array<ParticleEffectPool.PooledEffect>();
    }

    private void createExplosionEfc() {
        explosionEfcPrototype = new ParticleEffect();
        explosionEfcPrototype.load(Gdx.files.internal("effects/explosion.p"),Gdx.files.internal("effects"));
        explosionEfcPrototype.start();

        explosionPool = new ParticleEffectPool(explosionEfcPrototype,0,100);
        explosionEffects = new Array<ParticleEffectPool.PooledEffect>();
    }

    public void addExplosionEffect(Vector2 coor){
        ParticleEffectPool.PooledEffect effect = explosionPool.obtain();
        effect.setPosition(coor.x,coor.y);
        explosionEffects.add(effect);

    }

    public void addBigExplosionEffect(Vector2 coor){
        ParticleEffectPool.PooledEffect effect = big_explosionPool.obtain();
        effect.setPosition(coor.x,coor.y);
        big_explosionEffects.add(effect);

    }

    private void createEngineEffect() {
        TextureAtlas atlas = Assets.getInstance().assetManager.get(Assets.efcEngine);
        Array<Sprite> sprites = new Array<Sprite>();
        for (int i = 0; i < atlas.getRegions().size; i++) {
            sprites.add(new Sprite(atlas.getRegions().get(i)));
        }
        for (Sprite s : sprites) {
            s.flip(false,true);
        }
        engineEfc = new Animation<Sprite>(0.15f,sprites);
    }


    public void drawExplosionEffects(SpriteBatch batch){
        for(ParticleEffectPool.PooledEffect effect : explosionEffects){
            effect.draw(batch,Gdx.graphics.getDeltaTime());
            if(effect.isComplete()){
                explosionEffects.removeValue(effect,true);
                effect.free();
            }
        }
    }
    public void drawBigExplosionEffects(SpriteBatch batch){
        for(ParticleEffectPool.PooledEffect effect : big_explosionEffects){
            effect.draw(batch,Gdx.graphics.getDeltaTime());
            if(effect.isComplete()){
                big_explosionEffects.removeValue(effect,true);
                effect.free();
            }
        }
    }


    public void drawEngineEffect(SpriteBatch batch,Vector2 fireCenter,Vector2 spaceShipCenter,float spaceshipRotation){
        engineEfcElapsedTime += Gdx.graphics.getDeltaTime();
        Sprite sprite = engineEfc.getKeyFrame(engineEfcElapsedTime);
        Vector2 vector2 = rotateAround(fireCenter,spaceShipCenter,spaceshipRotation);
        sprite.setOriginBasedPosition(vector2.x,vector2.y);
        sprite.setRotation(spaceshipRotation);
        sprite.draw(batch);
    }



    private Vector2 rotateAround(Vector2 vector,Vector2 origin,float rotation){
        return vector.sub(origin).rotate(rotation).add(origin);
    }
}
