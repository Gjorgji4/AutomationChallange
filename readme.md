Step 1: Navigate to Scenarios class
path: src/test/java/Scenarios.java

All test have "@Test" tag
to run all test at once run the fullTask() method 

to run separately, each task have own test method  


Inform: Maybe the driver would fail on starts expecially if is tested on windows platform
to solve that please download proper driver for the platform and manually add into
src/main/java/Drivers
or contact me directly 

Update: to enabble UI mode for selenium, in ChromeDriverOption comment the 31 line
( chromeOptions.addArguments("headless"); ) method: public static ChromeOptions chromeOptions()
