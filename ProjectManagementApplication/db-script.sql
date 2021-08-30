DROP DATABASE IF EXISTS project_managementdb;
CREATE DATABASE project_managementdb;
use  project_managementdb;

CREATE TABLE Users (
	id INT NOT NULL AUTO_INCREMENT,
    username VARCHAR(256) NOT NULL,
    `password` VARCHAR(256) NOT NULL,
    first_name VARCHAR(256) NOT NULL,
    last_name VARCHAR(256) NOT NULL,
    creation_date DATETIME NOT NULL DEFAULT NOW(),
    creator_id INT NOT NULL DEFAULT 0,
    updated_date DATETIME NOT NULL DEFAULT NOW(),
    updated_by INT NOT NULL,
    `role` boolean NOT NULL,
    CONSTRAINT pk_users_id PRIMARY KEY(id),
    CONSTRAINT ux_users_username UNIQUE(username)
);

CREATE TABLE Teams (
	id INT NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(256) NOT NULL,
    creation_date DATETIME NOT NULL DEFAULT NOW(),
    updated_date DATETIME NOT NULL DEFAULT NOW(),
    updated_by INT NOT NULL,
    user_id INT NOT NULL,
    CONSTRAINT pk_teams_id PRIMARY KEY(id),
    CONSTRAINT ux_teams_name UNIQUE(`name`),
    CONSTRAINT fk_teams_user_id FOREIGN KEY(user_id) REFERENCES Users(id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE Users_Teams (
	user_id INT NOT NULL,
    team_id INT NOT NULL,
    CONSTRAINT fk_users_teams_id FOREIGN KEY(user_id) REFERENCES Users(id) ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT fk_teams_users_id FOREIGN KEY(team_id) REFERENCES Teams(id) ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT ux_user_team UNIQUE (user_id, team_id)
);

CREATE TABLE Projects (
	id INT NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(256) NOT NULL,
    creation_date DATETIME NOT NULL DEFAULT NOW(),
    updated_date DATETIME NOT NULL DEFAULT NOW(),
    updated_by INT NOT NULL,
    user_id INT NOT NULL,
    CONSTRAINT pk_projects_id PRIMARY KEY(id),
    CONSTRAINT fk_projects_user_id FOREIGN KEY(user_id) REFERENCES Users(id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE Teams_Projects (
	team_id INT NOT NULL,
    project_id INT NOT NULL,
    CONSTRAINT fk_teams_projects_id FOREIGN KEY(team_id) REFERENCES Teams(id) ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT fk_projects_teams_id FOREIGN KEY(project_id) REFERENCES Projects(id) ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT ux_team_project UNIQUE(team_id,project_id)
);


CREATE TABLE Tasks (
	id INT NOT NULL AUTO_INCREMENT,
    title VARCHAR(256) NOT NULL,
    `description` VARCHAR(256) NOT NULL,
    creation_date DATETIME NOT NULL DEFAULT NOW(),
    updated_date DATETIME NOT NULL DEFAULT NOW(),
    updated_by INT NOT NULL,
    `status` VARCHAR(30) NOT NULL,
    user_id INT NOT NULL,
    project_id INT NOT NULL,
    CONSTRAINT pk_tasks_id PRIMARY KEY(id),
    CONSTRAINT fk_tasks_project_id FOREIGN KEY(project_id) REFERENCES Projects(id) ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT fk_tasks_user_id FOREIGN KEY (user_id) REFERENCES Users(id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE Users_Tasks (
	user_id INT NOT NULL,
    task_id INT NOT NULL,
    CONSTRAINT fk_users_tasks_id FOREIGN KEY(user_id) REFERENCES USERS(id) ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT fk_tasks_users_id FOREIGN KEY(task_id) REFERENCES TASKS(id) ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT ux_user_task UNIQUE (user_id, task_id)
);

CREATE TABLE Worklogs(
	id INT NOT NULL AUTO_INCREMENT,
    title VARCHAR(256) NOT NULL,
    `description` VARCHAR(256) NOT NULL,
    creation_date DATETIME NOT NULL DEFAULT NOW(),
    updated_date DATETIME NOT NULL DEFAULT NOW(),
    updated_by INT NOT NULL,
    task_id INT NOT NULL,
    user_id INT NOT NULL,
    CONSTRAINT pk_worklogs_id PRIMARY KEY(id),
    CONSTRAINT fk_worklogs_task_id FOREIGN KEY(task_id) REFERENCES Tasks(id) ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT fk_worklogs_user_id FOREIGN KEY(user_id) REFERENCES Users(id) ON UPDATE CASCADE ON DELETE CASCADE
);

INSERT INTO Users (username,`password`,first_name,last_name,creator_id,updated_by,`role`)
VALUES("admin","$2a$10$BePSayuQm3AS7NebF7npN.WMl1jeN6xWKh9Nph/Qe3cjFcZpGb/uq","Ivan","Ivanov",0,0,true);
