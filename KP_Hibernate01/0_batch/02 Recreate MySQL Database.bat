@echo off
pushd %cd%
cd "c:\Program Files\MySQL\MySQL Server 5.7\bin"

set SQL_SCRIPT=c:\1_workspace\KP_Hibernate01\src\sql\dropDB.txt
mysql --user=root --password=mikimiki --verbose --execute="\. %SQL_SCRIPT%"
:: pause

set SQL_SCRIPT=c:\1_workspace\KP_Hibernate01\src\sql\createDB.txt
mysql --user=root --password=mikimiki --verbose --execute="\. %SQL_SCRIPT%"
:: pause

set SQL_SCRIPT=c:\1_workspace\KP_Hibernate01\src\sql\initDB.txt
mysql --user=kp --password=mikimiki --verbose --execute="\. %SQL_SCRIPT%" kp_hibernate01
:: pause

set SQL_SCRIPT=c:\1_workspace\KP_Hibernate01\src\sql\populateDB.txt
mysql --user=kp --password=mikimiki --verbose --execute="\. %SQL_SCRIPT%" kp_hibernate01
:: pause
popd
