# GenericServiceXmlToCSV
  
    This service is used to consume rest xml resource and generate csv file.
    
    1. Install the project
    2. start the spring boot application
    3. open the browser and http://localhost:8080/swagger-ui.html#
    4. For example: 
      a. https://datenservice.net-connect-germany.de/XmlInterface/getXML.ashx?ReportId=Linepack&Start=20-01-2020
          document:
           <Linepack>
                   <Linepack xmlns="urn:schemas-microsoft-com:sql:SqlRowSet1">
                      <TimeStamp>2020-01-20T00:00:00</TimeStamp>
                      <Linepack_H>3477705023</Linepack_H>
                      <UnitLinepack_H>kWh</UnitLinepack_H>
                      <Linepack_L>506090338</Linepack_L>
                      <UnitLinepack_L>kWh</UnitLinepack_L>
                   </Linepack
                   ....
                   .....
            </Linepack>
          b. Give this above url as query param to the \xmlToCsv GET method
          c. It processs  and generate csv file and name of the csv file is Linepack.csv
          d. find the file in the project folder.
    
   

              
