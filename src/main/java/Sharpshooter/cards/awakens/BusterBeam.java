package Sharpshooter.cards.awakens;

import Sharpshooter.SharpshooterMod;
import Sharpshooter.cards.firearms.FirearmsAbstractCards;
import Sharpshooter.characters.sharpshooter;
import Sharpshooter.powers.SatelliteBeamPower;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class BusterBeam extends FirearmsAbstractCards {
    public static final String ID = SharpshooterMod.makeID(BusterBeam.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String IMG_PATH = SharpshooterMod.makeCardPath("BusterBeam.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    public static final int COST = 2;
    private static final AbstractCard.CardRarity RARITY = CardRarity.SPECIAL;
    private static final AbstractCard.CardTarget TARGET = CardTarget.SELF;
    private static final AbstractCard.CardType TYPE = CardType.SKILL;
    public static final AbstractCard.CardColor COLOR = CardColor.COLORLESS;

    public BusterBeam() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = 3;
        this.exhaust = true;
        this.heat = 3;
        this.tags.add(sharpshooter.Enums.AWAKEN);
    }


    @Override
    public void upgrade() {
        //卡牌升级后的效果
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(2);
            this.cardsToPreview = new OrbitalDisaster();
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

    @Override
    public void onUse(AbstractPlayer p, AbstractMonster m) {
        if(p.hasPower(SatelliteBeamPower.POWER_ID))
        {
            p.getPower(SatelliteBeamPower.POWER_ID).stackPower(3);
            for(int i =0;i<this.magicNumber;i++)
            {
                p.getPower(SatelliteBeamPower.POWER_ID).atStartOfTurn();
            }
        }
        if (this.upgraded){
            addToBot(new MakeTempCardInHandAction(new OrbitalDisaster()));
        }
    }
}
