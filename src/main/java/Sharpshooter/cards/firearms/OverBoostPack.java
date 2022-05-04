package Sharpshooter.cards.firearms;

import Sharpshooter.SharpshooterMod;
import Sharpshooter.characters.sharpshooter;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.Iterator;

public class OverBoostPack extends FirearmsAbstractCards {
    public static final String ID = SharpshooterMod.makeID(OverBoostPack.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String IMG_PATH = SharpshooterMod.makeCardPath("OverBoostPack.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final int COST = 1;
    private static final AbstractCard.CardRarity RARITY = CardRarity.UNCOMMON;
    private static final AbstractCard.CardTarget TARGET = CardTarget.SELF;
    private static final AbstractCard.CardType TYPE = CardType.SKILL;
    public static final AbstractCard.CardColor COLOR = sharpshooter.Enums.COLOR_LIGHT_BLUE;

    public OverBoostPack() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBaseCost(0);
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        upgradeAllCardsInGroup(p.hand);
        upgradeAllCardsInGroup(p.drawPile);
        upgradeAllCardsInGroup(p.discardPile);
        upgradeAllCardsInGroup(p.exhaustPile);
    }

    private void upgradeAllCardsInGroup(CardGroup cardGroup) {
        Iterator<AbstractCard> it = cardGroup.group.iterator();
        while (it.hasNext()) {
            AbstractCard c = it.next();
            if(c.hasTag(sharpshooter.Enums.FIREARMS))
            {
                c.costForTurn -=1;
            }
        }
    }
}
