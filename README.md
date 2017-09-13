# Simple Serverless Maven Full CD application
This is a simple example to demonstrate how to implement a serverless application using this fully managed aws codepipeline template: https://github.com/MatthewSilverstein/cloudformation-pipeline

The CD pipeline will:

1) Detect changes in GitHub
1) Build changes and run unit tests
1) Deploy changes to Alpha
1) Run integration tests against Alpha
1) Deploy changes to Beta

## Setting up the pipeline
1) Install awscli and setup aws account
1) Create a GitHub repository and push this application to it
1) Get an access key for GitHub repository with privileges: [admin:repo_hook, repo]
1) In commandline:

```
aws cloudformation deploy --template-file cloudformation-pipeline.yaml --stack-name <stack-name> --capabilities CAPABILITY_NAMED_IAM --parameter-overrides \
 GitHubUser=<username> \
 Repo=<repo> \
 Branch=<branch> \
 GitHubToken=<github access token>
```

# Testing
Running unit tests:
```
mvn clean test
```

Running integration tests:
```
mvn verify -DargLine="-Dstage=alpha" -P integration-test
```
(change stage to whichever stage you want to run integration tests against)

# References
Continuous Deployment pipeline based on this repository: https://github.com/MatthewSilverstein/cloudformation-pipeline

Project behaviour on this tutorial: https://lobster1234.github.io/2017/02/28/serverless-framework-java-maven-part-1/

Maven testing setup based on this tutorial: https://github.com/pkainulainen/maven-examples