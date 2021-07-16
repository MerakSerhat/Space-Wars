package com.serhatmerak.spacewars.helpers;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.I18NBundleLoader;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;
import com.badlogic.gdx.utils.I18NBundle;

import java.util.Locale;

/**
 * Created by Serhat Merak on 18.03.2018.
 */

public class Assets {
    private static final Assets ourInstance = new Assets();
    public static Assets getInstance() {
        return ourInstance;
    }
    private Assets() {}

    private final String characters = "abcçdefgğhıijklmnoööprsştuüvwyxzqABCÇDEFGĞHIİJKLMNOÖPRSŞTUÜVXWYZQ:,?.=!0123456789+\"() / -%";
    public AssetManager assetManager;

    public static String ship1 = "ships/ship/ship1.png";
    public static String ship2 = "ships/ship/ship2.png";
    public static String ship3 = "ships/ship/ship3.png";
    public static String ship4 = "ships/ship/ship4.png";
    public static String ship5 = "ships/ship/ship5.png";
    public static String ship6 = "ships/ship/ship6.png";
    public static String ship7 = "ships/ship/ship7.png";

    public static String redship1 = "ships/red/redship1.png";
    public static String redship2 = "ships/red/redship2.png";
    public static String redship3 = "ships/red/redship3.png";
    public static String redship4 = "ships/red/redship4.png";

    public static String orangeship1 = "ships/orange/orangeship1.png";
    public static String orangeship2 = "ships/orange/orangeship2.png";
    public static String orangeship3 = "ships/orange/orangeship3.png";
    public static String orangeship4 = "ships/orange/orangeship4.png";

    public static String greenship1 = "ships/green/greenship1.png";
    public static String greenship2 = "ships/green/greenship2.png";
    public static String greenship3 = "ships/green/greenship3.png";
    public static String greenship4 = "ships/green/greenship4.png";

    public static String droid1 = "ships/droid/droid1.png";
    public static String droid2 = "ships/droid/droid2.png";
    public static String droid3 = "ships/droid/droid3.png";
    public static String droid4 = "ships/droid/droid4.png";
    public static String droid5 = "ships/droid/droid5.png";

    public static String blueship1 = "ships/blue/blueship1.png";
    public static String blueship2 = "ships/blue/blueship2.png";
    public static String blueship3 = "ships/blue/blueship3.png";
    public static String blueship4 = "ships/blue/blueship4.png";

    public static String blackship1 = "ships/black/blackship1.png";
    public static String blackship2 = "ships/black/blackship2.png";
    public static String blackship3 = "ships/black/blackship3.png";
    public static String blackship4 = "ships/black/blackship4.png";

    public static String crystal_protector = "ships/crystal_protectors/crystal_protector1.png";

    public static String pet1 = "ships/pets/pet1.png";
    public static String pet2 = "ships/pets/pet2.png";
    public static String pet3 = "ships/pets/pet3.png";
    public static String pet4 = "ships/pets/pet4.png";

    public static String bluewep1 = "weapon/bluewep1.png";
    public static String bluewep2 = "weapon/bluewep2.png";
    public static String redwep1 = "weapon/redwep1.png";
    public static String redwep2 = "weapon/redwep2.png";
    public static String yellowwep1 = "weapon/yellowwep1.png";
    public static String yellowwep2 = "weapon/yellowwep2.png";
    public static String greenwep1 = "weapon/greenwep1.png";
    public static String greenwep2 = "weapon/greenwep2.png";
    public static String whitewep = "weapon/whitewep.png";

    public static String laser = "lasers/b1.png";
    public static String safeBorder = "lasers/safeBorder.png";

    public static String bg = "bg.png";
    public static String minimapbg = "minimap bg.png";
    public static String padbg = "pad bg.png";
    public static String padknock = "padknock.png";
    public static String pix = "pix.png";

    public static String fntAerial = "fonts/aerial.ttf";
    public static String fntSarani24 = "fonts/saraniMin.ttf";
    public static String fntSarani34 = "fonts/sarani.ttf";
    public static String fntSarani44 = "fonts/saraniNorm.ttf";
    public static String fntSarani64 = "fonts/saraniBiggest.ttf";

