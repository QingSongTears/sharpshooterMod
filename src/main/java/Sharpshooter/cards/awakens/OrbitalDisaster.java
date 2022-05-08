package Sharpshooter.cards.awakens;

import Sharpshooter.SharpshooterMod;
import Sharpshooter.cards.firearms.FirearmsAbstractCards;
import Sharpshooter.characters.sharpshooter;
import Sharpshooter.powers.SpectralSearchEyePower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.Iterator;

public class OrbitalDisaster extends FirearmsAbstractCards {
    public static final String ID = SharpshooterMod.makeID(OrbitalDisaster.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String IMG_PATH = SharpshooterMod.makeCardPath("OrbitalDisaster.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final int COST = 3;
    private static final AbstractCard.CardRarity RARITY = CardRarity.SPECIAL;
    private static final AbstractCard.CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final AbstractCard.CardType TYPE = CardType.ATTACK;
    public static final AbstractCard.CardColor COLOR = CardColor.COLORLESS;

    public OrbitalDisaster() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = 5;
        this.damage = this.baseDamage = 4;
        this.heat = 5;
        this.tags.add(sharpshooter.Enums.AWAKEN);
        this.exhaust = true;
    }

    @Override
    public void upgrade() {
        if(!this.upgraded)
        {
            upgradeName();
            upgradeDamage(2);
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

    @Override
    public void onUse(AbstractPlayer p, AbstractMonster m) {
        if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            flash();
            Iterator<AbstractMonster> it = AbstractDungeon.getMonsters().monsters.iterator();
            while (it.hasNext()) {
                AbstractMonster monster = it.next();
                if (!monster.isDead && !monster.isDying) {
                    int num = this.magicNumber;
                    if (monster.hasPower(SpectralSearchEyePower.POWER_ID)){
                        num += monster.getPower(SpectralSearchEyePower.POWER_ID).amount;
                    }
                    for(int i= 0;i<num;i++)
                    {
                        addToBot(new DamageAction(m,new DamageInfo(p,this.damage,this.damageTypeForTurn)));
                    }
                }
            }
        }
    }
}
