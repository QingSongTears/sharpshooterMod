package Sharpshooter.powers;

import Sharpshooter.SharpshooterMod;
import Sharpshooter.characters.sharpshooter;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;

public class StylishPower extends SharpshooterAbstractPower {
    public static final String POWER_ID = SharpshooterMod.makeID("StylishPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public int number = 0;
    public StylishPower(AbstractCreature owner, int number)
    {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.number = number;
        this.type = PowerType.BUFF;
        this.img = new Texture(SharpshooterMod.makePowerPath("StylishPower.png"));
        updateDescription();
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        super.onUseCard(card, action);
        if (card.hasTag(sharpshooter.Enums.SHOOTING))
        {
            this.amount += 1;
            if (this.amount == this.number)
            {
                this.amount = 0;
                addToBot(new DrawCardAction(1));
            }
        }
    }
}