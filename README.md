# ParserCSV

Wrap crawler logic with Rest Api on the top of Spring Boot and Hibernate.
Wrap crawler traverses websites predefined link depth (8 by default) and max visited page limit (10000 by default).
The wrap crawler starts from a predefined URL and follows links to dive deeper. 
The main purpose of this crawler is to detect the presence of the defined terms on the page and collect statistics.
All results are persisted into DB so users can request CSV reports generated from this data.
Expected reports are the same:
-	All stat report – serializes data into CSV file without sort
-	Top 10 page report – serializes data into CSV file sorted by total hits

 
