@echo off
start "YM2151 Controller" /d %~dp0 "java" -jar YM2151-Controller.jar %1
exit 0
pause