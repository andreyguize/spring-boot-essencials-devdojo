package academy.devdojo.springboot.requests;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Builder
public class AnimePostRequestBody {
    @NotEmpty(message = "The anime name cannot be empty")
    @NotNull
    private String name;
}
