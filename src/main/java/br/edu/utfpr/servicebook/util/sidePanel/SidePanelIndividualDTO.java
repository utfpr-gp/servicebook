package br.edu.utfpr.servicebook.util.sidePanel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SidePanelIndividualDTO {

    private Long id;
    private String name;
    private String description;
    private int rating;
    protected String profilePicture;
    protected boolean phoneVerified;
    protected boolean emailVerified;
    protected boolean profileVerified;
    protected Long followingAmount;
}
