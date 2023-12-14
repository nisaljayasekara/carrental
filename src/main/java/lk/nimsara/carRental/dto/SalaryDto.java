package lk.nimsara.carRental.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class SalaryDto {
    private String Salary_id;
    private String Salary_date;
    private String SalaryAmount;
    private String Employee_id;

}

