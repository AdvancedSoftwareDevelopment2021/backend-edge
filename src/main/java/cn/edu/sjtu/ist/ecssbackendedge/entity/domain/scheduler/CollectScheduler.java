package cn.edu.sjtu.ist.ecssbackendedge.entity.domain.scheduler;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.quartz.CalendarIntervalScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;

import java.util.Calendar;
import java.util.Date;

/**
 * @author dyanjun
 * @date 2021/10/28 10:31
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CollectScheduler {

    private int interval;

    private TimeUnit unit;

    private Date startTime;

    public Trigger generateTrigger() {
        CalendarIntervalScheduleBuilder scheduleBuilder = CalendarIntervalScheduleBuilder.calendarIntervalSchedule();
        switch (unit) {
            case HOUR:
                scheduleBuilder.withIntervalInHours(interval);
                break;
            case DAY:
                scheduleBuilder.withIntervalInDays(interval);
                break;
            case WEEK:
                scheduleBuilder.withIntervalInWeeks(interval);
                break;
            case MONTH:
                scheduleBuilder.withIntervalInMonths(interval);
                break;
            case MINUTE:
                scheduleBuilder.withIntervalInMinutes(interval);
                break;
            case SECOND:
                scheduleBuilder.withIntervalInSeconds(interval);
                break;
            default:
        }
        return TriggerBuilder.newTrigger().startAt(justifyStartTime()).withSchedule(scheduleBuilder).build();
    }

    public Date justifyStartTime() {
        Date now = new Date();
        if (startTime.before(now)) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(startTime);
            while (calendar.getTime().before(now)) {
                switch (unit) {
                    case HOUR:
                        calendar.add(Calendar.HOUR, interval);
                        continue;
                    case DAY:
                        calendar.add(Calendar.DATE, interval);
                        continue;
                    case WEEK:
                        calendar.add(Calendar.DATE, 7 * interval);
                        continue;
                    case MONTH:
                        calendar.add(Calendar.MONTH, interval);
                        continue;
                    case MINUTE:
                        calendar.add(Calendar.MINUTE, interval);
                        continue;
                    case SECOND:
                        calendar.add(Calendar.SECOND, interval);
                        continue;
                    default:
                }
            }
            return calendar.getTime();
        }
        return startTime;
    }
}
