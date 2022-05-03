package Sharpshooter.powers;

import Sharpshooter.SharpshooterMod;
import Sharpshooter.cards.firearms.FirearmSupport;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;

public class OverheatingPower extends SharpshooterAbstractPower {
    public static final String POWER_ID = SharpshooterMod.makeID("OverheatingPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public OverheatingPower(AbstractCreature owner, int amount)
    {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.type = PowerType.DEBUFF;
        this.img = new Texture(SharpshooterMod.makePowerPath("OverheatingPower.png"));
        updateDescription();
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }

    @Override
    public void stackPower(int stackAmount) {
        super.stackPower(stackAmount);
        if (this.amount >= 5)
        {
            this.reducePower(5);
            addToTop(new MakeTempCardInHandAction(new FirearmSupport()));
            addToTop(new ApplyPowerAction(this.owner,this.owner,new LeakagePower(this.owner,0),1));
        }
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        super.atEndOfTurn(isPlayer);
        this.reducePower(1);
    }
}