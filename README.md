# maven-selenium-cucumber-allure
Template on Maven + Selenium + Cucumber + Allure Reports written in Java.
Webdriver creation is thread safe and parallel execution is working by simply providing maven parameter, ie. '-Dthreads=5'

## Precondition
### Allure report install
- https://docs.qameta.io/allure/#_get_started

## Run
### Maven parameters:
- -Dbrowser {chrome, firefox...}
- -Dheadless {true(default), false}
- -DremoteDriver {default false and then it runs in local environment}
- -DgridURL {i.e. -DgridURL=https://username:Ws2yz7oTgSKxo5jzjUC4@hub-cloud.browserstack.com/wd/hub}
- -DdesiredBrowserVersion
- -DdesiredPlatform
- -Dthreads {$number_of_tests_in_parallel}
- -Dsurefire.runFailingTestsCount=2 (retries failed test for 2 times before marking it a failure)

### Allure
- Running Allure reports (if installed, or plugin added to jenkins) with command `allure:serve` at the end of Maven execution command

### Run local example
- `mvn clean test -Dheadless=false -Dbrowser=CHROME  -Dthreads=1 allure:serve`

### Remote Web Driver run
- mvn clean test -Dbrowser=CHROME -DremoteDriver=true -DgridURL=https://${username}:${automate_key}@hub-cloud
.browserstack.com/wd/hub