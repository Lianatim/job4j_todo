package ru.job4j.todo.util;

import ru.job4j.todo.model.Task;
import ru.job4j.todo.model.User;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

public final class ZoneSetTime {

    private ZoneSetTime() {
    }

    public static void setTimeZone(User user, Task task) {
        ZonedDateTime zonedDateTime;
        if (user.getUserZone() == null) {
            zonedDateTime = task.getCreated().withZoneSameInstant(TimeZone.getDefault().toZoneId());
        } else {
            zonedDateTime = task.getCreated().withZoneSameInstant(ZoneId.of(user.getUserZone()));
        }
        task.setCreated(zonedDateTime);
    }

    public static List<TimeZone> getZones() {
        var zones = new ArrayList<TimeZone>();
        for (String timeId : TimeZone.getAvailableIDs()) {
            zones.add(TimeZone.getTimeZone(timeId));
        }
        return zones;
    }
}
