Telegram Бот для Таро раскладов

Этот репозиторий содержит исходный код Telegram-бота, разработанного как инструмент для получения раскладов на картах таро на основе информации, введенной пользователями. Бот интегрирован с Gemini AI через REST API для получения уникальных раскладов, поддерживает платные запросы через Telegram, использует асинхронные процессы для улучшения производительности и хранит данные с помощью PostgreSQL. Проект временно приостановлен в связи с возможными изменениями в законодательстве.

📌 Описание проекта

Бот позволяет пользователям получить расклады на картах таро, которые подстраиваются под введенные данные о пользователе. Интеграция с Gemini AI позволяет генерировать персонализированные расклады, создавая уникальный опыт для каждого запроса. Оплата проводится через внутренний платежный API Telegram, обеспечивая удобство и безопасность для пользователей.

Внимание: В связи с возможными изменениями в законодательстве данный проект приостановлен и в данный момент не функционирует.

🛠️ Используемые технологии и библиотеки

Java: Основной язык программирования для создания бота.

Spring Core и Spring Data: Управление зависимостями, взаимодействие с базой данных и реализация асинхронных процессов.

Hibernate: ORM для упрощенного взаимодействия с PostgreSQL.

PostgreSQL: Реляционная база данных для надежного хранения пользовательских данных и истории транзакций.

Gemini AI REST API: Интеграция для генерации раскладов на картах таро на основе введенных данных.

Telegram Bot API: Использование сложных элементов API Telegram, включая inline кнопки и внутренний API для обработки платежей.

Асинхронность: Применение асинхронных операций для генерации ответа от нейросети параллельно с отправкой подготовительных сообщений

📐 Архитектура проекта

Проект построен на многослойной архитектуре для обеспечения высокой гибкости:

Контроллер: Обрабатывает входящие запросы и перенаправляет команды и сообщения в соответствующие сервисы.

Сервисный слой: Реализует основную бизнес-логику, включая взаимодействие с Gemini AI API и проверку оплаты.

Файловое хранилище: Хранит изображения карт Таро.

📂 Особенности работы с Telegram API и AI

Оплата через внутренний API Telegram: Поддержка платных функций бота, что позволяет пользователям оплачивать специальные расклады, не покидая интерфейса Telegram.

Интеграция с Gemini AI: Используется для генерации уникальных раскладов, которые зависят от информации, введенной пользователем.

Inline клавиатуры и интерактивные элементы: Бот поддерживает сложные элементы интерфейса, позволяя пользователю выбирать параметры расклада и подтверждать оплату через инлайн-кнопки.
