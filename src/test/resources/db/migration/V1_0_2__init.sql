INSERT INTO department (id,name) VALUES (1,'IT');
INSERT INTO department (id,name) VALUES (2,'HR');
INSERT INTO team (id,name,department_id)VALUES (1,'TeamA',1);
INSERT INTO team (id,name,department_id)VALUES (2,'TeamB',1);
INSERT INTO employee (id,national_id, name, gender,date_of_birth,grad_date,gross_salary,manager_id,department_id,team_id) VALUES (1,1000,'khaled','MALE','2000-09-14','2023-07-01',100000.0,NULL,1,1);
INSERT INTO employee (id,national_id, name, gender,date_of_birth,grad_date,gross_salary,manager_id,department_id,team_id) VALUES (2,1001, 'amany','FEMALE','2000-09-14','2023-07-01',300.0,1,1,1);
INSERT INTO employee (id,national_id, name, gender,date_of_birth,grad_date,gross_salary,manager_id,department_id,team_id) VALUES (3,1002, 'omar','MALE','2000-09-14','2023-07-01',400.0,1,1,2);
INSERT INTO employee (id,national_id, name, gender,date_of_birth,grad_date,gross_salary,manager_id,department_id,team_id) VALUES (4,1003, 'engy','MALE','2000-09-14','2023-07-01',500.0,3,1,2);
//INSERT INTO employee (id,national_id, name, gender,date_of_birth,grad_date,gross_salary,manager_id,department_id,team_id) VALUES (5,1004, 'ezz','MALE','2000-09-14','2023-07-01',500.0,NULL,1,2);

