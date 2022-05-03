package Sharpshooter.actions;

import Sharpshooter.characters.sharpshooter;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.EmptyDeckShuffleAction;
import com.megacrit.cardcrawl.actions.common.PlayTopCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;


public class DeathHawkAction extends AbstractGameAction {
    public boolean isEffected = false;
    public DeathHawkAction(AbstractCreature target){
        this.duration = Settings.ACTION_DUR_FAST;
        this.actionType = ActionType.WAIT;
        this.source = AbstractDungeon.player;
        this.target = target;
        this.amount = 2;
        this.isEffected = false;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            if (AbstractDungeon.player.drawPile.size() + AbstractDungeon.player.discardPile.size() == 0) {
                this.isDone = true;
                return;
            }

            if (AbstractDungeon.player.drawPile.isEmpty()) {
                this.addToTop(new PlayTopCardAction(this.target,false));
                this.addToTop(new EmptyDeckShuffleAction());
                this.isDone = true;
                return;
            }

            if (!AbstractDungeon.player.drawPile.isEmpty()) {
                AbstractCard card = AbstractDungeon.player.drawPile.getTopCard();
                if (card.hasTag(sharpshooter.Enums.SHOOTING) && !this.isEffected)
                {
                    addToBot(new DeathHawk2Action(this.target));
                    this.isEffected = true;
                }

                AbstractDungeon.player.draw();
                AbstractDungeon.player.hand.refreshHandLayout();
            }

            this.amount -=1;
            if (this.amount == 0)
                this.isDone = true;
        }
    }
}
