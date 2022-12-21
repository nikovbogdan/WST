package dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class Client {
    private String name;
    private String town;
    private String address;
    private String bulstat;
    private Boolean is_reg_vat;
    private String vat_number;
    private String mol;
    private Boolean is_person;
    private String egn;
    private String country;
    private String code;
    private String office;

}

