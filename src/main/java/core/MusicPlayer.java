package core;

import com.badlogic.gdx.audio.Music;

public class MusicPlayer {

    private static Music music;

    public static void play() {
        music.setLooping(true);
        music.play();
    }

    public static void pause() {
        music.pause();
    }

    public static void stop() {
        music.stop();
    }

    public static boolean isPlaying() {
        return music.isPlaying();
    }

    public static Music getMusic() {
        return music;
    }

    public static void setMusic(Music music) {
        MusicPlayer.music = music;
    }
}