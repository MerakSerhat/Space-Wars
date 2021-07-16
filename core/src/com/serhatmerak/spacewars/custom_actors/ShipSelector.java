package com.serhatmerak.spacewars.custom_actors;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Array;
import com.serhatmerak.spacewars.User;
import com.serhatmerak.spacewars.database.GameManager;
import com.serhatmerak.spacewars.game_objects.Gem;
import com.serhatmerak.spacewars.game_objects.Weapon;
import com.serhatmerak.spacewars.helpers.Assets;
import com.serhatmerak.spacewars.pets.PetData;
import com.serhatmerak.spacewars.pets.PetStyle;
import com.serhatmerak.spacewars.ships.Ship;
import com.serhatmerak.spacewars.ships.SpaceShipStyle;

/**
 * Created by Serhat Merak on 21.06.2018.
 */

public class ShipSelector extends Actor {
    private Image background,line1,line2;
    private ImageButton btnRight,btnLeft;
    private Array<SelectorCell> cells;
    private int index = 0,oldIndex;
    public static Stats stats;

    public ShipSelector(){
        cells = new Array<SelectorCell>();

        background = new Image(Assets.getInstance().assetManager.get(Assets.menu8,Texture.class));
        background.setSize(1190,490);
        setSize(1190,490);

        line1 = new Image(Assets.getInstance().assetManager.get(Assets.pix,Texture.class));
        line2 = new Image(Assets.getInstance().assetManager.get(Assets.pix,Texture.class));

        line1.setColor(Color.valueOf("2993d9"));
        line2.setColor(Color.valueOf("2993d9"));

        line1.setSize(6,460);
        line2.setSize(6,460);

        TextureAtlas atlas = Assets.getInstance().assetManager.get(Assets.greenSquareButton);

        SpriteDrawable up = new SpriteDrawable(new Sprite(atlas.findRegion("GreenNormal2")));
        SpriteDrawable over = new SpriteDrawable(new Sprite(atlas.findRegion("GreenHover5")));
        SpriteDrawable down = new SpriteDrawable(new Sprite(atlas.findRegion("GreenClicked8")));

        Sprite right = new Sprite(Assets.getInstance().assetManager.get(Assets.right,Texture.class));
        Sprite left = new Sprite(Assets.getInstance().assetManager.get(Assets.right,Texture.class));

        right.setColor(Color.valueOf("dcffb8"));
        left.setColor(Color.valueOf("dcffb8"));

        left.setFlip(true,false);

        right.setSize(94,94);
        left.setSize(94,94);

        ImageButton.ImageButtonStyle rightStyle = new ImageButton.ImageButtonStyle();
        rightStyle.imageUp = new SpriteDrawable(right);
        rightStyle.up = up;
        rightStyle.over = over;
        rightStyle.down = down;

        ImageButton.ImageButtonStyle leftStyle = new ImageButton.ImageButtonStyle();
        leftStyle.imageUp = new SpriteDrawable(left);
        leftStyle.up = up;
        leftStyle.over = over;
        leftStyle.down = down;

        btnLeft = new ImageButton(leftStyle);
        btnRight = new ImageButton(rightStyle);

        btnLeft.setSize(164,164);
        btnRight.setSize(164,164);

        btnRight.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(index + 1 < cells.size){
                    oldIndex = index;
                    index++;
                    indexChanged();
                }
            }
        });

        btnLeft.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(index  > 0){
                    oldIndex = index;
                    index--;
                    indexChanged();
                }
            }
        });
    }

    private void indexChanged(){
        cells.get(oldIndex).remove();
        cells.get(index).checkState();
        getStage().addActor(cells.get(index));
    }


    public void addCell(SpaceShipStyle spaceShipStyle){
        cells.add(new SelectorCell(spaceShipStyle).setPos(getX() + 220,getY()));
    }

    public void addCell(PetStyle petStyle){
        cells.add(new SelectorCell(petStyle).setPos(getX() + 220,getY()));
    }

    public void pack(){
        int i = 0;
        for (SelectorCell cell:cells){
            if(cell.petStyle != null){
                if(cell.petStyle == PetData.petData.petStyle){
                    index = i;
                }
            }else {
                if(cell.spaceShipStyle == User.user.shipStyle){
                    index = i;
                }
            }
            i++;
        }
        cells.get(index).checkState();
        getStage().addActor(cells.get(index));
    }

    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x, y);
        btnLeft.setPosition(x + 40, y + getHeight() / 2 - btnLeft.getHeight() / 2);
        btnRight.setPosition(x + getWidth() - 40 - btnRight.getWidth(),btnLeft.getY());
        line1.setPosition(btnLeft.getX() +  btnLeft.getWidth() + 10,y + getHeight() / 2 - line1.getHeight() / 2);
        line2.setPosition(btnRight.getX() - 10 - line2.getWidth(),line1.getY());
        background.setPosition(x,y);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if(background.getStage() == null){
            getStage().addActor(background);
            getStage().addActor(btnLeft);
            getStage().addActor(btnRight);
            getStage().addActor(line1);
            getStage().addActor(line2);
        }
    }

    @Override
    public boolean remove() {
        return super.remove();
    }


    public static class SelectorCell extends Actor {
        public TextButton btnInfo,btnSelect,btnSelected,btnUnavailable;
        public enum State{SELECTED,AVAILABLE,UNAVAILABLE}
        public State state;
        public SpaceShipStyle spaceShipStyle;
        public PetStyle petStyle;
        public Image image;

        //todo: State ekle
        public SelectorCell(SpaceShipStyle spaceShipStyle){

            this.spaceShipStyle = spaceShipStyle;
            checkState();
            createActors(spaceShipStyle.shipTexture,state);
        }

        public void checkState() {

            //todo:veritabanından bakl
            if(petStyle != null){
                if(petStyle == PetData.petData.petStyle)
                    state = State.SELECTED;
                else if(GameManager.gameManager.isAvailable(petStyle))
                    state = State.AVAILABLE;
                else
                    state = State.UNAVAILABLE;
            }else {
                if(spaceShipStyle == User.user.shipStyle)
                    state = State.SELECTED;
                else if(GameManager.gameManager.isAvailable(spaceShipStyle))
                    state = State.AVAILABLE;
                else
                    state = State.UNAVAILABLE;
            }
        }

        public SelectorCell(PetStyle petStyle){
            
            this.petStyle = petStyle;
            checkState();
            createActors(petStyle.petTexture,state);
        }
        private void createActors(String texture, State state) {
            image = new Image(Assets.getInstance().assetManager.get(texture, Texture.class));
            if(petStyle != null)
                image.setSize(image.getWidth() * 0.7f,image.getHeight() * 0.7f);

            TextButton.TextButtonStyle infoStyle = new TextButton.TextButtonStyle();
            infoStyle.font = Assets.getInstance().assetManager.get(Assets.fntSarani44);
            infoStyle.up = new SpriteDrawable(new Sprite(Assets.getInstance().assetManager.get(Assets.yellowBtnNorm,Texture.class)));
            infoStyle.over = new SpriteDrawable(new Sprite(Assets.getInstance().assetManager.get(Assets.yellowBtnHover,Texture.class)));
            infoStyle.down = new SpriteDrawable(new Sprite(Assets.getInstance().assetManager.get(Assets.yellowBtnClicked,Texture.class)));

            btnInfo = new TextButton("INFO",infoStyle);
            btnInfo.setSize(360,360 * (btnInfo.getHeight() / btnInfo.getWidth()));
            //TODO:info

            TextButton.TextButtonStyle selectedStyle = new TextButton.TextButtonStyle();
            selectedStyle.font = Assets.getInstance().assetManager.get(Assets.fntSarani44);
            selectedStyle.up = new SpriteDrawable(new Sprite(Assets.getInstance().assetManager.get(Assets.greenBtnNorm,Texture.class)));

            btnSelected = new TextButton("selected",selectedStyle);
            btnSelected.setSize(360,360 * (btnSelected.getHeight() / btnSelected.getWidth()));
            TextButton.TextButtonStyle selectStyle = new TextButton.TextButtonStyle();
            selectStyle.font = Assets.getInstance().assetManager.get(Assets.fntSarani44);
            selectStyle.up = new SpriteDrawable(new Sprite(Assets.getInstance().assetManager.get(Assets.blueBtnNorm,Texture.class)));
            selectStyle.over = new SpriteDrawable(new Sprite(Assets.getInstance().assetManager.get(Assets.blueBtnHover,Texture.class)));
            selectStyle.down = new SpriteDrawable(new Sprite(Assets.getInstance().assetManager.get(Assets.blueBtnClicked,Texture.class)));

            btnSelect = new TextButton("select",selectStyle);
            btnSelect.setSize(360,360 * (btnSelect.getHeight() / btnSelect.getWidth()));
            TextButton.TextButtonStyle unavailableStyle = new TextButton.TextButtonStyle();
            unavailableStyle.font = Assets.getInstance().assetManager.get(Assets.fntSarani44);
            unavailableStyle.up = new SpriteDrawable(new Sprite(Assets.getInstance().assetManager.get(Assets.blackDisabled,Texture.class)));

            btnUnavailable = new TextButton("unavailable",unavailableStyle);
            btnUnavailable.setSize(360,360 * (btnUnavailable.getHeight() / btnUnavailable.getWidth()));

            btnSelect.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    btnSelect.remove();
                    getStage().addActor(btnSelected);

                    Array<Gem> gems = new Array<Gem>();
                    Array<Weapon> weapons = new Array<Weapon>();


                    if(petStyle != null){
                        for (Gem gem:PetData.petData.gems){
                            if(gem != null)
                                gems.add(gem);
                        }

                        for (Weapon weapon:PetData.petData.weapons){
                            if(weapon != null)
                                weapons.add(weapon);
                        }

                        PetData.petData.create(petStyle);

                        for (int i=0;i < gems.size;i++){
                            if(i < petStyle.gemNest)
                                PetData.petData.gems.set(i,gems.get(i));
                            else
                                User.user.gemBag.add(gems.get(i));
                        }

                        for (int i=0;i < weapons.size;i++){
                            if(i < petStyle.weaponNest)
                                PetData.petData.weapons.set(i,weapons.get(i));
                            else
                                User.user.weaponBag.add(weapons.get(i));
                        }

                        GameManager.gameManager.saveSlots();
                    }else {
                        User.user.shipStyle = spaceShipStyle;
                    }

                    GameManager.gameManager.saveShips();
                    checkState();
                    stats.setStats();
                }
            });

            btnInfo.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if(spaceShipStyle != null)
                        getStage().addActor(new ShipInfoPanel(spaceShipStyle));
                    else
                        getStage().addActor(new ShipInfoPanel(petStyle));
                }
            });
        }

        @Override
        public void act(float delta) {
            if(image.getStage() == null){
                getStage().addActor(image);
                getStage().addActor(btnInfo);
                switch (state){
                    case UNAVAILABLE:
                        getStage().addActor(btnUnavailable);
                        break;
                    case SELECTED:
                        getStage().addActor(btnSelected);
                        break;
                    case AVAILABLE:
                        getStage().addActor(btnSelect);
                        break;
                }
            }
        }

        @Override
        public boolean remove() {
            image.remove();
            if(btnUnavailable != null)
                btnUnavailable.remove();
            if(btnSelected != null)
                btnSelected.remove();
            if(btnSelect != null)
                btnSelect.remove();
            btnInfo.remove();

            return super.remove();
        }


        public SelectorCell setPos(float x, float y) {

            btnInfo.setPosition(x + 370,y + 225 + 10);
            if(btnSelect != null)
                btnSelect.setPosition(x + 370,y + 225 - 10 - btnSelect.getHeight());
            if(btnSelected != null)
                btnSelected.setPosition(x + 370,y + 225 - 10 - btnSelected.getHeight());
            if(btnUnavailable != null)
                btnUnavailable.setPosition(x + 370,y + 225 - 10 - btnUnavailable.getHeight());
            image.setPosition(x + 185 - image.getWidth() / 2,y + 225 - image.getHeight() / 2);

            return this;
        }
    }
}
