package Sharpshooter.powers;

import Sharpshooter.SharpshooterMod;
import Sharpshooter.characters.sharpshooter;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;

public class LeakagePower extends SharpshooterAbstractPower {
    public static final String POWER_ID = SharpshooterMod.makeID("LeakagePower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static double RATE = 0.5;
    public LeakagePower(AbstractCreature owner, int amount)
    {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.type = PowerType.DEBUFF;
        this.img = new Texture(SharpshooterMod.makePowerPath("LeakagePower.png"));
        updateDescription();
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }


    @Override
    public void atEndOfTurn(boolean isPlayer) {
        super.atEndOfTurn(isPlayer);
        addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
    }

    @Override
    public float atDamageGive(float damage, DamageInfo.DamageType damageType, AbstractCard card) {
        if (card.hasTag(sharpshooter.Enums.FIREARMS))
        {
            damage *= RATE;
            return damage;
        }
        else
        {
            return super.atDamageGive(damage,damageType,card);
        }
    }
}