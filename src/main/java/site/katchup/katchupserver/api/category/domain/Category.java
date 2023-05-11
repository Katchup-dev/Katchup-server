package site.katchup.katchupserver.api.category.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.katchup.katchupserver.api.folder.domain.Folder;
import site.katchup.katchupserver.api.member.domain.Member;
import site.katchup.katchupserver.common.domain.BaseEntity;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(name = "is_shared", nullable = false)
    private boolean isShared;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private List<Folder> folders = new ArrayList<>();

    @Builder
    public Category(String name, boolean isShared, Member member) {
        this.name = name;
        this.isShared = isShared;
        this.member = member;
    }

    public void addFolder(Folder folder) {
        folders.add(folder);
    }

}