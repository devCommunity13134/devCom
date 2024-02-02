package devcom.main.domain.teamInvite.service;

import devcom.main.domain.team.entity.Team;
import devcom.main.domain.teamInvite.entity.TeamInvite;
import devcom.main.domain.teamInvite.repository.TeamInviteRepository;
import devcom.main.domain.teamMember.entity.TeamMember;
import devcom.main.domain.user.entity.SiteUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TeamInviteService {
    private final TeamInviteRepository teamInviteRepository;

    public TeamInvite create(Team team, SiteUser siteUser) {

        LocalDateTime now = LocalDateTime.now();
        TeamInvite teamInvite = TeamInvite.builder().team(team).siteUser(siteUser).expireDate(now.plusDays(3)).build();
        teamInviteRepository.save(teamInvite);

        return teamInvite;
    }

    public Optional<TeamInvite> findById(Long inviteId) {
        return teamInviteRepository.findById(inviteId);
    }

    public void delete(TeamInvite invite) {
        teamInviteRepository.delete(invite);
    }

    public Optional<TeamInvite> getTeamInviteByTeamAndSiteUser(Team team, SiteUser invitedUser) {
        return teamInviteRepository.findByTeamAndSiteUser(team,invitedUser);
    }

    public TeamInvite expireDateUpdate(TeamInvite teamInvite) {
        teamInvite = teamInvite.toBuilder().expireDate(LocalDateTime.now().plusDays(3)).build();
        teamInviteRepository.save(teamInvite);
        return teamInvite;
    }
}
