package Sharpshooter.powers;

import Sharpshooter.SharpshooterMod;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

import java.util.Iterator;

public class SatelliteBeamPower extends SharpshooterAbstractPower {
    public static final String POWER_ID = SharpshooterMod.makeID("SatelliteBeamPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static int NUM = 1;
    public SatelliteBeamPower(AbstractCreature owner, int amount)
    {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.type = PowerType.BUFF;
        this.amount = amount;
        this.img = new Texture(SharpshooterMod.makePowerPath("SatelliteBeamPower.png"));
        updateDescription();
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1] + NUM + DESCRIPTIONS[2];
    }


    @Override
    public void atStartOfTurn() {
        super.atStartOfTurn();

        int damage = this.amount;
        if (AbstractDungeon.player.hasPower(StrengthPower.POWER_ID))
        {
            damage += AbstractDungeon.player.getPower(StrengthPower.POWER_ID).amount;
        }
        addToBot(new DamageAllEnemiesAction(AbstractDungeon.player, DamageInfo.createDamageMatrix(damage, false), DamageInfo.DamageType.NORMAL, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL, true));

        if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            flash();
            Iterator<AbstractMonster> it = AbstractDungeon.getMonsters().monsters.iterator();
            while (it.hasNext()) {
                AbstractMonster monster = it.next();
                if (!monster.isDead && !monster.isDying) {
                    addToBot(new ApplyPowerAction(monster, this.owner, new SpectralSearchEyePower(monster, NUM), NUM));
                }
            }
        }
    }

    @Override
    public void onApplyPower(AbstractPower power, AbstractCreature target, AbstractCreature source) {
        super.onApplyPower(power, target, source);
        updateDescription();
    }
}