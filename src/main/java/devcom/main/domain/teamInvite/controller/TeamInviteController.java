package devcom.main.domain.teamInvite.controller;

import com.google.gson.Gson;
import devcom.main.domain.team.entity.Team;
import devcom.main.domain.team.service.TeamAndProjectService;
import devcom.main.domain.teamInvite.TeamInviteForm;
import devcom.main.domain.teamInvite.entity.TeamInvite;
import devcom.main.domain.user.entity.SiteUser;
import devcom.main.domain.user.service.UserService;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/teamInvite")
public class TeamInviteController {

    private final UserService userService;
    private final TeamAndProjectService teamAndProjectService;

    @Getter
    public static class TeamInviteResponse{
        private Boolean isSuccess;
        private String message;

        public TeamInviteResponse(Boolean isSuccess, String message) {
            this.isSuccess = isSuccess;
            this.message = message;
        }
    }

    @GetMapping("/inviteRes/{inviteId}/{yesOrNo}")
    public String inviteRes(@PathVariable("inviteId") Long inviteId,@PathVariable("yesOrNo") String yesOrNo, Principal principal) {

        SiteUser siteUser = userService.findByUsername(principal.getName());

        teamAndProjectService.inviteResponse(inviteId,yesOrNo,siteUser);

        return "redirect:/";
    }

    @PostMapping("/inviteMember")
    @ResponseBody
    public String inviteMember(@Valid @RequestBody TeamInviteForm teamInviteForm, BindingResult bindingResult, Principal principal) {

        SiteUser siteUser = userService.findByUsername(principal.getName());
        Gson gson = new Gson();

        return gson.toJson(teamAndProjectService.inviteMember(teamInviteForm, siteUser, bindingResult));
    }

    @GetMapping("/delete/{inviteId}")
    public String deleteInvite(@PathVariable("inviteId") Long inviteId, Principal principal) {

        SiteUser siteUser = userService.findByUsername(principal.getName());

        Long teamId = teamAndProjectService.deleteInvite(inviteId,siteUser);

        return "redirect:/teamMember/"+teamId;
    }
}
