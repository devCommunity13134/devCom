package devcom.main.domain.skill.service;


import devcom.main.domain.skill.entity.Skill;
import devcom.main.domain.skill.repository.SkillRepository;
import devcom.main.domain.user.entity.SiteUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SkillService {

    private final SkillRepository skillRepository;

    public void save(Skill skill) {
        this.skillRepository.save(skill);
    }

    public List<Skill> findAll() {
        return this.skillRepository.findAll();
    }


    public void create(List<String> skills, SiteUser user) {
        for(int i = 0; i < skills.size(); i++) {
            Skill skill = Skill.builder()
                    .skillName(skills.get(i))
                    .user(user)
                    .build();

            this.skillRepository.save(skill);
        }
    }

    public List<Skill> findByskillList(List<String> skills) {
        List<Skill> skillList = new ArrayList<>();
        for(int i = 0; i < skills.size(); i++) {
            Skill skill = Skill.builder()
                    .skillName(skills.get(i))
                    .build();
            skillList.add(skill);
        }
        return skillList;
    }
}
