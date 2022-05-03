package Sharpshooter.cards.awakens;

import Sharpshooter.cards.SharpshooterAbstractCards;
import Sharpshooter.characters.sharpshooter;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;
import java.util.Iterator;

public abstract class AwakenAbstractCards extends SharpshooterAbstractCards {
    public AwakenAbstractCards(String id, String name, String img, int cost, String rawDescription, CardType type, CardColor color, CardRarity rarity, CardTarget target) {
        super(id, name, img, cost, rawDescription, type, color, rarity, target);

        this.tags.add(sharpshooter.Enums.AWAKEN);
        this.exhaust = true;
    }
}
