import FHIR from "fhirclient"

export class Client {
    client: any;
    patient: any;

    getPatient(): Promise<any> {
        if (!this.patient) {
            console.log('starting');
            return new Promise((resolve, reject) => {
                FHIR.oauth2.init({
                    clientId: 'Input client id you get when you register the app',
                    scope: 'launch/patient openid profile'
                }).then(client => {
                    client.request(`Patient/${client.patient.id}`)
                        .then(patient => {
                            this.patient = patient;
                            resolve(this.patient);
                        }, e => {
                            reject(e);
                        });
                })
                .catch(e => reject(e));
            });
        } else {
            return new Promise<any>((resolve, reject) => {
                resolve(this.patient);
            })
        }
    }
}
