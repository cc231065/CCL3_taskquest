# TaskQuest: Gamified To-Do List App
📋 Overview
Creative Code Lab of Max Barry (cc231065) and Boris Kodzhabashev (cc231069).

TaskQuest is a mobile application built with Kotlin, designed to make task management more engaging through gamification. Users can create and categorize tasks (daily, weekly, or monthly) while earning XP and leveling up as they complete them. TaskQuest helps users stay productive while providing a fun sense of achievement.


🚀 Getting Started

    1. Download the apk file (from the releases section on the right side)

    2. Run it on an android device

🎯 Features

    Task Management:
    Create tasks with specific frequencies: daily, weekly, or monthly.
    Organize tasks efficiently to track progress.

    Gamification:
        Earn XP by completing tasks.
        Level up your profile as you achieve more.

    Profile Page:
    View your current level, XP progress, and completed tasks summary.

🛠️ Tech Stack

    Language: Kotlin
    IDE: Android Studio
    Architecture: MVVM (Model-View-ViewModel)
    Database: Room (local database for storing tasks and progress)
    UI Design: XML layouts with Material Design components

Use Case

    Problem:
    Managing tasks efficiently can be overwhelming, especially when juggling multiple responsibilities like work, personal errands, or long-term goals. Traditional to-do list apps often feel dull and lack the motivation to keep users engaged.

    Solution:
    askQuest provides a gamified to-do list experience, turning task management into a rewarding and fun process. Users can create tasks with different frequencies (daily, weekly, or monthly), earn XP as they complete them, and level up their profiles to track their progress. This keeps users motivated to stay productive  and organized.
    Target User

Target User:

    Students:
        Need to manage assignments, study schedules, and personal errands while staying motivated.
    Young Professionals:
        Balance work tasks, personal goals, and household responsibilities effectively.
    Gamification Enthusiasts:
        Enjoy RPG-style systems that reward effort and provide a sense of progression.

🧪 How It Works

    Tasks:
        Navigate to the "Tasks" screen to add tasks.
        Choose task frequency (Daily, Weekly, or Monthly).
        Mark tasks as completed to earn XP.

    Profile:
        View your current level and total XP on the "Profile" screen.
        Track your progress and achievements as you level up.

Database Structure:

This application uses a Room Database to store and manage task-related data locally. The database is structured as follows:

    1. Task.kt

    This is the data model class representing a task.
    It contains fields such as id, title, description, category, xpValue, and isCompleted.
    The @Entity annotation indicates that this class is an entity that will be mapped to a table in the Room database.

    2. TaskDao.kt

    This is the Data Access Object (DAO) for Task entities.
    It defines methods for performing operations on the task_table, such as inserting, updating, deleting, and querying tasks.
    Common functions include:
        insertTask(): Adds a task to the database.
        updateTask(): Updates an existing task in the database.
        deleteTask(): Deletes a task from the database.
        getAllTasks(): Retrieves all tasks from the database.
        getTasksByCategory(): Retrieves tasks filtered by category.

    3. TaskDatabase.kt

    This is the abstract class that extends RoomDatabase.
    It defines the database and includes abstract methods for accessing DAOs.
    It serves as the main access point for the Room database, providing the TaskDao.

    4. TaskDatabaseInstance.kt

    This is a singleton class that ensures only one instance of the database is created.
    It uses Room’s Room.databaseBuilder() to create and manage the database instance.

    5. TaskViewModel.kt

    This ViewModel acts as the intermediary between the UI and the database.
    It uses the TaskDao to interact with the database and manages the UI-related data in a lifecycle-conscious way.
    It contains methods for inserting, updating, and deleting tasks, as well as fetching tasks by category or task ID.
   
Usability Testing Results

A usability testing session was conducted with 6 participants to assess the overall user experience of the app. The results from this testing help identify both strengths and areas for improvement.
Overall SEQ Score (all tasks)

    Average SEQ Score: 6.72

    Task-Specific Scores

    Task 1: Create a Weekly Task
        Average score: 6.7
    Task 2: Create a Onetime Task
        Average score: 6.83
    Task 3: Complete a Task
        Average score: 6.5
    Task 4: View the Details Page of a Task
        Average score: 6.7
    Task 5: View Your Profile
        Average score: 7
    Task 6: Delete a Task
        Average score: 6.7

    Positive Feedback

    Clear Navigation: Participants found it easy to navigate through the app and access different features without confusion.
    Clear Visibility of Items: The items and buttons within the app were easily visible, making it simple for users to interact with the app.
    Purpose of Functions Was Clear: The app's functions were intuitive, and users understood the purpose of each feature.
    Minimalistic Design: Some participants appreciated the clean and simple design, which contributed to a distraction-free experience.
    Task Completion: The majority of tasks were completed without any errors, suggesting the app is generally easy to use.

    Areas for Improvement

    Complete Button UI Update: The "Complete" button should trigger an instant update of the UI. Some users were confused by the need for a separate reset button.
    Profile Experience: The profile page should display the amount of XP needed for the next level to provide a clearer progression path.
    Task Color Usage: Some users found the colors of the tasks unclear and questioned their purpose, suggesting a need for more meaningful color choices.
    Design Simplicity: While many users liked the minimalistic design, a few felt that the app appeared too simple and could benefit from a more engaging interface.
    Personalization of Profile: The profile felt impersonal for some participants, and adding customizable elements or more detailed user info could improve the user experience.

These results highlight that while the app is generally intuitive and easy to use, there are a few areas that could benefit from design refinements to enhance user satisfaction and engagement.


📜 License

This project is licensed under the MIT License.
