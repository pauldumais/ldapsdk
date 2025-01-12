# UnboundID LDAP SDK for Java

[Download the Latest Release of the LDAP SDK Here](https://github.com/pingidentity/ldapsdk/releases)

The UnboundID LDAP SDK for Java is a fast, powerful, user-friendly, and completely free and open source Java library for communicating with LDAP directory servers. It offers better performance, better ease of use, and more features than other Java-based LDAP APIs. It is developed by [Ping Identity Corporation](https://www.pingidentity.com/) and is actively being maintained and enhanced as a critical component of Ping Identity client and server software.

The UnboundID LDAP SDK for Java is free to use and redistribute in open source or proprietary applications under the terms of the [Apache License, Version 2.0](LICENSE.md), which is a very permissive OSI-approved open source license. For legacy purposes, the software is also available under the terms of the GNU [GPLv2](https://raw.githubusercontent.com/pingidentity/ldapsdk/master/LICENSE-GPLv2.txt) or [LGPLv2.1](https://raw.githubusercontent.com/pingidentity/ldapsdk/master/LICENSE-LGPLv2.1.txt) licenses, or the not-open-source-but-still-free-to-use [UnboundID Free Use License](https://raw.githubusercontent.com/pingidentity/ldapsdk/master/LICENSE-UnboundID-LDAPSDK.txt).

The LDAP SDK does not have any third-party dependencies beyond a Java 7 or higher Java Runtime Environment, so a single jar file is all you need to add top-notch LDAP support to your Java application. It can also be used in conjunction with most other JVM-based languages, as well as on the Android mobile platform.

The full documentation for the LDAP SDK is available online at [https://docs.ldap.com/ldap-sdk/docs/index.html](https://docs.ldap.com/ldap-sdk/docs/index.html). The API documentation (aka JavaDoc) for the UnboundID LDAP SDK for Java is available at [https://docs.ldap.com/ldap-sdk/docs/javadoc/index.html](https://docs.ldap.com/ldap-sdk/docs/javadoc/index.html). The [LDAP.com](https://ldap.com/) website also has a lot of useful information about LDAP and directory services.

## Advantages of the UnboundID LDAP SDK for Java

Some of the key advantages of the UnboundID LDAP SDK for Java include:

* Full support for the LDAPv3 protocol as of the most recent specification update, including all operation types, intermediate response messages, the increment modification extension, and absolute true/false filters.

* Built-in support for a wide range of official and de facto standard protocol extensions, including controls, extended operations, and SASL mechanisms. The LDAP SDK also includes APIs for developing support for any custom protocol extensions that you may need but aren't included as part of the LDAP SDK.

* A very convenient and user-friendly API that reduces the amount of code you need to write in order to perform the desired operations.

* A number of security-related features to help simplify secure communication and ensure that all interaction with LDAP servers and data remains safe and available to only properly-authorized users.

* Powerful and flexible connection pooling, load balancing, and failover support to help ensure that your application keeps working flawlessly even if one or more of the directory servers becomes unavailable.

* A simple yet powerful persistence framework that allows you to interact with LDAP entries as if they were Java objects. The persistence framework works with your existing schema and directory data without any changes so that it remains compatible with other applications that may need to access it.

* Enhanced support for a number of special entry types, including the root DSE, subschema subentries, and changelog entries.

* Support for a number of related APIs, including reading and writing LDIF entries and change records (including parallelized encoding and decoding for maximum performance), base64 encoding and decoding, and ASN.1 BER encoding and decoding. There are also APIs to simplify the creation of LDAP-enabled command-line tools.

* Support for simplifying LDAP-based testing. The LDAP SDK includes an in-memory directory server is available to allow you easily create one or more simple LDAPv3-compliant servers to use in your testing frameworks. It also provides a set of utility methods for making assertions about the content stored in an LDAP directory server.

* The LDAP SDK is provided as a single self-contained jar file with no dependencies on anything outside of Java SE version 7 or later.

## How To Get the UnboundID LDAP SDK for Java

Packaged releases of the UnboundID LDAP SDK for Java are available for download from GitHub at [https://github.com/pingidentity/ldapsdk/releases](https://github.com/pingidentity/ldapsdk/releases) and from SourceForge at [https://sourceforge.net/projects/ldap-sdk/files/](https://sourceforge.net/projects/ldap-sdk/files/).

If you prefer to use the Maven tool to manage your library dependencies, then you can get the UnboundID LDAP SDK for Java in the [Maven Central Repository](https://search.maven.org/#search%7Cgav%7C1%7Cg%3A%22com.unboundid%22%20AND%20a%3A%22unboundid-ldapsdk%22) with a GroupId of "com.unboundid" and an ArtifactId of "unboundid-ldapsdk".

If you want to check out and build the LDAP SDK from source, then you can do that with the following git command:

```
git clone https://github.com/pingidentity/ldapsdk.git
```

If you prefer subversion, you can instead use the command:

```
svn checkout https://github.com/pingidentity/ldapsdk/trunk ldapsdk-svn
```

Once the code has been checked out, you can build the LDAP SDK by running the `build.sh` shell script on UNIX-based systems or `build.bat` batch file on Windows. Once the build has completed, the zip file containing the packaged LDAP SDK may be found in the build/package directory.

## How To Get Help with the UnboundID LDAP SDK for Java

If you run into a problem when using the LDAP SDK and you can't figure it out from the [documentation](https://docs.ldap.com/ldap-sdk/docs/index.html), then there are a few ways that you can get help:

* For bug reports and feature requests, use the GitHub issue tracker at [https://github.com/pingidentity/ldapsdk/issues](https://github.com/pingidentity/ldapsdk/issues).

* For questions, feedback, and other discussions, use the online discussion forum at [http://sourceforge.net/p/ldap-sdk/discussion/](http://sourceforge.net/p/ldap-sdk/discussion/) or the [ldap-sdk-discuss@lists.sourceforge.net](mailto:ldap-sdk-discuss@lists.sourceforge.net) mailing list. See [https://sourceforge.net/p/ldap-sdk/mailman/ldap-sdk-discuss/](https://sourceforge.net/p/ldap-sdk/mailman/ldap-sdk-discuss/) for more information about this mailing list, including an archive and information about how to subscribe.

* If you'd rather not have the communication in public, send an email message to [ldapsdk-support@pingidentity.com](mailto:ldapsdk-support@pingidentity.com).

* If you have a support contract with Ping Identity, you may get assistance with the LDAP SDK in the same way that you get support for other Ping Identity products.

## Contributing to the UnboundID LDAP SDK for Java

Ping Identity does not accept third-party code submissions. However, there are other ways that you can help, including submitting bug reports and feature requests. See the [CONTRIBUTING.md](CONTRIBUTING.md) file for additional information.
