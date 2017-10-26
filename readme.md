# Introduction

To show jenkins build success rate in Grafana, this tool will scan jenkins build info and insert into a database. 

1. Get job build info from jenkins
2. Save build info into DB(MySQL)
    

DB Schema:
Job, buildId, Date, status, trigger


Tasks:

    1. get job list
    2. get job build list by job
    3. get new build list after a certain build number. 
    4. get job build detail by build number
    5. save build detail into DB  
    6. if scan failed, then should exit with non-zero code