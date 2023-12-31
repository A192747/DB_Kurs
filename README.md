# Задание 
В рамках данной курсовой работы необходимо автоматизировать работу автопарка. 

## Требование к клиентскому приложению:
1) Отдельный файл параметров подключения к БД, который задается файлом (формат plain text file или ini config file или xml file);
2) Авторизация с хешированием паролей для пользователей (2 пользователя);
3) Меню приложения содержит группы элементов: справочники, журнал, отчеты;
4) Должен быть обеспечен ввод, редактирование, удаление, просмотр информации из БД;
5) Используются пользовательские элементы вида GridView для обзора таблиц (представлений) БД;
6) GridView, который содержит внешние ключи, должен содержать нужные/необходимые атрибуты внешней таблицы;
7) Есть отдельная форма для работы с записью таблицы, содержащая эл-ты интерфейса: добавить, изменить, удалить, закрыть;
8) Меню пункта ОТЧЕТЫ вызывает формы вывода отчетов (минимум 2-х) (выходной формат txt или xlsx или pdf).

## Пользователи базы данных

Помимо основного пользователя (администратора) был создан дополнительный пользователь, наделенными только правами на чтение. Пароли пользователей были захэшированы методом sha256 и сохранены в файл hashs.txt. 
1) Пользователю postgres. Выданы все права доступа на управление схемы БД. Имеет права на редактирование, вставку, удаление, изменение, просмотр таблиц БД.  
2) Пользователь petr имеет права только на просмотр таблиц БД и формирование отчетов.


## Работа интерфейса
При запуске интерфейса встречает окно авторизации пользователя.<br/>
![Screenshot](https://github.com/A192747/DB_Kurs/blob/main/images/image1.png)
<br/>
При вводе неверного логина или пароля, появится окно ошибки, которое предупредит пользователя об этом.
<br/>
![Screenshot](https://github.com/A192747/DB_Kurs/blob/main/images/image2.png)
<br/>
Когда учетные данные пользователя введены корректно появляется главное окно интерфейса БД (см. Рисунок 3).
<br/>
![Screenshot](https://github.com/A192747/DB_Kurs/blob/main/images/image3.png)
<br/>
Данный журнал позволяет отслеживать водителей, по пути по каким дорогам они находятся. При нажатии кнопки «Добавить», появляется окно, в котором можно выбрать в какую таблицу необходимо добавить данные. (см. Рисунок 4). В данном окне необходимо ввести необходимые данные.
<br/>
![Screenshot](https://github.com/A192747/DB_Kurs/blob/main/images/image4.png)
![Screenshot](https://github.com/A192747/DB_Kurs/blob/main/images/image5.png)
<br/>
При желании можно внести изменения, нажав на кнопку «Изменить» (см. Рисунок 5). Так же когда необходимо удалить данные из необходимой таблицы, можно нажать на кнопку «Удалить».
<br/>
![Screenshot](https://github.com/A192747/DB_Kurs/blob/main/images/image6.png)
<br/>
Далее в интерфейсе присутствуют справочники (см. Рисунок 7), в которых находятся авто, персонал, маршруты.
<br/>
![Screenshot](https://github.com/A192747/DB_Kurs/blob/main/images/image7.png)
<br/>
Далее в интерфейсе присутствуют отчеты (см. Рисунок 23), в которых находятся два отчета: «Все водители» и «Все маршруты и кол-во автомобилей».
<br/>
![Screenshot](https://github.com/A192747/DB_Kurs/blob/main/images/image8.png)
<br/>
Отчеты в БД генерируются в формате txt.
<br/>
![Screenshot](https://github.com/A192747/DB_Kurs/blob/main/images/image9.png)
<br/>
Отчет по водителям.
<br/>
![Screenshot](https://github.com/A192747/DB_Kurs/blob/main/images/image10.png)
<br/>
Отчет по дорогам.
<br/>
![Screenshot](https://github.com/A192747/DB_Kurs/blob/main/images/image11.png)
<br/>
