package Sharpshooter.characters;


import Sharpshooter.SharpshooterMod;
import Sharpshooter.cards.Defend_sharpshooter;
import Sharpshooter.cards.Strike_sharpshooter;
import Sharpshooter.cards.awakens.BlackRoses;
import Sharpshooter.cards.awakens.SatelliteBeam;
import Sharpshooter.cards.buffs.DeathRevolver;
import Sharpshooter.cards.buffs.EagleEye;
import Sharpshooter.cards.buffs.OverCharge;
import Sharpshooter.cards.bullets.*;
import Sharpshooter.cards.firearms.*;
import Sharpshooter.cards.grenades.FlashGrenade;
import Sharpshooter.cards.grenades.LockOnGrenade;
import Sharpshooter.cards.shoots.*;
import Sharpshooter.powers.*;
import Sharpshooter.relics.Pistol;
import basemod.abstracts.CustomPlayer;
import basemod.animations.AbstractAnimation;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.blue.Strike_Blue;
import com.megacrit.cardcrawl.cards.optionCards.BecomeAlmighty;
import com.megacrit.cardcrawl.cards.purple.MasterReality;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.localization.CharacterStrings;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.screens.CharSelectInfo;

import java.util.ArrayList;
import java.util.Iterator;

public class sharpshooter extends CustomPlayer {

    public static final int ENERGY_PER_TURN = 3;
    public static final int STARTING_HP = 99;
    public static final int MAX_HP = 99;
    public static final int STARTING_GOLD = 99;
    public static final int CARD_DRAW = 5;
    public static final int ORB_SLOTS = 0;
    private static final String ID = SharpshooterMod.makeID("sharpshooter");
    private static final CharacterStrings characterStrings = CardCrawlGame.languagePack.getCharacterString(ID);
    private static final String[] NAMES = characterStrings.NAMES;
    private static final String[] TEXT = characterStrings.TEXT;
    public static final String[] orbTextures = {
            "sharpshooterResources/images/char/sharpshooter/orb/layer1.png",
            "sharpshooterResources/images/char/sharpshooter/orb/layer2.png",
            "sharpshooterResources/images/char/sharpshooter/orb/layer3.png",
            "sharpshooterResources/images/char/sharpshooter/orb/layer4.png",
            "sharpshooterResources/images/char/sharpshooter/orb/layer5.png",
            "sharpshooterResources/images/char/sharpshooter/orb/layer6.png",
            "sharpshooterResources/images/char/sharpshooter/orb/layer1d.png",
            "sharpshooterResources/images/char/sharpshooter/orb/layer2d.png",
            "sharpshooterResources/images/char/sharpshooter/orb/layer3d.png",
            "sharpshooterResources/images/char/sharpshooter/orb/layer4d.png",
            "sharpshooterResources/images/char/sharpshooter/orb/layer5d.png"};
    // public Slot eye = this.skeleton.findSlot("eye");

    public static class Enums {
        @SpireEnum
        public static AbstractPlayer.PlayerClass SHARP_SHOOTER;
        @SpireEnum(name = "SHARP_SHOOTER_YELLOW")
        public static AbstractCard.CardColor COLOR_LIGHT_BLUE;
        @SpireEnum(name = "SHARP_SHOOTER_YELLOW")
        public static CardLibrary.LibraryType LIBRARY_COLOR;
        @SpireEnum(name = "SHARP_SHOOTER_YELLOW")
        public static AbstractCard.CardTags BULLET;
        @SpireEnum(name = "SHARP_SHOOTER_YELLOW")
        public static AbstractCard.CardTags SHOOTING;
        @SpireEnum(name = "SHARP_SHOOTER_YELLOW")
        public static AbstractCard.CardTags GRENADE;
        @SpireEnum(name = "SHARP_SHOOTER_YELLOW")
        public static AbstractCard.CardTags FIREARMS;
        @SpireEnum(name = "SHARP_SHOOTER_YELLOW")
        public static DamageInfo.DamageType FLASH;
        @SpireEnum(name = "SHARP_SHOOTER_YELLOW")
        public static DamageInfo.DamageType NORMAL_BULLET_DMG;
        @SpireEnum(name = "SHARP_SHOOTER_YELLOW")
        public static DamageInfo.DamageType SPECIAL_BULLET_DMG;
        // @SpireEnum(name = "SHARP_SHOOTER_YELLOW")
        // public static DamageInfo.DamageType FIREARM_BULLET_DMG;

        @SpireEnum(name = "SHARP_SHOOTER_YELLOW")
        public static AbstractCard.CardTags BUFF;
        @SpireEnum(name = "SHARP_SHOOTER_YELLOW")
        public static AbstractCard.CardTags AWAKEN;
        @SpireEnum(name = "SHARP_SHOOTER_YELLOW")
        public static AbstractCard.CardTags LOAD;
    }

    public sharpshooter(String name, AbstractPlayer.PlayerClass setClass) {
        super(name, setClass, orbTextures,
                "sharpshooterResources/images/char/sharpshooter/orb/vfx.png", (float[]) null, new AbstractAnimation() { // from class: sharpshooter.characters.sharpshooter.1
                    public AbstractAnimation.Type type() {
                        return AbstractAnimation.Type.NONE;
                    }
                });
        initializeClass(SharpshooterMod.THE_DEFAULT_IDLE
                , SharpshooterMod.THE_DEFAULT_SHOULDER_2
                , SharpshooterMod.THE_DEFAULT_SHOULDER_1
                , SharpshooterMod.THE_DEFAULT_CORPSE
                , getLoadout(), 20.0f, -10.0f, 220.0f, 290.0f
                , new EnergyManager(ENERGY_PER_TURN));
        this.dialogX = this.drawX + (0.0f * Settings.scale);
        this.dialogY = this.drawY + (220.0f * Settings.scale);
    }


