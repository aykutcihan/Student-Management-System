# backend-project

**Author:**  
_Mehmet Akgul_   
_mehmetakgul.dev@gmail.com_
---
## Contact Message Process
### Using the Communication Message Service,
anyone can send messages to the system and admin also can list all message by email or subject.  
A. on your local machine, 
* Using the  [localhost:8080/contactMessages/save](localhost:8080/contactMessages/save) , you can send messages without register 
* Using the  [localhost:8080/contactMessages/getAll?page=0&size=10&sort="date"&type="desc"](localhost:8080/contactMessages/getAll?page=0&size=10&sort="date"&type="desc") , If you are an admin, you can see all messages.
* Using the  [localhost:8080/contactMessages/searchByEmail?email=test@test.com&page=0&size=10&sort="date"&type="desc"](localhost:8080/contactMessages/searchByEmail?email=test@test.com&page=0&size=10&sort="date"&type="desc") , If you are an admin, you can see the messages by email you want.
* Using the  [localhost:8080/contactMessages/searchBySubject?subject=test@test.com&page=0&size=10&sort="date"&type="desc"](localhost:8080/contactMessages/searchBySubject?subject=test@test.com&page=0&size=10&sort="date"&type="desc") , If you are an admin, you can see the messages by subject you want.

---
## Term and Final Grades Process 
### Using the Communication Message Service,
Necessary codings were made for calculating the fall and spring semesters and the grade point average of that semester.  
* Students must choose a separate course for each semester.
* At least 3 compulsory courses must be selected.
* The total credit score of the selected courses must be more than 30 points for one semester.

When calculating the end-of-term average, 60% of the compulsory courses are multiplied by their own credit score, and 40% of the elective courses are multiplied by their own credit score and divided by the total credit score.
* Using the  [localhost:8080/contactMessages/save](localhost:8080/contactMessages/save) , you can send messages without register 
* Using the  [localhost:8080/contactMessages/getAll?page=0&size=10&sort="date"&type="desc"](localhost:8080/contactMessages/getAll?page=0&size=10&sort="date"&type="desc") , If you are an admin, you can see all messages.
* Using the  [localhost:8080/contactMessages/searchByEmail?email=test@test.com&page=0&size=10&sort="date"&type="desc"](localhost:8080/contactMessages/searchByEmail?email=test@test.com&page=0&size=10&sort="date"&type="desc") , If you are an admin, you can see the messages by email you want.
* Using the  [localhost:8080/contactMessages/searchBySubject?subject=test@test.com&page=0&size=10&sort="date"&type="desc"](localhost:8080/contactMessages/searchBySubject?subject=test@test.com&page=0&size=10&sort="date"&type="desc") , If you are an admin, you can see the messages by subject you want.

