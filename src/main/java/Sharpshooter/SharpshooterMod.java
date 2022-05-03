package Sharpshooter;

import Sharpshooter.cards.SharpshooterAbstractCards;
import Sharpshooter.characters.*;
import Sharpshooter.relics.Pistol;
import Sharpshooter.utils.*;
import basemod.AutoAdd;
import basemod.BaseMod;
import basemod.interfaces.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

@SpireInitializer
public class SharpshooterMod implements EditCardsSubscriber, EditRelicsSubscriber, EditStringsSubscriber, EditKeywordsSubscriber, EditCharactersSubscriber, PostInitializeSubscriber, AddAudioSubscriber, OnStartBattleSubscriber {
    private static String modID;
    public static final String ENABLE_PLACEHOLDER_SETTINGS = "enablePlaceholder";
    private static final String MODNAME = "SharpshooterMod";
    private static final String AUTHOR = "QingSongTear";
    private static final String DESCRIPTION = "A base for Slay the Spire to start your own mod from, feat. the Default.";
    private static final String ATTACK_DEFAULT_GRAY = "sharpshooterResources/images/512/bg_attack_default_gray.png";
    private static final String SKILL_DEFAULT_GRAY = "sharpshooterResources/images/512/bg_skill_default_gray.png";
    private static final String POWER_DEFAULT_GRAY = "sharpshooterResources/images/512/bg_power_default_gray.png";
    private static final String ENERGY_ORB_DEFAULT_GRAY = "sharpshooterResources/images/512/card_default_gray_orb.png";
    private static final String CARD_ENERGY_ORB = "sharpshooterResources/images/512/card_small_orb.png";
    private static final String ATTACK_DEFAULT_GRAY_PORTRAIT = "sharpshooterResources/images/1024/bg_attack_default_gray.png";
    private static final String SKILL_DEFAULT_GRAY_PORTRAIT = "sharpshooterResources/images/1024/bg_skill_default_gray.png";
    private static final String POWER_DEFAULT_GRAY_PORTRAIT = "sharpshooterResources/images/1024/bg_power_default_gray.png";
    private static final String ENERGY_ORB_DEFAULT_GRAY_PORTRAIT = "sharpshooterResources/images/1024/card_default_gray_orb.png";
    private static final String THE_DEFAULT_BUTTON = "sharpshooterResources/images/charSelect/sharpshooterButton.png";
    private static final String THE_DEFAULT_PORTRAIT = "sharpshooterResources/images/charSelect/sharpshooterSelect.png";
    public static final String THE_DEFAULT_IDLE = "sharpshooterResources/images/char/sharpshooter/idle.png";
    public static final String THE_DEFAULT_SHOULDER_1 = "sharpshooterResources/images/char/sharpshooter/shoulder.png";
    public static final String THE_DEFAULT_SHOULDER_2 = "sharpshooterResources/images/char/sharpshooter/shoulder2.png";
    public static final String THE_DEFAULT_CORPSE = "sharpshooterResources/images/char/sharpshooter/corpse.png";
    public static boolean enablePlaceholder = true;

    public static Properties theDefaultDefaultSettings = new Properties();
    public static final Color sharpshooter_YELLOW = CardHelper.getColor(254.0f, 223.0f, 0.0f);

    private static boolean alreadyInitialized = false;

    public static String makeCardPath(String resourcePath) {
        return getModID() + "Resources/images/cards/" + resourcePath;
    }

    public static String makeRelicPath(String resourcePath) {
        return getModID() + "Resources/images/relics/" + resourcePath;
    }

    public static String makeRelicOutlinePath(String resourcePath) {
        return getModID() + "Resources/images/relics/outline/" + resourcePath;
    }

    public static String makeOrbPath(String resourcePath) {
        return getModID() + "Resources/orbs/" + resourcePath;
    }

    public static String makePowerPath(String resourcePath) {
        return getModID() + "Resources/images/powers/" + resourcePath;
    }

    public static String makeEffectPath(String resourcePath) {
        return getModID() + "Resources/images/effects/" + resourcePath;
    }

    public static String makeEventPath(String resourcePath) {
        return getModID() + "Resources/images/events/" + resourcePath;
    }

    public static String getModID() {
        return modID;
    }

