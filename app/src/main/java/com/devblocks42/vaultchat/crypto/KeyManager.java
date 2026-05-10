package com.devblocks42.vaultchat.crypto;

import android.content.Context;
import android.os.Build;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.security.keystore.KeyStoreManager;
import android.util.Base64;

import java.security.InvalidAlgorithmParameterException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.spec.ECGenParameterSpec;

public class KeyManager {

    private KeyPair ecdsaKeyPair;
    private KeyPair ecdhKeyPair;


    public void generateECDSAKeyPair() throws NoSuchAlgorithmException, NoSuchProviderException, InvalidAlgorithmParameterException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(
                KeyProperties.KEY_ALGORITHM_EC,
                "AndroidKeyStore"
        );

        keyPairGenerator.initialize(new KeyGenParameterSpec.Builder(
                "ecdsa_key",
                KeyProperties.PURPOSE_SIGN | KeyProperties.PURPOSE_VERIFY
                ).setAlgorithmParameterSpec(
                        new ECGenParameterSpec("secp256r1")
                ).setDigests(
                        KeyProperties.DIGEST_SHA256
                ).build()
        );

        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        ecdsaKeyPair = keyPair;
    }

    public void generateECDHKeyPair() throws NoSuchAlgorithmException, InvalidAlgorithmParameterException, NoSuchProviderException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(
                KeyProperties.KEY_ALGORITHM_EC,
                "AndroidKeyStore"
        );

        KeyGenParameterSpec spec = new KeyGenParameterSpec.Builder(
                "ecdh_key",
                KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT
        ).setAlgorithmParameterSpec(
                    new ECGenParameterSpec("secp256r1")
                ).setDigests(KeyProperties.DIGEST_SHA256)
                .build();

        keyPairGenerator.initialize(spec);
        ecdhKeyPair = keyPairGenerator.generateKeyPair();
    }

    public String getECDSAPublicKey() {
        return Base64.encodeToString(
                ecdsaKeyPair.getPublic().getEncoded(),
                Base64.NO_WRAP
        );
    }
    public String getECDHPublicKey() {
        return Base64.encodeToString(
                ecdhKeyPair.getPublic().getEncoded(),
                Base64.NO_WRAP
        );
    }


}
