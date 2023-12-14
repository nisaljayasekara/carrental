package lk.nimsara.carRental.model;

import lk.nimsara.carRental.db.DbConnection;
import lk.nimsara.carRental.dto.CustomerDto;
import lk.nimsara.carRental.dto.UserDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserModel {
    public static String verifyUser(String userName,String password){
        try {
            DbConnection dbConnection = DbConnection.getInstance();
            Connection connection = dbConnection.getConnection();

            String sql = "SELECT User_password,User_id,type FROM user WHERE USER_name =?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1,userName);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()){
                if (password.equals(resultSet.getString(1)) && resultSet.getString(3).equals("admin")){
                    return resultSet.getString(2);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }


    public List<UserDto> loadAllUser() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM user";
        ResultSet resultSet = connection.prepareStatement(sql).executeQuery();

        List<UserDto> userList = new ArrayList<>();

        while (resultSet.next()){
            userList.add(new UserDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getString(6)
                    )
            );

        }
        return userList;
    }

    public UserDto searchUser(String id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM user WHERE User_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, id);

        ResultSet resultSet = pstm.executeQuery();

        UserDto dto = null;

        if (resultSet.next()){
            String User_id =resultSet.getString(1);
            String User_name =resultSet.getString(2);
            String User_password =resultSet.getString(3);
            String User_email =resultSet.getString(4);
            String Employee_id =resultSet.getString(5);
            String type =resultSet.getString(6);


            dto =new UserDto(User_id,User_name,User_password,User_email,Employee_id,type);
        }
        return dto;
    }
}
