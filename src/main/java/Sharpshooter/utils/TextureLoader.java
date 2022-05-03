package Sharpshooter.utils;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import java.util.HashMap;

public class TextureLoader {
    private static HashMap<String, Texture> textures = new HashMap<>();

    public static Texture getTexture(String textureString) {
        if (textures.get(textureString) == null) {
            try {
                loadTexture(textureString);
            } catch (GdxRuntimeException e) {
                System.out.println("Could not find texture: " + textureString);
                return getTexture("sharpshooterResources/images/ui/missing_texture.png");
            }
        }
        return textures.get(textureString);
    }

    private static void loadTexture(String textureString) throws GdxRuntimeException {
        System.out.println("sharpshooterMod | Loading Texture: " + textureString);
        Texture texture = new Texture(textureString);
        texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        textures.put(textureString, texture);
    }

    @SpirePatch(clz = Texture.class, method = "dispose")
    public static class DisposeListener {
        @SpirePrefixPatch
        public static void DisposeListenerPatch(Texture __instance) {
            TextureLoader.textures.entrySet().removeIf(entry -> {
                if (((Texture) entry.getValue()).equals(__instance)) {
                    System.out.println("TextureLoader | Removing Texture: " + ((String) entry.getKey()));
                }
                return ((Texture) entry.getValue()).equals(__instance);
            });
        }
    }
}