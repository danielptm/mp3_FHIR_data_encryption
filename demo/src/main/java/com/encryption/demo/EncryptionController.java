package com.encryption.demo;

import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.UUID;

@Component
@Path("tools")
public class EncryptionController {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("encrypt")
    public Response encrypt(Input input) throws Exception {
        String key = UUID.randomUUID().toString().substring(0, 16);
        String encrypted = EncryptionService.aesEncrypt(input.getText(), key);

        TextAndKey output = new TextAndKey();
        output.setKey(key);
        output.setText(encrypted);

        return Response.ok(output).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("decrypt")
    public Response decrypt(TextAndKey textAndKey) throws Exception {

        String decrypted = EncryptionService.aesDecrypt(textAndKey.getText(), textAndKey.getKey());
        Input output = new Input();
        output.setText(decrypted);
        return Response.ok(output).build();
    }
}
