package devcom.main.domain.skill.service;


import devcom.main.domain.skill.entity.Skill;
import devcom.main.domain.skill.repository.SkillRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
}
