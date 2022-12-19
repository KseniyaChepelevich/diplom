# Дипломный проект профессии «Инженер по тестированию»

Дипломная работа представляет собой автоматизацию тестирования мобильного приложения “Мобильный хоспис”.

## Начало работы

  1. Запустить Android studio.  
  1. В терминале Android studio ввести команду "clone https://github.com/KseniyaChepelevich/diplom/tree/testing/fmhandroidhttps://github.com/KseniyaChepelevich/diplom.git".  
  1. Дождаться окончания закрузки и индексации файлов.  
  1. Выполнить шаги описанные в разделе "Установка и запуск."  

### Prerequisites

Для работы с проектом необходимо установить:

* Android Studio 
* Allure 


### Установка и запуск

Для запуска тестов необходимо:

  1. Создать эмулятор/ подключить устройство android (minSdkVersion 26) 
  1. Установить приложение на эмулятор, выбрав верхней в панели инструментов Android studio:
      * в поле Edit configurations - app, 
      * в поле Available devices - эмулятор или подключенное устройство.  
  1. Открыть папку с тестами fmhandroid/app/src/androidTest/java/ru/iteco/fmhandroid/ui/.   
  1. Запускать тесты кнопкой Run.
  1. После запуска тестов подключиться через adb - 'adb shell'.
  1. Скопировать результаты из каталога с приложением в другое место - 'run-as ru.iteco.fmhandroid sh -c 'cd /data/data/ru.iteco.fmhandroid/files && tar cf - allure-results' | tar xvf - -C /data/local/tmp'.
  1. Отключился от adb - 'exit'.
  1. Скачать результаты - 'adb pull /data/local/tmp/allure-results'.
  1. Для просмотра Allure отчета запустить 'allure serve'.


## Лицензия

* ОС - Windows 10 Pro x64
* версия Java: 4


## [Документация](https://github.com/KseniyaChepelevich/diplom/blob/main/Plan.md)
