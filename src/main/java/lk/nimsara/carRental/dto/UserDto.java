package lk.nimsara.carRental.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public class UserDto {
        private String User_id;
        private String User_name;
        private String User_password;
        private String User_email;
        private String Employee_id;
        private String type;
    }


