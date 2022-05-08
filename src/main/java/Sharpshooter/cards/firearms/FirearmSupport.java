package Sharpshooter.cards.firearms;

import Sharpshooter.SharpshooterMod;
import Sharpshooter.characters.sharpshooter;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;


public class FirearmSupport extends FirearmsAbstractCards {
    public static final String ID = SharpshooterMod.makeID(FirearmSupport.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String IMG_PATH = SharpshooterMod.makeCardPath("FirearmSupport.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final int COST = 0;
    private static final AbstractCard.CardRarity RARITY = CardRarity.SPECIAL;
    private static final AbstractCard.CardTarget TARGET = CardTarget.SELF;
    private static final AbstractCard.CardType TYPE = CardType.SKILL;
    public static final AbstractCard.CardColor COLOR = CardColor.COLORLESS;

    public FirearmSupport() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseMagicNumber = this.magicNumber = 10;
        this.exhaust = true;
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new MakeTempCardInHandAction(sharpshooter.getTagsCard(sharpshooter.Enums.FIREARMS,CardRarity.RARE)));
    }
}
