
### Project Management Application

Rest API for Project Management Application.

## Features

- Login
- User Management
- Team Management
- Project Management
- Task Management
- Worklog Management

## Used Tools

- Java 11
- Spring Boot 2.5.1
- Spring Boot Web
- Spring Boot Test
- Spring Security
- Maven
- MySQL
- JUnit 4
- Lombok
- JWT

## Instalation

1. Download the code from the repository.
2. Install MySQL and MySQL workbench.
3. You have to execute <b>db-script.sql</b> in MySQL Workbench
4. You have to change the username and password in application.yaml
5. Open the project in InteliJ and run <b>ProjectManagementApplication.java</b>

## Rest API Documentation

### Authentication

Method | URL | Description | Request | Response |
--- | --- | --- | --- | ---
POST | /login | Login | Provide JSON with username and password | Token

### User Management

Method | URL | Description | Request | Response |
--- | --- | --- | --- | ---
GET | /users | List all users |  | JSON
GET | /users/{userId} | List user | Path variable userId  | JSON
POST | /users | Save user | Provide JSON | JSON
PUT | /users/{userId} | Update user | Provide Path variable userId and JSON | JSON
DELETE | /users/{userId} | Delete user | Provide Path variable userId |

### Team Management

Method | URL | Description | Request | Response |
--- | --- | --- | --- | ---
GET | /teams | List all teams |  | JSON
GET | /teams/{teamId} | List team | Path variable teamId  | JSON
POST | /teams | Save team | Provide JSON | JSON
PUT | /teams/{teamId} | Update team | Provide Path variable teamId and JSON | JSON
DELETE | /teams/{teamId} | Delete team | Provide Path variable teamId |
POST | /teams/assign | Assign user to team | Provide RequestParams userId and teamId |
DELETE | /teams/unassign | Unassign user from team | Provide Path variable userId and teamId |

### Project Management

Method | URL | Description | Request | Response |
--- | --- | --- | --- | ---
GET | /projects/all/{userId} | List all projects by userId |  | JSON
GET | /projects/{projectId} | List project | Path variable projectId  | JSON
GET | /projects/assigned | List assigned project | Provide Path Variable userId | JSON
POST | /projects | Save project | Provide JSON | JSON
PUT | /projects/{projectId} | Update project | Provide Path variable projectId and JSON | JSON
DELETE | /projects/{projectId} | Delete project | Provide Path variable projectId |
POST | /projects/assign | Assign team to project | Provide RequestParams teamId and projectId |
DELETE | /projects/unassign | Unassign team from project | Provide Path variable teamId and projectId |

### Task Management

Method | URL | Description | Request | Response |
--- | --- | --- | --- | ---
GET | /tasks/all/{projectId} | List all tasks by projectId |  | JSON
GET | /tasks/{taskId} | List task | Path variable taskId  | JSON
GET | /tasks/assigned | List assigned project | Provide Path Variable userId and projectId | JSON
POST | /tasks | Save task | Provide JSON | JSON
PUT | /tasks/{taskId} | Update task | Provide Path variable taskId and JSON | JSON
DELETE | /tasks/{taskId} | Delete task | Provide Path variable taskId |
POST | /tasks/assign | Assign user to task | Provide RequestParams userId and taskId |
DELETE | /tasks/unassign | Unassign user from task | Provide Path variable userId and taskId |

#### Worklog Management

Method | URL | Description | Request | Response |
--- | --- | --- | --- | ---
GET | /worklogs | List all worklogs |  | JSON
GET | /worklogs/{worklogId} | List worklog | Path variable worklogId  | JSON
POST | /worklogs | Save worklog | Provide JSON | JSON
PUT | /worklogs/{worklogId} | Update worklog | Provide Path variable worklogId and JSON | JSON
DELETE | /worklogs/{worklogId} | Delete worklog | Provide Path variable worklogId |

## Requests

### Login

This request will return token and you will have to copy the token and add it in Bearer Auth in Postman.
> {  
> &nbsp; &nbsp; &nbsp; "username" : "admin",  
> &nbsp; &nbsp; &nbsp; "password" : "adminpass"  
> }

### Create User

