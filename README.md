<div id="top"></div>
<!--
*** Thanks for checking out the Best-README-Template. If you have a suggestion
*** that would make this better, please fork the repo and create a pull request
*** or simply open an issue with the tag "enhancement".
*** Don't forget to give the project a star!
*** Thanks again! Now go create something AMAZING! :D
-->

<!-- PROJECT SHIELDS -->
<!--
*** I'm using markdown "reference style" links for readability.
*** Reference links are enclosed in brackets [ ] instead of parentheses ( ).
*** See the bottom of this document for the declaration of the reference variables
*** for contributors-url, forks-url, etc. This is an optional, concise syntax you may use.
*** https://www.markdownguide.org/basic-syntax/#reference-style-links
-->

[![Contributors][contributors-shield]][contributors-url]
[![Forks][forks-shield]][forks-url]
[![Stargazers][stars-shield]][stars-url]
[![Issues][issues-shield]][issues-url]
[![Apache-2.0][license-shield]][license-url]

<!-- PROJECT LOGO -->
<br />
<div align="center">
  <a href="https://github.com/github_username/repo_name">
    <img src="https://assets.brandfolder.com/q2tsde-8kenzk-4cg1pz/v/8222261/original/Yubico%20Logo%20Big%20(PNG).png" alt="Logo" width="363" height="100">
  </a>

<h3 align="center">Enterprise Attestation Demo</h3>

  <p align="center">
    Small sample application to demonstrate how to implement WebAuthn Enterprise Attestation from a backend relying party, and client application.
    <a href="https://github.com/YubicoLabs/enterprise-attestation-demo/tree/master#about-the-project"><strong>Explore the docs »</strong></a>
    <br />
    <br />
    ·
    <a href="https://github.com/YubicoLabs/enterprise-attestation-demo/issues">Report Bug</a>
    ·
    <a href="https://github.com/YubicoLabs/enterprise-attestation-demo/issues">Request Feature</a>
  </p>
</div>

<!-- TABLE OF CONTENTS -->
<details>
  <summary>Table of contents</summary>
  <ol>
    <li>
      <a href="#about-the-project">About The project</a>
    </li>
    <li>
      <a href="#built-with">Built with</a>
    </li>
    <li>
      <a href="#getting-started">Getting started</a>
    </li>
    <li><a href="#contributing">Contributing</a></li>
    <li><a href="#license">License</a></li>
    <li><a href="#contact">Contact</a></li>
  </ol>
</details>

<!-- ABOUT THE PROJECT -->

## About The Project

TODO
This project demonstrates how to implement WebAuthn Enterprise Attestation from a backend relying party, and client application. The primary focus is on the relying party, specifically how to parse the serial number from an enterprise attestation result returned from a YubiKey. The client application provided with the project is meant to provide an easy interface for testing EA capabilities from an EA supported browser (Google Chrome).

The content presented in this demo is meant to compliment the material found within the [Enterprise Attestation](https://developers.yubico.com/WebAuthn/Concepts/Enterprise_Attestation/) section on the Yubico Developer Site.

**Disclaimer** - This project is not meant to act as a production ready solution. Please review and understand the code, integrate the needed components, and make any modifications based on your security requirements.

<p align="right">(<a href="#top">back to top</a>)</p>

## Built With

- [Spring Boot](https://spring.io/projects/spring-boot)
- [Yubico's java-webauthn-server library](https://github.com/Yubico/java-webauthn-server)
- [React](https://react.dev/)
- [Docker](https://www.docker.com/)

<p align="right">(<a href="#top">back to top</a>)</p>

## Getting Started

To use the demo, first ensure that you have the latest version of Docker installed.

Once ready, follow the steps below for a quick deployment.

1. Clone the repository

```bash
git clone https://github.com/YubicoLabs/enterprise-attestation-demo.git
```

2. Run the following command

```bash
docker compose up -d
```

3. Navigate to the web app using `localhost:3000`

4. Follow the steps on screen

## Contributing

Contributions are what make the open source community such an amazing place to learn, inspire, and create. Any contributions you make are **greatly appreciated**.

If you have a suggestion that would make this better, please fork the repo and create a pull request. You can also simply open an issue with the tag "enhancement".
Don't forget to give the project a star! Thanks again!

1. Fork the Project
2. Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3. Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the Branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

<p align="right">(<a href="#top">back to top</a>)</p>

<!-- LICENSE -->

## License

Distributed under the Apache-2.0 License. See `LICENSE` for more information.

<p align="right">(<a href="#top">back to top</a>)</p>

<!-- CONTACT -->

## Contact

[Yubico Developer Program](https://developers.yubico.com/)

[Report an issue](https://github.com/YubicoLabs/enterprise-attestation-demo/issues)

<p align="right">(<a href="#top">back to top</a>)</p>

<!-- MARKDOWN LINKS & IMAGES -->
<!-- https://www.markdownguide.org/basic-syntax/#reference-style-links -->

[contributors-shield]: https://img.shields.io/github/contributors/YubicoLabs/enterprise-attestation-demo.svg?style=for-the-badge
[contributors-url]: https://github.com/YubicoLabs/enterprise-attestation-demo/graphs/contributors
[forks-shield]: https://img.shields.io/github/forks/YubicoLabs/enterprise-attestation-demo.svg?style=for-the-badge
[forks-url]: https://github.com/YubicoLabs/enterprise-attestation-demo/network/members
[stars-shield]: https://img.shields.io/github/stars/YubicoLabs/enterprise-attestation-demo.svg?style=for-the-badge
[stars-url]: https://github.com/YubicoLabs/enterprise-attestation-demo/stargazers
[issues-shield]: https://img.shields.io/github/issues/YubicoLabs/enterprise-attestation-demo.svg?style=for-the-badge
[issues-url]: https://github.com/YubicoLabs/enterprise-attestation-demo/issues
[license-shield]: https://img.shields.io/github/license/YubicoLabs/enterprise-attestation-demo.svg?style=for-the-badge
[license-url]: https://github.com/YubicoLabs/enterprise-attestation-demo/blob/master/LICENSE
