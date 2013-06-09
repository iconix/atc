#!/bin/bash
mysqldump ebdb -h aa1bdj7bwieyv5s.cxstmaj8fdow.us-west-1.rds.amazonaws.com -P 3306 -u sapenguins -psapenguins > ebdb.sql
tar -zcvpf ~/db_backups/ebdb_$(date +%y%m%d_%H%M%S).tar.gz ebdb.sql
rm ebdb.sql