package moonduck.server.dto.query;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import moonduck.server.enums.Category;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryCountDTO {

    private Category category;
    private long count;
}
