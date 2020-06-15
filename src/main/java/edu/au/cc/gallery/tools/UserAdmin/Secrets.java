package edu.au.cc.gallery.tools.UserAdmin;

import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;

import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueRequest;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueResponse;


public final class Secrets {

    private SecretsManagerClient sm;
    public Region region = Region.US_EAST_2;



    public Secrets() {
        this.sm = SecretsManagerClient.builder().region(region).build();
    }

    public String getSecretString(String secretId) {
        GetSecretValueRequest req = GetSecretValueRequest.builder().secretId(secretId).build();
        GetSecretValueResponse secret = this.sm.getSecretValue(req);

        return secret.secretString();
    }

    public SdkBytes getSecretBinary(String secretId) {
        GetSecretValueRequest req = GetSecretValueRequest.builder().secretId(secretId).build();

        GetSecretValueResponse secret = this.sm.getSecretValue(req);

        return secret.secretBinary();
    }
}


//public class Secrets {
//    // Use this code snippet in your app.
//    // If you need more information about configurations or implementing the sample code, visit the AWS docs:
//    // https://docs.aws.amazon.com/sdk-for-java/v1/developer-guide/java-dg-samples.html#prerequisites
//
//    public static String getSecretImageGallery() {
//
//        String secretName = "admins";
//        Region region = Region.US_EAST_2;
//
//        // Create a Secrets Manager client
//        SecretsManagerClient client  = SecretsManagerClient.builder()
//                .region(region)
//                .build();
//
//        // In this sample we only handle the specific exceptions for the 'GetSecretValue' API.
//        // See https://docs.aws.amazon.com/secretsmanager/latest/apireference/API_GetSecretValue.html
//        // We rethrow the exception by default.
//
//        String secret, decodedBinarySecret;
//        GetSecretValueRequest getSecretValueRequest = GetSecretValueRequest.builder()
//                .secretId(secretName)
//                .build();
//        GetSecretValueResponse getSecretValueResult = null;
//
//        try {
//            getSecretValueResult = client.getSecretValue(getSecretValueRequest);
//        } catch (DecryptionFailureException e) {
//            // Secrets Manager can't decrypt the protected secret text using the provided KMS key.
//            // Deal with the exception here, and/or rethrow at your discretion.
//            throw e;
//        } catch (InternalServiceErrorException e) {
//            // An error occurred on the server side.
//            // Deal with the exception here, and/or rethrow at your discretion.
//            throw e;
//        } catch (InvalidParameterException e) {
//            // You provided an invalid value for a parameter.
//            // Deal with the exception here, and/or rethrow at your discretion.
//            throw e;
//        } catch (InvalidRequestException e) {
//            // You provided a parameter value that is not valid for the current state of the resource.
//            // Deal with the exception here, and/or rethrow at your discretion.
//            throw e;
//        } catch (ResourceNotFoundException e) {
//            // We can't find the resource that you asked for.
//            // Deal with the exception here, and/or rethrow at your discretion.
//            throw e;
//        }
//
//        // Decrypts secret using the associated KMS CMK.
//        // Depending on whether the secret is a string or binary, one of these fields will be populated.
//        return getSecretValueResult.secretString();
//
//        // Your code goes here.
//    }
//}


//// Use this code snippet in your app.
//// If you need more information about configurations or implementing the sample code, visit the AWS docs:
//// https://docs.aws.amazon.com/sdk-for-java/v1/developer-guide/java-dg-samples.html#prerequisites
//
//    public static void getSecret() {
//
//        String secretName = "admins-star";
//        String region = "us-east-2";
//
//        // Create a Secrets Manager client
//        AWSSecretsManager client  = AWSSecretsManagerClientBuilder.standard()
//                .withRegion(region)
//                .build();
//
//        // In this sample we only handle the specific exceptions for the 'GetSecretValue' API.
//        // See https://docs.aws.amazon.com/secretsmanager/latest/apireference/API_GetSecretValue.html
//        // We rethrow the exception by default.
//
//        String secret, decodedBinarySecret;
//        GetSecretValueRequest getSecretValueRequest = new GetSecretValueRequest()
//                .withSecretId(secretName);
//        GetSecretValueResult getSecretValueResult = null;
//
//        try {
//            getSecretValueResult = client.getSecretValue(getSecretValueRequest);
//        } catch (DecryptionFailureException e) {
//            // Secrets Manager can't decrypt the protected secret text using the provided KMS key.
//            // Deal with the exception here, and/or rethrow at your discretion.
//            throw e;
//        } catch (InternalServiceErrorException e) {
//            // An error occurred on the server side.
//            // Deal with the exception here, and/or rethrow at your discretion.
//            throw e;
//        } catch (InvalidParameterException e) {
//            // You provided an invalid value for a parameter.
//            // Deal with the exception here, and/or rethrow at your discretion.
//            throw e;
//        } catch (InvalidRequestException e) {
//            // You provided a parameter value that is not valid for the current state of the resource.
//            // Deal with the exception here, and/or rethrow at your discretion.
//            throw e;
//        } catch (ResourceNotFoundException e) {
//            // We can't find the resource that you asked for.
//            // Deal with the exception here, and/or rethrow at your discretion.
//            throw e;
//        }
//
//        // Decrypts secret using the associated KMS CMK.
//        // Depending on whether the secret is a string or binary, one of these fields will be populated.
//        if (getSecretValueResult.getSecretString() != null) {
//            secret = getSecretValueResult.getSecretString();
//        }
//        else {
//            decodedBinarySecret = new String(Base64.getDecoder().decode(getSecretValueResult.getSecretBinary()).array());
//        }
//
//        // Your code goes here.
//    }
