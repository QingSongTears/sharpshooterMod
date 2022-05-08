package Sharpshooter.cards.firearms;

import Sharpshooter.SharpshooterMod;
import Sharpshooter.characters.sharpshooter;
import Sharpshooter.powers.DangerClosePower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;


public class DangerClose extends FirearmsAbstractCards {
    public static final String ID = SharpshooterMod.makeID(DangerClose.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String IMG_PATH = SharpshooterMod.makeCardPath("DangerClose.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final int COST = 1;
    private static final AbstractCard.CardRarity RARITY = CardRarity.UNCOMMON;
    private static final AbstractCard.CardTarget TARGET = CardTarget.SELF;
    private static final AbstractCard.CardType TYPE = CardType.POWER;
    public static final AbstractCard.CardColor COLOR = sharpshooter.Enums.COLOR_LIGHT_BLUE;

    public DangerClose() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseMagicNumber = this.magicNumber = 2;
        this.tags.add(sharpshooter.Enums.FIREARMS);
        this.heat = 1;
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(1);
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p,p,new DangerClosePower(p,this.magicNumber),this.magicNumber));
    }
}
