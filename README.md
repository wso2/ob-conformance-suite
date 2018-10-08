# ob-conformance-suite

## Installation and Running

### Prerequisites

1. Install Java8 or above 
2. Install [Apache Maven 3.0.5](https://maven.apache.org/download.cgi) or above.
1. Download or clone the WSO2 Open Banking Conformance  Suite from [this repository](https://github.com/wso2/ob-conformance-suite.git)
	* To **clone the solution**, copy the URL and execute the following command in a command prompt.
	   `git clone <the copiedURL>`
	* To **download the pack**, click **Download ZIP** and unzip the downloaded file.

### Building and running
1. Navigate to the cloned or downloaded folder.
2. Perform `mvn clean install`
3. Execute `./startSolution.sh`
4. go to [http://localhost:8082/](htpp://localhost:8082/)
5. Login with credentials **username: *nab*** and **password: *nab***

## Project Structure

```
.
├── components
│   ├── com.wso2.finance.open.banking.conformance.api
│   ├── com.wso2.finance.open.banking.conformance.mgt
│   ├── com.wso2.finance.open.banking.conformance.test.core
│   └── ui-component
└── samples
    ├── com.wso2.finance.open.banking.conformance.tests.accountsinfromation
    └── com.wso2.finance.open.banking.conformance.tests.opendata

- components
  contains the core components for the ob-conformance-suite.
  
- samples
  contains API Specification tests samples for demonstration purposes.
```
