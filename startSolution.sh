#!/usr/bin/env bash
ps aux  |  grep -i org.wso2.finance.open.banking.conformance.api |  awk '{print $2}'  |  xargs kill -9
ps aux  |  grep -i SimpleHTTPServer |  awk '{print $2}'  |  xargs kill -9

mkdir -p components/ui-component/public/dist
cp components/ui-component/dist/bundle.js components/ui-component/public/dist/bundle.js

echo "Starting Conformance Suite"
echo "visit http://localhost:8082/ to use suite [CORS Should be disabled]"
nohup java -jar components/org.wso2.finance.open.banking.conformance.api/target/com.wso2.finance.open.banking.conformance.api-1.0.0-SNAPSHOT-jar-with-dependencies.jar >/dev/null 2>&1 &
pushd components/ui-component/public; nohup python -m SimpleHTTPServer 8082 >/dev/null 2>&1 &

echo "Ctrl+C to stop Conformance Suite"
read -r -d '' _ </dev/tty

ps aux  |  grep -i org.wso2.finance.open.banking.conformance.api |  awk '{print $2}'  |  xargs kill -9
ps aux  |  grep -i SimpleHTTPServer |  awk '{print $2}'  |  xargs kill -9
rm -rf components/ui-component/public/dist/
