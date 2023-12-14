package lk.nimsara.carRental.model;

import lk.nimsara.carRental.db.DbConnection;
import lk.nimsara.carRental.dto.CarDto;
import lk.nimsara.carRental.dto.CustomerDto;
import lk.nimsara.carRental.dto.EmployeeDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CarModel {
    public boolean saveCar(final CarDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO car VALUES(?, ?, ?,?)";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getId());
        pstm.setString(2, dto.getName());
        pstm.setString(3, dto.getAvailability());
        pstm.setString(4, dto.getPrice());


        boolean isSaved = pstm.executeUpdate() > 0;

        return isSaved;
    }

    public boolean deleteCar(String id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "DELETE FROM car WHERE Car_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, id);

        return pstm.executeUpdate() > 0;
    }

    public static List<CarDto> getAllCars() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "SELECT * FROM car";

        PreparedStatement pstm = connection.prepareStatement(sql);
        List<CarDto> dtoList = new ArrayList<>();

        ResultSet resultSet = pstm.executeQuery();


        while (resultSet.next()) {
            String id = resultSet.getString(1);
            String name = resultSet.getString(2);
            String availability = resultSet.getString(3);
            String price =resultSet.getString(4);


            var dto = new CarDto(id, name, availability,price);
            dtoList.add(dto);
        }

        return dtoList;
    }
  public boolean updateCar(final CarDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql ="UPDATE car SET Car_name =?,Car_availability =?,Car_price =? WHERE Car_id=?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getName());
        pstm.setString(2, dto.getAvailability());
        pstm.setString(3, dto.getPrice());
        pstm.setString(4, dto.getId());



        return pstm.executeUpdate() > 0;
    }

    public static CarDto searchCar(String id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM car WHERE Car_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, id);

        ResultSet resultSet = pstm.executeQuery();

        CarDto carDto = null;

        if (resultSet.next()){
            String Id=resultSet.getString(1);
            String Name=resultSet.getString(2);
            String Availability =resultSet.getString(3);
            String price =resultSet.getString(4);

            carDto =new CarDto(Id,Name,Availability,price);
        }
        return carDto;
    }
    public static List<CarDto> loadAllCars() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM car";
        ResultSet resultSet = connection.prepareStatement(sql).executeQuery();

        List<CarDto> carList = new ArrayList<>();

        while (resultSet.next()){
            carList.add(new CarDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4)

            ));
        }
        return carList;
    }


    public boolean updateCarstatus(String carId) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = " update car set Car_Availability = ? where Car_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1 ,"No");
        preparedStatement.setString(2,carId);

        return preparedStatement.executeUpdate() > 0;
    }
}