package ai.craftworks.model;

import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class RequestTask {
    private String dueDate;
    private String title;
    private String description;
    private Integer priority;
}
