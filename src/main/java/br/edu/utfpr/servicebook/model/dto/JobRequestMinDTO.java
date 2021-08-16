
package br.edu.utfpr.servicebook.model.dto;

        import br.edu.utfpr.servicebook.model.entity.Expertise;
        import br.edu.utfpr.servicebook.model.entity.JobCandidate;
        import br.edu.utfpr.servicebook.model.entity.JobContracted;
        import lombok.AllArgsConstructor;
        import lombok.Data;
        import lombok.NoArgsConstructor;
        import org.springframework.web.multipart.MultipartFile;
        import java.io.Serializable;
        import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobRequestMinDTO implements Serializable {

    private Long id;

    private Expertise expertise;

    private LocalDate dateCreated;

    public String status;

    private String description;

}
