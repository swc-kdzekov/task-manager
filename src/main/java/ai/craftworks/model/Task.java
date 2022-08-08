package ai.craftworks.model;

import java.sql.Timestamp;
import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class Task {
    private String id;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private Date dueDate;
    private Timestamp resolvedAt;
    private String title;
    private String description;
    private int priority;
    private boolean status;
}
