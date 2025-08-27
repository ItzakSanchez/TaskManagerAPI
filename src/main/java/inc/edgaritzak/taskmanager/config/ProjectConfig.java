package inc.edgaritzak.taskmanager.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ProjectConfig {

    @Value("${project.max-duration-days}")
    private int maxDurationDays;

    public int getMaxDurationDays() {
        return maxDurationDays;
    }
}
