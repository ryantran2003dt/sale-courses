package com.salecourseweb.dto.reponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ModuleReponse {
    private Long id;
    private String title;
    private String link;
    private String icon;
}
