package lk.nimsara.carRental.model;

import lk.nimsara.carRental.db.DbConnection;
import lk.nimsara.carRental.dto.AppoimentDto;

import java.sql.Connection;
import java.sql.SQLException;

public class RepairTransAction {

        private AppoimentModel appoimentModel=new AppoimentModel();

        private RapierModel rapierModel= new RapierModel();
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
                    boolean isUpdated = RapierModel.updateCarstatus2(carId);
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


