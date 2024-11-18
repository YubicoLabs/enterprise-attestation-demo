import "./App.css";
import {
  create,
  parseCreationOptionsFromJSON,
} from "@github/webauthn-json/browser-ponyfill";
import "bootstrap/dist/css/bootstrap.min.css";
import { useState } from "react";
import {
  Alert,
  Button,
  Container,
  Image,
  ListGroup,
  Stack,
} from "react-bootstrap";

function App() {
  const [showAlert, setShowAlert] = useState(false);

  //ENUMS - success, danger (from bootstrap)
  const [responseResult, setResponseResult] = useState("success");

  const [responseSerialNumber, setResponseSerialNumber] = useState("");
  const submitClick = async (e) => {
    e.preventDefault();

    try {
      const startOptions = {
        method: "GET",
      };

      const response = await fetch(
        "http://localhost:8080/v1/attestation/options",
        startOptions
      );

      const response_json = await response.json();

      console.log(response_json);

      const attestationResult = await create(
        parseCreationOptionsFromJSON(response_json)
      );

      console.log(attestationResult.toJSON());

      const resultOptions = {
        method: "POST",
        body: JSON.stringify(attestationResult),
      };

      const response2 = await fetch(
        "http://localhost:8080/v1/attestation/result",
        resultOptions
      );

      console.log(response2);

      if (response2.status === 200) {
        const response2_json = await response2.json();
        const serialNumber = response2_json.serialNumber;
        setResponseResult("success");
        setResponseSerialNumber(serialNumber);
      } else {
        setResponseResult("danger");
      }
      setShowAlert(true);
    } catch (e) {
      console.log(e);
      console.log("An error occurred");
    }
  };

  return (
    <div>
      <Container>
        <h1>Enterprise Attestation Demo</h1>
        <Stack>
          <div className="p-2">
            <h3>1) Set Chrome configurations</h3>
            <ol>
              <li>
                In a new tab, navigate to chrome://flags/#web-authentication-permit-enterprise-attestation
              </li>
              <li>
                Search for <b>enterprise attestation</b>
              </li>
              <li>Ensure the setting is set to "Enabled"</li>
              <li>
                In the text-box, add <b>http://localhost:3000</b>
              </li>
              <li>Allow Chrome to relaunch to add the new settings</li>
              <li>
                (Optional) return to
                chrome://flags/#web-authentication-permit-enterprise-attestation
                to ensure that the configuration changes were made
              </li>
            </ol>
            <Image className="app-shadow center" src="chrome-flags.png" />
          </div>
          <div className="p-2">
            <h3>2) Register a new credential</h3>
            <ol>
              <li>
                Ensure that your Enterprise Attestation YubiKey is plugged in
              </li>
              <li>
                Click the <b>Register credential</b> button below and follow the
                browser prompts
              </li>
            </ol>
            <Button onClick={submitClick}>Register credential</Button>
          </div>
          <div className="p-2">
            <h3>3) Analyze the result below</h3>
            {showAlert && (
              <Alert variant={responseResult}>
                {responseResult === "success" ? (
                  <div>
                    <p>
                      <b>Device serial number:</b> {responseSerialNumber}
                    </p>
                    <p>
                      You can verify this by running the Yubico Authenticator
                      App or YubiKey Manager
                    </p>
                  </div>
                ) : (
                  <div>
                    <p>
                      There was an error in processing the serial number. Try
                      the steps below to troubleshoot the issue.
                    </p>
                    <ul>
                      <li>
                        Ensure that you are using a browser that supports EA
                        (Chrome only)
                      </li>
                      <li>
                        Ensure that EA is enabled on your YubiKey. It may have
                        been disabled during a FIDO reset. Install the ykman
                        CLI, and run the following command:{" "}
                        <code>ykman fido config enable-ep-attestation</code>
                      </li>
                      <li>
                        Return to Step 1, and ensure that you have the correct
                        settings in
                        chrome://flags/#web-authentication-permit-enterprise-attestation
                      </li>
                      <li>
                        Look at the system logs in the companion Java
                        application
                      </li>
                    </ul>
                  </div>
                )}
              </Alert>
            )}
          </div>
        </Stack>
      </Container>
    </div>
  );
}

export default App;
