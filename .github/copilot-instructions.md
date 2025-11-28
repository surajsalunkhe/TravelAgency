# Copilot Instructions for TravelAgency Test Automation

## Architecture Overview
This is a **Cucumber BDD + Selenium + Java 21** test automation framework for BlazeDemo travel booking, using hybrid framework with Page Object Model (POM). Key architectural pattern: **ThreadLocal WebDriver** for parallel execution + **Lazy-initialized PageObjectManager** for efficient page object creation.

## Critical Setup & Execution

### Build & Run
```bash
# Run all tests
mvn clean test

# Run specific environment with tags
mvn clean integration-test -Denv=stage -Dcucumber.options="--tags @regression"

# Run specific feature
mvn clean test -Dcucumber.options="src/test/resources/featureFiles/CheapestFlightBooking.feature"
```

### Environment Configuration
- **Multi-environment support**: Properties in `src/test/resources/environment/{stage|prod}/environment.properties`
- **Environment selection**: Via `-Denv=stage` or `-Denv=prod` (defaults to `stage` if not specified)
- `PropertiesFileManager.getPropertyValue(key)` automatically loads from correct environment folder
- **Runtime data**: Test writes dynamic data (purchase IDs, selected cities) to `AutomationData.properties` for cross-step sharing

## Core Components & Patterns

### 1. Driver Management (ThreadLocal Pattern)
**File**: `src/main/java/com/org/driverFactory/DriverFactory.java`
- Uses `ThreadLocal<WebDriver>` for thread-safe parallel execution
- Initialize via `init_Driver(browserName)` in `@Before` hooks
- Retrieve via static `DriverFactory.getDriver()` anywhere in code
- Supports Chrome (with ad-blocker extension), Firefox, Safari

### 2. Page Object Manager (Lazy Singleton)
**File**: `src/main/java/com/org/managers/PageObjectManager.java`
- Lazy initialization pattern: `return (page==null) ? page=new Page(driver) : page;`
- Single manager instance per test scenario
- Usage in steps: `pageObjectManager.getblazeDemoStartPage().selectDepartureCity("Boston");`

### 3. Page Objects Structure
**Location**: `src/main/java/com/org/pages/`
- **Constructor pattern**: Always initialize `ElementUtil` with driver
  ```java
  public BlazeDemoStartPage(WebDriver driver){
      this.driver=driver;
      PageFactory.initElements(driver, this);
      elementutil=new ElementUtil(driver);
  }
  ```
- **Locator storage**: Private `By` locators at class level
- **Action methods**: Public methods that use `ElementUtil` wrapper methods (NOT raw Selenium)

### 4. ElementUtil Wrapper (Selenium 4 + Java 21)
**File**: `src/main/java/com/org/util/ElementUtil.java`
- **CRITICAL**: All Selenium interactions go through ElementUtil methods
- **Duration API**: Uses `Duration.ofSeconds(timeout)` for waits (Selenium 4 requirement)
- Common patterns:
  - `waitTillDisplay(locator, timeout)` - explicit waits
  - `selectFromDropDown(locator, text)` - dropdown handling
  - `safeJavaScriptClick(element)` - JS executor clicks with error handling
  - `getFirst()` - Java 21 List method for first element

### 5. Cucumber Hooks & Lifecycle
**File**: `src/test/java/appHooks/ApplicationHook.java`
- `@Before("@smoke and @regression")` - Driver initialization
- `@After` - Screenshot on failure + driver quit
- Screenshots attached to Cucumber reports via `sc.attach(screenshot, "image/png", name)`

### 6. Properties Management
**Files**: `src/main/java/com/org/util/PropertiesFileManager.java` + `Constants.java`
- **Reading**: `PropertiesFileManager.getPropertyValue("BLAZEDEMO")` - auto-loads from current environment
- **Writing**: `PropertiesFileManager.writeToProperties(key, value, TEMP_DATA_FILE)` - persist runtime data
- **Constants**: File paths in `Constants.java` (use these, don't hardcode paths)

## Coding Conventions

### Naming & Structure
- **Feature files**: Descriptive scenarios with `Scenario Outline` for data-driven tests
- **Step definitions**: Package `stepDefination` (note: singular, not "definitions")
- **Tags**: Use `@smoke`, `@regression`, `@sanity` for test categorization
- **Logger**: Initialize as `Logger log = LoggerHelper.getLogger(ClassName.class);` in every class

### Test Data Management
- **Static test data**: In environment properties (`USER_NAME=Sam jones`)
- **Dynamic test data**: Written to `AutomationData.properties` during execution (e.g., `PURCHASE_ID`, `FROM_CITY`)
- **Excel data**: Use `ExcelReader.java` for Excel-based test data

### Reporting
- **ExtentReports**: Configured via `Extent.properties` and `Extent-config.xml`
- Plugin auto-configured in `TestRunner.java`: `com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:`
- Reports generated in `test-output/SparkReport/ExtentSpark.html`

## Java 21 Specific Considerations
- **Module system**: Maven Surefire configured with `--add-opens java.base/java.lang=ALL-UNNAMED` for Gson reflection
- **List.getFirst()**: Available in Java 21 - replaces `list.get(0)` pattern
- **Duration API**: Always use `Duration.ofSeconds()` for Selenium timeouts, not integers

## Common Pitfalls to Avoid
1. **Don't bypass ElementUtil**: Always use `elementutil.doClick(locator)`, not `driver.findElement().click()`
2. **Don't hardcode waits**: Use `waitTillDisplay()` or `WebDriverWait` with Duration
3. **Don't forget PageFactory**: Call `PageFactory.initElements(driver, this)` in page constructors
4. **Don't mix environments**: Ensure `-Denv` flag matches intended test environment
5. **Don't create WebDriver directly**: Always use `DriverFactory.init_Driver()` and `getDriver()`

## Quick Reference
- **Add new page**: Extend pattern in `PageObjectManager` + create page in `com.org.pages`
- **Add new step**: Create in `StepDefination.java`, use `pageObjectManager.getXxxPage()`
- **Add new feature**: Place in `src/test/resources/featureFiles/`, use existing tags
- **Debug failing test**: Check `test-output/SparkReport/ExtentSpark.html` for screenshots + logs
