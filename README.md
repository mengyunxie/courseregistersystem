# Course Registration System



### Summary

The course registration system provides users with a service for course registration, including enrolling in courses, dropping courses, creating courses, and processing student requests to register for courses. More information can be found in the `doc` directory.



### Install

This system uses the SQLite library to build the database function of the system. The SQLite library is in the `lib` directory, its relative path is 

```
INFO5100/lib/sqlite-jdbc-3.39.3.0.jar
```



You can simply build this project using Eclipse with the help of `.classpath` and `.project`.

If you use other IDE, you can import this SQLite library and then build this project.



### The User Account

Here is some user's account in the database, you can use them to login this system.

Or, you can create yours new account by the register page.

| Username | Password | Role       |
| -------- | -------- | ---------- |
| teacher1 | 1234     | Instructor |
| teacher2 | 1234     | Instructor |
| student1 | 1234     | Student    |
| student2 | 1234     | Student    |

And the database file is in the `db` directory.
