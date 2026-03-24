const { Before, After, Status, setDefaultTimeout } = require('@cucumber/cucumber');
const { Builder } = require('selenium-webdriver');
const chrome = require('selenium-webdriver/chrome');

// Встановлюємо таймаут на 60 секунд
setDefaultTimeout(60 * 1000);

Before(async function () {
    let options = new chrome.Options();
    options.addArguments(
        '--headless', 
        '--disable-gpu', 
        '--no-sandbox', 
        '--disable-dev-shm-usage',
        '--window-size=1920,1080'
    );
    
    // Ініціалізація драйвера
    this.driver = await new Builder()
        .forBrowser('chrome')
        .setChromeOptions(options)
        .build();
});

After(async function (scenario) {
    if (this.driver) {
        // Якщо тест впав, робимо скріншот
        if (scenario.result?.status === Status.FAILED) {
            try {
                const screenShot = await this.driver.takeScreenshot();
                this.attach(Buffer.from(screenShot, 'base64'), 'image/png');
            } catch (error) {
                console.error("Не вдалося зробити скріншот:", error);
            }
        }
        // Закриваємо браузер
        await this.driver.quit();
    }
});