    public static String white_gem1 = "gems/white1.png";
    public static String white_gem2 = "gems/white2.png";
    public static String white_gem3 = "gems/white3.png";
    public static String white_gem4 = "gems/white4.png";
    public static String white_gem5 = "gems/white5.png";
    public static String white_gem6 = "gems/white6.png";
    public static String red_gem1 = "gems/red1.png";
    public static String red_gem2 = "gems/red2.png";
    public static String red_gem3 = "gems/red3.png";
    public static String red_gem4 = "gems/red4.png";
    public static String red_gem5 = "gems/red5.png";
    public static String red_gem6 = "gems/red6.png";
    public static String green_gem1 = "gems/green1.png";
    public static String green_gem2 = "gems/green2.png";
    public static String green_gem3 = "gems/green3.png";
    public static String green_gem4 = "gems/green4.png";
    public static String green_gem5 = "gems/green5.png";
    public static String green_gem6 = "gems/green6.png";
    public static String blue_gem1 = "gems/blue1.png";
    public static String blue_gem2 = "gems/blue2.png";
    public static String blue_gem3 = "gems/blue3.png";
    public static String blue_gem4 = "gems/blue4.png";
    public static String blue_gem5 = "gems/blue5.png";
    public static String blue_gem6 = "gems/blue6.png";
    public static String yellow_gem1 = "gems/yellow1.png";
    public static String yellow_gem2 = "gems/yellow2.png";
    public static String yellow_gem3 = "gems/yellow3.png";
    public static String yellow_gem4 = "gems/yellow4.png";
    public static String yellow_gem5 = "gems/yellow5.png";
    public static String yellow_gem6 = "gems/yellow6.png";

    public static String lyrBlueDust = "bgLayers/blueDust.png";
    public static String lyrPinkDust = "bgLayers/pinkDust.png";
    public static String lyrYellowDust = "bgLayers/yellowDust.png";
    public static String lyrColdNebula= "bgLayers/coldNebula.png";
    public static String lyrHotNebula = "bgLayers/hotNebula.png";
    public static String lyrStar = "bgLayers/star.png";
    public static String lyrEarth = "bgLayers/earth.png";
    public static String lyrPlanet1 = "bgLayers/planet1.png";
    public static String lyrPlanet2 = "bgLayers/planet2.png";
    public static String lyrPlanet3 = "bgLayers/planet3.png";
    public static String lyrRock = "bgLayers/rock.png";
    public static String lyrSun = "bgLayers/sun.png";
    public static String lyrRedAsteroid = "bgLayers/redAsteroids.png";
    public static String lyrGrayAsteroid = "bgLayers/grayAsteroids.png";

    public static String portal = "stations/portal.png";
    public static String mission = "stations/mission.png";


    public static String portal_animation = "portal_animation.pack";
    public static String portal_btn = "portal_btn.png";
    public static String crystal = "crystal.png";

    public static String menu1 = "gui/menus/Menu1.png";
    public static String menu2 = "gui/menus/Menu2.png";
    public static String menu3 = "gui/menus/Menu3.png";
    public static String menu5 = "gui/menus/Menu5.png";
    public static String menu6 = "gui/menus/Menu6.png";
    public static String menu7 = "gui/menus/Menu7.png";
    public static String menu8 = "gui/menus/Menu8.png";
    public static String menu9 = "gui/menus/Menu9.png";
    public static String menu10 = "gui/menus/Menu10.png";
    public static String menu11 = "gui/menus/Menu11.png";

    public static String blueBtnNorm = "gui/buttons/BlueNormal.png";
    public static String blueBtnHover = "gui/buttons/BlueHover.png";
    public static String blueBtnClicked = "gui/buttons/BlueClicked.png";
    public static String yellowBtnNorm = "gui/buttons/YellowNormal.png";
    public static String yellowBtnHover = "gui/buttons/YellowHover.png";
    public static String yellowBtnClicked = "gui/buttons/YellowClicked.png";
    public static String redBtnNorm = "gui/buttons/RedNormal.png";
    public static String redBtnHover = "gui/buttons/RedHover.png";
    public static String redBtnClicked = "gui/buttons/RedClicked.png";
    public static String blackDisabled = "gui/buttons/BlackDisabled.png";
    public static String greenBtnNorm = "gui/buttons/GreenNormal.png";
    public static String greenBtnHover = "gui/buttons/GreenHover.png";
    public static String greenBtnClicked = "gui/buttons/GreenClicked.png";


    public static String box = "gui/others/Box.png";
    public static String boxBorder = "gui/others/Box_border.png";
    public static String scrollbar1 = "gui/others/Scrollbar1.png";
    public static String scrollbar2 = "gui/others/Scrollbar2.png";


