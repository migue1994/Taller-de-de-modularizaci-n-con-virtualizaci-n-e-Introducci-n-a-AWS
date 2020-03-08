# Taller de de modularización con virtualización e Introducción a AWS

This project aims create a virtual machine through aws educate, where i'll upload a client which perform several request to my webserver placed in heroku and it'll do a response depending of the solicited source.

I'm going to parallelize the client process and the server process as well, in order to test the web servers perform created on both heroku and aws educate.

## Getting started

To download the current projet, we'll copy the folliwing code in a cmd command prompt

``$ git clone https://github.com/migue1994/Taller-de-de-modularizaci-n-con-virtualizaci-n-e-Introducci-n-a-AWS.git``

The project will be downloaded in the current directory where we are in

## Prerequisites

We need the programs bellow to execute the project, in case that you haven´t any of these, i'm going to leave a link that redirect you to an installation tutorial.

-  [Java JDK](https://docs.oracle.com/javase/10/install/installation-jdk-and-jre-microsoft-windows-platforms.htm#JSJIG-GUID-A740535E-9F97-448C-A141-B95BF1688E6F)
- [Apache Maven](https://howtodoinjava.com/maven/how-to-install-maven-on-windows/)
- [Git](https://www.linode.com/docs/development/version-control/how-to-install-git-on-linux-mac-and-windows/)

## Installing

So, if we can execute the project, we only must enter the following code in the same directory on what we downloaded the project

``$ mvn exec:java -Dexec.mainClass="edu.escuelaing.arem.Cliente.CustomerMain"``

If you want to execute the client or

``$ mvn exec:java -Dexec.mainClass="edu.escuelaing.arem.servidorweb.HttpServer"

If you wnat to execute the local webserver.

## Built with 

- Apache maven
- Java 8 JDK
- IntelliJ Idea

## Author

- Miguel Rivera

## License

[LICENSE.txt](LICENSE.txt)

## CircleCi Status Badge

[![CircleCI](https://circleci.com/gh/migue1994/Taller-de-de-modularizaci-n-con-virtualizaci-n-e-Introducci-n-a-AWS.svg?style=svg)](https://circleci.com/gh/migue1994/Taller-de-de-modularizaci-n-con-virtualizaci-n-e-Introducci-n-a-AWS)

## Architecture

### Software model

[Software model](https://github.com/migue1994/Taller-de-de-modularizaci-n-con-virtualizaci-n-e-Introducci-n-a-AWS/blob/master/img/SoftwareModel.PNG)

### Deployment model

[Deploy model](https://github.com/migue1994/Taller-de-de-modularizaci-n-con-virtualizaci-n-e-Introducci-n-a-AWS/blob/master/img/DeploymentModel.PNG)

### AWS Architecture

[AWSArchitecture](https://github.com/migue1994/Taller-de-de-modularizaci-n-con-virtualizaci-n-e-Introducci-n-a-AWS/blob/master/img/AWSArchitecture.PNG)

### CLient Model

[Client model](https://github.com/migue1994/Taller-de-de-modularizaci-n-con-virtualizaci-n-e-Introducci-n-a-AWS/blob/master/img/ClientModel.PNG)
