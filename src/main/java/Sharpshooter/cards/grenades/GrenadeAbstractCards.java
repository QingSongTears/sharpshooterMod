package Sharpshooter.cards.grenades;

import Sharpshooter.cards.SharpshooterAbstractCards;
import Sharpshooter.characters.sharpshooter;
import Sharpshooter.powers.GrenadeBoxPower;
import Sharpshooter.powers.OverChargePower;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public abstract class GrenadeAbstractCards extends SharpshooterAbstractCards {
    public int grenadeType = 20;
    public int upMagicNum = 0;
    public GrenadeAbstractCards(String id, String name, String img, int cost, String rawDescription, CardType type, CardColor color, CardRarity rarity, CardTarget target) {
        super(id, name, img, cost, rawDescription, type, color, rarity, target);

        this.tags.add(sharpshooter.Enums.GRENADE);
    }

    @Override
    public void upgrade() {
        //卡牌升级后的效果
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(upMagicNum);
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if(p.hasPower(GrenadeBoxPower.POWER_ID))
        {
            ((GrenadeBoxPower)p.getPower(GrenadeBoxPower.POWER_ID)).addBulletNum(this.magicNumber,grenadeType);
        }
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        if (AbstractDungeon.player.hasPower(OverChargePower.POWER_ID) && !this.upgraded && this.hasTag(sharpshooter.Enums.LOAD))
        {
            if (this.canUpgrade()) {
                this.superFlash();
                this.upgrade();
            }
        }
    }
}
