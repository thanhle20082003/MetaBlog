package fullstack.website.blog.utils.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SearchCriteria {
    private Integer page;
    private Integer size;
    private String columSort;

}
