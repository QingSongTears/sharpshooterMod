package Sharpshooter.cards.shoots;

import Sharpshooter.SharpshooterMod;
import Sharpshooter.characters.sharpshooter;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;

import java.util.ArrayList;
import java.util.Iterator;

public class FastDraw extends ShootAbstractCards {
    public static final String ID = SharpshooterMod.makeID(FastDraw.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String IMG_PATH = SharpshooterMod.makeCardPath("FastDraw.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final int COST = 1;
    private static final AbstractCard.CardRarity RARITY = CardRarity.COMMON;
    private static final AbstractCard.CardTarget TARGET = CardTarget.ENEMY;
    private static final AbstractCard.CardType TYPE = CardType.ATTACK;
    public static final AbstractCard.CardColor COLOR = sharpshooter.Enums.COLOR_LIGHT_BLUE;
    private boolean isAgain= false;
    public int num = 0;
    public FastDraw() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = 4;
    }

    @Override
    public void upgrade() {
        //卡牌升级后的效果
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(2);
        }
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        this.isAgain = false;
        ArrayList list = AbstractDungeon.actionManager.cardsPlayedThisTurn;
        Iterator var15 = list.iterator();

        while(var15.hasNext()) {
            AbstractCard i = (AbstractCard)var15.next();
            num = 0;
            if (i instanceof ShootAbstractCards && i.type == CardType.ATTACK && i.hasTag(sharpshooter.Enums.SHOOTING)) {
                this.isAgain = true;
                num += 1;
            }
        }
    }

    public void triggerOnGlowCheck() {
        this.glowColor = this.isAgain ? AbstractCard.GOLD_BORDER_GLOW_COLOR : AbstractCard.BLUE_BORDER_GLOW_COLOR;
    }
}
