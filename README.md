# sonar-qube-rules-exporter  
###### v1.0.0.0  
Command line tool for exporting ruleset of available languages from SonarQube into an excel

### Pre-requisite
- Before running the tool make sure that SonarQube is running 
- Apache Maven to run project from command-prompt/terminal

### Tested on
- jdk-11.0.8
- sonarqube-8.5.1.38104 Community Edition
 
## How to use  

### 1) Export excel using source code

- Extract project  
- Open command-prompt/terminal and mount root of project 
- Clean & package the application: mvn clean install
- Run following maven command as described below :  
    - Command Format:  
      - mvn exec:java -Dexec.mainClass="in.flyspark.sonarqube.rulesexporter.RulesExporter" -Dexec.args="*SonarQubeURL*"    
    
    - Command Example:  
      - mvn exec:java -Dexec.mainClass="in.flyspark.sonarqube.rulesexporter.RulesExporter" -Dexec.args="http://localhost:9000/" 
   
- On success, check root directory of project for generated excel file.