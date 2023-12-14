package lk.nimsara.carRental.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
    public class SupplierDto {
        private String supplier_id;
        private String supplier_name;
        private String supplier_email;
        private String tel;
        private String user_id;
    }



