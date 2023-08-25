package core;

import com.badlogic.gdx.audio.Music;
import core.database.objects.Settings;
import java.util.Map;

public class SoundEffectPlayer {
    private final Settings settings;
    private final Map<SoundEffectType, Music> soundEffectMap;

    public SoundEffectPlayer() {
        this.settings = GameData.SETTINGS;
        this.soundEffectMap = GameData.SOUND_EFFECT_MAP;
    }

    public enum SoundEffectType {
        WALKING_EFFECT,
        JUMP_EFFECT,
        SHOOT_EFFECT,
        ENEMY_SHOOT_EFFECT,
        COLLECT_EFFECT,
        HIT_EFFECT
    }

    public void playEffect(SoundEffectType soundEffectType) {
        if (settings.getSoundEffects() == 1) {
            Music effect = soundEffectMap.get(soundEffectType);
            if (effect != null) {
                effect.play();
            }
        }
    }

    public void stopEffect(SoundEffectType soundEffectType) {
        Music effect = soundEffectMap.get(soundEffectType);
        if (effect != null) {
            effect.stop();
        }
    }

    public void stopAll() {
        for (Music effect : soundEffectMap.values()) {
            effect.stop();
        }
    }
}