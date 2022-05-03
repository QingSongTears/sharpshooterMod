package Sharpshooter.cards.shoots;

import Sharpshooter.cards.SharpshooterAbstractCards;
import Sharpshooter.characters.sharpshooter;
import Sharpshooter.powers.DeathRevolverPower;
import Sharpshooter.powers.MarksmanshipPower;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public abstract class ShootAbstractCards extends SharpshooterAbstractCards {
    private boolean isReduceCost = false;
    public int extraDamage = 0;
    public ShootAbstractCards(String id, String name, String img, int cost, String rawDescription, CardType type, CardColor color, CardRarity rarity, CardTarget target) {
        super(id, name, img, cost, rawDescription, type, color, rarity, target);

        if (type == CardType.ATTACK)
            this.tags.add(sharpshooter.Enums.SHOOTING);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        onUse(p,m);
    }

    public void onUse(AbstractPlayer p, AbstractMonster m)
    {

    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        AbstractPlayer player = AbstractDungeon.player;
        if (player.hasPower(DeathRevolverPower.POWER_ID) && !this.upgraded && this.hasTag(sharpshooter.Enums.SHOOTING))
        {
            if (this.canUpgrade()) {
                this.superFlash();
                this.upgrade();
            }
        }
        if (player.hasPower(MarksmanshipPower.POWER_ID) && this.hasTag(sharpshooter.Enums.SHOOTING))
        {
            MarksmanshipPower power = (MarksmanshipPower) player.getPower(MarksmanshipPower.POWER_ID);
            if (power.amount > 0 && !this.isReduceCost)
            {
                this.costForTurn -= 1;
                this.isReduceCost = true;
            }
            else if (power.amount == 0 && this.isReduceCost)
            {
                this.costForTurn += 1;
                this.isReduceCost = false;
            }
        }
    }
}
