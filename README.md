# SurveyFeedback - A Survey Management

SurveyFeedback is a survey management system. A simple survey management console based system that handles both Admin and User side responsibilites. Coordinate users can Create, Edit, Open/Close surveys.Whereas, Respondent users can take surveys and view survey reports. The application is developed as a group project for the course SWE 4304 - Software Project Lab at Islamic University of Technology.

## Project Layout

Our project adheres to the following directory layout:

```
├─── coordinatorUsers.txt
├─── respondentUsers.txt
├─┬─ Surveys/
│ └─── All_Surveys.txt              (Stores all created Surveys)
├─┬─ SurveyResponses/               (Stores all Survey Responses)
│ ├─── Survey_ID_(SurveyNo)_Responses.txt
│ └─── So on ....
├─┬─ SurveyReports/                 (Stores all Survey Reports)
│ ├─── Survey_ID_(SurveyNo)_Report.txt
│ └─── So on ....
├─┬─ src/
│ ├─┬─ AuthenticationHelper/
│ │ ├─── UserAuthentication.java
│ │ └─── PasswordHashManager.java
│ ├─┬─Survey/
│ │ ├─── Survey.java
│ │ └─── Question.java
│ ├─┬─User/
│ │ ├─── User.java
│ │ ├─── SurveyCoordinator.java
│ │ └─── SurveyRespondent.java
│ ├─┬─TextFileHandler/
│ │ ├─── SurveyTextFileHandler.java
│ │ ├─── UserTextFileHandler.java
│ │ └─── SurveyReponseTextFileHander.java
│ └─┬─ Report/
│   └─── SurevyReportGenerator.java
```

### Features

Our application handles both Admin and User side responsibilites. For the admin panel, the following features are implemented:

- Create default template surveys
- Create custom surveys
- Edit surveys
- Delete surveys
- Open/Close surveys
- View Survey Responses
- Generate Survey Reports
- View Survey Reports
- View Survey Statistics

For the user panel, the following features are implemented:

- View open surveys
- Take surveys
- View Survey Reports
- View Survey Statistics

### How to run the application

```doc
# Clone the repository
# Open the project in Visual Studio Code
# Run the main method in the Main.java file
# The application will run in the console
# Follow the instructions in the console to navigate through the application
# The application will create the required files and folders in the project directory
# ENJOY!
```



### Contributors

- [Hasin Mahtab](github.com/hasin023) <a href="https://l.messenger.com/l.php?u=https%3A%2F%2Fwww.linkedin.com%2Fin%2Fhasin-mahtab-4b9640217%2F&h=AT0_BXRXckXdUsKDv6VfBn5dxXXDQluPGZ96aioD_A6HWgUAHcvXWWmb-iV7C_P9huZx4URbwGRk064bkoExCG6x3BIPVaEsxvPds-_81SXBtBpTdbJMKMrpcJplpgj4p05fIA" target="_blank"><img src="https://content.linkedin.com/content/dam/me/business/en-us/amp/brand-site/v2/bg/LI-Bug.svg.original.svg" alt="LinkedIn" width="20" height="15"></a>

- [Mahmudul Islam Mahin](github.com/MI-Mahin) <a href="www.linkedin.com/in/mahmudul-islam-mahin" target="_blank"><img src="https://content.linkedin.com/content/dam/me/business/en-us/amp/brand-site/v2/bg/LI-Bug.svg.original.svg" alt="LinkedIn" width="20" height="15"></a>
- [Mohammad Nahiyan kabir](github.com/Nahiyan3)
