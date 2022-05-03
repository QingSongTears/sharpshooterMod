package Sharpshooter.cards.awakens;

import Sharpshooter.SharpshooterMod;
import Sharpshooter.powers.*;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;

import java.util.Iterator;

public class DeathCrisis extends AwakenAbstractCards {
    public static final String ID = SharpshooterMod.makeID(DeathCrisis.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String IMG_PATH = SharpshooterMod.makeCardPath("DeathCrisis.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    public static final int COST = 3;
    private static final AbstractCard.CardRarity RARITY = CardRarity.SPECIAL;
    private static final AbstractCard.CardTarget TARGET = CardTarget.SELF;
    private static final AbstractCard.CardType TYPE = CardType.SKILL;
    public static final AbstractCard.CardColor COLOR = CardColor.COLORLESS;

    public DeathCrisis() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseMagicNumber = this.magicNumber = 3;
    }


    @Override
    public void upgrade() {
        //卡牌升级后的效果
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(3);
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        WeaknessPower.onceDamage += this.magicNumber;
        Iterator<AbstractMonster> it = AbstractDungeon.getMonsters().monsters.iterator();
        while (it.hasNext()) {
            AbstractMonster monster = it.next();
            if (!monster.isDead && !monster.isDying) {
                addToBot(new ApplyPowerAction(monster, p, new WeaknessPower(monster, this.magicNumber), this.magicNumber));
                if(monster.hasPower(WeaknessPower.POWER_ID)){
                    addToBot(new ApplyPowerAction(monster,p,new VulnerablePower(monster,1,false),1));
                }
            }
        }
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        if (AbstractDungeon.player.hasPower(DeathRevolverPower.POWER_ID))
        {
            this.upgrade();
        }
    }
}
