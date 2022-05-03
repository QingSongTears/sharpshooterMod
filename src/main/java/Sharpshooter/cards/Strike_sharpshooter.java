package Sharpshooter.cards;

import Sharpshooter.characters.*;
import Sharpshooter.SharpshooterMod;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Strike_sharpshooter extends SharpshooterAbstractCards {
    public static final String ID = SharpshooterMod.makeID(Strike_sharpshooter.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = SharpshooterMod.makeCardPath("Strike.png");
    private static final int COST = 1;
    public Strike_sharpshooter() {
        // 卡牌ID，卡牌名称，图片路径，费用，描述，卡牌类型，卡牌颜色，卡牌稀有度，卡牌目标
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.ATTACK, sharpshooter.Enums.COLOR_LIGHT_BLUE, CardRarity.BASIC, CardTarget.ENEMY);
        this.tags.add(CardTags.STARTER_STRIKE);
        this.tags.add(CardTags.STRIKE);
        this.baseDamage = 6;
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(3);
        }
    }

    public void use(AbstractPlayer p, AbstractMonster m)
    {
        addToBot(new DamageAction(m,new DamageInfo(p, this.damage, this.damageTypeForTurn) , AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
    }
}
