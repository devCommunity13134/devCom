package devcom.main.domain.message;


import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RemoveMessageForm {

    private List<String> id;

}
