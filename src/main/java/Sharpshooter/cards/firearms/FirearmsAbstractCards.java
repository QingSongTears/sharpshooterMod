package Sharpshooter.cards.firearms;

import Sharpshooter.cards.SharpshooterAbstractCards;
import Sharpshooter.characters.sharpshooter;
import Sharpshooter.powers.EagleEyePower;
import Sharpshooter.powers.HeavyWeaponEnhancementPower;
import Sharpshooter.powers.LeakagePower;
import Sharpshooter.powers.OverheatingPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;


public abstract class FirearmsAbstractCards extends SharpshooterAbstractCards {
    public static int RATE = 2;
    public int heat = 0;
    public FirearmsAbstractCards(String id, String name, String img, int cost, String rawDescription, CardType type, CardColor color, CardRarity rarity, CardTarget target) {
        super(id, name, img, cost, rawDescription, type, color, rarity, target);
        if (this.type == CardType.ATTACK)
            this.tags.add(sharpshooter.Enums.FIREARMS);
    }


    @Override
    public void applyPowers() {
        AbstractPower strength = AbstractDungeon.player.getPower(StrengthPower.POWER_ID);
        if (strength != null) {
            strength.amount *= RATE;
        }
        super.applyPowers();
        if (strength != null) {
            strength.amount /= RATE;
        }

        if (AbstractDungeon.player.hasPower(EagleEyePower.POWER_ID) && !this.upgraded && this.hasTag(sharpshooter.Enums.FIREARMS))
        {
            if (this.canUpgrade()) {
                this.superFlash();
                this.upgrade();
            }
        }
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        AbstractPower strength = AbstractDungeon.player.getPower(StrengthPower.POWER_ID);
        if (strength != null) {
            strength.amount *= RATE;
        }
        super.calculateCardDamage(mo);
        if (strength != null) {
            strength.amount /= RATE;
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        onUse(p,m);
        if(this.heat > 0 && !p.hasPower(HeavyWeaponEnhancementPower.POWER_ID))
            addToTop(new ApplyPowerAction(p,p,new OverheatingPower(p,this.heat),this.heat));
    }

    public void onUse(AbstractPlayer p, AbstractMonster m)
    {

    }
}
