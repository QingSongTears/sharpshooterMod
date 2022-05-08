package Sharpshooter.powers;

import Sharpshooter.SharpshooterMod;
import Sharpshooter.cards.firearms.FirearmSupport;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class OverheatingPower extends SharpshooterAbstractPower {
    public static final String POWER_ID = SharpshooterMod.makeID("OverheatingPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public int reduceNum = 1;
    public static int LIMIT = 5;
    public static int BASE_LIMIT = 5;
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
        this.description = DESCRIPTIONS[0] + this.LIMIT + DESCRIPTIONS[1] + this.LIMIT + DESCRIPTIONS[2] + this.reduceNum + DESCRIPTIONS[3];
    }

    @Override
    public void stackPower(int stackAmount) {
        super.stackPower(stackAmount);
        if (this.amount >= this.LIMIT)
        {
            this.reducePower(this.LIMIT);
            addToTop(new MakeTempCardInHandAction(new FirearmSupport()));
            addToTop(new ApplyPowerAction(this.owner,this.owner,new LeakagePower(this.owner,0),1));
        }
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        super.atEndOfTurn(isPlayer);
        this.reducePower(this.reduceNum);
    }

    public void reducePower(int amount)
    {
        super.reducePower(amount);
        if(this.amount <= 0)
        {
            addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
        }
    }

    @Override
    public void onApplyPower(AbstractPower power, AbstractCreature target, AbstractCreature source) {
        super.onApplyPower(power, target, source);
        updateDescription();
    }
}