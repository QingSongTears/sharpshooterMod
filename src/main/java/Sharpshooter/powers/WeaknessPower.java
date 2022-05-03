package Sharpshooter.powers;

import Sharpshooter.SharpshooterMod;
import Sharpshooter.cards.shoots.SuddenDeath;
import Sharpshooter.characters.sharpshooter;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class WeaknessPower extends SharpshooterAbstractPower {
    public static final String POWER_ID = SharpshooterMod.makeID("WeaknessPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static int baseDamage = 5;
    public static int onceDamage = 5;
    public static boolean isUpgrade = false;
    public WeaknessPower(AbstractCreature owner, int amount)
    {
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.type = PowerType.DEBUFF;
        if (this.isUpgrade)
        {
            this.name = "死亡印记";
            this.img = new Texture(SharpshooterMod.makePowerPath("SuddenDeathPower.png"));
        }
        else
        {
            this.img = new Texture(SharpshooterMod.makePowerPath("WeaknessPower.png"));
            this.name = NAME;
        }

        updateDescription();
    }

    public void updateDescription( ) {
        if (this.isUpgrade)
            this.description = DESCRIPTIONS[0] + this.onceDamage + DESCRIPTIONS[1] + DESCRIPTIONS[2];
        else
            this.description = DESCRIPTIONS[0] + onceDamage + DESCRIPTIONS[1];
    }

    @Override
    public void reducePower(int reduceAmount) {
        super.reducePower(reduceAmount);
        if (this.amount == 0)
            addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
    }

    @Override
    public int onAttacked(DamageInfo info, int damageAmount) {
        if (info.type == sharpshooter.Enums.NORMAL_BULLET_DMG)
        {
            addToBot(new DamageAction(this.owner,new DamageInfo(AbstractDungeon.player, onceDamage, DamageInfo.DamageType.HP_LOSS) , AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
            if (this.isUpgrade)
            {
                addToBot(new GainEnergyAction(1));
            }
            reducePower(1);
        }
        return super.onAttacked(info, damageAmount);
    }

    @Override
    public void onApplyPower(AbstractPower power, AbstractCreature target, AbstractCreature source) {
        super.onApplyPower(power, target, source);
        if (this.isUpgrade)
        {
            this.name = "死亡印记";
            this.img = new Texture(SharpshooterMod.makePowerPath("SuddenDeathPower.png"));
            updateDescription();
        }
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        super.onUseCard(card, action);
        if (this.isUpgrade && card.cardID == SuddenDeath.ID)
        {
            this.name = "死亡印记";
            this.img = new Texture(SharpshooterMod.makePowerPath("SuddenDeathPower.png"));
            updateDescription();
        }
    }
}