package core.database.objects;

public class MissionBestScore {

    private Long id;
    private String missionName;
    private String missionPicture;
    private String username;
    private int score;

    public MissionBestScore() {}

    public Long getId() {
        return id;
    }

    public String getMissionName() {
        return missionName;
    }

    public String getMissionPicture() {
        return missionPicture;
    }

    public String getUsername() {
        return username;
    }

    public int getScore() {
        return score;
    }
}