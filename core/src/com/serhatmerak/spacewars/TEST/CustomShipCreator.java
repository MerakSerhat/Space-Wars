//package com.serhatmerak.spacewars.TEST;
//
//import com.badlogic.gdx.Gdx;
//import com.badlogic.gdx.Input;
//import com.badlogic.gdx.graphics.GL20;
//import com.badlogic.gdx.graphics.OrthographicCamera;
//import com.badlogic.gdx.graphics.Texture;
//import com.badlogic.gdx.graphics.g2d.Sprite;
//import com.badlogic.gdx.graphics.g2d.SpriteBatch;
//import com.badlogic.gdx.graphics.g2d.TextureAtlas;
//import com.badlogic.gdx.scenes.scene2d.InputEvent;
//import com.badlogic.gdx.scenes.scene2d.Stage;
//import com.badlogic.gdx.scenes.scene2d.ui.Image;
//import com.badlogic.gdx.scenes.scene2d.ui.List;
//import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
//import com.badlogic.gdx.scenes.scene2d.ui.Skin;
//import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
//import com.badlogic.gdx.scenes.scene2d.ui.Window;
//import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
//import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
//import com.badlogic.gdx.utils.Align;
//import com.badlogic.gdx.utils.Array;
//import com.badlogic.gdx.utils.viewport.StretchViewport;
//import com.serhatmerak.spacewars.GameMain;
//import com.serhatmerak.spacewars.User;
//import com.serhatmerak.spacewars.game_objects.GameObjectCollector;
//import com.serhatmerak.spacewars.game_objects.Gem;
//import com.serhatmerak.spacewars.game_objects.Mission;
//import com.serhatmerak.spacewars.game_objects.Weapon;
//import com.serhatmerak.spacewars.helpers.Assets;
//import com.serhatmerak.spacewars.helpers.CustomScreen;
//import com.serhatmerak.spacewars.helpers.GameInfo;
//import com.serhatmerak.spacewars.custom_actors.CustomDialog;
//import com.serhatmerak.spacewars.custom_actors.MissionsPanel;
//import com.serhatmerak.spacewars.maps.Map1;
//import com.serhatmerak.spacewars.maps.Map5;
//import com.serhatmerak.spacewars.maps.Map7;
//import com.serhatmerak.spacewars.ships.SpaceShipStyle;
//
//
///**
// * Created by Serhat Merak on 17.04.2018.
// */
//
//public class CustomShipCreator extends CustomScreen{
//
//    private GameMain game;
//
//    private OrthographicCamera camera;
//    private SpriteBatch batch;
//    private Stage stage;
//
//    private Array<Image> gems;
//    private Array<Image> weapons;
//    private Image ship;
//
//    private List<String> list;
//    private Array<String> gemNames;
//    private Array<String> weaponNames;
//    private Array<String> shipNames;
//
//    private boolean changeGems;
//    private int index;
//
//    private TextButton button;
//    private Skin skin;
//    private ScrollPane scrollPane;
//    private CustomDialog customDialog;
//
//    public CustomShipCreator(GameMain game){
//        this.game = game;
//
//        skin = new Skin(Gdx.files.internal("skin/uiskin.json"),new TextureAtlas(Gdx.files.internal("skin/uiskin.atlas")));
//
//
//        init();
//        createGems();
//        createWeapons();
//        createList();
//        createShip();
//        addListListener();
//        createButton();
//
//        MissionsPanel missionHud = new MissionsPanel();
//        missionHud.setPosition(500,0);
//        missionHud.setSize(missionHud.menu1.getWidth(),missionHud.menu1.getHeight());
//        missionHud.addToStage(stage);
//
//        Mission mission = new Mission();
//        mission.setTxtTitle("Mission 1");
//        mission.setTxtMission("Beat 25 [RED]"+ SpaceShipStyle.RedShip4.name() + "[] in Map5" +
//                " and collect 2 [#66ffff]Crystals[] in Map1");
//        mission.addPrize(new GameObjectCollector().addCoin(500));
//        mission.addPrize(new GameObjectCollector().addGem(Gem.GemStyle.RED3,100));
//        mission.addPrize(new GameObjectCollector().addWep(Weapon.WeaponStyle.Red_2,100));
//
//        Mission.ComplexMission complexMission = new Mission.ComplexMission();
//
//        Mission.BeatMission beatMission = new Mission.BeatMission();
//        beatMission.amount = 25;
//        beatMission.spaceShipStyle = SpaceShipStyle.RedShip4;
//        beatMission.map = Map5.class;
//
//        Mission.BeatMission beatMission2 = new Mission.BeatMission();
//        beatMission2.amount = 2;
//        beatMission2.map = Map1.class;
//        beatMission2.beatEnemy = false;
//
//        Mission.GoMission goMission = new Mission.GoMission();
//        goMission.durationOfStay = 60;
//        goMission.map = Map7.class;
//
//        Mission.FindMission findMission = new Mission.FindMission();
//        findMission.gameObjectCollector = new GameObjectCollector().addWep(Weapon.WeaponStyle.Yellow_2,100);
//        findMission.amount = 1;
//        findMission.enemy = SpaceShipStyle.Droid5;
//
//
//        complexMission.addBeatMission(beatMission);
//        complexMission.addBeatMission(beatMission2);
//        complexMission.addGoMission(goMission);
//        complexMission.addFindMission(findMission);
//
//        mission.setComplexMission(complexMission);
//
////        CurrentMissionPanel currentMissionPanel = new CurrentMissionPanel();
////        currentMissionPanel.setMission(mission);
////        currentMissionPanel.setPosition(500,0);
////        stage.addActor(currentMissionPanel);
//
//        for (int i=0;i<10;i++)
//            missionHud.addMission(mission);
//
//        Window.WindowStyle windowStyle = new Window.WindowStyle();
//        windowStyle.titleFont = Assets.getInstance().assetManager.get(Assets.fntSaraniNorm);
//        windowStyle.background = new SpriteDrawable(new Sprite(Assets.getInstance().assetManager.get(Assets.menu2,Texture.class)));
//        customDialog = new CustomDialog(windowStyle);
//        customDialog.setMessage("This is a custom dialog example!! \n Please answer the question.");
//        customDialog.getTitleLabel().setText("CONFIRMATION");
//        customDialog.setConfirmationButtons();
//
//        customDialog.pack();
//        customDialog.show(stage);
//
//    }
//
//    private void createButton() {
//        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
//        textButtonStyle.font = Assets.getInstance().assetManager.get(Assets.fntAerial);
//        textButtonStyle.up = new SpriteDrawable(new Sprite(Assets.getInstance().assetManager.get(Assets.padknock,Texture.class)));
//
//        button = new TextButton("TEST",textButtonStyle);
//        button.setBounds(50,1000,200,60);
//        stage.addActor(button);
//
//        button.addListener(new ClickListener(){
//            @Override
//            public void clicked(InputEvent event, float x, float y) {
//                game.setScreen(new TestScreen(game));
//            }
//        });
//    }
//    private void addListListener() {
//
//        list.addListener(new ClickListener(){
//            @Override
//            public void clicked(InputEvent event, float x, float y) {
//                try {
//                    if (changeGems) {
//                        int gemID = gemNames.indexOf(list.getSelected(), true);
//                        Gem.GemStyle gemStyle = Gem.GemStyle.values()[gemID];
//                        Gem gem = new Gem(gemStyle);
//                        User.user.gems.removeValue(User.user.gems.get(index), true);
//                        User.user.addGem(gem);
//                        gems.get(index).setDrawable(new SpriteDrawable(new Sprite(gem.img)));
//                        gems.get(index).setUserObject(gem);
//                    } else {
//                        int weaponId = weaponNames.indexOf(list.getSelected(), true);
//                        Weapon.WeaponStyle weaponStyle = Weapon.WeaponStyle.values()[weaponId];
//                        Weapon weapon = new Weapon(weaponStyle);
//                        User.user.weapons.removeValue(User.user.weapons.get(index), true);
//                        User.user.addWeapon(weapon);
//                        weapons.get(index).setDrawable(new SpriteDrawable(new Sprite(weapon.img)));
//                        weapons.get(index).setUserObject(weapon);
//                    }
//                }catch (Exception e){}
//            }
//        });
//    }
//
//    private void init() {
//
//        gemNames = new Array<String>();
//        for(int i= 0;i< Gem.GemStyle.values().length;i++) {
//            gemNames.add(Gem.GemStyle.values()[i].name);
//        }
//
//        weaponNames = new Array<String>();
//        for(int i = 0; i< Weapon.WeaponStyle.values().length; i++) {
//            weaponNames.add(Weapon.WeaponStyle.values()[i].name);
//        }
//
//        shipNames = new Array<String>();
//        shipNames.add("Ship 1");
//        shipNames.add("Ship 2");
//        shipNames.add("Ship 3");
//        shipNames.add("Ship 4");
//        shipNames.add("Ship 5");
//        shipNames.add("Ship 6");
//        shipNames.add("Ship 7");
//
//
//
//        camera = new OrthographicCamera();
//        camera.setToOrtho(false, GameInfo.WIDTH,GameInfo.HEIGHT);
//        batch = new SpriteBatch();
//
//        stage = new Stage(new StretchViewport(GameInfo.WIDTH,GameInfo.HEIGHT,camera),batch);
//        Gdx.input.setInputProcessor(stage);
//
//        gems = new Array<Image>(5);
//        weapons = new Array<Image>(3);
//    }
//    private void createShip() {
//        ship = new Image(User.user.getShipTexture());
//        ship.setPosition(750,750);
//        stage.addActor(ship);
//
//    }
//    private void createList() {
//
//        list = new List<String>(skin);
//        list.getStyle().font = Assets.getInstance().assetManager.get(Assets.fntAerial);
//        list.pack();
//
//        Sprite menu2 = new Sprite(new Texture("gui/Menu6.png"));
//
//        Image img = new Image(new SpriteDrawable(menu2));
//        img.setSize(300,2000);
//
//        ScrollPane scrollPane = new ScrollPane(list,skin);
//        scrollPane.setWidth(400);
//        scrollPane.setHeight(800);
//        scrollPane.setPosition(GameInfo.WIDTH - 250,GameInfo.HEIGHT / 2,Align.center);
//        stage.addActor(scrollPane);
//
//
//
//    }
//    private void createWeapons() {
//        for (int i= 0;i < 3; i++){
//            Image img = new Image();
//            img.setBounds(250, 50 + i * 220,200,200);
//            img.setDrawable(new SpriteDrawable(new Sprite(User.user.weapons.get(i).img)));
//            stage.addActor(img);
//            img.setUserObject(User.user.weapons.get(i));
//            weapons.add(img);
//        }
//
//        for (final Image img : weapons) {
//            img.addListener(new ClickListener() {
//                @Override
//                public void clicked(InputEvent event, float x, float y) {
//                    list.setItems(weaponNames);
//                    list.setSelectedIndex(weaponNames.indexOf(((Weapon) img.getUserObject()).name, true));
//                    list.pack();
//                    changeGems = false;
//                    index = weapons.indexOf(img,true);
//                }
//            });
//        }
//    }
//    private void createGems() {
//        for (int i= 0;i < 5; i++){
//            Image img = new Image();
//            img.setBounds(50, 50 + i * 180,141,144);
//            img.setDrawable(new SpriteDrawable(new Sprite(User.user.gems.get(i).img)));
//            stage.addActor(img);
//            img.setUserObject(User.user.gems.get(i));
//            gems.add(img);
//        }
//
//        for (final Image img : gems){
//            img.addListener(new ClickListener(){
//                @Override
//                public void clicked(InputEvent event, float x, float y) {
//                    list.setItems(gemNames);
//                    list.setSelectedIndex(gemNames.indexOf(( (Gem)img.getUserObject()).name,true));
//                    list.pack();
//                    changeGems = true;
//                    index = gems.indexOf(img,true);
//                }
//            });
//
//
//        }
//    }
//
//    @Override
//    public void render(float delta) {
//        Gdx.gl.glClearColor(0, 0, 0, 1);
//        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
//
//        stage.act();
//        stage.draw();
//
//        if(Gdx.input.isKeyPressed(Input.Keys.K)){
//            customDialog.show(stage);
//        }
//    }
//}
