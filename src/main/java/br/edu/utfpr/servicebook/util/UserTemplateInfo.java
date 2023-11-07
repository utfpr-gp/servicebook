package br.edu.utfpr.servicebook.util;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserTemplateInfo {
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
