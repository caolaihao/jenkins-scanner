# Introduction

To show jenkins build success rate in Grafana, this tool will scan jenkins build info and insert into a database. 

1. Get job build info from jenkins
2. Save build info into DB(MySQL)
    

DB Schema:
Job, buildId, Date, status, trigger


Tasks:

    1. get job list
    2. get job build list by job
    3. get job build list by job & date
    4. get job build detail
    5. get build total count by date
    6. get build success count by date
    7. save build count(success/total)  by date
    8. if scan failed, then should exit with non-zero code