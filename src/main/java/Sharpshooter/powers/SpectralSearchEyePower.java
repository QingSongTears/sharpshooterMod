package Sharpshooter.powers;

import Sharpshooter.SharpshooterMod;
import Sharpshooter.characters.sharpshooter;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;

public class SpectralSearchEyePower extends SharpshooterAbstractPower {
    public static final String POWER_ID = SharpshooterMod.makeID("SpectralSearchEyePower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public SpectralSearchEyePower(AbstractCreature owner, int amount)
    {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.type = PowerType.BUFF;
        this.img = new Texture(SharpshooterMod.makePowerPath("SpectralSearchEyePower.png"));
        updateDescription();
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount * 10 + DESCRIPTIONS[1];
    }

    @Override
    public float atDamageGive(float damage, DamageInfo.DamageType damageType, AbstractCard card) {
        if (card.hasTag(sharpshooter.Enums.FIREARMS))
        {
            damage += (this.amount * 0.1 + 1) * damage;
            return damage;
        }
        else
        {
            return super.atDamageGive(damage,damageType,card);
        }
    }
}