    public static String closeBtn = "gui/buttons/Close.png";
    public static String lock = "gui/buttons/Lock.png";
    public static String select = "gui/icons/Select.png";
    public static String arrow = "gui/icons/arrow.png";
    public static String downArrow = "gui/icons/downArrow.png";
    public static String questionMark = "gui/icons/questionMark.png";
    public static String home = "gui/icons/home.png";
    public static String settings = "gui/icons/settings.png";
    public static String right = "gui/icons/right.png";


    public static String blueSquareButton = "gui/buttons/BlueSquareButton.pack";
    public static String greenSquareButton = "gui/buttons/GreenSquareButton.pack";

    public static String backgroundBlack = "gui/others/BackgroundBlack.png";
    public static String checkbox1 = "gui/others/Checkbox1.png";
    public static String checkbox2 = "gui/others/Checkbox2.png";

    public static String efcEngine = "effects/engine_effect.pack";

    public static String texts = "languages/strings";




    public void create(){
        assetManager = new AssetManager();
        loadFonts();
        loadAssets();

    }

    private void loadFonts() {
        FileHandleResolver resolver = new InternalFileHandleResolver();
        assetManager.setLoader(FreeTypeFontGenerator.class,new FreeTypeFontGeneratorLoader(resolver));
        assetManager.setLoader(BitmapFont.class,".ttf",new FreetypeFontLoader(resolver));

        FreetypeFontLoader.FreeTypeFontLoaderParameter aerialFont = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        aerialFont.fontFileName = fntAerial;
        aerialFont.fontParameters.minFilter = Texture.TextureFilter.Linear;
        aerialFont.fontParameters.magFilter = Texture.TextureFilter.Linear;
        aerialFont.fontParameters.size = 28;
        aerialFont.fontParameters.characters = characters;
        assetManager.load(fntAerial, BitmapFont.class, aerialFont);

        FreetypeFontLoader.FreeTypeFontLoaderParameter saraniFont = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        saraniFont.fontFileName = fntSarani34;
        saraniFont.fontParameters.minFilter = Texture.TextureFilter.Linear;
        saraniFont.fontParameters.magFilter = Texture.TextureFilter.Linear;
        saraniFont.fontParameters.size = 34;
        saraniFont.fontParameters.characters = characters;
        assetManager.load(fntSarani34, BitmapFont.class, saraniFont);

        FreetypeFontLoader.FreeTypeFontLoaderParameter saraniFontMin = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        saraniFontMin.fontFileName = fntSarani34;
        saraniFontMin.fontParameters.minFilter = Texture.TextureFilter.Linear;
        saraniFontMin.fontParameters.magFilter = Texture.TextureFilter.Linear;
        saraniFontMin.fontParameters.size = 24;
        saraniFontMin.fontParameters.characters = characters;
        assetManager.load(fntSarani24, BitmapFont.class, saraniFontMin);

        FreetypeFontLoader.FreeTypeFontLoaderParameter saraniFontBig = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        saraniFontBig.fontFileName = fntSarani34;
        saraniFontBig.fontParameters.minFilter = Texture.TextureFilter.Linear;
        saraniFontBig.fontParameters.magFilter = Texture.TextureFilter.Linear;
        saraniFontBig.fontParameters.size = 42;
        saraniFontBig.fontParameters.characters = characters;
        assetManager.load(fntSarani44, BitmapFont.class, saraniFontBig);

        FreetypeFontLoader.FreeTypeFontLoaderParameter saraniFontBiggest = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        saraniFontBiggest.fontFileName = fntSarani34;
        saraniFontBiggest.fontParameters.minFilter = Texture.TextureFilter.Linear;
        saraniFontBiggest.fontParameters.magFilter = Texture.TextureFilter.Linear;
        saraniFontBiggest.fontParameters.size = 64;
        saraniFontBiggest.fontParameters.characters = characters;
        assetManager.load(fntSarani64, BitmapFont.class, saraniFontBiggest);
    }

