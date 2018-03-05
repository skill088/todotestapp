# TODO test app
### Это приложение, кторое демонстрирует обладание навыками и умениями, предъявленными условиями тестового задания.

При выполнении использовались следующие библиотеки:
* [RxJava2](https://github.com/ReactiveX/RxJava/wiki/What's-different-in-2.0)
* [Retrofit 2](http://square.github.io/retrofit/)
* [Dagger 2](https://google.github.io/dagger/)
* [ButterKnife](http://jakewharton.github.io/butterknife/)
* [AndroidSwipeLayout](https://github.com/daimajia/AndroidSwipeLayout)

-------
Функционал на текущем этапе представлет следующие возможности:
- авторизация в приложении
- выход из аккаунта
- автоподгружаемый список
- поиск по списку с применением фильтров (не реализована серверная часть, поиск осуществляется по уже загрежнному списку)
- удаление элементов (не реализована серверная часть, удаляет только из локального списка), удаление элемента из списка осуществляется свайпом влево или вправо
- горизонтальная ориентация недосутпна на текущем этапе
-------
Актуальная версия приложения представлена в последнем коммите ветки dagger2 в текущем репозитории.
Поскольку серверная часть расположена на бесплатном хостинге, то 1 час в течении суток работа приложения невозможна, так как сервер "спит".
Учётные данные для тестового пользователя:
 - login: admin
 - password: asdfgh

 ------
 ## Скриншоты приложения:
 
 ![Authorization](https://s8.hostingkartinok.com/uploads/images/2018/03/31134023ff8be7b0617824a09e8da589.jpg)
 ![List loading](https://s8.hostingkartinok.com/uploads/images/2018/03/09c00e68878aa87edddec08b70d973c2.jpg)
 ![End of list](https://s8.hostingkartinok.com/uploads/images/2018/03/e36a3c587c5334ed5b6d4fd14a0c294a.jpg)
 ![Search with filters](https://s8.hostingkartinok.com/uploads/images/2018/03/13600a4d8f5c626c4e5227b43d02b29c.jpg)
 ![Menu](https://s8.hostingkartinok.com/uploads/images/2018/03/cc786d3c552f4890e92aed6e9d6b2a2d.jpg)
 
