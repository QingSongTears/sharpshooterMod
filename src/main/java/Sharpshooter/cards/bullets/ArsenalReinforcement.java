package Sharpshooter.cards.bullets;

import Sharpshooter.SharpshooterMod;
import Sharpshooter.characters.sharpshooter;
import Sharpshooter.powers.NormalMagazinePower;
import Sharpshooter.powers.SpecialMagazinePower;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class ArsenalReinforcement extends BulletAbstractCards {
    public static final String ID = SharpshooterMod.makeID(ArsenalReinforcement.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String IMG_PATH = SharpshooterMod.makeCardPath("ArsenalReinforcement.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final int COST = 1;
    private static final AbstractCard.CardRarity RARITY = CardRarity.RARE;
    private static final AbstractCard.CardTarget TARGET = CardTarget.SELF;
    private static final AbstractCard.CardType TYPE = CardType.SKILL;
    public static final AbstractCard.CardColor COLOR =  sharpshooter.Enums.COLOR_LIGHT_BLUE;

    public ArsenalReinforcement() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.exhaust = true;
    }

    @Override
    public void upgrade() {
        //卡牌升级后的效果
        if (!this.upgraded) {
            upgradeName();
            upgradeBaseCost(0);
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if(p.hasPower(NormalMagazinePower.POWER_ID))
        {
            NormalMagazinePower power = (NormalMagazinePower)p.getPower(NormalMagazinePower.POWER_ID);
            power.onceDamage += 1;
            power.updateDescription();
            SpecialMagazinePower power_ = (SpecialMagazinePower)p.getPower(SpecialMagazinePower.POWER_ID);
            power_.onceDamage += 1;
            power_.updateDescription();
        }
    }
}
