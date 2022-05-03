package Sharpshooter.relics;

import Sharpshooter.SharpshooterMod;
import Sharpshooter.powers.SpecialMagazinePower;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class RainBarrage extends CustomRelic {
    public static final String ID = SharpshooterMod.makeID("RainBarrage");
    private static Texture texture = new Texture(SharpshooterMod.makeRelicPath("RainBarrage.png")) ;
    private int goldNum = 0;
    public RainBarrage() {
        super(ID,texture, RelicTier.STARTER, CustomRelic.LandingSound.FLAT);
    }

    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }


    @Override
    public void atTurnStart() {
        super.atTurnStart();
        if(AbstractDungeon.player.hasPower(SpecialMagazinePower.POWER_ID))
        {
            ((SpecialMagazinePower)AbstractDungeon.player.getPower(SpecialMagazinePower.POWER_ID)).addBulletNum(10,SpecialMagazinePower.SILVERBULLET);
        }
    }
}