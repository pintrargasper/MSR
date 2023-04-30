package core.screens.helper;

import com.badlogic.gdx.utils.TimeUtils;
import core.views.NavigationBar;

public class DurationTimer {

    private final NavigationBar navigationBar;
    private Long startTime;
    private int hours, minutes, seconds;
    private String time;

    public DurationTimer(NavigationBar navigationBar) {
        this.navigationBar = navigationBar;
        this.startTime = TimeUtils.nanoTime();
    }

    public void timer() {
        long timer = 1000000000L;
        if (TimeUtils.timeSinceNanos(startTime) >= timer) {
            createTime();
            startTime = TimeUtils.nanoTime();
        }
    }

    private void createTime() {

        seconds++;
        if (seconds > 59) {
            minutes++;
            seconds = 0;
        }

        if (minutes > 59) {
            hours++;
            minutes = 0;
        }

        var lastSeconds = seconds < 10 ? "0" + seconds : String.valueOf(seconds);
        var lastMinutes = minutes < 10 ? "0" + minutes : String.valueOf(minutes);
        var lastHours = hours < 10 ? "0" + hours : String.valueOf(hours);

        time = lastHours + ":" + lastMinutes + ":" + lastSeconds;

        navigationBar.updateDurationTimer(time);
    }

    public String  getTime() {
        return time;
    }
}