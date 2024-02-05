package devcom.main.domain.teamInvite.service;

import devcom.main.domain.message.entity.ReceiveMessage;
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

    public TeamInvite create(Team team, SiteUser siteUser,LocalDateTime expireDate) {

        TeamInvite teamInvite = TeamInvite.builder().team(team).siteUser(siteUser).expireDate(expireDate).build();
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

    public void reInvite(TeamInvite teamInvite, LocalDateTime dateTime, ReceiveMessage rm) {
        teamInvite = teamInvite.toBuilder().expireDate(dateTime).receiveMessage(rm).build();
        teamInviteRepository.save(teamInvite);
    }

    public void setMessage(TeamInvite invite, ReceiveMessage rm) {
        invite = invite.toBuilder().receiveMessage(rm).build();
        teamInviteRepository.save(invite);
    }

    public Optional<TeamInvite> getTeamInviteByReceiveMessage(ReceiveMessage receiveMessage) {
        return teamInviteRepository.findByReceiveMessage(receiveMessage);
    }
}
