# EpamProject

Web-based library management system

Instructions to run the project: 

1. Download and install jdk 1.8, IntelliJ Idea (ultimate edition), Git, Apache Tomcat, MySQL
2. Clone project: git clone https://github.com/laqaliyeva/EpamProject.git
3. Configure project sdk: jdk1.8
4. Configure project as Maven project: in Idea, right click on project module -> Add framework support -> Maven
5. Prepare database by running script library.sql
6. Configure connection with database by changing property file main/resources/database.properties, if needed
7. Configure path to log files by changing "log" variable in property file main/resources/log4j.properties, if needed
8. To run with Tomcat: in Idea, add Tomcat run configuration: Run -> Edit Configurations -> Add New Configuration -> Tomcat Server (Local) -> configure path to server -> configure project war artefact
9. Run (for testing purposes you can use the following data for librarian: email - kaliyeva.laura@gmail.com password - 123 and for reader: email - a.kaliyeva@mail.ru password - 123)
