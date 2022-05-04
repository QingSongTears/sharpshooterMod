package Sharpshooter.cards.awakens;

import Sharpshooter.SharpshooterMod;
import Sharpshooter.cards.bullets.BurstBullet;
import Sharpshooter.cards.bullets.MagazineDrum;
import Sharpshooter.cards.firearms.FirearmsAbstractCards;
import Sharpshooter.cards.grenades.LockOnGrenade;
import Sharpshooter.characters.sharpshooter;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;
import java.util.Iterator;

public class Supernova extends FirearmsAbstractCards {
    public static final String ID = SharpshooterMod.makeID(Supernova.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String IMG_PATH = SharpshooterMod.makeCardPath("Supernova.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final int COST = 2;
    private static final AbstractCard.CardRarity RARITY = CardRarity.SPECIAL;
    private static final AbstractCard.CardTarget TARGET = CardTarget.SELF;
    private static final AbstractCard.CardType TYPE = CardType.ATTACK;
    public static final AbstractCard.CardColor COLOR = CardColor.COLORLESS;

    public Supernova() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = 10;
        this.damage = this.baseDamage = 1;
        this.tags.add(sharpshooter.Enums.AWAKEN);
        this.exhaust = true;
    }

    @Override
    public void upgrade() {
        if(!this.upgraded)
        {
            upgradeName();
            upgradeMagicNumber(5);
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
            this.cardsToPreview = new WastelandStorm();
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for(int i=0;i<this.magicNumber;i++)
        {
            addToBot(new DamageAllEnemiesAction(p, DamageInfo.createDamageMatrix(this.damage, false), this.damageTypeForTurn, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL, true));
        }
        if (this.upgraded)
        {
            addToBot(new MakeTempCardInHandAction(new WastelandStorm()));
        }
    }
}
