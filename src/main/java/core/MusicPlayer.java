package core;

import com.badlogic.gdx.audio.Music;
import core.database.objects.Settings;
import java.util.Map;

public class MusicPlayer {
    private final Settings settings;
    private final Map<MusicType, Music> musicMap;

    public MusicPlayer() {
        this.settings = GameData.SETTINGS;
        this.musicMap = GameData.MUSIC_MAP;
    }

    public enum MusicType {
        BASIC,
        MISSION,
    }

    public void playMusic(MusicType musicType) {
        if (settings.getMusic() == 1) {
            Music music = musicMap.get(musicType);
            if (music != null) {
                music.setLooping(true);
                music.play();
            }
        }
    }

    public void stopMusic(MusicType musicType) {
        Music music = musicMap.get(musicType);
        if (music != null) {
            music.stop();
        }
    }

    public void stopAll() {
        for (Music music : musicMap.values()) {
            music.stop();
        }
    }
}