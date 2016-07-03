# air_ticket_reservation_system
SpringBoot AngularJS SpringSecurity

Project Air Ticket Reservation System

Content of the zip
- air_ticket_reservation_system eclipse project(available in github https://github.com/LionH/air_ticket_reservation_system)
- README (this file)
- Air Ticket Reservation System.docx (Analysis and Technical Documentation)

###########################################################################################################################
What has been done:
###########################################################################################################################
- Server side : Spring boot and H2 Database with JPA
- Unit Testing : JUnit covering 100% of the existing services
- Cloud : taken an account in Heroku, it is possible to deploy without problem as the uber jar is standalone (tomcat embedded)
- SPA : angularJS
- Roles : User and Admin with inMemory management (Spring Security) 
- Login : AngularJS integration with basic authentication (popup)
- PDF : generation of PDF with Apache PDFBox (the sample is only an empty document)
- Team Repo : created a project with git and deployed it in github https://github.com/LionH/air_ticket_reservation_system
- Dependencies Management : Maven for both server side and client side (with wro4j and webjars for javascript)
- Are able to search multiple flights based on criteria
- System core is ready to serve whatever frontend technology (inclusive mobile) due to his extent use of rest api

###########################################################################################################################
and what not
###########################################################################################################################
- Continuous Integration: Jenkins/Hudson integration
- Unit Testing: Jacoco coverage integration and Mockito integration
- Roles: no time to manage client side
- Email: no time to setup it, but can be done easily in java
- Login: no time to setup a Google+/Facebook login api but can be done easily
- Payment gateway: no time to setup but there's a lot of available gateway out there with their apis (need to install a valid certificate from a valid Trusted Root Certification autority like Verisign to use https)
- Cancel booking (not hard)
- Receive notification ==> Batch service (would use Spring Batch or @Scheduled annotation for that)

###########################################################################################################################
How to use it
###########################################################################################################################

1. "mvn install" on the root directory or maven install in IDE (The unit test classes will be executed)
2. run the main class org/tesolin/crossover/AirTicketReservationSystemApplication.java or Run Spring Boot App (eclipse)
3. open http://localhost:8081 the port has been set in application.properties (if port is not available, feel free to choose another)
4. The db is not much populated, so please select Airport origin and Airport destination (no validation made if airport1==airport2), and please select (0<today<+10) date (validation of roundtrip date > oneway date) and click search
5. select in the two datagrid a row ==> proceed button appear, please click
6. normally a login popup appear, telling you are not authorized, please put "user" and "password" and get in
7. click proceed one more time
8. a popup should appear with a preview of your Tickets (no time to make a preview)
9. click Print button to get an empty pdf

Key points

Create solution/projects with 3rd party references/packages integrated 										  --> done
Create layers, modules, technical / cross cutting concern classes       									  --> done
All integrations with 3rd party libraries/references should be fully coded 									  --> done
Security and authentication should be fully coded															  --> done
Complete and test one sample “functional chain” end to end, so for example one CRUD operation should work end to end from database to the UI.																							--> done
Write automatic integration/unit tests with at least 70% coverage for the “functional chain”, to demonstrate you can write tests for different layers.																						   --> done
