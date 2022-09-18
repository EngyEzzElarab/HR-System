CREATE TABLE department
(
    id   INT         NOT NULL AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    PRIMARY KEY (id)
);
CREATE TABLE team
(
    id            INT         NOT NULL AUTO_INCREMENT,
    name          VARCHAR(50) NOT NULL,
    department_id INT         NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (department_id) REFERENCES department (id)

);
CREATE TABLE employee
(
    id            INT                    NOT NULL AUTO_INCREMENT,
    national_id   INT                    NOT NULL UNIQUE,
    name          VARCHAR(50)            NOT NULL,
    gender        ENUM ('FEMALE','MALE') NOT NULL,
    date_of_birth DATE                   NOT NULL,
    grad_date     DATE                   NOT NULL,
    gross_salary  DOUBLE                 NOT NULL,
    department_id INT                    NOT NULL,
    team_id       INT                    NOT NULL,
    manager_id    INT                    NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (department_id) REFERENCES department (id),
    FOREIGN KEY (team_id) REFERENCES team (id),
    FOREIGN KEY (manager_id) REFERENCES employee (id)
);
