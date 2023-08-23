package core.database.objects;

public class Settings {

    private int id;
    private int music;
    private int soundEffects;
    private String language;
    private int keyUpCode;
    private int keyDownCode;
    private int keyLeftCode;
    private int keyRightCode;
    private int keyShootCode;
    private int keyPauseCode;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMusic() {
        return music;
    }

    public void setMusic(int music) {
        this.music = music;
    }

    public int getSoundEffects() {
        return soundEffects;
    }

    public void setSoundEffects(int soundEffects) {
        this.soundEffects = soundEffects;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public int getKeyUpCode() {
        return keyUpCode;
    }

    public void setKeyUpCode(int keyUpCode) {
        this.keyUpCode = keyUpCode;
    }

    public int getKeyDownCode() {
        return keyDownCode;
    }

    public void setKeyDownCode(int keyDownCode) {
        this.keyDownCode = keyDownCode;
    }

    public int getKeyLeftCode() {
        return keyLeftCode;
    }

    public void setKeyLeftCode(int keyLeftCode) {
        this.keyLeftCode = keyLeftCode;
    }

    public int getKeyRightCode() {
        return keyRightCode;
    }

    public void setKeyRightCode(int keyRightCode) {
        this.keyRightCode = keyRightCode;
    }

    public int getKeyShootCode() {
        return keyShootCode;
    }

    public void setKeyShootCode(int keyShootCode) {
        this.keyShootCode = keyShootCode;
    }

    public int getKeyPauseCode() {
        return keyPauseCode;
    }

    public void setKeyPauseCode(int keyPauseCode) {
        this.keyPauseCode = keyPauseCode;
    }
}