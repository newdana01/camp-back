package everyCamp.campback.team.entity;

import everyCamp.campback.user.entity.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "team_members")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class TeamMember{
    @Id
    @Column(name = "team_member_id")
    @GeneratedValue(generator = "uuid4")
    @GenericGenerator(name = "uuid4", strategy = "org.hibernate.id.UUIDGenerator")
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private String id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    @Column(name = "join_dt_or_null")
    private LocalDateTime joinDtOrNull;
    @Column(name = "exit_dt_or_null")
    private LocalDateTime exitDtOrNull;

    @Builder
    protected TeamMember(
            User user
    ) {
        this.user = user;
    }

    public void exitTeam() {
        this.exitDtOrNull = LocalDateTime.now();
    }

    public void setTeam(Team team) {
        team.getTeamMembers().add(this);
        this.joinDtOrNull = LocalDateTime.now();

        this.team = team;
    }

    public void joinTeam() {this.joinDtOrNull = LocalDateTime.now();}
}
