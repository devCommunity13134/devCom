package devcom.main.domain.team.repository;

import devcom.main.domain.team.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TeamRepository extends JpaRepository<Team, Long> {

    Optional<Team> findByName(String teamName);

    @Query(value = "select t.id ,t.name ,t.description,t.team_admin_id ,t.create_date ,t.modified_date  from team as t" +
                    " left join team_member tm" +
                    " on t.id = tm.team_id" +
                    " where tm.site_user_id=?1",
            nativeQuery = true)
    List<Team> findByUser(Long id);

}