> {  
>&nbsp; &nbsp; &nbsp; "username" : "user1",   
> &nbsp; &nbsp; &nbsp; "password" : "adminpass",    
> &nbsp; &nbsp; &nbsp; "firstName" : "John",  
> &nbsp; &nbsp; &nbsp; "lastName" : "Doe",  
> &nbsp; &nbsp; &nbsp; "isAdmin" : false,  
> &nbsp; &nbsp; &nbsp; "editedBy" : 1,  
> &nbsp; &nbsp; &nbsp; "creatorId" : 1  
> }

### Update User
Provide Path Variable   2
> {  
>&nbsp; &nbsp; &nbsp; "username" : "user1",   
> &nbsp; &nbsp; &nbsp; "password" : "adminpass",    
> &nbsp; &nbsp; &nbsp; "firstName" : "John",  
> &nbsp; &nbsp; &nbsp; "lastName" : "Doe",  
> &nbsp; &nbsp; &nbsp; "isAdmin" : false,  
> &nbsp; &nbsp; &nbsp; "editedBy" : 1,  
> &nbsp; &nbsp; &nbsp; "creatorId" : 1  
> }

### Create Team
> {   
> &nbsp; &nbsp; &nbsp; "name" : "Testers",  
> &nbsp; &nbsp; &nbsp; "editedBy" : 1,  
> &nbsp; &nbsp; &nbsp; "creatorId" : 1  
> }

### Update Team
Provide Path Variable 1
> {   
> &nbsp; &nbsp; &nbsp; "name" : "Development",  
> &nbsp; &nbsp; &nbsp; "editedBy" : 1,  
> &nbsp; &nbsp; &nbsp; "creatorId" : 1  
> }


### Create Project
> {   
> &nbsp; &nbsp; &nbsp; "name" : "Project Management Application",  
> &nbsp; &nbsp; &nbsp; "editedBy" : 1,  
> &nbsp; &nbsp; &nbsp; "creatorId" : 1  
> }

### Update Project
Provide Path Variable 1
> {   
> &nbsp; &nbsp; &nbsp; "name" : "Project Management Application",  
> &nbsp; &nbsp; &nbsp; "editedBy" : 1,  
> &nbsp; &nbsp; &nbsp; "creatorId" : 1  
> }

### Create task
> {   
> &nbsp; &nbsp; &nbsp; "title" : "test repository",  
> &nbsp; &nbsp; &nbsp; "description" : "write unit tests",  
> &nbsp; &nbsp; &nbsp; "status" : "NOT COMPLETED",  
> &nbsp; &nbsp; &nbsp; "editedBy" : 1,  
> &nbsp; &nbsp; &nbsp; "creatorId" : 1,  
> &nbsp; &nbsp; &nbsp; "projectId" : 1  
> }

### Update task
Provide Path Variable 1
> {   
> &nbsp; &nbsp; &nbsp; "title" : "test repository",  
> &nbsp; &nbsp; &nbsp; "description" : "write unit tests",  
> &nbsp; &nbsp; &nbsp; "status" : "COMPLETED",  
> &nbsp; &nbsp; &nbsp; "editedBy" : 1,  
> &nbsp; &nbsp; &nbsp; "creatorId" : 1,  
> &nbsp; &nbsp; &nbsp; "projectId" : 1  
> }

### Create worklog
> {   
> &nbsp; &nbsp; &nbsp; "title" : "test users",  
> &nbsp; &nbsp; &nbsp; "description" : "read,save,update,delete",   
> &nbsp; &nbsp; &nbsp; "editedBy" : 1,  
> &nbsp; &nbsp; &nbsp; "creatorId" : 1,  
> &nbsp; &nbsp; &nbsp; "taskId" : 1  
> }

### Update worklog
Provide Path Variable 1
> {   
> &nbsp; &nbsp; &nbsp; "title" : "test users",  
> &nbsp; &nbsp; &nbsp; "description" : "only save",   
> &nbsp; &nbsp; &nbsp; "editedBy" : 1,  
> &nbsp; &nbsp; &nbsp; "creatorId" : 1,  
> &nbsp; &nbsp; &nbsp; "taskId" : 1  
> }


#### Developed by Evgeni Kolev
