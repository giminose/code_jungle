@echo off
for /f "tokens=1-4 delims=/ " %%i in ("%date%") do (
    set dow=%%i
    set month=%%j
    set day=%%k
    set year=%%l
)
set datestr=%month%_%day%_%year%
echo datestr is %datestr%

set BACKUP_FILE=<NameOfTheFile>_%datestr%.backup
echo backup file name is %BACKUP_FILE%
SET PGPASSWORD=<PassWord>
echo on
bin\pg_dump -h <HostName> -p 5432 -U <UserName> -F c -b -v -f %BACKUP_FILE% <DATABASENAME>