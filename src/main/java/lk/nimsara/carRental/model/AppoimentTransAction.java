package lk.nimsara.carRental.model;

import lk.nimsara.carRental.db.DbConnection;
import lk.nimsara.carRental.dto.AppoimentDto;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLOutput;

public class AppoimentTransAction {

    private AppoimentModel appoimentModel=new AppoimentModel();

    private CarModel carModel=new CarModel();
    public boolean saveAppoiment(AppoimentDto dto, String carId) throws SQLException {
        boolean resalt = false;
        Connection connection=null;
        System.out.println("1");

        try{
            connection= DbConnection.getInstance().getConnection();
            connection.setAutoCommit(false);
            boolean isSaved =appoimentModel.saveAppoiment(dto);
            System.out.println("2");
            if(isSaved){
                boolean isUpdated = carModel.updateCarstatus(carId);
                System.out.println("3");
                if (isUpdated){
                    connection.commit();
                    resalt = true;
                }
            }

        }catch(SQLException e){
            connection.rollback();
        }finally {
            connection.setAutoCommit(true);
        }
        return resalt;
    }


}