    private void loadAssets() {
        assetManager.load(ship1, Texture.class);
        assetManager.load(ship2, Texture.class);
        assetManager.load(ship3, Texture.class);
        assetManager.load(ship4, Texture.class);
        assetManager.load(ship5, Texture.class);
        assetManager.load(ship6, Texture.class);
        assetManager.load(ship7, Texture.class);
        assetManager.load(redship1, Texture.class);
        assetManager.load(redship2, Texture.class);
        assetManager.load(redship3, Texture.class);
        assetManager.load(redship4, Texture.class);
        assetManager.load(blueship1, Texture.class);
        assetManager.load(blueship2, Texture.class);
        assetManager.load(blueship3, Texture.class);
        assetManager.load(blueship4, Texture.class);
        assetManager.load(greenship1, Texture.class);
        assetManager.load(greenship2, Texture.class);
        assetManager.load(greenship3, Texture.class);
        assetManager.load(greenship4, Texture.class);
        assetManager.load(blackship1, Texture.class);
        assetManager.load(blackship2, Texture.class);
        assetManager.load(blackship3, Texture.class);
        assetManager.load(blackship4, Texture.class);
        assetManager.load(droid1, Texture.class);
        assetManager.load(droid2, Texture.class);
        assetManager.load(droid3, Texture.class);
        assetManager.load(droid4, Texture.class);
        assetManager.load(droid5, Texture.class);
        assetManager.load(crystal_protector, Texture.class);
        assetManager.load(pet1,Texture.class);
        assetManager.load(pet2,Texture.class);
        assetManager.load(pet3,Texture.class);
        assetManager.load(pet4,Texture.class);
        assetManager.load(orangeship1, Texture.class);
        assetManager.load(orangeship2, Texture.class);
        assetManager.load(orangeship3, Texture.class);
        assetManager.load(orangeship4, Texture.class);
        assetManager.load(yellowwep1, Texture.class);
        assetManager.load(yellowwep2, Texture.class);
        assetManager.load(greenwep1, Texture.class);
        assetManager.load(greenwep2, Texture.class);
        assetManager.load(redwep1, Texture.class);
        assetManager.load(redwep2, Texture.class);
        assetManager.load(bluewep1, Texture.class);
        assetManager.load(bluewep2, Texture.class);
        assetManager.load(whitewep, Texture.class);
        assetManager.load(bg, Texture.class);
        assetManager.load(padbg, Texture.class);
        assetManager.load(padknock, Texture.class);
        assetManager.load(minimapbg, Texture.class);
        assetManager.load(red_gem1, Texture.class);
        assetManager.load(red_gem2, Texture.class);
        assetManager.load(red_gem3, Texture.class);
        assetManager.load(red_gem4, Texture.class);
        assetManager.load(red_gem5, Texture.class);
        assetManager.load(red_gem6, Texture.class);
        assetManager.load(green_gem1, Texture.class);
        assetManager.load(green_gem2, Texture.class);
        assetManager.load(green_gem3, Texture.class);
        assetManager.load(green_gem4, Texture.class);
        assetManager.load(green_gem5, Texture.class);
        assetManager.load(green_gem6, Texture.class);
        assetManager.load(blue_gem1, Texture.class);
        assetManager.load(blue_gem2, Texture.class);
        assetManager.load(blue_gem3, Texture.class);
        assetManager.load(blue_gem4, Texture.class);
        assetManager.load(blue_gem5, Texture.class);
        assetManager.load(blue_gem6, Texture.class);
        assetManager.load(yellow_gem1, Texture.class);
        assetManager.load(yellow_gem2, Texture.class);
        assetManager.load(yellow_gem3, Texture.class);
        assetManager.load(yellow_gem4, Texture.class);
        assetManager.load(yellow_gem5, Texture.class);
        assetManager.load(yellow_gem6, Texture.class);
        assetManager.load(white_gem1, Texture.class);
        assetManager.load(white_gem2, Texture.class);
        assetManager.load(white_gem3, Texture.class);
        assetManager.load(white_gem4, Texture.class);
        assetManager.load(white_gem5, Texture.class);
        assetManager.load(white_gem6, Texture.class);
        assetManager.load(lyrBlueDust,Texture.class);
        assetManager.load(lyrPinkDust,Texture.class);
        assetManager.load(lyrYellowDust,Texture.class);
        assetManager.load(lyrHotNebula,Texture.class);
        assetManager.load(lyrColdNebula,Texture.class);
        assetManager.load(lyrSun,Texture.class);
        assetManager.load(lyrEarth,Texture.class);
        assetManager.load(lyrPlanet1,Texture.class);
        assetManager.load(lyrPlanet2,Texture.class);
        assetManager.load(lyrPlanet3,Texture.class);
        assetManager.load(lyrRock,Texture.class);
        assetManager.load(lyrGrayAsteroid,Texture.class);
        assetManager.load(lyrRedAsteroid,Texture.class);
        assetManager.load(lyrStar,Texture.class);
        assetManager.load(efcEngine, TextureAtlas.class);
        assetManager.load(laser,Texture.class);
        assetManager.load(portal,Texture.class);
        assetManager.load(mission,Texture.class);
        assetManager.load(portal_animation,TextureAtlas.class);
        assetManager.load(portal_btn,Texture.class);
        assetManager.load(crystal,Texture.class);
        assetManager.load(safeBorder,Texture.class);
        assetManager.load(menu1,Texture.class);
        assetManager.load(menu2,Texture.class);
        assetManager.load(menu3,Texture.class);
        assetManager.load(menu5,Texture.class);
        assetManager.load(menu6,Texture.class);
        assetManager.load(menu7,Texture.class);
        assetManager.load(menu8,Texture.class);
        assetManager.load(menu9,Texture.class);
        assetManager.load(menu10,Texture.class);
        assetManager.load(menu11,Texture.class);
        assetManager.load(box,Texture.class);
        assetManager.load(arrow,Texture.class);
        assetManager.load(downArrow,Texture.class);
        assetManager.load(boxBorder,Texture.class);
        assetManager.load(blueBtnNorm,Texture.class);
        assetManager.load(blueBtnHover,Texture.class);
        assetManager.load(yellowBtnClicked,Texture.class);
        assetManager.load(yellowBtnHover,Texture.class);
        assetManager.load(yellowBtnNorm,Texture.class);
        assetManager.load(redBtnClicked,Texture.class);
        assetManager.load(redBtnHover,Texture.class);
        assetManager.load(redBtnNorm,Texture.class);
        assetManager.load(greenBtnClicked,Texture.class);
        assetManager.load(greenBtnHover,Texture.class);
        assetManager.load(greenBtnNorm,Texture.class);
        assetManager.load(blackDisabled,Texture.class);
        assetManager.load(closeBtn,Texture.class);
        assetManager.load(lock,Texture.class);
        assetManager.load(select,Texture.class);
        assetManager.load(home,Texture.class);
        assetManager.load(questionMark,Texture.class);
        assetManager.load(settings,Texture.class);
        assetManager.load(right,Texture.class);
        assetManager.load(blueBtnClicked,Texture.class);
        assetManager.load(backgroundBlack,Texture.class);
        assetManager.load(blueSquareButton,TextureAtlas.class);
        assetManager.load(greenSquareButton,TextureAtlas.class);
        assetManager.load(scrollbar1,Texture.class);
        assetManager.load(scrollbar2,Texture.class);
        assetManager.load(checkbox1,Texture.class);
        assetManager.load(checkbox2,Texture.class);

        //TODO:if tr
        //assetManager.load(texts, I18NBundle.class,new I18NBundleLoader.I18NBundleParameter(new Locale("tr")));
        //TODO:if en
        assetManager.load(texts, I18NBundle.class,new I18NBundleLoader.I18NBundleParameter(new Locale("en")));

        assetManager.load(pix, Texture.class);
    }

