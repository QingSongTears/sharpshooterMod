package Sharpshooter.relics;


import Sharpshooter.SharpshooterMod;
import Sharpshooter.cards.bullets.SilverBullet;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;


public class Pistol extends CustomRelic {
    public static final String ID = SharpshooterMod.makeID("Pistol");
    private static Texture texture = new Texture(SharpshooterMod.makeRelicPath("Pistol.png")) ;
    private int goldNum = 0;
    public Pistol() {
        super(ID,texture, RelicTier.STARTER, CustomRelic.LandingSound.FLAT);
    }

    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    public void atBattleStart() {
        flash();
        addToTop(new MakeTempCardInHandAction(new SilverBullet()));
    }
}