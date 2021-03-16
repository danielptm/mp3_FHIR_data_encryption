## To run react app.

1. Cd to my-app folder
2. Run ```npm install```
3. Run npm run start, and navigate to http://localhost:3000

## To run java app
1. Open the project demo in intellij
2. Ensure that you have a springboot configuration setup in intellij.
3. Press the green arrow button at top of screen.

## Use application.
1. Navgiate to http://localhost:3000
2. Press the encrypt button to encrypt the patient data after logging in.
3. Press copy/paste the encrypted data into the text-area.
4. Press decrypt to decrypt the patient data.


## Alternative use case.
1. To decrypt the data outside of this application create a postman ```POST``` request for the backend decrypt service.
```http://localhost:8080/tools/decrypt```

2. Create a postman body request in the form:

```
{
  "key": "<your encryption key>",
  "text": "<Your encrypted text>"
}
```
