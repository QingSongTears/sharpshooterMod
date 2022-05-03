package Sharpshooter.powers;

import Sharpshooter.SharpshooterMod;
import Sharpshooter.cards.shoots.BusterShot;
import Sharpshooter.characters.sharpshooter;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;
import java.util.List;


public class SpecialMagazinePower extends SharpshooterAbstractPower {
    public static final String POWER_ID = SharpshooterMod.makeID("SpecialMagazinePower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static int MAXAMOUNT = 30;
    public int EXTRALAMOUNT = 0;
    public int onceNum = 1;
    public int onceDamage = 1;

    public static int SILVERBULLET = 0;
    public static int FREEZEBULLET = 1;
    public static int FLAMEBULLET = 2;
    public static int BOOSTERBULLET = 3;
    public static int BURSTBULLET = 4;
    public static int BULLETALL = 5;

    public List bulletList = new ArrayList();
    public ArrayList<String> descList = new ArrayList<>();
    public SpecialMagazinePower(AbstractCreature owner, int amount)
    {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.type = PowerType.BUFF;
        this.img = new Texture(SharpshooterMod.makePowerPath("SpecialMagazinePower.png"));
        for (int i = 0;i < BULLETALL;i++)
        {
            bulletList.add(i,0);
            descList.add("");
        }

        descList.set(SILVERBULLET," NL #y银弹： #b");
        descList.set(FREEZEBULLET," NL #y冰冻弹： #b");
        descList.set(FLAMEBULLET," NL #y爆炎弹： #b");
        descList.set(BOOSTERBULLET," NL #y穿甲弹： #b");
        descList.set(BURSTBULLET," NL #y爆裂弹： #b");

        updateDescription();

    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + getMaxAmount() + DESCRIPTIONS[1] + this.onceNum + DESCRIPTIONS[2] + getBulletDamage() + DESCRIPTIONS[3];
        for (int i= 0;i<BULLETALL;i++)
        {
            if((int)bulletList.get(i) > 0)
            {
                this.description = this.description + descList.get(i) + (int)bulletList.get(i) + " 颗";
            }
        }
    }

    public int getBulletDamage()
    {
        return this.onceDamage * this.onceNum;
    }


    public int getMaxAmount()
    {
        return MAXAMOUNT + EXTRALAMOUNT;
    }

    public void addBulletNum(int num,int type)
    {
        if (type == BULLETALL) return;
        if(this.amount + num > getMaxAmount())
        {
            int number = (int)bulletList.get(type);
            bulletList.set(type,  number + getMaxAmount() - this.amount);
            this.amount = getMaxAmount();
        }
        else
        {
            this.amount += num;
            bulletList.set(type,  (int)bulletList.get(type) + num);
        }

        updateDescription();
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        super.onUseCard(card, action);
        if (card.hasTag(sharpshooter.Enums.SHOOTING))
        {
            if (card.cardID == BusterShot.ID)
            {
                for (int i = 0;i<BULLETALL; i++)
                {
                    int num = (int)bulletList.get(i);
                    if (num >0)
                    {
                        if (i == SILVERBULLET)
                            sliverBulletEffect(AbstractDungeon.player,(AbstractMonster) action.target,num);
                        else if (i == FLAMEBULLET)
                            flameBulletEffect(AbstractDungeon.player,(AbstractMonster) action.target,num);
                        else if (i == BOOSTERBULLET)
                            boosterBulletEffect(AbstractDungeon.player,(AbstractMonster) action.target,num);
                    }
                    bulletList.set(i,0);
                }
                reducePower(this.amount);
                updateDescription();
                return;
            }
        }
    }

    public void useBullet(AbstractCreature m,int num)
    {
        randomConsumeBullet(AbstractDungeon.player,m,num);
    }

    //随机消耗子弹
    public void randomConsumeBullet(AbstractPlayer p,AbstractCreature m,int num)
    {
        if(this.amount <= 0) return;
        int ran = (int)(Math.random() * BULLETALL);
        if ((int)bulletList.get(ran) <=0)
        {
            randomConsumeBullet(p,m,num);
            return;
        }
        bulletList.set(ran,(int)bulletList.get(ran) - 1);
        AbstractMonster monster = (AbstractMonster)m;
        this.amount -= 1;
        if (ran == SILVERBULLET)
            sliverBulletEffect(p,monster,num);
        else if (ran == FLAMEBULLET)
            flameBulletEffect(p,monster,num);
        else if (ran == BOOSTERBULLET)
            boosterBulletEffect(p,monster,num);
        else if (ran == FREEZEBULLET)
            freezeBulletEffect(p,monster,num);
        else if (ran == BURSTBULLET)
            burstBulletEffect(p,monster,num);

        updateDescription();
    }

    //银弹
    public void sliverBulletEffect(AbstractPlayer p,AbstractMonster m,int num)
    {
        addToTop(new DamageAction(m,new DamageInfo(p, num * onceDamage, sharpshooter.Enums.SPECIAL_BULLET_DMG) , AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
    }

    //爆炎弹
    public void flameBulletEffect(AbstractPlayer p,AbstractMonster m,int num)
    {
        addToBot(new DamageAllEnemiesAction(p, DamageInfo.createDamageMatrix(num * onceDamage, false), sharpshooter.Enums.SPECIAL_BULLET_DMG, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL, true));
    }

    //穿甲弹
    public void boosterBulletEffect(AbstractPlayer p,AbstractMonster m,int num)
    {
        addToTop(new DamageAction(m,new DamageInfo(p, num * onceDamage, sharpshooter.Enums.SPECIAL_BULLET_DMG) , AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        addToBot(new GainBlockAction(p, p, num));
    }

    //冰冻弹
    public void freezeBulletEffect(AbstractPlayer p,AbstractMonster m,int num)
    {
        addToTop(new DamageAction(m,new DamageInfo(p, num * onceDamage, sharpshooter.Enums.SPECIAL_BULLET_DMG) , AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        addToBot(new ApplyPowerAction(m, p, new FreezePower(m, num * 1), num * 1));
    }

    //爆裂弹
    public void burstBulletEffect(AbstractPlayer p,AbstractMonster m,int num)
    {
        addToBot(new DamageAllEnemiesAction(p, DamageInfo.createDamageMatrix(num * (onceDamage + 1), false), sharpshooter.Enums.SPECIAL_BULLET_DMG, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL, true));
    }
}
