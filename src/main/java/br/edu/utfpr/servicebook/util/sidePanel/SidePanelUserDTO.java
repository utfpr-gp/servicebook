package br.edu.utfpr.servicebook.util.sidePanel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SidePanelUserDTO {
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
