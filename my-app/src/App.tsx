import React from 'react';
import './App.css';
import {Client} from './util/Client';
import image from './turtleOnFire.png';
const axios = require("axios");

class App extends React.Component<any, any>{

    componentDidMount() {
      var client = new Client();
      client.getPatient()
      .then(patient => {
          console.log(patient);
          this.setState({patient: patient})
      }, e => {
        console.log(e);
      })
       setInterval(() => {
           console.log(this.state);
       }, 5000)
    }

    getEncrypted(key: string, text: string) {
        return(
        <div className={"resultsBox"}>
            <p>Your private key</p>
            <p>{key}</p>
            {/*<p>Your encrypted data:</p>*/}
            {/*<p>{text}</p>*/}
        </div>
        );
    }

    getDecrypted(text: string) {
        return(
            <div>

                <p>Your decrypted data:</p>
                <p>{text}</p>
            </div>
        );
    }

    encrypt(text: string) {
        const instance = axios.create({
            baseURL: 'http://localhost:8080'
        });

        instance.post('/tools/encrypt', {text: text})
        .then( (response: any) => {
            console.log(response);
            console.log("returned");
            this.setState({key: response.data.key})
            this.setState({encrypted: response.data.text})

        });
    }

    decrypt(key: string, text: string) {
        const instance = axios.create({
            baseURL: 'http://localhost:8080'
        });

        instance.post('/tools/decrypt', {key: key, text: text})
            .then( (response: any) => {
                var x = JSON.parse(response.data.text);
                this.setState({decrypted: JSON.stringify(x, undefined, 3)})
            }, (e: any) => {
                console.log(e);
            });
    }

    save() {

    }



    editBox() {
        var x = JSON.stringify(this.state.patient, undefined, 2);

        return (
            <div>
                <header className={"header"}>
                    <span className={"turtleContainer"}><img className={"image"} src={image} /> </span>
                    <span className={"headerText"}>Turtle on fhir ... A medical record encryption application. ~ By: Daniel Tuttle</span>
                </header>
                <div className={"container"}>
                    <div className={"innerContainer"}>
                        <div className={"editBox"}>
                            <div>
                                Your encrypted FHIR health data:
                                <pre>
                                <p className={"encrypted"}>{this.state.encrypted}</p>
                                </pre>
                            </div>
                            <div className={"decryptContainer"}>
                                Your decrypted FHIR health data
                                <pre className={"decrypted"}>
                                    {this.state.decrypted}
                                </pre>
                            </div>
                            {/*<p>Update your info</p>*/}
                            {/*<div className={"boxRow"}>*/}
                            {/*    <input type={"text"} placeholder={"Given name"}/>*/}
                            {/*    <input type={"text"} placeholder={"City"}/>*/}
                            {/*</div>*/}
                            {/*<div className={"boxRow"}>*/}
                            {/*    <input type={"text"} placeholder={"Phone"}/>*/}
                            {/*    <input type={"text"} placeholder={"State"}/>*/}
                            {/*</div>*/}
                            {/*<div className={"boxRow"}>*/}
                            {/*    <input type={"text"} placeholder={"Birthdate"}/>*/}
                            {/*    <input type={"text"} placeholder={"Country"}/>*/}
                            {/*</div>*/}
                        </div>
                        <div className={"jsonDisplay"}>
                            <pre>{x}</pre>
                        </div>
                    </div>
                    <div className={"buttonContainer"}>
                        <div>
                        <button onClick={() => this.encrypt(JSON.stringify(this.state.patient))}>encrypt</button>
                        </div>
                        <div className={"innerText"}>
                            <textarea className={"text"} placeholder={"Copy/paste your encrypted text to decrypt"}></textarea>
                            <button onClick={() => this.decrypt(this.state.key, this.state.encrypted)}>decrypt</button>
                        </div>
                        {this.state.encrypted ? this.getEncrypted(this.state.key, this.state.encrypted) : ""}
                        {this.state.decrypted ? this.getDecrypted(this.state.decrypted) : ""}
                    </div>
                </div>
            </div>
        )
    }

    render(){

        if (this.state && this.state.patient) {
            return this.editBox();
        }
        else {
            return <div>Loading... (Make sure you start the app from <a href="https://launch.smarthealthit.org/">here</a> and launch URL points to http://localhost:3000/)</div>
        }
    }
}

export default App;
