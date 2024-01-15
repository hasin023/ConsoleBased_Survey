# SnapFeedback - A Survey Management

SnapFeedback is a survey management system. A simple survey management console based system that handles both Admin and User side responsibilites. Coordinate users can Create, Edit, Open/Close surveys.Whereas, Respondent users can take surveys and view survey reports. The application is developed as a group project for the course SWE 4304 - Software Project Lab at Islamic University of Technology.

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
# Open the project in IntelliJ
# Run the main method in the Main.java file
# The application will run in the console
# Follow the instructions in the console to navigate through the application
# The application will create the required files and folders in the project directory
# ENJOY!
```

### User Credentials

```doc
# Coordinator Users
# Username: admin
# Password: admin

# Respondent Users
# Username: user
# Password: user
```

### Contributors

- [Hasin Mahtab](github.com/hasin023)
- [Mahmudul Islam Mahin](github.com/MI-Mahin)
- [Mohammad Nahiyan kabir](github.com/Nahiyan3)
