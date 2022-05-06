package Sharpshooter.powers;

import Sharpshooter.SharpshooterMod;
import Sharpshooter.characters.sharpshooter;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;

public class HeavyFirearmTechniquePower extends SharpshooterAbstractPower {
    public static final String POWER_ID = SharpshooterMod.makeID("HeavyFirearmTechniquePower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private boolean isFirearms = false;
    public HeavyFirearmTechniquePower(AbstractCreature owner, int amount)
    {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.type = PowerType.BUFF;
        this.img = new Texture(SharpshooterMod.makePowerPath("HeavyFirearmTechniquePower.png"));
        updateDescription();
    }

    public void updateDescription() {
        this.amount = this.amount > 100 ? 100: this.amount;
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        super.atEndOfTurn(isPlayer);
        addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        super.onUseCard(card, action);
        if (card.hasTag(sharpshooter.Enums.FIREARMS))
        {
            if (card.type == AbstractCard.CardType.ATTACK)
            {
                this.isFirearms = true;
//                if (card.target == AbstractCard.CardTarget.ENEMY)
//                {
//                    addToBot(new GainBlockAction(this.owner,card.damage * this.amount / 100));
//                }
//                else if (card.target == AbstractCard.CardTarget.ALL_ENEMY)
//                {
//                }
            }
        }
    }

    @Override
    public void onAttack(DamageInfo info, int damageAmount, AbstractCreature target) {
        super.onAttack(info, damageAmount, target);
        if (this.isFirearms)
        {
            addToBot(new GainBlockAction(this.owner,damageAmount * this.amount / 100));
        }
        this.isFirearms = false;
    }
}