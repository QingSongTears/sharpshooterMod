package Sharpshooter.cards;

import Sharpshooter.characters.sharpshooter;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;
import java.util.Iterator;

public abstract class SharpshooterAbstractCards extends CustomCard {
    public SharpshooterAbstractCards(String id, String name, String img, int cost, String rawDescription, CardType type,
                                     CardColor color, CardRarity rarity, CardTarget target) {
        super(id, name, img, cost, rawDescription, type, color, rarity, target);
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        ArrayList list = AbstractDungeon.actionManager.cardsPlayedThisCombat;
        Iterator var15 = list.iterator();

        while(var15.hasNext()) {
            AbstractCard i = (AbstractCard)var15.next();
            if (i.hasTag(sharpshooter.Enums.AWAKEN) && i.cardID == this.cardID) {
                this.glowColor = AbstractCard.GREEN_BORDER_GLOW_COLOR;
                return false;
            }
        }
        return super.canUse(p, m);
    }
}