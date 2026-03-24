const { Given, When, Then } = require('@cucumber/cucumber');
const { By } = require('selenium-webdriver');
const { expect } = require('chai');
const path = require('path');

Given('на моєму рахунку є {int} гривень', async function (balance) {
    const filePath = 'file:///' + path.resolve(__dirname, '../../atm.html').replace(/\\/g, '/');
    await this.driver.get(filePath);

    const balanceInput = await this.driver.findElement(By.id('balanceInput'));
    await balanceInput.clear();
    await balanceInput.sendKeys(balance.toString());
    
    const setBalanceBtn = await this.driver.findElement(By.id('setBalanceBtn'));
    await setBalanceBtn.click();
});

When('я намагаюся зняти {int} гривень', async function (amount) {
    const amountInput = await this.driver.findElement(By.id('amountInput'));
    await amountInput.clear();
    await amountInput.sendKeys(amount.toString());
    
    const withdrawBtn = await this.driver.findElement(By.id('withdrawBtn'));
    await withdrawBtn.click();
});

Then('банкомат повинен видати {int} гривень', async function (expectedDispensed) {
    const dispensedEl = await this.driver.findElement(By.id('dispensedAmount'));
    const text = await dispensedEl.getText();
    expect(parseInt(text, 10)).to.equal(expectedDispensed);
});

Then('мій залишок на рахунку повинен бути {int} гривень', async function (expectedBalance) {
    const balanceEl = await this.driver.findElement(By.id('currentBalance'));
    const text = await balanceEl.getText();
    expect(parseInt(text, 10)).to.equal(expectedBalance);
});

Then('на екрані має з\'явитися повідомлення {string}', async function (expectedMessage) {
    const messageEl = await this.driver.findElement(By.id('message'));
    const text = await messageEl.getText();
    expect(text).to.equal(expectedMessage);
});
