package Sharpshooter.powers;

import Sharpshooter.SharpshooterMod;
import Sharpshooter.cards.bullets.Loaded;
import Sharpshooter.cards.shoots.*;
import Sharpshooter.characters.sharpshooter;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.powers.StunMonsterPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageRandomEnemyAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;  
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.Iterator;

public class NormalMagazinePower extends SharpshooterAbstractPower {
    public static final String POWER_ID = SharpshooterMod.makeID("NormalMagazinePower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static int MAXAMOUNT = 50;
    public int EXTRALAMOUNT = 0;
    public int onceNum = 1;
    public int onceDamage = 1;
    public ShootAbstractCards lastCard = null;
    public NormalMagazinePower(AbstractCreature owner, int amount)
    {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.type = PowerType.BUFF;
        this.img = new Texture(SharpshooterMod.makePowerPath("NormalMagazinePower.png"));
        updateDescription();
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + getMaxAmount() + DESCRIPTIONS[1] + this.onceNum + DESCRIPTIONS[2] + getBulletDamage() + DESCRIPTIONS[3];
    }

    public int getMaxAmount()
    {
        return MAXAMOUNT + EXTRALAMOUNT;
    }

    public void addAmount(int amount)
    {
        this.amount += amount;
        this.amount = this.amount > getMaxAmount() ? getMaxAmount() : this.amount;
    }

    @Override
    public void reducePower(int reduceAmount) {
        super.reducePower(reduceAmount);

        if (this.amount == 0)
        {
            addToTop(new MakeTempCardInHandAction(new Loaded()));
        }
    }

    public int getBulletDamage()
    {
        return this.onceDamage * this.onceNum;
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        super.onUseCard(card, action);
        if (card.hasTag(sharpshooter.Enums.SHOOTING))
        {
            this.lastCard = (ShootAbstractCards) card;
            //这里开始结算射击前得效果
            if (card.cardID == BusterShot.ID)
            {
                addToBot(new DamageAction(action.target,new DamageInfo(this.owner, this.amount, sharpshooter.Enums.NORMAL_BULLET_DMG) , AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
                reducePower(this.amount);
                updateDescription();
                return;
            }
            else if (card.cardID == WildShot.ID)
            {
                for(int i = 0;i < card.magicNumber;i++)
                {
                    shoot(AbstractDungeon.getMonsters().getRandomMonster(),1);
                }
                return;
            }
            else if (card.cardID == SuppressiveBarrage.ID)
            {
                if (action.target.hasPower(WeaknessPower.POWER_ID)) {
                    if (action.target instanceof AbstractMonster)
                    {
                        addToTop(new ApplyPowerAction(action.target, AbstractDungeon.player, new StunMonsterPower(((AbstractMonster) action.target), 1), 1));
                    }
                }
            }

            //这里开始结算射击
            shoot(action.target,card);

            //结算射击后得效果
            if (card.cardID == Headshot.ID)
            {
                int num = 1;
                if (card.upgraded) num = 2;
                addToBot(new ApplyPowerAction(action.target, this.owner, new WeaknessPower(action.target, num), num));
            }
            else if (card.cardID == MultiHeadshot.ID)
            {
                int num = 1;
                if (card.upgraded) num = 2;
                if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
                    flash();
                    Iterator<AbstractMonster> it = AbstractDungeon.getMonsters().monsters.iterator();
                    while (it.hasNext()) {
                        AbstractMonster monster = it.next();
                        if (!monster.isDead && !monster.isDying) {
                            addToBot(new ApplyPowerAction(monster, this.owner, new WeaknessPower(monster, num), num));
                        }
                    }
                }
            }
            else if (card.cardID == PistolCarbine.ID)
            {
                if (action.target.hasPower(WeaknessPower.POWER_ID)) {
                    shoot(action.target,card);
                }
            }
            else if (card.cardID == UltimateMultiHeadshot.ID)
            {
                int num = 2;
                if (card.upgraded) num = 3;
                if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
                    flash();
                    Iterator<AbstractMonster> it = AbstractDungeon.getMonsters().monsters.iterator();
                    while (it.hasNext()) {
                        AbstractMonster monster = it.next();
                        if (!monster.isDead && !monster.isDying) {
                            addToBot(new ApplyPowerAction(monster, this.owner, new WeaknessPower(monster, num), num));
                        }
                    }
                }
            }
            else if (card.cardID == FastDraw.ID)
            {
                int num = ((FastDraw)card).num;
                for (int i = 0;i<num;i++)
                {
                    shoot(action.target,card);
                }
            }
        }
    }

    public void shoot(AbstractCreature creature,AbstractCard card)
    {
        if (card.target == AbstractCard.CardTarget.ENEMY)
        {
            shoot(creature,card.magicNumber);
        }
        else if (card.target == AbstractCard.CardTarget.ALL_ENEMY) { //随机敌人
            for (int i =0;i<card.magicNumber;i++)
            {
                shoot(AbstractDungeon.getRandomMonster(),1);
            }
        }
        else if (card.target == AbstractCard.CardTarget.ALL) //全部敌人
        {
            if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
                Iterator<AbstractMonster> it = AbstractDungeon.getMonsters().monsters.iterator();
                while (it.hasNext()) {
                    AbstractMonster monster = it.next();
                    if (!monster.isDead && !monster.isDying) {
                        shoot(monster,card.magicNumber);
                    }
                }
            }
        }
    }

    public void shoot(AbstractCreature creature,int num)
    {
        for(int i = 0; i < num;i++)
        {
            if (this.amount >0)
            {
                int damage = getBulletDamage();
                if(this.lastCard != null)
                    damage += this.lastCard.extraDamage;
                addToTop(new DamageAction(creature,new DamageInfo(this.owner, damage, sharpshooter.Enums.NORMAL_BULLET_DMG) , AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
                reducePower(this.onceNum);
                useSpecialBullet(creature,this.onceNum);
                updateDescription();
            }
            else
                return;
        }
    }

    public void useSpecialBullet(AbstractCreature m,int num)
    {
        SpecialMagazinePower power = ((SpecialMagazinePower) this.owner.getPower(SpecialMagazinePower.POWER_ID));
        power.useBullet(m,num);
    }
}
