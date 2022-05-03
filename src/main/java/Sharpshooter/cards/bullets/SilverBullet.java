package Sharpshooter.cards.bullets;

import Sharpshooter.SharpshooterMod;
import Sharpshooter.characters.sharpshooter;
import Sharpshooter.powers.SpecialMagazinePower;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;


public class SilverBullet extends BulletAbstractCards {
    public static final String ID = SharpshooterMod.makeID(SilverBullet.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String IMG_PATH = SharpshooterMod.makeCardPath("SilverBullet.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final int COST = 1;
    private static final AbstractCard.CardRarity RARITY = CardRarity.COMMON;
    private static final AbstractCard.CardTarget TARGET = CardTarget.SELF;
    private static final AbstractCard.CardType TYPE = CardType.ATTACK;
    public static final AbstractCard.CardColor COLOR = sharpshooter.Enums.COLOR_LIGHT_BLUE;

    public SilverBullet() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseMagicNumber = this.magicNumber = 10;
        this.bulletType = SpecialMagazinePower.SILVERBULLET;
        this.upMagicNum = 5;
        this.tags.add(sharpshooter.Enums.LOAD);
    }
}
