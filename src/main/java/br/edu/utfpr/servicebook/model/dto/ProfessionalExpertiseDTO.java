package br.edu.utfpr.servicebook.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfessionalExpertiseDTO {
    private String ids;

//    public int[] getIds() {
//        if (this.ids == null) {
//            int[] ints = {};
//            return ints;
//        }
//
//        String[] idsString = this.ids.split(",");
//        int[] ids = new int[idsString.length];
//
//        for (int i = 0; i < ids.length; i++) {
//            ids[i] = Integer.parseInt(idsString[i]);
//        }
//
//        return ids;
//    }
}
