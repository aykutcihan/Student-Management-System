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

