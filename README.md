# Habit Tracker mini-project
Java, OOP, and Spring Boot practice.

## Tools and Technologies
- **IntelliJ IDEA**: Integrated Development Environment (IDE) specifically for Java.
- **Maven**: Build and dependency management.
- **Git**: Version control system.
- **Java**: The programming language used for developing the application.
- **Spring Boot**: Framework used for building Java-based web applications.
- **RESTful APIs**: Design architecture for API endpoints.
- **JWT (JSON Web Tokens)**: Used for authentication and security.
- **Spring Data JPA**: Library for object-relational mapping and data access.
- **H2**: In-memory relational database management system.
- **Postman**: Tool used for testing API endpoints.
- **Lucid**: Tool used for creating various types of diagrams, such as UML and flowcharts.

## Approach

## Roadclocks

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
    - The application should display the User’s profile, including email.

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

## ERD

## Planning

## Installation Instructions

## Acknowledgments
This project was developed as part of the Software Engineering Immersive program by General Assembly. It serves as a practice project to apply and reinforce skills in Java, Object-Oriented Programming, and Spring Boot.
