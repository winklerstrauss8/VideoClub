@echo off
REM =============================================================
REM Script de lancement du projet VideoClub
REM =============================================================

echo.
echo ====================================
echo   Lancement de VideoClub
echo ====================================
echo.

REM Lancer l'application avec le classpath incluant le driver MySQL
java -cp "bin;lib\*" Main

echo.
pause
