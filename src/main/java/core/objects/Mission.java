package core.objects;

public class Mission {

    private Long id;
    private String name;
    private String picture;
    private String map;
    private float price;
    private String description;
    private int completed;
    private String createdAt;
    private int maxScore;
    private int lastScore;

    public Mission() {}

    public Mission(Long id, String name, String picture, String map, float price, String description, int completed, String createdAt, int maxScore, int lastScore) {
        this.id = id;
        this.name = name;
        this.picture = picture;
        this.map = map;
        this.price = price;
        this.description = description;
        this.completed = completed;
        this.createdAt = createdAt;
        this.maxScore = maxScore;
        this.lastScore = lastScore;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPicture() {
        return picture;
    }

    public String getMap() {
        return map;
    }

    public float getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public int getCompleted() {
        return completed;
    }

    public void setCompleted(int completed) {
        this.completed = completed;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public int getMaxScore() {
        return maxScore;
    }

    public void setMaxScore(int maxScore) {
        this.maxScore = maxScore;
    }

    public int getLastScore() {
        return lastScore;
    }

    public void setLastScore(int lastScore) {
        this.lastScore = lastScore;
    }
}