package Sharpshooter.powers;

import Sharpshooter.SharpshooterMod;
import Sharpshooter.characters.sharpshooter;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
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
import java.util.Iterator;
import java.util.List;

public class GrenadeBoxPower extends SharpshooterAbstractPower {
    public static final String POWER_ID = SharpshooterMod.makeID("GrenadeBoxPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static int MAXAMOUNT = 9;
    public int EXTRALAMOUNT = 0;
    public int onceNum = 1;

    public static int NORMALEGRENADE = 0;
    public static int FLASHGRENADE = 1;
    public static int FREEZEGRENADE = 2;
    public static int LOCKONGRENADE = 3;
    public static int GRENADEALL = 4;

    public List grenadeList = new ArrayList();
    public ArrayList<String> descList = new ArrayList<>();
    public GrenadeBoxPower(AbstractCreature owner, int amount)
    {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.type = PowerType.BUFF;
        this.img = new Texture(SharpshooterMod.makePowerPath("GrenadeBoxPower.png"));
        for (int i = 0;i < GRENADEALL;i++)
        {
            grenadeList.add(i,0);
            descList.add("");
        }

        descList.set(NORMALEGRENADE," NL #y普通手雷 ： #b");
        descList.set(FLASHGRENADE," NL #y感电手雷 ： #b");
        descList.set(FREEZEGRENADE," NL #y冰冻手雷 ： #b");
        descList.set(LOCKONGRENADE," NL #y镭射手雷 ： #b");

        updateDescription();
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + getMaxAmount() + DESCRIPTIONS[1] + this.onceNum + DESCRIPTIONS[2];
        for (int i= 0;i<GRENADEALL;i++)
        {
            if((int)grenadeList.get(i) > 0)
            {
                this.description = this.description + descList.get(i) + (int)grenadeList.get(i) + " 颗";
            }
        }
    }

    public int getMaxAmount()
    {
        return MAXAMOUNT + EXTRALAMOUNT;
    }

    public void addBulletNum(int num,int type)
    {
        if(this.amount + num > getMaxAmount())
        {
            int number = (int)grenadeList.get(type);
            grenadeList.set(type,  number + getMaxAmount() - this.amount);
            this.amount = getMaxAmount();
        }
        else
        {
            this.amount += num;
            grenadeList.set(type,  (int)grenadeList.get(type) + num);
        }

        updateDescription();
    }


    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        super.onUseCard(card, action);
        if (card.type == AbstractCard.CardType.ATTACK )//&& !card.hasTag(sharpshooter.Enums.GRENADE))
        {
            randomConsumeGrenade(AbstractDungeon.player,(AbstractMonster) action.target);
            updateDescription();
        }
    }

    //随机消耗手雷
    public void randomConsumeGrenade(AbstractPlayer p, AbstractMonster m)
    {
        if(this.amount <= 0) return;
        int ran = (int)(Math.random() * GRENADEALL);
        if ((int)grenadeList.get(ran) <=0)
        {
            randomConsumeGrenade(p,m);
            return;
        }
        grenadeList.set(ran,(int)grenadeList.get(ran) - 1);
        this.amount -= 1;
        if (ran == NORMALEGRENADE)
            normalGrenadeEffect(p,m,1);
        else if (ran == FLASHGRENADE)
            flashGrenadeEffect(p,m,1);
        else if (ran == FREEZEGRENADE)
            freezeGrenadeEffect(p,m,1);
        else if (ran == LOCKONGRENADE)
            lockOnGrenadeEffect(p,m,1);
    }

    //毛雷
    public void normalGrenadeEffect(AbstractPlayer p, AbstractMonster m,int num)
    {
        int damage = 5 * num;
        addToBot(new DamageAllEnemiesAction(p, DamageInfo.createDamageMatrix(damage, false), DamageInfo.DamageType.NORMAL, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL, true));
    }

    //感电手雷
    public void flashGrenadeEffect(AbstractPlayer p, AbstractMonster m,int num)
    {
        int damage = 1 * num;
        addToBot(new DamageAllEnemiesAction(p, DamageInfo.createDamageMatrix(damage, false), DamageInfo.DamageType.NORMAL, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL, true));
        if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            flash();
            Iterator<AbstractMonster> it = AbstractDungeon.getMonsters().monsters.iterator();
            while (it.hasNext()) {
                AbstractMonster monster = it.next();
                if (!monster.isDead && !monster.isDying) {
                    addToBot(new ApplyPowerAction(monster, p, new FlashPower(monster, num), num));
                }
            }
        }
    }

    //冰冻手雷
    public void freezeGrenadeEffect(AbstractPlayer p, AbstractMonster m,int num)
    {
        int damage = 3 * num;
        addToBot(new DamageAllEnemiesAction(p, DamageInfo.createDamageMatrix(damage, false), DamageInfo.DamageType.NORMAL, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL, true));
        if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            flash();
            Iterator<AbstractMonster> it = AbstractDungeon.getMonsters().monsters.iterator();
            while (it.hasNext()) {
                AbstractMonster monster = it.next();
                if (!monster.isDead && !monster.isDying) {
                    addToBot(new ApplyPowerAction(monster, p, new FreezePower(monster, num * 2), num * 2));
                }
            }
        }
    }

    //镭射手雷
    public void lockOnGrenadeEffect(AbstractPlayer p, AbstractMonster m,int num)
    {
        int damage = 5 * num;
        addToBot(new DamageAllEnemiesAction(p, DamageInfo.createDamageMatrix(damage, false), DamageInfo.DamageType.NORMAL, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL, true));
        if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            flash();
            Iterator<AbstractMonster> it = AbstractDungeon.getMonsters().monsters.iterator();
            while (it.hasNext()) {
                AbstractMonster monster = it.next();
                if (!monster.isDead && !monster.isDying) {
                    addToBot(new ApplyPowerAction(monster, p, new WeaknessPower(monster, num), num));
                }
            }
        }
    }
}