    public void setFilterToTextures(){
        assetManager.get(Assets.ship1,Texture.class).setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        assetManager.get(Assets.ship2,Texture.class).setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        assetManager.get(Assets.ship3,Texture.class).setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        assetManager.get(Assets.ship4,Texture.class).setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        assetManager.get(Assets.ship5,Texture.class).setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        assetManager.get(Assets.ship6,Texture.class).setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        assetManager.get(Assets.ship7,Texture.class).setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        assetManager.get(Assets.blueship1,Texture.class).setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        assetManager.get(Assets.blueship2,Texture.class).setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        assetManager.get(Assets.blueship3,Texture.class).setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        assetManager.get(Assets.blueship4,Texture.class).setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        assetManager.get(Assets.lyrHotNebula,Texture.class).setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        assetManager.get(Assets.lyrColdNebula,Texture.class).setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        assetManager.get(Assets.portal_btn,Texture.class).setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        assetManager.get(Assets.crystal,Texture.class).setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        assetManager.get(Assets.menu7,Texture.class).setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        assetManager.get(Assets.menu5,Texture.class).setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        assetManager.get(Assets.menu1,Texture.class).setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        assetManager.get(Assets.menu2,Texture.class).setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        assetManager.get(Assets.menu3,Texture.class).setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        assetManager.get(Assets.menu6,Texture.class).setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        assetManager.get(Assets.menu9,Texture.class).setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        assetManager.get(Assets.menu10,Texture.class).setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        assetManager.get(Assets.menu11,Texture.class).setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        assetManager.get(Assets.closeBtn,Texture.class).setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
//        assetManager.get(Assets.blueBtnClicked,Texture.class).setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
//        assetManager.get(Assets.blueBtnHover,Texture.class).setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
//        assetManager.get(Assets.blueBtnNorm,Texture.class).setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        assetManager.get(Assets.red_gem1,Texture.class).setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        assetManager.get(Assets.red_gem2,Texture.class).setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        assetManager.get(Assets.red_gem3,Texture.class).setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        assetManager.get(Assets.red_gem4,Texture.class).setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        assetManager.get(Assets.red_gem5,Texture.class).setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        assetManager.get(Assets.red_gem6,Texture.class).setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        assetManager.get(Assets.yellow_gem1,Texture.class).setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        assetManager.get(Assets.yellow_gem2,Texture.class).setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        assetManager.get(Assets.yellow_gem3,Texture.class).setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        assetManager.get(Assets.yellow_gem4,Texture.class).setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        assetManager.get(Assets.yellow_gem5,Texture.class).setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        assetManager.get(Assets.yellow_gem6,Texture.class).setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        assetManager.get(Assets.blue_gem1,Texture.class).setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        assetManager.get(Assets.blue_gem2,Texture.class).setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        assetManager.get(Assets.blue_gem3,Texture.class).setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        assetManager.get(Assets.blue_gem4,Texture.class).setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        assetManager.get(Assets.blue_gem5,Texture.class).setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        assetManager.get(Assets.blue_gem6,Texture.class).setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        assetManager.get(Assets.white_gem1,Texture.class).setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        assetManager.get(Assets.white_gem2,Texture.class).setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        assetManager.get(Assets.white_gem3,Texture.class).setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        assetManager.get(Assets.white_gem4,Texture.class).setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        assetManager.get(Assets.white_gem5,Texture.class).setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        assetManager.get(Assets.white_gem6,Texture.class).setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        assetManager.get(Assets.green_gem1,Texture.class).setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        assetManager.get(Assets.green_gem2,Texture.class).setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        assetManager.get(Assets.green_gem3,Texture.class).setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        assetManager.get(Assets.green_gem4,Texture.class).setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        assetManager.get(Assets.green_gem5,Texture.class).setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        assetManager.get(Assets.green_gem6,Texture.class).setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        assetManager.get(Assets.greenwep1,Texture.class).setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        assetManager.get(Assets.greenwep2,Texture.class).setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        assetManager.get(Assets.whitewep,Texture.class).setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        assetManager.get(Assets.redwep1,Texture.class).setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        assetManager.get(Assets.redwep2,Texture.class).setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        assetManager.get(Assets.bluewep1,Texture.class).setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        assetManager.get(Assets.bluewep2,Texture.class).setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        assetManager.get(Assets.yellowwep1,Texture.class).setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        assetManager.get(Assets.yellowwep2,Texture.class).setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        assetManager.get(Assets.arrow,Texture.class).setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        assetManager.get(Assets.downArrow,Texture.class).setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        assetManager.get(Assets.home,Texture.class).setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        assetManager.get(Assets.settings,Texture.class).setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        assetManager.get(Assets.right,Texture.class).setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        assetManager.get(Assets.checkbox2,Texture.class).setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        assetManager.get(Assets.checkbox1,Texture.class).setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        assetManager.get(Assets.questionMark,Texture.class).setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        for (Texture texture:assetManager.get(blueSquareButton,TextureAtlas.class).getTextures())
            texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        for (Texture texture:assetManager.get(greenSquareButton,TextureAtlas.class).getTextures())
            texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);





        assetManager.get(fntSarani34,BitmapFont.class).getData().markupEnabled = true;
        assetManager.get(fntSarani24,BitmapFont.class).getData().markupEnabled = true;
        assetManager.get(fntSarani44,BitmapFont.class).getData().markupEnabled = true;
        assetManager.get(fntSarani64,BitmapFont.class).getData().markupEnabled = true;


    }
}