    @Override
    public ArrayList<String> getStartingDeck() {
        ArrayList<String> retVal = new ArrayList<>();

        retVal.add(EnhancedStrength.ID);
        retVal.add(HeavyFirearmMastery.ID);
        retVal.add(EagleEye.ID);
        retVal.add(Extruder.ID);
        retVal.add(DangerClose.ID);
        retVal.add(FlamePillar.ID);
        retVal.add(OverBoostPack.ID);
        retVal.add(GrenadeLauncher.ID);
        retVal.add(HeavyFirearmTechnique.ID);
        retVal.add(HeavyWeaponEnhancement.ID);
        retVal.add(SatelliteBeam.ID);
        retVal.add(InterlockSatellite.ID);

//        retVal.add(Strike_sharpshooter.ID);
//        retVal.add(Strike_sharpshooter.ID);
//        retVal.add(Strike_sharpshooter.ID);
//        retVal.add(Strike_sharpshooter.ID);
//        retVal.add(Defend_sharpshooter.ID);
//        retVal.add(Defend_sharpshooter.ID);
//        retVal.add(Defend_sharpshooter.ID);
//        retVal.add(Defend_sharpshooter.ID);
//        retVal.add(BBQ.ID);
//        retVal.add(RisingShot.ID);
        return retVal;
    }

    @Override
    public ArrayList<String> getStartingRelics() {
        ArrayList<String> retVal = new ArrayList<>();
        retVal.add(Pistol.ID);

        return retVal;
    }

    @Override
    public CharSelectInfo getLoadout() {
        return new CharSelectInfo(NAMES[0], TEXT[0], STARTING_HP, MAX_HP, ORB_SLOTS, STARTING_GOLD, CARD_DRAW, this, getStartingRelics(), getStartingDeck(), false);
    }

    @Override
    public String getTitle(PlayerClass playerClass) {
        return  NAMES[0];
    }

    @Override
    public AbstractCard.CardColor getCardColor() {
        return Enums.COLOR_LIGHT_BLUE;
    }

    @Override
    public Color getCardRenderColor() {
        return Color.YELLOW;
    }

    @Override
    public AbstractCard getStartCardForEvent() {
        return new Strike_Blue();
    }

    @Override
    public Color getCardTrailColor() {
        return Color.YELLOW;
    }

    @Override
    public int getAscensionMaxHPLoss() {
        return 0;
    }

    @Override
    public BitmapFont getEnergyNumFont() {
        return FontHelper.energyNumFontRed;
    }

    @Override
    public void doCharSelectScreenSelectEffect() {
        CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.MED, ScreenShake.ShakeDur.SHORT, true);
    }

    @Override
    public String getCustomModeCharacterButtonSoundKey() {
        return null;
    }

    @Override
    public String getLocalizedCharacterName() {
        return NAMES[0];
    }

    @Override
    public AbstractPlayer newInstance() {
        return new sharpshooter(this.name, this.chosenClass);
    }

    @Override
    public Color getSlashAttackColor() {
        return Color.YELLOW;
    }

    @Override
    public AbstractGameAction.AttackEffect[] getSpireHeartSlashEffect() {
        return new AbstractGameAction.AttackEffect[0];
    }

    @Override
    public String getSpireHeartText() {
        return TEXT[0];
    }

    @Override
    public String getVampireText() {
        return TEXT[1];
    }

    @Override
    public void applyStartOfCombatPreDrawLogic() {
        super.applyStartOfCombatPreDrawLogic();

        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this,this,new NormalMagazinePower(this, NormalMagazinePower.MAXAMOUNT), NormalMagazinePower.MAXAMOUNT));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this,this,new SpecialMagazinePower(this, 0), 1));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this,this,new GrenadeBoxPower(this, 0), 1));
        WeaknessPower.isUpgrade = false;
        WeaknessPower.onceDamage = WeaknessPower.baseDamage;
        OverheatingPower.LIMIT = OverheatingPower.BASE_LIMIT;

        //AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this,this,new WeakPower(this,10,false),10));
    }

    public static AbstractCard getTagsCard(AbstractCard.CardTags tags, AbstractCard.CardRarity rarity)
    {
        ArrayList<AbstractCard> list = new ArrayList();
        Iterator var2 = AbstractDungeon.srcCommonCardPool.group.iterator();

        AbstractCard c;
        while(var2.hasNext()) {
            c = (AbstractCard)var2.next();
            if (c.hasTag(tags) && c.rarity != rarity) {
                list.add(c);
            }
        }

        var2 = AbstractDungeon.srcUncommonCardPool.group.iterator();

        while(var2.hasNext()) {
            c = (AbstractCard)var2.next();
            if (c.hasTag(tags) && c.rarity != rarity) {
                list.add(c);
            }
        }

        var2 = AbstractDungeon.srcRareCardPool.group.iterator();

        while(var2.hasNext()) {
            c = (AbstractCard)var2.next();
            if (c.hasTag(tags) && c.rarity != rarity) {
                list.add(c);
            }
        }

        return (AbstractCard)list.get(AbstractDungeon.cardRandomRng.random(list.size() - 1));
    }
}
