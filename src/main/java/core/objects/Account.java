package core.objects;

public class Account {

    private Long id;
    private String username;
    private int rank;
    private float money;

    public Account() {}

    public Account(Long id, String username, int rank, float money) {
        this.id = id;
        this.username = username;
        this.rank = rank;
        this.money = money;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public int getRank() {
        return rank;
    }

    public float getMoney() {
        return money;
    }

    public void setMoney(float money) {
        this.money = money;
    }
}