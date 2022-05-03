package Sharpshooter.cards.awakens;

import Sharpshooter.SharpshooterMod;
import Sharpshooter.cards.shoots.ShootAbstractCards;
import Sharpshooter.characters.sharpshooter;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;


public class SeventhFlow extends ShootAbstractCards {
    public static final String ID = SharpshooterMod.makeID(SeventhFlow.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String IMG_PATH = SharpshooterMod.makeCardPath("SeventhFlow.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    public static final int COST = 2;
    private static final AbstractCard.CardRarity RARITY = CardRarity.SPECIAL;
    private static final AbstractCard.CardTarget TARGET = CardTarget.ALL;
    private static final AbstractCard.CardType TYPE = CardType.ATTACK;
    public static final AbstractCard.CardColor COLOR = CardColor.COLORLESS;

    public SeventhFlow() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseMagicNumber = this.magicNumber = 10;
        this.tags.add(sharpshooter.Enums.AWAKEN);
        this.exhaust = true;
    }


    @Override
    public void upgrade() {
        //卡牌升级后的效果
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(5);
            this.cardsToPreview = new DeathCrisis();
        }
    }

    @Override
    public void onUse(AbstractPlayer p, AbstractMonster m) {
        if (this.upgraded)
            addToBot(new MakeTempCardInHandAction(new DeathCrisis()));
    }
}
