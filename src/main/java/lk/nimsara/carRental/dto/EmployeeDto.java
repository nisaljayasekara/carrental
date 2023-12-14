package lk.nimsara.carRental.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class EmployeeDto {
    private String Employee_id;
    private String Employee_name;
    private String Employee_address;
    private String Employee_job_category;
    private String Employee_contactNum;
}