    public SharpshooterMod() {
        System.out.println("Subscribe to BaseMod hooks");
        BaseMod.subscribe(this);
        setModID("sharpshooter");
        System.out.println("Done subscribing");
        System.out.println("Creating the color " + sharpshooter.Enums.COLOR_LIGHT_BLUE.toString());
        BaseMod.addColor(sharpshooter.Enums.COLOR_LIGHT_BLUE
                , sharpshooter_YELLOW
                , sharpshooter_YELLOW
                , sharpshooter_YELLOW
                , sharpshooter_YELLOW
                , sharpshooter_YELLOW
                , sharpshooter_YELLOW
                , sharpshooter_YELLOW
                , ATTACK_DEFAULT_GRAY
                , SKILL_DEFAULT_GRAY
                , POWER_DEFAULT_GRAY
                , ENERGY_ORB_DEFAULT_GRAY
                , ATTACK_DEFAULT_GRAY_PORTRAIT
                , SKILL_DEFAULT_GRAY_PORTRAIT
                , POWER_DEFAULT_GRAY_PORTRAIT
                , ENERGY_ORB_DEFAULT_GRAY_PORTRAIT
                , CARD_ENERGY_ORB);
        System.out.println("Done creating the color");
        System.out.println("Adding mod settings");
        theDefaultDefaultSettings.setProperty(ENABLE_PLACEHOLDER_SETTINGS, "FALSE");
        try {
            SpireConfig config = new SpireConfig("defaultMod", "theDefaultConfig", theDefaultDefaultSettings);
            config.load();
            enablePlaceholder = config.getBool(ENABLE_PLACEHOLDER_SETTINGS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Done adding mod settings");
    }

    private void setModID(String id) {
        this.modID = id;
    }


    public static void initialize(){
        new SharpshooterMod();
    }


    @Override
    public void receiveAddAudio() {

    }

    @Override
    public void receiveEditCards() {

        new AutoAdd("Sharpshooter").packageFilter(SharpshooterAbstractCards.class).setDefaultSeen(true).cards();
    }

    private static void pathCheck() {
        IDCheckDontTouchPls EXCEPTION_STRINGS = (IDCheckDontTouchPls) new Gson().fromJson(new InputStreamReader(SharpshooterMod.class.getResourceAsStream("/IDCheckStringsDONT-EDIT-AT-ALL.json"), StandardCharsets.UTF_8), IDCheckDontTouchPls.class);
        String packageName = SharpshooterMod.class.getPackage().getName();
        FileHandle resourcePathExists = Gdx.files.internal(getModID() + "Resources");
        if (modID.equals(EXCEPTION_STRINGS.DEVID)) {
            return;
        }
        if (!packageName.equals(getModID())) {
            throw new RuntimeException(EXCEPTION_STRINGS.PACKAGE_EXCEPTION + getModID());
        } else if (!resourcePathExists.exists()) {
            throw new RuntimeException(EXCEPTION_STRINGS.RESOURCE_FOLDER_EXCEPTION + getModID() + "Resources");
        }
    }


    @Override
    public void receiveEditCharacters() {
        System.out.println("Beginning to edit characters. Add " + sharpshooter.Enums.SHARP_SHOOTER.toString());
        BaseMod.addCharacter(new sharpshooter("sharpshooter", sharpshooter.Enums.SHARP_SHOOTER), THE_DEFAULT_BUTTON, THE_DEFAULT_PORTRAIT, sharpshooter.Enums.SHARP_SHOOTER);
        //System.out.println("Added " + sharpshooter.Enums.SHARP_SHOOTER.toString());

    }

    public void receiveEditKeywords() {
        if (Settings.language != Settings.GameLanguage.ENG) {
            this.loadKeywords("zhs");
        }

    }

    private void loadKeywords(String langKey) {
        if (!Gdx.files.internal(getModID() + "Resources/localization/" + langKey).exists()) {
            System.out.println("sharpshooter: Language not found: " + langKey);
        } else {
            KeywordWithProper[] var2 = (KeywordWithProper[])BaseMod.gson.fromJson(GetLocString(langKey, "Keyword"), KeywordWithProper[].class);
            int var3 = var2.length;

            for(int var4 = 0; var4 < var3; ++var4) {
                KeywordWithProper keyword = var2[var4];
                BaseMod.addKeyword("sharpshooter", keyword.PROPER_NAME, keyword.NAMES, keyword.DESCRIPTION);
//                if (keyword.ID.equals("rugged")) {
//                    Tonic.keyword_name = keyword.PROPER_NAME;
//                    Tonic.keyword_description = keyword.DESCRIPTION;
//                }
//
//                if (keyword.ID.equals("bruise")) {
//                    BlackBile.keyword_name = keyword.PROPER_NAME;
//                    BlackBile.keyword_description = keyword.DESCRIPTION;
//                }
            }

        }
    }

    @Override
    public void receiveEditRelics() {
        BaseMod.addRelicToCustomPool(new Pistol(), sharpshooter.Enums.COLOR_LIGHT_BLUE);
        UnlockTracker.markRelicAsSeen(Pistol.ID);
    }

    @Override
    public void receiveEditStrings() {
        loadStrings("zhs");
    }

    private void loadStrings(String langKey) {
        if (!Gdx.files.internal(getModID() + "Resources/localization/" + langKey).exists()) {
            System.out.println("sharpshooter: Language not found: " + langKey);
            return;
        }
        BaseMod.loadCustomStrings(CardStrings.class, GetLocString(langKey, "Card"));
        BaseMod.loadCustomStrings(RelicStrings.class, GetLocString(langKey, "Relic"));
        BaseMod.loadCustomStrings(PowerStrings.class, GetLocString(langKey, "Power"));
        BaseMod.loadCustomStrings(CharacterStrings.class, GetLocString(langKey, "Character"));
        BaseMod.loadCustomStrings(UIStrings.class, GetLocString(langKey, "UI"));
        BaseMod.loadCustomStrings(PotionStrings.class, GetLocString(langKey, "Potion"));
        BaseMod.loadCustomStrings(TutorialStrings.class, GetLocString(langKey, "Tutorial"));
    }

    private static String GetLocString(String locCode, String name) {
        return Gdx.files.internal(getModID() + "Resources/localization/" + locCode + "/SharpshooterMod-" + name + ".json").readString(String.valueOf(StandardCharsets.UTF_8));
    }


    @Override
    public void receiveOnBattleStart(AbstractRoom abstractRoom) {

    }

    @Override
    public void receivePostInitialize() {

    }

    public static String makeID(String idText) {
        return getModID() + ":" + idText;
    }

}
