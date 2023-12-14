drop database if exists carrental;
create database if not exists carrental;
use carrental;

CREATE TABLE Employee(
                         Employee_id varchar(20) primary key,
                         Employee_name varchar(150) not null,
                         Employee_address varchar(150) not null,
                         Employee_job_category varchar(150) not null,
                         Employee_contactNum varchar(20)
);

CREATE TABLE User(

                     User_id varchar(20) primary key,
                     User_name varchar(150) not null,
                     User_password varchar(50)  not null,
                     User_email    varchar(100) not null,
                     Employee_id   varchar(20)  not null,
                     constraint foreign key (Employee_id) references Employee (Employee_id) on delete cascade on update cascade
);

CREATE TABLE Customer(
                         Customer_id varchar(20) primary key,
                         Customer_name varchar(150)  not null,
                         Customer_address varchar(100)  not null,
                         Email varchar(150),
                        Tel varchar(100)  not null,
                         User_id varchar(20) not null,
                         constraint foreign key (User_id) references User(User_id)   on delete cascade on update cascade

);


CREATE TABLE Salary(
                       Salary_id varchar(20) primary key,
                       Date DATE not null,
                       Amount DECIMAL(10,2),
                       Employee_id varchar(20) not null,
                       constraint foreign key (Employee_id) references Employee(Employee_id)   on delete cascade on update cascade
);



CREATE TABLE Attendance(
                           Attendance_id varchar(20) primary key,
                           Attendance_date DATE not null,
                           Attendance_time VARCHAR(10) not null,
                           Employee_id varchar(20) not null,
                           constraint foreign key (Employee_id) references Employee(Employee_id)   on delete cascade on update cascade
);



CREATE TABLE Appoiment(
                          Appoiment_id  varchar(20) primary key,
                          Appoiment_date DATE not null,
                          Appoiment_time Time not null,
                          Return_date DATE not null,
                          Customer_id varchar(20) not null,
                          constraint foreign key (Customer_id) references Customer(Customer_id)   on delete cascade on update cascade
);




CREATE TABLE Car(
                    Car_id varchar(20) primary key,
                    Car_name  varchar(50) not null,
                    Car_availability varchar(100) not null
);


CREATE TABLE Booking_Details(
                                Car_id      varchar(20)    not null,
                                Appoiment_id      varchar(20)    not null,
                                Appoiment_day_qty int(50)        not null,
                                Payment         DECIMAL(10, 2) not null,
                                constraint foreign key (Car_id  ) references Car (Car_id ) on delete cascade on update cascade,
                                constraint foreign key (Appoiment_id) references Appoiment (Appoiment_id) on delete cascade on update cascade
);



CREATE TABLE Rapier(
                       Rapier_id varchar(35) primary key,
                       Rapier_date  DATE not null,
                       Rapier_desc  varchar(100) not null,
                       Rapier_returnDate  DATE not null,
                       Rapier_price DECIMAL(10,2),
                       Car_id varchar(35) not null,
                       constraint foreign key (Car_id) references Car(Car_id)   on delete cascade on update cascade
);




CREATE TABLE Supplier(
                         Supplier_id varchar(35) primary key,
                         Supplier_name  varchar(155)  not null,
                         Supplier_email varchar(100) not null,
                         Tel  varchar(100)  not null,
                         User_id varchar(35) not null,
                         constraint foreign key (User_id) references User(User_id)  on delete cascade on update cascade
);



show tables ;



CREATE TABLE Supplier_oder(
                              Oder_id varchar(35) primary key,
                              Oder_desc  varchar(100) not null,
                              Unit_price DECIMAL(10,2) not null,
                              oder_qty int(100) not null,
                              Supplier_id  varchar(35) not null,
                              constraint foreign key (Supplier_id) references Supplier(Supplier_id)  on delete cascade on update cascade
);


CREATE TABLE Stock(
                      Stock_id  varchar(35) primary key,
                      description varchar(155)  not null,
                      qty_On_hand int(100) not null
);



CREATE TABLE Oder_details(
                             Oder_id varchar(35) not null,
                             Stock_id varchar(35) not null,
                             date DATE not null,
                             constraint foreign key (Oder_id) references Supplier_oder(Oder_id)  on delete cascade on update cascade,
                             constraint foreign key (Stock_id ) references Stock(Stock_id)  on delete cascade on update cascade
);