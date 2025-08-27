package inc.edgaritzak.taskmanager.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class TaskConfig {

    @Value("${task.default-priority}")
    private String defaultPriority;

    @Value("${task.default-state}")
    private String defaultState;

    public String getDefaultPriority() {
        return defaultPriority;
    }
    public String getDefaultState() {
        return defaultState;
    }
}
