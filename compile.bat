@echo off
REM =============================================================
REM Script de compilation du projet VideoClub
REM =============================================================

echo.
echo ====================================
echo   Compilation du projet VideoClub
echo ====================================
echo.

REM Créer le dossier de sortie s'il n'existe pas
if not exist "bin" mkdir bin

REM Compiler tous les fichiers Java
echo Compilation en cours...
javac -encoding UTF-8 -d bin -cp "lib\*" src\model\*.java src\dao\*.java src\ui\*.java src\Main.java

REM Vérifier si la compilation a réussi
if %ERRORLEVEL% == 0 (
    echo.
    echo Compilation réussie !
    echo Les fichiers .class sont dans le dossier bin\
    echo.
    echo Pour lancer l'application, exécutez : run.bat
) else (
    echo.
    echo ERREUR : La compilation a échoué !
    echo Vérifiez les erreurs ci-dessus.
)

echo.
pause
