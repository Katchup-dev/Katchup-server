package site.katchup.katchupserver.api.TaskKeyword.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.katchup.katchupserver.api.keyword.domain.Keyword;
import site.katchup.katchupserver.api.task.domain.Task;

@Entity
@Table(name = "task_keyword")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TaskKeyword {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task task;

    @ManyToOne
    @JoinColumn(name = "keyword_id")
    private Keyword keyword;

    @Builder
    public TaskKeyword(Long id, Task task, Keyword keyword) {
        this.id = id;
        this.task = task;
        this.keyword = keyword;
    }
}
