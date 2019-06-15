# Shared Configurations
Both Jenkins and the local dev server share configuration. The dev server uses none of the jenkins scripts however, and passes it's environment variables through docker-compose.

# Setup
You can immediately begin modifying code, but for CI development tools (Jenkins) or the Devserver you'll need to set up the following things:

- Artifactory (or similar maven server)
- Verdaccio (or similar local repository)

These are used by the docker containers as well as the CI for deployment and caching of resources.

# Running Dev Server
In order to run the development server, you'll need to configure some environment variables.

## Variables
- MAVEN_USR 
  - Username for accessing an Artifactory instance.
- MAVEN_PSW 
  - Password for accessing the Artifactory instance.
- ARTIFACTORY_CONTEXT_URL
  - The optional artifactory context url to configure.
  - See Dockerfile for the default configuration.
- NPM_TOKEN
  - NPM Authentication token. You can find this in your .npmrc if you've authenticated with a private repository.
- NPM_REGISTRY_URL
  - The optional npm registry url to configure. Does NOT start with "http(s)://"
  - See Dockerfile for the default configuration.

# Jenkins

