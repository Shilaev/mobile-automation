**Настройки устройств**

Capabilities - это настройки для Appium, которые говорят:
какое устройство использовать и как с ним работать.

```text
config/capabilities/
├── android/
│   ├── emulator_pixel_6.json    # эмулятор Pixel 6
│   └── real_samsung_s21.json    # реальный Samsung
└── ios/
    └── simulator_iphone_14.json # iOS симулятор
```

**Пример**
```json
{
  "deviceName": "Pixel_6_API_31",
  "platformName": "Android",
  "platformVersion": "12.0",
  "udid": "emulator-5554",
  "automationName": "UiAutomator2",
  "appPackage": "com.example.app",
  "appActivity": ".MainActivity"
}
```

```bash
./gradlew test -Ddevice.name=emulator_pixel_6
```