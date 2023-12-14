package lk.nimsara.carrental.model;

import lk.nimsara.carRental.db.DbConnection;
import lk.nimsara.carRental.dto.CustomerDto;
import lk.nimsara.carRental.dto.EmployeeDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeModel {
    public boolean saveEmployee(EmployeeDto employeeDto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO employee VALUES(?, ?, ?, ?,?)";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1,employeeDto.getEmployee_id());
        pstm.setString(2,employeeDto.getEmployee_name());
        pstm.setString(3,employeeDto.getEmployee_address());
        pstm.setString(4,employeeDto.getEmployee_job_category());
        pstm.setString(5,employeeDto.getEmployee_contactNum());

        int i = pstm.executeUpdate();

        return i > 0;
    }

    public boolean deleteEmployee(String id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "DELETE FROM employee Where Employee_id =?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1,id);
        return pstm.executeUpdate()>0;
    }

    public boolean updateEmployee(EmployeeDto employeeDto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "UPDATE employee SET Employee_name =?,Employee_address=?,Employee_job_category=?,Employee_contactNum=? WHERE Employee_id=? ";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, employeeDto.getEmployee_name());
        pstm.setString(2, employeeDto.getEmployee_address());
        pstm.setString(3, employeeDto.getEmployee_job_category());
        pstm.setString(4, employeeDto.getEmployee_contactNum());
        pstm.setString(5, employeeDto.getEmployee_id());

        return pstm.executeUpdate()>0;

    }

    public List<EmployeeDto> getAllEmployee() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql ="SELECT * FROM employee";

        PreparedStatement pstm = connection.prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<EmployeeDto> emptList =new ArrayList<>();

        while (resultSet.next()){
            EmployeeDto dto =new EmployeeDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5));

            emptList.add(dto);
        }
        return emptList;

    }

    public EmployeeDto searchEmployee(String empId) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM employee WHERE Employee_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, empId);
        ResultSet resultSet = pstm.executeQuery();

        EmployeeDto employeeDto = null;

        if (resultSet.next()){

            String Employee_id = resultSet.getString(1);
            String Employee_name = resultSet.getString(2);
            String Employee_address = resultSet.getString(3);
            String Employee_job_category = resultSet.getString(4);
            String Employee_contactNum = resultSet.getString(5);


            employeeDto=new EmployeeDto(Employee_id,Employee_name,Employee_address,Employee_job_category,Employee_contactNum);

        }
        return employeeDto;
    }

    public List<EmployeeDto> loadAllEmployees() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM Employee";
        ResultSet resultSet = connection.prepareStatement(sql).executeQuery();

        List<EmployeeDto> empList = new ArrayList<>();

        while (resultSet.next()){
            empList.add(new EmployeeDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5)
            ));
        }
        return empList;
    }
}
