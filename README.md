# Habit Tracker
Is a Java-based web application designed to help users manage and track their daily habits. The application provides a secure platform where users can create and monitor various habits, also categorize them for better focus and analysis.

![Status](https://img.shields.io/badge/Status-Under%20Development-yellow)

## Approach
**Initial Planning**: Created a Trello board for project management and drafted an Entity Relation Diagram and User Stories.  
**Task Breakdown**: Turned User Stories into Trello cards and outlined the required API endpoints.  
**Repo Setup and Development**: Created a GitHub repository, cloned it locally, established MVC architecture, and implemented JWT-based authentication. Then continued implementing features and testing on-the-go, ensuring a functional application.

## Tools and Technologies
- **IntelliJ IDEA**: IDE for Java
- **Maven**: Build and dependency management
- **Git**: Version control
- **Java**: Programming language
- **Spring Boot**: Java web framework
- **Tomcat Server**: Web server
- **MVC Architecture**: Design pattern
- **Spring Security**: Authentication and authorization
- **RESTful APIs**: API design
- **JWT (JSON Web Tokens)**: Secure authentication
- **Spring Data JPA**: Database operations
- **H2 Database**: In-memory DBMS
- **Postman**: API testing
- **Lucid**: ERD creation
- **Trello**: Project Management

## User Stories
<details>
  <summary>User</summary>

1. **As a new user, I want to be able to register** so that I can access personalized features and settings in the application.
   - The user must provide a unique email address that is not already registered in the system.
     * :warning: The application should show an error message if an email is already registered.
   - The user must have a password.

2. **As a registered user, I want to log in,** so that I can access and manage my habit tracker.
    - The user must be previously registered.
    - The user must provide the correct pair of unique email and password.
      * :warning: The application should show an error message if the email is not found in the system.
      * :warning: The application should show an error message if the authentication fails.

3. **As a logged-in user, I want to personalize my profile** with my first and last name, and a bio.
    - The user must be able to input their first name.
    - The user must be able to input their last name.
    - The user must be able to input their bio.
    - The application should check if the information has changed, then update it.
      - :warning: The application should show an error if nothing has changed.

4. **As a logged-in user, I want to be able to see my profile,** so I can check my personal information.
    - The application should display the User’s profile.
</details>

<details>
  <summary>Category</summary>

1. **As a logged-in user, I want to create categories,** to separate my habits into.
    - The user must input a name and description.
        * :warning: The name must be unique.
        * :warning: The application should show an error if the name already exists.
    - The category must be related only to its creator user.

2. **As a logged-in user, I want to see all the categories I’ve created** for my habits.
    - The application should list all the categories related to this user.
    - :warning: The application should show an error message if no categories were found.

3. **As a logged-in user, I want to search a category by its name,** so I can see the habits associated with it.
    - The application should show the category related to this user.
        * :warning: The application should show an error message if no categories were found.
    - The application should list the habits associated with the category.

4. **As a logged-in user, I want to update category details** if needed.
    - The input must be different from the original name or description.
        * :warning: The application should show an error if there’s no change.
    - The category must previously exist.
        * :warning: The application should show an error if it doesn’t.

5. **As a logged-in user, I want to delete a category,** so I can have a cleaner application.
    - The category must previously exist.
        * :warning: The application should show an error if it doesn’t.
    - The habits associated with it are also deleted.
</details>

<details>
  <summary>Habit</summary>

1. **As a logged-in user, I want to create a habit and associate it with a category,** so I can keep things organized.
    - The user must input a unique name, routine, and category.
        * :warning: The application should show an error if Habit’s name is not unique.
        * :warning: The application should show an error if the category is not found.
    - The user can input a trigger and outcome.
    - The habit should be associated with its creator user.

2. **As a logged-in user, I want to search a habit for its name,** so I can see the days I’ve completed it.
    - The habit must exist.
        * :warning: The application should show an error if the habit is not found.
    - The application should show the habit and the habit track related to the habit or an empty list.

3. **As a logged-in user, I want to update the habit’s details** if needed.
    - The habit must exist.
        * :warning: The application should show an error if the habit is not found.
    - The application should show the habit and the habit track related to the habit or an empty list.

4. **As a logged-in user, I want to delete a habit** if needed.
    - The habit must exist.
        * :warning: The application should show an error if the habit is not found.
    - The habit tracks associated with it are also deleted.
</details>

<details>
  <summary>Practice</summary>

1. **As a logged-in user, I want to register when I practice a habit.**
    - The user must input an existing habit.
        * :warning: The application should show an error if the habit is not found.
    - The user must input a date.
        * :warning: The date must not be after the registration date.
    - The user must input if the practice was completed or not.

2. **As a logged-in user, I want to see the habits I practiced or not on a given date.**
    - The application should list the habit tracks that match the date.
    - The date should be equal to the search’s day or earlier.
        * :warning: The application should show an error if the date is invalid.

3. **As a logged-in user, I want to edit the details of a given habit track.**
    - The user should be able to input done, date, and/or habit_id.
        * :warning: The application should show an error if the new information is not different than the original.
    - The date should be equal to the update’s day or before.
        * :warning: The application should show an error if the date is invalid.
    - The habit track must be previously created.
        * :warning: The application should show an error if not.

4. **As a logged-in user, I want to delete a specific habit practice.**
    - The habit track must be previously created.
        * :warning: The application should show an error if the practice is not found.
    - The application should delete the habit practice record.
</details>

## Entity-Relation Diagram (ERD)
The ERD illustrates the relationship between different entities such as `User`, `Profile`, `Habit`, `Practice`, and `Category`.

![ERD](https://github.com/GabrielleYnara/habit-tracker/blob/main/assets/Habit%20Tracker%20ERD.png)

- **User**: Manages user credentials.
- **Profile**: Manages user information.
- **Habit**: Represents individual habits that users want to track.
- **Practice**: Represents the habit tracking.
- **Category**: Allows users to categorize their habits for better management.
- 
## API Endpoints

| HTTP Methods | Full URL                  | Endpoint URL          | Functionality                               | Implementation Status |
|--------------|---------------------------|-----------------------|---------------------------------------------|-----------------------|
| POST         | `/api/users/register/`    | `/users/register/`    | Register a new user                         | Implemented           |
| POST         | `/api/users/login/`       | `/users/login/`       | Login a registered user                     | Implemented           |
| GET          | `/api/users/profile/`     | `/users/profile/`     | Get the logged-in user's profile            | Implemented           |
| PUT          | `/api/users/profile/`     | `/users/profile/`     | Update the logged-in user's profile         | Implemented           |
| GET          | `/api/categories/`        | `/categories/`        | Get all categories for a logged-in user     | Implemented           |
| POST         | `/api/categories/`        | `/categories/`        | Create a new category for a logged-in user  | Implemented           |
| GET          | `/api/categories/{name}/` | `/categories/{name}/` | Get a category by its name                  | Coming Soon           |
| PUT          | `/api/categories/{id}/`   | `/categories/{id}/`   | Update a category by its ID                 | Coming Soon           |
| DELETE       | `/api/categories/{id}/`   | `/categories/{id}/`   | Delete a category by its ID                 | Coming Soon           |
| GET          | `/api/habits/`            | `/habits/`            | Get all habits for a logged-in user         | Coming Soon           |
| POST         | `/api/habits/`            | `/habits/`            | Create a new habit for a logged-in user     | Coming Soon           |
| GET          | `/api/habits/{name}/`     | `/habits/{name}/`     | Get a habit by its name                     | Coming Soon           |
| PUT          | `/api/habits/{id}/`       | `/habits/{id}/`       | Update a habit by its ID                    | Coming Soon           |
| DELETE       | `/api/habits/{id}/`       | `/habits/{id}/`       | Delete a habit by its ID                    | Coming Soon           |
| POST         | `/api/practices/`         | `/practices/`         | Register a new practice for a habit         | Coming Soon           |
| GET          | `/api/practices/{date}/`  | `/practices/{date}/`  | Get all practices for a given date          | Coming Soon           |
| PUT          | `/api/practices/{id}/`    | `/practices/{id}/`    | Update a practice by its ID                 | Coming Soon           |
| DELETE       | `/api/practices/{id}/`    | `/practices/{id}/`    | Delete a practice by its ID                 | Coming Soon           |


## Installation Instructions
Coming soon.

## Roadblocks

- **ERD Confusion**: Initially, I was unsure about what to include in the Entity-Relation Diagram, which led to delays in the planning phase.
- **User Stories Overload**: I found myself spending an excessive amount of time crafting user stories, trying to cover all possible scenarios and features.
- **Project Management Tool Crash**: The project management tool I initially chose crashed unexpectedly, forcing me to redo the planning steps. I switched to Trello for a more reliable experience.

## Acknowledgments
This project was developed as part of the Software Engineering Immersive program by General Assembly. 
It serves as a practice project to apply and reinforce skills in Java, Object-Oriented Programming, and Spring Boot.
