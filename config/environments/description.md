**Настройка окружений**
Здесь хранятся свойства для разных сред, где работает приложение.

Приложение работает на разных серверах:
**DEV** — для разработчиков
**STAGING** — для тестирования
**PROD** — для реальных пользователей

У каждого сервера свой адрес, свои тестовые пользователи, свои настройки.

**Environments** — это просто набор файлов, где лежат эти настройки.
```text
config/environments/
├── dev.properties      # настройки для DEV
├── staging.properties  # настройки для STAGING
└── prod.properties     # настройки для PROD
```

**Что лежит в файлах:**
- dev.properties (разработка)
```properties
base.url=https://dev.api.myapp.com
test.user.email=dev_test@mail.com
test.user.password=dev123
timeout.seconds=10
```

- staging.properties (тестовый)
```properties
base.url=https://staging.api.myapp.com
test.user.email=staging_test@mail.com
test.user.password=staging456
timeout.seconds=30
```

- prod.properties (реальный)
```properties
base.url=https://api.myapp.com
test.user.email=prod_test@mail.com
test.user.password=prod789
timeout.seconds=60
```

**Как это используется**
Когда запускаем тесты, то говорим: Используй настройки из staging.properties
```bash
mvn test -Denv=staging -Ddevice.type=android -Ddevice.name=real_samsung
./gradlew test -Denv=staging -Ddevice.type=android -Ddevice.name=real_samsung
```

**Где это используется в коде**
```java
// Простой пример, чтобы понять суть
public class EnvironmentConfig {
    private static Properties props = new Properties();

    static {
        String env = System.getProperty("env", "dev"); // Читает параметры, переданные в командной строке через -D
        String filePath = "config/environments/" + env + ".properties";

        // Загружаем файл
        try (FileInputStream fis = new FileInputStream(filePath)) {
            props.load(fis);
        }
    }

    // Используем в тестах
    public static String getBaseUrl() {
        return props.getProperty("base.url");
    }
}
```
```java
// Если запустили с -Denv=staging
String env = System.getProperty("env", "dev");  // вернет "staging"

// Если запустили БЕЗ параметра -Denv
String env = System.getProperty("env", "dev");  // вернет "dev" (значение по умолчанию)

// Если запустили с -Denv=prod
String env = System.getProperty("env", "dev");  // вернет "prod"

// Возьмет нужный файл конфигурации окружения
String filePath = "config/environments/" + env + ".properties";
```


config/environmets/ — это мои данные, меняются часто, лежат отдельно
src/main/resources/ — это кодавая часть, меняется редко, компилируется
**Почему отдельно:** чтобы менять настройки, не пересобирая весь проект.
Просто правлю текстовый файл и запускаю тесты.

**Простая схема работы**
```text
config/environments/dev.properties
              ↓
    EnvironmentConfig.java (читает файл)
              ↓
    Твои классы (берут оттуда нужные значения)
```

В EnvironmentConfig.java например есть метод getBaseUrl()
и в других классах я буду использовать
String url = EnvironmentConfig.getBaseUrl();

Или в EnvironmentConfig.java есть useMock()...
то в классах я делаю проверку
if(EnvironmentConfig.useMock()) - окружение можно настроить в @BeforeClass
там настроить окружение и затем тесты.

```java
public static void main(String[] args) throws IOException {
    System.out.println("=== Testing Framework starting ===\n");

    // Получаем окружение из параметров
    String env = System.getProperty("env", "dev");
    System.out.println("Environment: " + env);

    // Путь к файлу конфигурации
    String configPath = "config/environments/" + env + ".properties";
    System.out.println("Read file: " + configPath + "\n");

    // Загружаем конфигурацию
    Properties props = new Properties();
    try(FileInputStream fis = new FileInputStream(configPath)) {
        props.load(fis);
        System.out.println("=== Environment settings ===");
        System.out.println("base.url: " + props.getProperty("base.url"));
        System.out.println("environment.name: " + props.getProperty("environment.name"));
        System.out.println("timeout.seconds: " + props.getProperty("timeout.seconds"));
    } catch (IOException e) {
        System.out.println("Exception: " + e.getMessage());
    }

    System.out.println("=== Ready to battle ===");
}
```

