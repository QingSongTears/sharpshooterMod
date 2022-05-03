package Sharpshooter.cards.awakens;

import Sharpshooter.SharpshooterMod;
import Sharpshooter.cards.bullets.BurstBullet;
import Sharpshooter.cards.bullets.MagazineDrum;
import Sharpshooter.cards.grenades.LockOnGrenade;
import Sharpshooter.characters.sharpshooter;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;


public class BlackRoses extends AwakenAbstractCards {
    public static final String ID = SharpshooterMod.makeID(BlackRoses.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String IMG_PATH = SharpshooterMod.makeCardPath("BlackRoses.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final int COST = 1;
    private static final AbstractCard.CardRarity RARITY = CardRarity.RARE;
    private static final AbstractCard.CardTarget TARGET = CardTarget.SELF;
    private static final AbstractCard.CardType TYPE = CardType.SKILL;
    public static final AbstractCard.CardColor COLOR = sharpshooter.Enums.COLOR_LIGHT_BLUE;

    public BlackRoses() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.block = this.baseBlock = 10;
    }

    @Override
    public void upgrade() {
        if(!this.upgraded)
        {
            upgradeName();
            upgradeBlock(5);
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
            this.cardsToPreview = new Supernova();
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p,p,this.block));
        if (this.upgraded)
        {
            AbstractCard card = new BurstBullet();
            card.upgrade();
            addToBot(new MakeTempCardInHandAction(card));
            card = new LockOnGrenade();
            card.upgrade();
            addToBot(new MakeTempCardInHandAction(card));
            card = new MagazineDrum();
            card.upgrade();
            addToBot(new MakeTempCardInHandAction(card));
            addToBot(new MakeTempCardInHandAction(new Supernova()));
        }
        else
        {
            addToBot(new MakeTempCardInHandAction(new BurstBullet()));
            addToBot(new MakeTempCardInHandAction(new LockOnGrenade()));
            addToBot(new MakeTempCardInHandAction(new MagazineDrum()));
        }
    }
}
