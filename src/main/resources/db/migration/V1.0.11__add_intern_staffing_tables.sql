DROP TABLE IF EXISTS intern_hiring_status;
DROP TABLE IF EXISTS project_hiring_status;
DROP TABLE IF EXISTS intern_project_history;
DROP TABLE IF EXISTS intern_staffing;


CREATE TABLE intern_hiring_status (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  status varchar(45) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

CREATE TABLE project_hiring_status (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  status varchar(45) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

CREATE TABLE intern_staffing (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  employee_id bigint(20) NOT NULL,
  intern_hiring_status_id bigint(20) NOT NULL,
  internship_workload INT,
  workload INT,
  extension datetime NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT FK_employee_intern_staffing
  FOREIGN KEY (employee_id)
  REFERENCES employee(employee_id),
  CONSTRAINT FK_intern_hiring_status_id_intern_staffing
  FOREIGN KEY (intern_hiring_status_id)
  REFERENCES intern_hiring_status(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

CREATE TABLE intern_project_history (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  intern_staffing_id bigint(29) NOT NULL,
  project_id bigint(20) NOT NULL,
  responsible_person_id bigint(20) NOT NULL,
  project_hiring_status_id bigint(20) NOT NULL,
  comments varchar(200) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT FK_intern_project_history_intern_staffing
  FOREIGN KEY (intern_staffing_id)
  REFERENCES intern_staffing(id),
  CONSTRAINT FK_intern_project_history_project
  FOREIGN KEY (project_id)
  REFERENCES project(project_id),
  CONSTRAINT FK_intern_project_history_employee
  FOREIGN KEY (responsible_person_id)
  REFERENCES employee(employee_id),
  CONSTRAINT FK_project_hiring_status_id
  FOREIGN KEY (project_hiring_status_id)
  REFERENCES project_hiring_status(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;


 INSERT INTO intern_hiring_status(id, status) VALUES (1, 'Good Status');
 INSERT INTO intern_hiring_status(id, status) VALUES (2, 'Bad Status');
 INSERT INTO intern_hiring_status(id, status) VALUES (3, 'Neutral Status');

 INSERT INTO project_hiring_status(id, status) VALUES (1, 'Good Status');
 INSERT INTO project_hiring_status(id, status) VALUES (2, 'Bad Status');
 INSERT INTO project_hiring_status(id, status) VALUES (3, 'Neutral Status');

 INSERT INTO intern_staffing(id, employee_id, intern_hiring_status_id, internship_workload, workload, extension) VALUES (1, 3, 1, 1, 7, '2021-10-21');
 INSERT INTO intern_staffing(id, employee_id, intern_hiring_status_id, internship_workload, workload, extension) VALUES (2, 7, 1, 5, 4, '2021-10-21');
 INSERT INTO intern_staffing(id, employee_id, intern_hiring_status_id, internship_workload, workload, extension) VALUES (3, 8, 3, 7, 1, '2021-10-21');
 INSERT INTO intern_staffing(id, employee_id, intern_hiring_status_id, internship_workload, workload, extension) VALUES (4, 6, 3, 4, 5, '2021-10-21');
 INSERT INTO intern_staffing(id, employee_id, intern_hiring_status_id, internship_workload, workload, extension) VALUES (5, 2, 1, 8, 2, '2021-10-21');

 INSERT INTO intern_project_history(id, intern_staffing_id, project_id, responsible_person_id, project_hiring_status_id, comments) VALUES (1, 2, 2, 1, 1, 'Comments 1');
 INSERT INTO intern_project_history(id, intern_staffing_id, project_id, responsible_person_id, project_hiring_status_id, comments) VALUES (2, 1, 1, 4, 1, 'Comments 2');
 INSERT INTO intern_project_history(id, intern_staffing_id, project_id, responsible_person_id, project_hiring_status_id, comments) VALUES (3, 5, 4, 7, 3, 'Comments 3');
 INSERT INTO intern_project_history(id, intern_staffing_id, project_id, responsible_person_id, project_hiring_status_id, comments) VALUES (4, 3, 4, 3, 1, 'Comments 4');
 INSERT INTO intern_project_history(id, intern_staffing_id, project_id, responsible_person_id, project_hiring_status_id, comments) VALUES (5, 4, 1, 5, 2, 'Comments 5');