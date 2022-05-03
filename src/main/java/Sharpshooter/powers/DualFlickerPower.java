package Sharpshooter.powers;


import Sharpshooter.SharpshooterMod;
import Sharpshooter.characters.sharpshooter;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;

public class DualFlickerPower extends SharpshooterAbstractPower {
    public static final String POWER_ID = SharpshooterMod.makeID("DualFlickerPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public DualFlickerPower(AbstractCreature owner, int amount)
    {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.type = PowerType.BUFF;
        this.img = new Texture(SharpshooterMod.makePowerPath("DualFlickerPower.png"));
        updateDescription();
    }

    public void updateDescription() {
        int amount = this.amount > 100 ? 100 : this.amount;
        this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        super.onUseCard(card, action);

        if (card.hasTag(sharpshooter.Enums.SHOOTING))
        {
            for (int i = 0; i< card.magicNumber; i++)
            {
                int ran = (int)(Math.random() * 100);
                if (ran < this.amount)
                {
                    NormalMagazinePower power = (NormalMagazinePower)this.owner.getPower(NormalMagazinePower.POWER_ID);
                    power.shoot(action.target,power.onceNum);
                }
            }
        }
    }
}