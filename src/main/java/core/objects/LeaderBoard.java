package core.objects;

public class LeaderBoard {

    private Long id;
    private String username;
    private String picture;
    private int rank;
    private String xp;
    private float completed;

    public LeaderBoard() {}

    public LeaderBoard(Long id, String username, String picture, int rank, String xp, float completed) {
        this.id = id;
        this.username = username;
        this.picture = picture;
        this.rank = rank;
        this.xp = xp;
        this.completed = completed;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPicture() {
        return picture;
    }

    public int getRank() {
        return rank;
    }

    public String getXp() {
        return xp;
    }

    public float getCompleted() {
        return completed;
    }
}