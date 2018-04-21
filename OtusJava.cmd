@Echo off
if a%1==a%1%1 goto usage

set myname=danik_ik

mvn archetype:generate -DarchetypeArtifactId=archetype_otus -DarchetypeGroupId=ru.danik_ik -DgroupId=ru.otus.%myname% -Dpackage=ru.otus.%myname%.%1 -DartifactId=%1 -Dversion=1.0-SNAPSHOT
goto eof

:usage
echo Use it in main otus homeworks folder. This script creates subfolder with 
echo project template for otus homework from maven local archetype. 
echo usage: %~nx0 <artifactId>
echo example: %~nx0 homework01
echo.
echo source for the archetype: https://bitbucket.org/danik-ik/maven-archetype-otus.git
pause