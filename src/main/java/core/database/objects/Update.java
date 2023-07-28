package core.database.objects;

public class Update {

    private int earnedMoney;
    private int hostageKilledMoney;
    private int enemyKilledMoney;
    private int ammoCosts;
    private String usedTime;
    private int bonusPenalty;
    private int totalMoney;

    public Update(int earnedMoney, int hostageKilledMoney, int enemyKilledMoney, int ammoCosts, String usedTime, int bonusPenalty, int totalMoney) {
        this.earnedMoney = earnedMoney;
        this.hostageKilledMoney = hostageKilledMoney;
        this.enemyKilledMoney = enemyKilledMoney;
        this.ammoCosts = ammoCosts;
        this.usedTime = usedTime;
        this.bonusPenalty = bonusPenalty;
        this.totalMoney = totalMoney;
    }

    public Update() {}

    public int getEarnedMoney() {
        return earnedMoney;
    }

    public int getHostageKilledMoney() {
        return hostageKilledMoney;
    }

    public int getEnemyKilledMoney() {
        return enemyKilledMoney;
    }

    public int getAmmoCosts() {
        return ammoCosts;
    }

    public String getUsedTime() {
        return usedTime;
    }

    public int getBonusPenalty() {
        return bonusPenalty;
    }

    public int getTotalMoney() {
        return totalMoney;
    }
}