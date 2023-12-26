# Release validator

This simple standalone project is supposed to act as a simple mechanism to vet out if an upcoming TestNG release is good enough before we announce it to the TestNG audience.

It currently attempts at doing the following:

1. Compare dependency versions of the current project to dependencies or dependency management of a remote repository project (previous TestNG release).
2. Checks if the Javadocs are available and if its not empty.
3. Tests out if the Manifest file is readable.
4. Lists the contents of the jar file so that we can manually vet it out.

## Executing the release validator.

To run the tests, just execute

```bash
./mvntest.sh 7.8.0 7.9.0
```

The shell script takes two parameters.

1. The first parameter represents the current TestNG version that is being validated and which should be used.
2. The second parameter represents the previous TestNG version against which we need to be comparing dependencies.
