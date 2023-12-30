const {remote} = require('webdriverio');

const capabilities2 = {
  platformName: 'Android',
  'appium:automationName': 'Espresso',
  'appium:deviceName': 'Android',
  'appium:appPackage': 'com.example.rickandmorty',
  'appium:appActivity': '.app.MainActivity',
  'appium:noReset': false,
  'appium:fullReset': true,
  'appium:app': '/Users/vital/Developer/Personal/RickAndMorty/app/build/outputs/apk/debug/app-debug.apk',
  "appium:appActivity": ".app.MainActivity",
  "appium:espressoBuildConfig": "{\"additionalAndroidTestDependencies\": [\"androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2\",\"androidx.activity:activity:1.7.0\", \"androidx.activity:activity-compose:1.7.0\", \"androidx.fragment:fragment:1.5.1\", \"androidx.compose.runtime:runtime-android:1.5.4\", \"androidx.compose.ui:ui-android:1.5.4\"]}"
};

const capabilities = {
  platformName: 'Android',
  'appium:automationName': 'UIAutomator2',
  'appium:deviceName': 'Android',
  'appium:appPackage': 'com.example.rickandmorty',
  'appium:appActivity': '.app.MainActivity',
};

const wdOpts = {
  hostname: process.env.APPIUM_HOST || 'localhost',
  port: parseInt(process.env.APPIUM_PORT, 10) || 4723,
  logLevel: 'info',
  capabilities,
};

async function runTest() {

  const driver = await remote(wdOpts);

  try {

    //await driver.updateSettings({'driver': 'compose'});

    (await driver.$('//*[@resource-id="characters_button"]')).click();
    //(await driver.$('//*[@text="Characters"]')).click();

    await driver.pause(3000);
    
    (await driver.$('//*[@text="Rick Sanchez"]')).click();
    await driver.pause(3000);
 //   await driver.back();
    (await driver.$('//*[@content-desc="Go home"]')).click();

    await driver.pause(3000);

    (await driver.$('//*[@resource-id="locations_button"]')).click();
   // (await driver.$('//*[@text="Locations"]')).click()
    await driver.pause(3000);
    await driver.back();

    await driver.pause(3000);

    (await driver.$('//*[@resource-id="episodes_button"]')).click();
    //(await driver.$('//*[@text="Episodes"]')).click()
    await driver.pause(3000);
    await driver.back();

   await driver.back();


  } finally {
    await driver.pause(1000);
    await driver.deleteSession();
  }
}

runTest().catch(console.error);
