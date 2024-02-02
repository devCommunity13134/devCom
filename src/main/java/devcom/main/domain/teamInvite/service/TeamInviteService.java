package devcom.main.domain.teamInvite.service;

import devcom.main.domain.team.entity.Team;
import devcom.main.domain.teamInvite.entity.TeamInvite;
import devcom.main.domain.teamInvite.repository.TeamInviteRepository;
import devcom.main.domain.user.entity.SiteUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TeamInviteService {
    private final TeamInviteRepository teamInviteRepository;

    public void create(Team team, SiteUser siteUser) {

        LocalDateTime now = LocalDateTime.now();
        TeamInvite teamInvite = TeamInvite.builder().team(team).siteUser(siteUser).expireDate(now.plusDays(3)).build();
        teamInviteRepository.save(teamInvite);

    }

    public TeamInvite findById(Long inviteId) {
        return teamInviteRepository.findById(inviteId).get();
    }

    public void delete(TeamInvite invite) {
        teamInviteRepository.delete(invite);
    }
}
